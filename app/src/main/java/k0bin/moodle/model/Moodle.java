package k0bin.moodle.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.model.api.Course;
import k0bin.moodle.model.api.MoodleApi;
import k0bin.moodle.model.api.PublicConfig;

public final class Moodle {
    @Nullable
    private MoodleApi moodle;
    private static final String SERVICE_KEY = "moodle_mobile_app";
    private static final String URL_SCHEME = "moodlemobile";
    private final double passport;

    @NonNull
    private final MoodleConfiguration configuration;

    private Moodle(@NonNull MoodleConfiguration configuration) {
        this.passport = new Random().nextDouble() * 1000.0;
        this.configuration = configuration;
        this.moodle = new MoodleApi(configuration.getSiteUrl());
    }

    public MoodleSetupStatus getStatus() {
        return configuration.getStatus();
    }

    public void setToken(@NonNull String token) {
        configuration.setToken(token);
    }

    public void setSiteUrl(@NonNull String siteUrl) {
        if (!configuration.getSiteUrl().equals(siteUrl)) {
            configuration.setSiteUrl(siteUrl);
            moodle = new MoodleApi(siteUrl);
        }
    }

    public void setPrivateToken(@NonNull String privateToken) {}

    @NonNull
    public final Single<LoginRequest> prepareLogin() {
        if (moodle == null) {
            throw new RuntimeException("Call setSiteUrl first.");
        }
        return Single
                .fromCallable(() -> {
                    final PublicConfig config;
                    synchronized (moodle) {
                        config = moodle.getPublicConfig();
                    }
                    if (config == null) {
                        throw new MoodleException("Cannot connect to Moodle.");
                    } else if (config.getEnableWebServices() != 1 || config.getEnableMobileWebService() != 1) {
                        throw new MoodleException("This Moodle instance does not support mobile apps.");
                    } else if (config.getMaintenanceEnabled() == 1) {
                        throw new MoodleException("Moodle is in maintenance mode.", config.getMaintenanceMessage());
                    }

                    String url = config.getLaunchUrl() + String.format(Locale.ENGLISH, "?service=%s&passport=%f&urlscheme=%s", SERVICE_KEY, passport, URL_SCHEME);
                    return (LoginRequest)(new LoginRequest.SsoLoginRequest(configuration.getSiteUrl(), url));
                })
                .retry(error -> error instanceof IOException)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public final Single<Long> loadUserId() throws MoodleException.InvalidTokenException {
        if (moodle == null) {
            throw new RuntimeException("Call setSiteUrl first.");
        }
        if (configuration.getToken().length() == 0) {
            throw new MoodleException.InvalidTokenException();
        }
        return Single
                .fromCallable(() -> moodle.getSiteInfo(configuration.getToken()).getUserId())
                .doOnSuccess(it -> configuration.setUserId(it))
                .retry(error -> error instanceof IOException)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    @NonNull
    public final Flowable<Course> loadCourses() throws MoodleException.InvalidTokenException {
        if (moodle == null) {
            throw new RuntimeException("Call setSiteUrl first.");
        }
        if (configuration.getToken().length() == 0) {
            throw new MoodleException.InvalidTokenException();
        }
        final Flowable<Course> flowable = Flowable.defer(() -> s -> {
            try {
                final List<Course> courses = moodle.getCourses(configuration.getToken(), configuration.getUserId());
                for (Course course : courses) {
                    s.onNext(course);
                }
                s.onComplete();
            } catch (IOException | MoodleException e) {
                s.onError(e);
            }
        });
        flowable
                .retry(error -> error instanceof IOException)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
        return flowable;
    }

    private static Moodle instance;
    public synchronized static Moodle getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new Moodle(MoodleConfiguration.load(context));
        }
        return instance;
    }
}

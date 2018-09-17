package k0bin.moodle.model;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.model.api.Course;
import k0bin.moodle.model.api.MoodleApi;
import k0bin.moodle.model.api.PublicConfig;

public final class Moodle {
    @NonNull
    private final String siteUrl;
    @NonNull
    private final MoodleApi moodle;
    private static final String SERVICE_KEY = "moodle_mobile_app";
    private static final String URL_SCHEME = "moodlemobile";
    private final double passport;

    @Nullable
    private String token = null;

    @Nullable
    private String privateToken = null;

    private long userId = 0L;

    private Moodle(@NonNull String siteUrl) {
        this.siteUrl = siteUrl;
        this.moodle = new MoodleApi(siteUrl);
        this.passport = new Random().nextDouble() * 1000.0;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    public void setPrivateToken(@NonNull String privateToken) {
        this.privateToken = token;
    }

    @NonNull
    public final Single<LoginRequest> prepareLogin() {
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
                    return (LoginRequest)(new LoginRequest.SsoLoginRequest(moodle.getSiteUrl(), url));
                })
                .retry(error -> error instanceof IOException)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @NonNull
    public final Single<Long> loadUserId() throws MoodleException.InvalidTokenException {
        if (token == null) {
            throw new MoodleException.InvalidTokenException();
        }
        return Single
                .fromCallable(() -> moodle.getSiteInfo(token).getUserId())
                .doOnSuccess(it -> userId = it)
                .retry(error -> error instanceof IOException)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    @SuppressLint("CheckResult")
    @NonNull
    public final Flowable<Course> loadCourses() throws MoodleException.InvalidTokenException {
        if (token == null) {
            throw new MoodleException.InvalidTokenException();
        }
        final Flowable<Course> flowable = Flowable.defer(() -> s -> {
            try {
                final List<Course> courses = moodle.getCourses(token, userId);
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

    @NonNull
    public String getSiteUrl() {
        return siteUrl;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    private static final Map<String, WeakReference<Moodle>> instances = new HashMap<>();
    public static Moodle getInstance(@NonNull String siteUrl) {
        synchronized (instances) {
            WeakReference<Moodle> moodleRef = instances.get(siteUrl);
            if (moodleRef != null && moodleRef.get() != null) {
                return moodleRef.get();
            }
            Moodle instance = new Moodle(siteUrl);
            instances.put(siteUrl, new WeakReference<>(instance));
            return instance;
        }
    }
}

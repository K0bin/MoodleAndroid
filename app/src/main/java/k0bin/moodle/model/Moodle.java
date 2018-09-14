package k0bin.moodle.model;

import android.support.annotation.NonNull;

import java.util.Locale;
import java.util.Random;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.model.api.MoodleApi;
import k0bin.moodle.model.api.PublicConfig;

public final class Moodle {
    private final MoodleApi moodle;
    private static final String SERVICE_KEY = "moodle_mobile_app";
    private static final String URL_SCHEME = "moodlemobile";
    private final double passport;

    private String token = null;

    public Moodle(@NonNull String siteUrl) {
        this.moodle = new MoodleApi(siteUrl);
        this.passport = new Random().nextDouble() * 1000.0;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    @NonNull
    public final Single<LoginRequest> prepareLogin() {
        return Single.fromCallable(() -> {
            final PublicConfig config;
            synchronized (moodle) {
                config = moodle.getPublicConfig();
            }
            if (config == null) {
                throw new Exception("Cannot connect to Moodle.");
            } else if (config.getEnableWebServices() != 1 || config.getEnableMobileWebService() != 1) {
                throw new Exception("This Moodle instance does not support mobile apps.");
            } else if (config.getMaintenanceEnabled() == 1) {
                throw new Exception("Moodle is in maintenance mode.");
            }

            String url = config.getLaunchUrl() + String.format(Locale.ENGLISH, "?service=%s&passport=%f&urlscheme=%s", SERVICE_KEY, passport, URL_SCHEME);
            return (LoginRequest)(new LoginRequest.SsoLoginRequest(moodle.getSiteUrl(), url));
        }).subscribeOn(Schedulers.io());
    }
}

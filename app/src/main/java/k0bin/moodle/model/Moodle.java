package k0bin.moodle.model;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import k0bin.moodle.model.api.MoodleApi;
import k0bin.moodle.model.api.PublicConfig;

public class Moodle {
    private final MoodleApi moodle;
    private List<Callback> callbacks = new ArrayList<>();
    private static final String SERVICE_KEY = "moodle_mobile_app";
    private static final String URL_SCHEME = "moodlemobile";
    private final double passport;

    private Moodle(@NonNull String siteUrl) {
        this.moodle = new MoodleApi(siteUrl);
        this.passport = new Random().nextDouble() * 1000.0;
    }

    @SuppressLint("StaticFieldLeak") //Moodle will not hold any ui references
    public void initialize() {
        new AsyncTask<Void, Void, Attempt<LoginRequest>>() {
            @Override
            protected Attempt<LoginRequest> doInBackground(Void... voids) {
                final PublicConfig config;
                synchronized (moodle) {
                    config = moodle.getPublicConfig();
                }
                if (config == null) {
                    return new Attempt<>(new Exception("Cannot connect to Moodle.")); //TODO Exceptions
                } else if (config.getEnableWebServices() != 1 || config.getEnableMobileWebService() != 1) {
                    return new Attempt<>(new Exception("This Moodle instance does not support mobile apps."));
                } else if (config.getMaintenanceEnabled() == 1) {
                    return new Attempt<>(new Exception("Moodle is in maintenance mode."));
                }

                String url = config.getLaunchUrl() + String.format(Locale.ENGLISH, "?service=%s&passport=%d&urlscheme=%s", SERVICE_KEY, passport, URL_SCHEME);
                return new Attempt<>(new LoginRequest(url));
            }

            @Override
            protected void onPostExecute(Attempt<LoginRequest> attempt) {
                super.onPostExecute(attempt);

                if (!attempt.isSuccessful()) {
                    for (Callback callback : callbacks) {
                        callback.onError(attempt.getError());
                    }
                } else {
                    for (Callback callback : callbacks) {
                        callback.onReady(attempt.getValue());
                    }
                }
            }
        }.execute();
    }

    public void registerCallback(Callback callback) {
        if (!callbacks.contains(callback)) {
            callbacks.add(callback);
        }
    }

    public void removeCallback(Callback callback) {
        callbacks.remove(callback);
    }

    public interface Callback {
        void onReady(@Nullable LoginRequest request);
        void onError(@NonNull Throwable throwable);
    }

    private static Moodle instance = null;
    public static Moodle getInstance(@NonNull String siteUrl) {
        synchronized (instance) {
            if (instance == null) {
                instance = new Moodle(siteUrl);
            }
            return instance;
        }
    }
}

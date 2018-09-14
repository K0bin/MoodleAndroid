package k0bin.moodle.model;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.util.RxSharedPreference;

public class MoodlePrefs {
    @NonNull
    private final SharedPreferences prefs;
    @NonNull
    private final RxSharedPreference rxPrefs;
    @NonNull
    private static final String MOODLE_SITE_PREF = "moodle_site";
    @NonNull
    private static final String MOODLE_TOKEN_PREF = "moodle_token";
    @NonNull
    private static final String SHARED_PREF_NAME = "moodle_conf";

    private MoodlePrefs(@NonNull Context context) {
        this.prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.rxPrefs = new RxSharedPreference(prefs);
    }

    public Single<String> getSite() {
        return rxPrefs.getString(MOODLE_SITE_PREF, "")
                .subscribeOn(Schedulers.io());
    }

    public Single<String> getToken() {
        return rxPrefs.getString(MOODLE_TOKEN_PREF, "")
                .subscribeOn(Schedulers.io());
    }

    @SuppressLint("ApplySharedPref") //Intentional, gets called on background thread using RxJava
    public void setPrefs(@NonNull String site, @NonNull String token) {
        prefs
                .edit()
                .putString(MOODLE_SITE_PREF, site)
                .putString(MOODLE_TOKEN_PREF, token)
                .commit();
    }

    @SuppressLint("ApplySharedPref") //Intentional, gets called on background thread using RxJava
    public void setSite(@NonNull String site) {
        prefs
                .edit()
                .putString(MOODLE_SITE_PREF, site)
                .commit();
    }

    @SuppressLint("ApplySharedPref") //Intentional, gets called on background thread using RxJava
    public void setToken(@NonNull String token) {
        prefs
                .edit()
                .putString(MOODLE_TOKEN_PREF, token)
                .commit();
    }

    private static MoodlePrefs instance;
    public static synchronized MoodlePrefs getInstance(@NonNull Context context) {
        if (instance == null) {
            instance = new MoodlePrefs(context.getApplicationContext());
        }
        return instance;
    }
}

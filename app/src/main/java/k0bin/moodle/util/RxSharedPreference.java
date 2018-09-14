package k0bin.moodle.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SyncAdapterType;
import android.support.annotation.IdRes;
import android.support.annotation.IntegerRes;
import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class RxSharedPreference {
    private final SharedPreferences prefs;
    public RxSharedPreference(SharedPreferences prefs) {
        this.prefs = prefs;
    }

    public Single<String> getString(@NonNull String key, @NonNull String defaultVal) {
        return Single
                .fromCallable(() -> { synchronized (prefs) {
                    return prefs.getString(key, defaultVal);
                }})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Integer> getInt(@NonNull String key, int defaultVal) {
        return Single
                .fromCallable(() -> { synchronized (prefs) {
                    return prefs.getInt(key, defaultVal);
                }})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Long> getLong(@NonNull String key, long defaultVal) {
        return Single
                .fromCallable(() -> { synchronized (prefs) {
                    return prefs.getLong(key, defaultVal);
                }})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Float> getFloat(@NonNull String key, float defaultVal) {
        return Single
                .fromCallable(() -> { synchronized (prefs) {
                    return prefs.getFloat(key, defaultVal);
                }})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }

    public Single<Boolean> getBoolean(@NonNull String key, boolean defaultVal) {
        return Single
                .fromCallable(() -> { synchronized (prefs) {
                    return prefs.getBoolean(key, defaultVal);
                }})
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread());
    }
}

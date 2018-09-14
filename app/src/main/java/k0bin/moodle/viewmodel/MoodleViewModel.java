package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Pair;

import io.reactivex.Single;
import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.MoodlePrefs;
import k0bin.moodle.model.MoodleStatus;
import k0bin.moodle.util.RxSharedPreference;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public abstract class MoodleViewModel extends AndroidViewModel {
    @NonNull
    private final Single<Moodle> moodle;
    @NonNull
    private final MoodlePrefs moodlePrefs;

    public MoodleViewModel(@NonNull Application application) {
        super(application);

        moodlePrefs = MoodlePrefs.getInstance(application);
        moodle = moodlePrefs
                .getSite()
                .zipWith(moodlePrefs.getToken(), (site, token) -> {
                    final Moodle moodle;
                    if (site != null && site.length() > 0) {
                        moodle = Moodle.getInstance(site);
                    } else {
                        moodle = null;
                    }
                    if (moodle != null && token != null && token.length() > 0) {
                        moodle.setToken(token);
                    }
                    return moodle;
                });
    }

    @NonNull
    protected Single<Moodle> getMoodle() {
        return moodle;
    }
}

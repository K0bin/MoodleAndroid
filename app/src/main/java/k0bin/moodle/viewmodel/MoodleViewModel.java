package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.MoodlePrefs;

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
                .observeOn(AndroidSchedulers.mainThread())
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

package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Single;
import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.MoodlePrefs;
import k0bin.moodle.model.MoodleStatus;

public class MainViewModel extends AndroidViewModel {
    @NonNull
    private final MutableLiveData<MoodleStatus> status = new MutableLiveData<>();
    @NonNull
    private final MoodlePrefs moodlePrefs;

    @SuppressLint("CheckResult")
    public MainViewModel(@NonNull Application application) {
        super(application);

        this.moodlePrefs = MoodlePrefs.getInstance(application);
        moodlePrefs
                .getSite()
                .zipWith(moodlePrefs.getToken(), (site, token) -> {
                    final MoodleStatus status;
                    if (site == null || site.length() == 0) {
                        status = MoodleStatus.NEEDS_SETUP;
                    } else if (token == null || token.length() == 0) {
                        status = MoodleStatus.NEEDS_LOGIN;
                    } else {
                        status = MoodleStatus.LOGGED_IN;
                    }
                    return status;
                })
                .subscribe(status::setValue);
    }

    @NonNull
    public LiveData<MoodleStatus> getStatus() {
        return status;
    }
}

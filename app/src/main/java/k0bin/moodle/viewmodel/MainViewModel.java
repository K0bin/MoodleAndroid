package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

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
                .map(site -> site != null && site.length() != 0 ? MoodleStatus.DONE : MoodleStatus.NEEDS_SETUP)
                .zipWith(moodlePrefs.getToken(), (status, token) -> {
                    if (status != MoodleStatus.DONE) {
                        return status;
                    }
                    return token != null && token.length() != 0 ? MoodleStatus.DONE : MoodleStatus.NEEDS_LOGIN;
                })
                .zipWith(moodlePrefs.getUserId(), (status, userId) -> {
                    if (status != MoodleStatus.DONE) {
                        return status;
                    }
                    return userId != 0 ? MoodleStatus.DONE : MoodleStatus.NEEDS_LOGIN;
                })
                .subscribe(status::setValue);
    }

    @NonNull
    public LiveData<MoodleStatus> getStatus() {
        return status;
    }
}

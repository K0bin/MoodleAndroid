package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import k0bin.moodle.model.MoodleSetupStatus;

public class MainViewModel extends MoodleViewModel {
    @NonNull
    private final MutableLiveData<MoodleSetupStatus> status = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public MainViewModel(@NonNull Application application) {
        super(application);

        getMoodle()
                .map(moodle -> moodle.getStatus())
                .subscribe(status::setValue);
    }

    @NonNull
    public LiveData<MoodleSetupStatus> getStatus() {
        return status;
    }
}

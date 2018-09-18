package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.MoodleSetupStatus;

public class MainViewModel extends AndroidViewModel {
    @NonNull
    private final MutableLiveData<MoodleSetupStatus> status = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public MainViewModel(@NonNull Application application) {
        super(application);

        Single.fromCallable(() -> Moodle.getInstance(application))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .map(moodle -> moodle.getStatus())
                .subscribe(status::setValue);
    }

    @NonNull
    public LiveData<MoodleSetupStatus> getStatus() {
        return status;
    }
}

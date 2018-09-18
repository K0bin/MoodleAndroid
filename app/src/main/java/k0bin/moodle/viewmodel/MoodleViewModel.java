package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.model.Moodle;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public abstract class MoodleViewModel extends AndroidViewModel {
    @Nullable
    private Moodle moodle;

    public MoodleViewModel(@NonNull Application application) {
        super(application);
    }

    @NonNull
    protected Single<Moodle> getMoodle() {
        if (moodle != null) {
            return Single.just(moodle);
        } else {
            return Single.fromCallable(() -> Moodle.getInstance(getApplication()))
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSuccess(it -> moodle = it);
        }
    }
}

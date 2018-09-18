package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.List;

import io.reactivex.schedulers.Schedulers;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.api.Course;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public abstract class MoodleViewModel extends AndroidViewModel {
    @Nullable
    private Moodle moodle;

    @NonNull
    private final MutableLiveData<List<Course>> courses = new MutableLiveData<>();

    public MoodleViewModel(@NonNull Application application) {
        super(application);

        getMoodle()
                .toFlowable()
                .observeOn(Schedulers.io())
                .flatMap(Moodle::loadCourses)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(courses::setValue);
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

    @NonNull
    public LiveData<List<Course>> getCourses() {
        return courses;
    }
}

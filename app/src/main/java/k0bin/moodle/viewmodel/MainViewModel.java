package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.io.IOException;
import java.util.List;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.MoodleSetupStatus;
import k0bin.moodle.model.api.Course;

public class MainViewModel extends MoodleViewModel {
    @NonNull
    private final MutableLiveData<MoodleSetupStatus> status = new MutableLiveData<>();

    @NonNull
    private final MutableLiveData<List<Course>> courses = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public MainViewModel(@NonNull Application application) {
        super(application);

        getMoodle()
                .map(moodle -> moodle.getStatus())
                .subscribe(status::setValue);

        getMoodle()
                .toFlowable()
                .observeOn(Schedulers.io())
                .flatMap(Moodle::loadCourses)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .retry(it -> it instanceof IOException)
                .subscribe(courses::setValue);
    }

    @NonNull
    public LiveData<MoodleSetupStatus> getStatus() {
        return status;
    }

    @NonNull
    public LiveData<List<Course>> getCourses() {
        return courses;
    }
}

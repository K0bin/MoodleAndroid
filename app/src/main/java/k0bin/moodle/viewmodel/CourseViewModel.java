package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import io.reactivex.android.schedulers.AndroidSchedulers;
import k0bin.moodle.model.api.CourseContents;

public class CourseViewModel extends MoodleViewModel {
    @NonNull
    private final MutableLiveData<CourseContents> courseContents = new MutableLiveData<>();
    private long courseId = 0;

    public CourseViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public void setCourseId(long courseId) {
        if (this.courseId != courseId) {
            this.courseId = courseId;

            getMoodle()
                    .flatMap(it -> it.loadCourseContents(courseId))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(courseContents::setValue);
        }
    }
}

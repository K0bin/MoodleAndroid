package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import java.util.List;

import io.reactivex.Maybe;
import io.reactivex.schedulers.Schedulers;
import java9.util.Optional;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.MoodlePrefs;
import k0bin.moodle.model.api.Course;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public abstract class MoodleViewModel extends AndroidViewModel {
    @NonNull
    private final Single<Optional<Moodle>> moodle;

    @NonNull
    private final MutableLiveData<List<Course>> courses = new MutableLiveData<>();

    @NonNull
    private final MoodlePrefs moodlePrefs;

    public MoodleViewModel(@NonNull Application application) {
        super(application);

        moodlePrefs = MoodlePrefs.getInstance(application);
        moodle = moodlePrefs
                .getSite()
                .zipWith(moodlePrefs.getToken(), (site, token) -> {
                    final Optional<Moodle> moodle;
                    if (site != null && site.length() > 0) {
                        moodle = Optional.of(Moodle.getInstance(site));
                    } else {
                        moodle = Optional.empty();
                    }
                    if (moodle.isPresent() && token != null && token.length() > 0) {
                        moodle.get().setToken(token);
                    }
                    return moodle;
                })
                .zipWith(moodlePrefs.getUserId(), (moodle, userId) -> {
                    moodle.ifPresent(it -> it.setUserId(userId));
                    return moodle;
                });

        getMoodle()
                .toFlowable()
                .observeOn(Schedulers.io())
                .flatMap(Moodle::loadCourses)
                .toList()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(courses::setValue);
    }

    @NonNull
    protected Maybe<Moodle> getMoodle() {
        return moodle
                .filter(Optional::isPresent)
                .map(Optional::get);
    }

    @NonNull
    public LiveData<List<Course>> getCourses() {
        return courses;
    }
}

package k0bin.moodle.viewmodel;

import android.app.Application;
import android.support.annotation.NonNull;

public class HomeViewModel extends MoodleViewModel {
    public HomeViewModel(@NonNull Application application) {
        super(application);
        getMoodle().subscribe(it -> {

        });
    }
}

package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.util.Pair;

import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.MoodleStatus;
import k0bin.moodle.util.RxSharedPreference;

@SuppressWarnings("ResultOfMethodCallIgnored")
@SuppressLint("CheckResult")
public abstract class BaseViewModel extends AndroidViewModel {
    private Moodle moodle;
    private RxSharedPreference prefs;
    private static final String MOODLE_SITE_PREF = "moodle_site";
    private static final String MOODLE_TOKEN_PREF = "moodle_token";
    private static final String SHARED_PREF_NAME = "moodle_conf";

    private final MutableLiveData<MoodleStatus> status = new MutableLiveData<>();

    public BaseViewModel(@NonNull Application application) {
        super(application);

        SharedPreferences prefs = application.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        this.prefs = new RxSharedPreference(prefs);
        this.prefs.getString(MOODLE_SITE_PREF, "")
                .zipWith(this.prefs.getString(MOODLE_TOKEN_PREF, ""), Pair::create)
                .subscribe(pair -> {
                    if ("".equals(pair.first)) {
                        status.setValue(MoodleStatus.NEEDS_SETUP);
                    } else if ("".equals(pair.second)) {
                        status.setValue(MoodleStatus.NEEDS_LOGIN);
                    } else {
                        status.setValue(MoodleStatus.LOGGED_IN);
                        moodle = new Moodle(pair.first);
                        moodle.setToken(pair.second);
                    }
                });
    }

    protected Moodle getMoodle() {
        return moodle;
    }

    public LiveData<MoodleStatus> getStatus() {
        return status;
    }
}

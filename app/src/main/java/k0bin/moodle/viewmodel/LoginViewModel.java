package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Base64;
import android.util.Log;

import java.io.UnsupportedEncodingException;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.model.LoginRequest;
import k0bin.moodle.model.Moodle;
import k0bin.moodle.model.MoodlePrefs;

public class LoginViewModel extends MoodleViewModel {
    @NonNull
    private final MutableLiveData<LoginRequest> loginRequest = new MutableLiveData<>();

    @NonNull
    private final MoodlePrefs prefs;

    @NonNull
    private final MutableLiveData<Boolean> isDone = new MutableLiveData<>();

    @SuppressLint("CheckResult")
    public LoginViewModel(@NonNull Application application) {
        super(application);

        prefs = MoodlePrefs.getInstance(application);
        isDone.setValue(false);

        getMoodle()
                .subscribeOn(Schedulers.io())
                .flatMapSingleElement(Moodle::prepareLogin)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(it -> {
                    Log.e("LoginVM", it.getMessage());
                })
                .subscribe(it -> loginRequest.setValue(it));
    }

    @NonNull
    public LiveData<LoginRequest> getLoginRequest() {
        return loginRequest;
    }

    @NonNull
    public LiveData<Boolean> getIsDone() {
        return isDone;
    }

    public void setTokenUrl(@NonNull String url) {
        if (url.startsWith("moodlemobile://token=")) {
            try {
                String rawToken = url.substring("moodlemobile://token=".length());
                rawToken = rawToken.replaceAll("[#=]", "");
                byte[] data = Base64.decode(rawToken, Base64.DEFAULT);
                String token = new String(data, "UTF-8");
                String[] tokenParts = token.split(":::");
                setToken(tokenParts[1]);
            } catch (UnsupportedEncodingException e) {}
        }
    }

    @SuppressLint("CheckResult")
    public void setToken(@NonNull String token) {
        prefs.setToken(token);
        getMoodle()
                .flatMapSingleElement(Moodle::getUserId)
                .observeOn(Schedulers.io())
                .doOnSuccess(prefs::setUserId)
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    isDone.setValue(false);
                    Log.e("LoginVM", "Login failed with error: "+error.getMessage());
                })
                .onErrorReturn(error -> 0L)
                .subscribe(id -> {
                    if (id == 0L) {
                        return;
                    }
                    isDone.setValue(true);
                });
    }
}

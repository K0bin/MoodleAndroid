package k0bin.moodle.viewmodel;

import android.annotation.SuppressLint;
import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

import java.net.MalformedURLException;
import java.net.URL;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import k0bin.moodle.model.LoginRequest;
import k0bin.moodle.model.MoodlePrefs;
import k0bin.moodle.util.Attempt;

public class SetupViewModel extends MoodleViewModel {
    @NonNull
    private String siteUrl = "";

    public SetupViewModel(@NonNull Application application) {
        super(application);
    }

    @SuppressLint("CheckResult")
    public LiveData<Attempt<LoginRequest>> setSiteUrl(@NonNull String siteUrl) {
        final MutableLiveData<Attempt<LoginRequest>> result = new MutableLiveData<>();
        if (this.siteUrl.equals(siteUrl) || siteUrl.length() < "https".length()) {
            result.setValue(new Attempt<>(new MalformedURLException()));
            return result;
        }

        this.siteUrl = siteUrl;
        Single<LoginRequest> loginRequestMaybe;
        if (!siteUrl.startsWith("https") && !siteUrl.startsWith("http")) {
            loginRequestMaybe = prepareForLogin("https://" + siteUrl);
        } else if (!siteUrl.startsWith("https")) {
            loginRequestMaybe = prepareForLogin(siteUrl.replace("http://", "https://"));
        } else {
            loginRequestMaybe = prepareForLogin(siteUrl);
        }
        loginRequestMaybe
                .observeOn(AndroidSchedulers.mainThread())
                .doOnError(error -> {
                    if (siteUrl.startsWith("https://")) {
                        prepareForLogin(siteUrl.replace("http://", "https://"));
                    } else {
                        result.setValue(new Attempt<>(new MalformedURLException()));
                    }
                })
                .observeOn(Schedulers.io())
                .doOnSuccess(request -> MoodlePrefs.getInstance(getApplication()).setPrefs(request.getMoodleSiteUrl(), ""))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(request -> result.setValue(new Attempt<>(request)));
        return result;
    }

    @SuppressLint("CheckResult")
    private Single<LoginRequest> prepareForLogin(String siteUrl) {
        try {
            //Check if it's a proper siteUrl
            final URL parsedUrl = new URL(siteUrl);

            if (parsedUrl.getHost().length() == 0) {
                return Single.error(new MalformedURLException());
            }

            initializeMoodle(siteUrl);
            return getMoodle().prepareLogin();
        } catch (MalformedURLException e) {
            return Single.error(e);
        }
    }
}

package k0bin.moodle.view.fragment;

import android.annotation.SuppressLint;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import k0bin.moodle.model.Authentication;
import k0bin.moodle.model.MoodleStatus;
import k0bin.moodle.model.NavigationEvent;
import k0bin.moodle.viewmodel.BaseViewModel;

public abstract class BaseFragment extends Fragment {
    @NonNull
    protected abstract BaseViewModel getViewModel();

    private Disposable loginRequest;

    @SuppressLint("CheckResult")
    protected final void initialize() {
        BaseViewModel viewModel = getViewModel();
        viewModel.getStatus().observe(this, it -> {
            if (it == MoodleStatus.NEEDS_LOGIN) {
                
            }
        });
    }

    protected final void login(Authentication request) {

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        loginRequest.dispose();
    }
}

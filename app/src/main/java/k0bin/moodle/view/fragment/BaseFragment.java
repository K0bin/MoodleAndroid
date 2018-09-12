package k0bin.moodle.view.fragment;

import android.support.v4.app.Fragment;

import k0bin.moodle.viewmodel.BaseViewModel;

public abstract class BaseFragment extends Fragment {
    protected abstract BaseViewModel getViewModel();
    protected final void login() {

    }
}

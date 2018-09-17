package k0bin.moodle.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import k0bin.moodle.R;
import k0bin.moodle.viewmodel.HomeViewModel;
import k0bin.moodle.viewmodel.MoodleViewModel;

public class HomeFragment extends DrawerFragment {

    private HomeViewModel viewModel;

    public HomeFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @NonNull
    @Override
    protected MoodleViewModel getViewModel() {
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(HomeViewModel.class);
        }
        return viewModel;
    }
}

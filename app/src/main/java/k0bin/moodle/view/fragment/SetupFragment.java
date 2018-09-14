package k0bin.moodle.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import k0bin.moodle.R;
import k0bin.moodle.viewmodel.BaseViewModel;
import k0bin.moodle.viewmodel.SetupViewModel;

public final class SetupFragment extends BaseFragment {
    private SetupViewModel viewModel;

    private FloatingActionButton doneButton;
    private EditText urlEditText;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        urlEditText = view.findViewById(R.id.siteUrl);
        doneButton = view.findViewById(R.id.doneButton);
        doneButton.setOnClickListener(it -> getViewModel()
                .setSiteUrl(urlEditText.getText().toString())
                .observe(this, requestAttempt -> {
                    if (requestAttempt.wasSuccessful()) {
                        //Navigate
                        Log.d("SetupFrag", "Navigate");
                    } else {
                        Log.d("SetupFrag", "Error: "+requestAttempt.getError().getMessage());
                    }
                }));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        doneButton = null;
        urlEditText = null;
    }

    @NonNull
    @Override
    protected SetupViewModel getViewModel() {
        if (viewModel == null) {
            viewModel = ViewModelProviders.of(this).get(SetupViewModel.class);
        }
        return viewModel;
    }
}

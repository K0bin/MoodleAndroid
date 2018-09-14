package k0bin.moodle.view.fragment;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import k0bin.moodle.R;
import k0bin.moodle.viewmodel.SetupViewModel;

public final class SetupFragment extends Fragment {
    private SetupViewModel viewModel;

    private FloatingActionButton doneButton;
    private EditText urlEditText;

    private NavController navController;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        viewModel = ViewModelProviders.of(this).get(SetupViewModel.class);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);

        urlEditText = view.findViewById(R.id.site_url);
        doneButton = view.findViewById(R.id.done_button);
        doneButton.setOnClickListener(it -> viewModel
                .setSiteUrl(urlEditText.getText().toString())
                .observe(this, requestAttempt -> {
                    if (requestAttempt.wasSuccessful()) {
                        //Navigate
                        navController.navigate(R.id.action_setupLogin);
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
}

package k0bin.moodle.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import k0bin.moodle.R;
import k0bin.moodle.model.MoodleSetupStatus;
import k0bin.moodle.view.MainActivity;
import k0bin.moodle.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MainActivity.BackFragment {

    @Nullable
    private NavController navController;

    @Nullable
    private MainViewModel viewModel;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        viewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        viewModel.getStatus().observe(this, it -> {
            if (navController == null) {
                return;
            }
            final NavDestination currentDestination = navController.getCurrentDestination();
            if (MoodleSetupStatus.NEEDS_SETUP.equals(it) && (!(currentDestination instanceof FragmentNavigator.Destination) || ((FragmentNavigator.Destination)currentDestination).getFragmentClass() != SetupFragment.class)) {
                navController.navigate(R.id.action_setup);
            } else if (MoodleSetupStatus.NEEDS_LOGIN.equals(it) && (!(currentDestination instanceof FragmentNavigator.Destination) || ((FragmentNavigator.Destination)currentDestination).getFragmentClass() != LoginFragment.class)) {
                navController.navigate(R.id.action_login);
            }
        });
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_main, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(view);
    }

    @Override
    public boolean goBack() {
        final NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.childNavHost);
        final NavController navController = navHostFragment.getNavController();
        NavDestination currentTop = navController.getCurrentDestination();
        if (currentTop instanceof FragmentNavigator.Destination) {
            Class<? extends Fragment> fragmentClass = ((FragmentNavigator.Destination) currentTop).getFragmentClass();
            for (Fragment fragment : navHostFragment.getChildFragmentManager().getFragments()) {
                if (fragment.getClass().equals(fragmentClass) && fragment instanceof MainActivity.BackFragment) {
                    return ((MainActivity.BackFragment) fragment).goBack();
                }
            }
        }
        return false;
    }
}

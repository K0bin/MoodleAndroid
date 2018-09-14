package k0bin.moodle.view.fragment;

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
import io.reactivex.disposables.Disposable;
import k0bin.moodle.R;
import k0bin.moodle.model.MoodleStatus;
import k0bin.moodle.viewmodel.BaseViewModel;
import k0bin.moodle.viewmodel.HomeViewModel;

public abstract class BaseFragment extends Fragment {
    private NavController navController;

    @NonNull
    protected abstract BaseViewModel getViewModel();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        getViewModel().getStatus().observe(this, it -> {
            final NavDestination currentDestination = navController.getCurrentDestination();
            if (MoodleStatus.NEEDS_SETUP.equals(it) && (!(currentDestination instanceof FragmentNavigator.Destination) || ((FragmentNavigator.Destination)currentDestination).getFragmentClass() != SetupFragment.class)) {
                navController.navigate(R.id.action_setup);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        navController = Navigation.findNavController(getView());
    }
}

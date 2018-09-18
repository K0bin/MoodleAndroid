package k0bin.moodle.view;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import k0bin.moodle.R;

public class MainActivity extends AppCompatActivity {

    private NavController navController;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        navController = Navigation.findNavController(this, R.id.navHost);
    }


    @Override
    public boolean onSupportNavigateUp() {
        return navController.navigateUp();
    }

    @Override
    public void onBackPressed() {
        final NavHostFragment navHostFragment = (NavHostFragment) getSupportFragmentManager().findFragmentById(R.id.navHost);
        final NavController navController = navHostFragment.getNavController();
        NavDestination currentTop = navController.getCurrentDestination();
        if (currentTop instanceof FragmentNavigator.Destination) {
            Class<? extends Fragment> fragmentClass = ((FragmentNavigator.Destination) currentTop).getFragmentClass();
            for (Fragment fragment : navHostFragment.getChildFragmentManager().getFragments()) {
                if (fragment.getClass().equals(fragmentClass) && fragment instanceof MainActivity.BackFragment) {
                    if (((MainActivity.BackFragment) fragment).goBack()) {
                        return;
                    }
                }
            }
        }

        super.onBackPressed();
    }

    public interface BackFragment {
        boolean goBack();
    }
}

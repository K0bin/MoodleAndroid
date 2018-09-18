package k0bin.moodle.view.fragment;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.bottomappbar.BottomAppBar;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.fragment.FragmentNavigator;
import androidx.navigation.fragment.NavHostFragment;
import k0bin.moodle.R;
import k0bin.moodle.model.MoodleSetupStatus;
import k0bin.moodle.view.MainActivity;
import k0bin.moodle.view.adapter.DrawerAdapter;
import k0bin.moodle.viewmodel.MainViewModel;

/**
 * A simple {@link Fragment} subclass.
 */
public class MainFragment extends Fragment implements MainActivity.BackFragment {

    @Nullable
    private NavController navController;

    @Nullable
    private MainViewModel viewModel;

    @Nullable
    private RecyclerView drawerRecycler;
    @Nullable
    private DrawerAdapter drawerAdapter;

    private int overlayColor = 0;
    @Nullable
    private FrameLayout overlay;
    @Nullable
    private BottomSheetBehavior drawerBehavior;
    @Nullable
    private ObjectAnimator overlayAnimator;
    private int animationDuration;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        overlayColor = ContextCompat.getColor(requireContext(), R.color.drawerOverlay);
        animationDuration = requireContext().getResources().getInteger(android.R.integer.config_shortAnimTime);

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

        //Navigation drawer
        drawerRecycler = view.findViewById(R.id.drawerRecycler);
        drawerRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        drawerAdapter = new DrawerAdapter();
        drawerRecycler.setAdapter(drawerAdapter);

        MaterialCardView drawer = view.findViewById(R.id.drawerSheet);
        int statusBarHeight = getStatusBarHeight();

        final int initialMargin = ((FrameLayout.LayoutParams)drawerRecycler.getLayoutParams()).topMargin;
        final float roundRadius = drawer.getRadius();

        final ObjectAnimator cornerAnimator = ObjectAnimator.ofFloat(drawer, "radius", roundRadius, 0f);
        cornerAnimator.setDuration(animationDuration);

        final ValueAnimator marginAnimator = ValueAnimator.ofFloat((float)initialMargin, (float) statusBarHeight);
        marginAnimator.setDuration(animationDuration);
        marginAnimator.addUpdateListener(animator -> {
            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams)drawerRecycler.getLayoutParams();
            params.topMargin = (int)((float)animator.getAnimatedValue());
            drawerRecycler.setLayoutParams(params);
        });

        drawerBehavior = BottomSheetBehavior.from(drawer);
        drawerBehavior.setBottomSheetCallback(new BottomSheetBehavior.BottomSheetCallback() {
            private int oldState = BottomSheetBehavior.STATE_HIDDEN;

            @Override
            public void onStateChanged(@NonNull View view, int state) {
                switch (state) {
                    case BottomSheetBehavior.STATE_HIDDEN:
                        overlayAnimator.reverse();
                        break;

                    case BottomSheetBehavior.STATE_EXPANDED:
                        cornerAnimator.start();
                        marginAnimator.start();
                        break;

                    default:
                        if (oldState == BottomSheetBehavior.STATE_EXPANDED) {
                            cornerAnimator.reverse();
                            marginAnimator.reverse();
                        } else if (oldState == BottomSheetBehavior.STATE_HIDDEN) {
                            overlayAnimator.start();
                        }
                        break;
                }
                oldState = state;
            }

            @Override
            public void onSlide(@NonNull View view, float v) {}
        });
        drawerBehavior.setState(BottomSheetBehavior.STATE_HIDDEN);

        overlay = view.findViewById(R.id.overlay);
        overlay.setOnClickListener(v -> setDrawerVisibility(false));

        overlayAnimator = ObjectAnimator.ofArgb(overlay, "backgroundColor", 0, overlayColor);
        overlayAnimator.setDuration(animationDuration);
        overlayAnimator.addListener(new Animator.AnimatorListener() {
            private boolean isReversed = false;

            @Override
            public void onAnimationStart(Animator animator) {
                isReversed = overlay.getVisibility() == View.VISIBLE;
                if (!isReversed) {
                    overlay.setVisibility(View.VISIBLE);
                }
            }
            @Override
            public void onAnimationEnd(Animator animator) {
                if (isReversed) {
                    overlay.setVisibility(View.GONE);
                }
            }
            @Override
            public void onAnimationCancel(Animator animator) {}
            @Override
            public void onAnimationRepeat(Animator animator) {}
        });
        //overlayAnimator.start();

        final BottomAppBar appBar = view.findViewById(R.id.bottomAppBar);
        appBar.setNavigationOnClickListener(v -> setDrawerVisibility(true));

        //Drawer content
        viewModel.getCourses().observe(this, courses -> {
            drawerAdapter.submitList(courses);
        });

        //Nav controller
        navController = Navigation.findNavController(view);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        drawerRecycler = null;
        drawerAdapter = null;
        drawerBehavior = null;
        overlay = null;
        overlayAnimator = null;
        navController = null;
    }

    private void setDrawerVisibility(boolean isVisible) {
        int newState = isVisible ? BottomSheetBehavior.STATE_COLLAPSED : BottomSheetBehavior.STATE_HIDDEN;
        int oldState = drawerBehavior.getState();
        if (oldState == newState) {
            return;
        }
        drawerBehavior.setState(newState);
    }

    private int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    @Override
    public boolean goBack() {
        if (drawerBehavior != null && drawerBehavior.getState() != BottomSheetBehavior.STATE_HIDDEN) {
            setDrawerVisibility(false);
            return true;
        }

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

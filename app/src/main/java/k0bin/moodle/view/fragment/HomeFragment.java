package k0bin.moodle.view.fragment;


import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.card.MaterialCardView;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import k0bin.moodle.R;
import k0bin.moodle.viewmodel.HomeViewModel;
import k0bin.moodle.viewmodel.MoodleViewModel;

public class HomeFragment extends DrawerFragment {

    private HomeViewModel viewModel;
    private MaterialCardView drawerSheet;
    private TextView title;

    public HomeFragment() {}

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerSheet = view.findViewById(R.id.drawerSheet);
        title = view.findViewById(R.id.title);

        view.post(() -> {
            int height = view.getHeight();

            int titleBottom = title.getBottom();
            final ViewGroup.MarginLayoutParams titleParams = (ViewGroup.MarginLayoutParams)title.getLayoutParams();
            titleBottom += titleParams.bottomMargin;

            CoordinatorLayout.LayoutParams params = (CoordinatorLayout.LayoutParams)drawerSheet.getLayoutParams();
            BottomSheetBehavior behavior = (BottomSheetBehavior)params.getBehavior();
            behavior.setState(BottomSheetBehavior.STATE_COLLAPSED);
            behavior.setPeekHeight(height - titleBottom - getStatusBarHeight());
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        drawerSheet = null;
        title = null;
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

package k0bin.moodle.view.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import k0bin.moodle.R;
import k0bin.moodle.view.adapter.DrawerAdapter;
import k0bin.moodle.viewmodel.MoodleViewModel;

public abstract class DrawerFragment extends Fragment {

    @Nullable
    private RecyclerView drawerRecycler;
    @Nullable
    private DrawerAdapter drawerAdapter;


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerRecycler = view.findViewById(R.id.drawerRecycler);
        drawerRecycler.setLayoutManager(new LinearLayoutManager(requireContext()));
        drawerAdapter = new DrawerAdapter();
        drawerRecycler.setAdapter(drawerAdapter);

        getViewModel().getCourses().observe(this, courses -> {
            drawerAdapter.submitList(courses);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        drawerRecycler = null;
        drawerAdapter = null;
    }

    @NonNull
    protected abstract MoodleViewModel getViewModel();
}

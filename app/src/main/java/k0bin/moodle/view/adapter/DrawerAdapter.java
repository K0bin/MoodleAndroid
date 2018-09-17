package k0bin.moodle.view.adapter;

import android.support.annotation.NonNull;
import android.support.v7.recyclerview.extensions.ListAdapter;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import k0bin.moodle.R;
import k0bin.moodle.model.api.Course;

public class DrawerAdapter extends ListAdapter<Course, DrawerAdapter.ViewHolder> {
    @NonNull
    private static final DiffCallback DIFF_CALLBACK = new DiffCallback();

    public DrawerAdapter() {
        super(DIFF_CALLBACK);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        return new ViewHolder(
                LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_drawer_course, viewGroup, false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        viewHolder.bindCourse(getItem(position));
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        private final TextView courseName;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            this.courseName = itemView.findViewById(R.id.course_name);
        }

        public void bindCourse(@NonNull Course course) {
            courseName.setText(course.getShortName());
        }
    }

    public static class DiffCallback extends DiffUtil.ItemCallback<Course> {

        @Override
        public boolean areItemsTheSame(@NonNull Course course, @NonNull Course course2) {
            return course.getId() == course2.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Course course, @NonNull Course course2) {
            return course.equals(course2);
        }
    }
}

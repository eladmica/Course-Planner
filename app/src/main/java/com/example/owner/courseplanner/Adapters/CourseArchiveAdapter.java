package com.example.owner.courseplanner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.owner.courseplanner.Model.Course;
import com.example.owner.courseplanner.Model.CoursesDataManager;
import com.example.owner.courseplanner.R;

/**
 * Adapter for the CourseArchive Activity's ExpandableListView
 */
public class CourseArchiveAdapter extends CourseAdapter {
    private Context context;
    private static CoursesDataManager coursesDataManager = CoursesDataManager.getInstance();

    /**
     * Constructor creates an adapter using super
     * @param context   The context
     */
    public CourseArchiveAdapter(Context context) {
        super(context, coursesDataManager.getAllCourses());
        this.context = context;
    }


    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.choose_course, viewGroup, false);

            Button buttonNotNeeded = (ToggleButton) view.findViewById(R.id.course_button);
            buttonNotNeeded.setVisibility(View.INVISIBLE);
            RelativeLayout rl = (RelativeLayout) view.findViewById(R.id.id_name_layout);
            RelativeLayout.LayoutParams relativeParams = (RelativeLayout.LayoutParams) rl.getLayoutParams();
            relativeParams.setMargins(dipToPixels(50), 0, dipToPixels(15), 0);  // left, top, right, bottom

            holder = new ViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        final Course course = (Course) super.getGroup(groupPosition);
        String currentId = COURSES_SUBJECT + " " + course.getId();
        holder.id.setText(currentId);
        holder.name.setText(course.getName());


        if (course.getWarnings() != null)
            holder.warnings.setVisibility(View.VISIBLE);
        else
            holder.warnings.setVisibility(View.INVISIBLE);

        // Warnings listener:
        holder.warnings.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, course.getWarnings(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });

        int imageResourceId = isExpanded ? R.drawable.up_arrow_green : R.drawable.down_arrow_green;
        holder.expandIcon.setImageResource(imageResourceId);
        holder.expandIcon.setVisibility(View.VISIBLE);

        return view;
    }

    /**
     *  Convert dip into pixels
     * @param dip   dip to be converted
     * @return      dis as pixels
     */
    private int dipToPixels(int dip) {
        Resources r = context.getResources();
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, r.getDisplayMetrics());
    }

    /**
     * View holder class for the course
     */
    private static class ViewHolder {
        TextView id;
        TextView name;
        ImageView expandIcon;
        ImageView warnings;

        private ViewHolder(View view) {
            id = (TextView) view.findViewById(R.id.course_id);
            name = (TextView) view.findViewById(R.id.course_name);
            expandIcon = (ImageView) view.findViewById(R.id.expandable_icon);
            warnings = (ImageView) view.findViewById(R.id.choose_course_warning);
        }
    }
}

package com.example.owner.courseplanner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.owner.courseplanner.Model.Course;
import com.example.owner.courseplanner.Model.CoursesDataManager;
import com.example.owner.courseplanner.Model.Student;
import com.example.owner.courseplanner.R;

/**
 * Adapter for the ChooseCourses Activity's ExpandableListView
 */
public class ChooseCourseAdapter extends CourseAdapter {
    private Context context;
    private static Student student = Student.getInstance();
    private static CoursesDataManager coursesDataManager = CoursesDataManager.getInstance();

    /**
     * Constructor creates an adapter using super
     * @param context   The context
     */
    public ChooseCourseAdapter(Context context) {
        super(context, student.getCoursesCanTake(coursesDataManager.getAllCourses()));
        this.context = context;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        ViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.choose_course, viewGroup, false);
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
        holder.button.setTag(course);

        // Set up warnings, if applicable:
        if (course.getWarnings() != null) {
            holder.warnings.setVisibility(View.VISIBLE);
        } else {
            holder.warnings.setVisibility(View.INVISIBLE);
        }
        holder.warnings.setOnClickListener(new ImageView.OnClickListener() {

            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(context, course.getWarnings(), Toast.LENGTH_LONG);
                toast.setGravity(Gravity.CENTER, 0, 0);
                toast.show();
            }
        });


        // Course chosen Listener:
        holder.button.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                ToggleButton tb = (ToggleButton) compoundButton;
                if (tb.isChecked()) {
                    course.setSelectedAsChosenCourse(true);
                    buttonToggleOn(tb, course);
                }
                else {
                    course.setSelectedAsChosenCourse(false);
                    buttonToggleOff(tb, course);
                }
            }
        });

        if (course.isSelectedAsChosenCourse())
            buttonToggleOn(holder.button, course);
        else
            buttonToggleOff(holder.button, course);

        int imageResourceId = isExpanded ? R.drawable.up_arrow_green : R.drawable.down_arrow_green;
        holder.expandIcon.setImageResource(imageResourceId);
        holder.expandIcon.setVisibility(View.VISIBLE);

        return view;
    }


    /**
     * Course was selected
     * @param b     Button that was selected
     * @param c     Course that was selected
     */
    private void buttonToggleOn(ToggleButton b, Course c) {
        b.setBackgroundColor(context.getResources().getColor(R.color.red_custom));
        b.setText("-");
        b.setChecked(true);
        student.addChosenCourses(c);
    }

    /**
     * Course was deselected
     * @param b     Button that was deselected
     * @param c     Course that was deselected
     */
    private void buttonToggleOff(ToggleButton b, Course c) {
        b.setBackgroundColor(context.getResources().getColor(R.color.green_custom));
        b.setText("+");
        b.setChecked(false);
        student.removeChosenCourses(c);
    }

    /**
     * View holder class for the course
     */
    private static class ViewHolder {
        TextView id;
        TextView name;
        ToggleButton button;
        ImageView expandIcon;
        ImageView warnings;

        private ViewHolder(View view) {
            id = (TextView) view.findViewById(R.id.course_id);
            name = (TextView) view.findViewById(R.id.course_name);
            button = (ToggleButton) view.findViewById(R.id.course_button);
            expandIcon = (ImageView) view.findViewById(R.id.expandable_icon);
            warnings = (ImageView) view.findViewById(R.id.choose_course_warning);
        }
    }

}

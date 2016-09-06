package com.example.owner.courseplanner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import com.example.owner.courseplanner.Model.Course;
import com.example.owner.courseplanner.Model.Student;
import com.example.owner.courseplanner.R;
import java.util.List;

/**
 * Adapter for the SelectCompletedCourse Activity's ListView
 */
public class CompletedCourseAdapter extends ArrayAdapter<Course> {
    private Context context;
    private static Student student = Student.getInstance();
    private List<Course> courses;
    private static final String COURSES_SUBJECT = "CPSC";

    /**
     * Constructor creates the adapter
     * @param context   The context
     * @param data      The courses to be displayed in the ListView
     */
    public CompletedCourseAdapter(Context context, List<Course> data) {
        super(context, R.layout.completed_course, data);
        courses = data;
        this.context = context;
    }

    @Override
    public int getCount() {
        return courses.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            convertView = inflater.inflate(R.layout.completed_course, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        }
        else {
            holder = (ViewHolder) convertView.getTag();
        }


        final Course course = courses.get(position);
        String currentId = COURSES_SUBJECT + " " + course.getId();
        holder.name.setText(currentId);
        holder.name.setTag(course);

        // Listener for course selection:
        holder.name.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                CheckBox cb = (CheckBox) compoundButton;
                if (cb.isChecked()) {
                    course.setSelectedAsCompletedCourse(true);
                    checkBoxChecked(cb, course);
                }
                else {
                    course.setSelectedAsCompletedCourse(false);
                    checkBoxNotChecked(cb, course, position);
                }
            }
        });

        if (course.isSelectedAsCompletedCourse())
            checkBoxChecked(holder.name, course);
        else
            checkBoxNotChecked(holder.name, course, position);

        return convertView;
    }

    /**
     * Course was selected
     * @param cb        Button that was selected
     * @param c         Course that was selected
     */
    private void checkBoxChecked(CheckBox cb, Course c) {
        cb.setBackgroundColor(context.getResources().getColor(R.color.blue_custom));
        cb.setTextColor(context.getResources().getColor(R.color.white));
        cb.setChecked(true);
        student.addCompletedCourse(c);
    }

    /**
     * Course was deselected
     * @param cb        Button that was deselected
     * @param c         Course that was deselected
     */
    private void checkBoxNotChecked(CheckBox cb, Course c, int position) {
        if (position % 2 == 1) {
            cb.setBackgroundColor(context.getResources().getColor(R.color.grey_really_light));
        } else {
            cb.setBackgroundColor(context.getResources().getColor(R.color.white));
        }
        cb.setTextColor(context.getResources().getColor(R.color.black));
        cb.setChecked(false);
        student.removeCompletedCourse(c);
    }

    /**
     * View holder for the course
     */
    private static class ViewHolder {
        CheckBox name;

        private ViewHolder(View view) {
            name = (CheckBox) view.findViewById(R.id.course_checkbox);
        }
    }
}
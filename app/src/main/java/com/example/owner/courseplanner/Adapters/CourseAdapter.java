package com.example.owner.courseplanner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.*;
import com.example.owner.courseplanner.Model.Course;
import com.example.owner.courseplanner.Model.CoursesDataManager;
import com.example.owner.courseplanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstract class for a course ExpandableListView
 */
public abstract class CourseAdapter extends BaseExpandableListAdapter {
    private Context context;
    private List<Course> courses;
    private static CoursesDataManager coursesDataManager = CoursesDataManager.getInstance();
    public static final String COURSES_SUBJECT = "CPSC";
    private static final int NUM_CHILDREN = 1;

    /**
     * Constructor creates
     * @param context   The context
     * @param data
     */
    public CourseAdapter(Context context, List<Course> data) {
        this.context = context;
        this.courses = new ArrayList<>();
        this.courses.addAll(data);
    }


    @Override
    public int getGroupCount() {
        return courses.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return NUM_CHILDREN;
    }

    @Override
    public Object getGroup(int groupPosition) {
        return courses.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return courses.get(groupPosition).getDescription();
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return false;
    }

    @Override
    public abstract View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup);

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        ChildViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.choose_course_child, viewGroup, false);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        } else {
            holder = (ChildViewHolder) view.getTag();
        }
        final Course course = courses.get(groupPosition);
        holder.prerequisites.setText(getPrerequisitesAsText(course));
        holder.description.setText(course.getDescription());

        return view;
    }

    /**
     * Gets the course prerequisite as  a string
     * @param course    the course
     * @return String with the prerequisites
     */
    private String getPrerequisitesAsText(Course course) {
        if (course.getPrerequisites() == null ||  course.getPrerequisites().size() == 0) {
            if (course.getWarnings() == null)
                return "Pre-reqs: NONE";
            else
                return "Pre-reqs: NONE, but check warning above";
        }

        String prereqString = null;
        for (Integer i: course.getPrerequisites()) {
            Course prereq = coursesDataManager.getCourseWithId(i);
            if (prereq != null) {
                if (prereqString == null)
                    prereqString = "Pre-reqs: " + COURSES_SUBJECT + " " + prereq.getId();
                else
                    prereqString += ", " + COURSES_SUBJECT + " " + prereq.getId();
            }
        }
        if (course.getWarnings() != null)
            prereqString += ",  - check warning above";
        return prereqString;
    }

    /**
     * View Holder for the child
     */
    private static class ChildViewHolder {
        TextView prerequisites;
        TextView description;

        private ChildViewHolder(View view) {
            prerequisites = (TextView) view.findViewById(R.id.course_description_prerequisites);
            description = (TextView) view.findViewById(R.id.course_description);
        }
    }

}

package com.example.owner.courseplanner.Adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.owner.courseplanner.Model.Course;
import com.example.owner.courseplanner.Model.CoursesDataManager;
import com.example.owner.courseplanner.Model.Student;
import com.example.owner.courseplanner.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Adapter for the MyCourses Activity's ExpandableListView
 */
public class MyCoursesAdapter extends BaseExpandableListAdapter {
    private Context context;

    private List<Course> myCourses;
    private List<Course> myCompletedCourses;
    private List<Course> myMissingCoreCourses;

    private static final int NUM_GROUPS = 3;
    private static final String COURSES_SUBJECT = "CPSC";
    private static final String MY_COURSES_TITLE = "MY COURSES";
    private static final String MISSING_COURSES_TITLE = "MISSING CORE COURSES";
    private static final String COMPLETED_COURSES_TITLE = "MY COMPLETED COURSES";

    /**
     * Constructor for the adapter
     * @param context   The context
     */
    public MyCoursesAdapter(Context context) {
        this.context = context;
        CoursesDataManager coursesDataManager = CoursesDataManager.getInstance();
        Student student = Student.getInstance();
        this.myCourses = new ArrayList<>();
        this.myMissingCoreCourses = new ArrayList<>();
        this.myCompletedCourses = new ArrayList<>();
        this.myCourses.addAll(student.getChosenCourses());
        this.myMissingCoreCourses.addAll(student.getMissingCoreCourses(coursesDataManager.getAllCourses()));
        this.myCompletedCourses.addAll(student.getCompletedCourses());
    }

    @Override
    public int getGroupCount() {
        return NUM_GROUPS;
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return getListMatchingGroupPosition(groupPosition).size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return getListMatchingGroupPosition(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return getListMatchingGroupPosition(groupPosition).get(childPosition);
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
    public View getGroupView(int groupPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        GroupViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.my_courses_list_caption, viewGroup, false);
            holder = new GroupViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (GroupViewHolder) view.getTag();
        }

        setBackgroundColor(view, groupPosition);
        holder.listCaption.setText(getCaptionMatchingGroupPosition(groupPosition));

        int imageResourceId = isExpanded ? R.drawable.up_arrow_white : R.drawable.down_arrow_white;
        holder.expandIcon.setImageResource(imageResourceId);
        holder.expandIcon.setVisibility(View.VISIBLE);

        return view;
    }


    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isExpanded, View view, ViewGroup viewGroup) {
        ChildViewHolder holder;

        if (view == null) {
            LayoutInflater inflater = ((Activity)context).getLayoutInflater();
            view = inflater.inflate(R.layout.my_courses_child, viewGroup, false);
            holder = new ChildViewHolder(view);
            view.setTag(holder);
        }
        else {
            holder = (ChildViewHolder) view.getTag();
        }

        final Course course = getListMatchingGroupPosition(groupPosition).get(childPosition);
        String currentId = COURSES_SUBJECT + " " + course.getId();
        holder.id.setText(currentId);
        holder.name.setText(course.getName());

        setBackgroundColor(view, groupPosition);
        return view;
    }

    /**
     * Set the background color of the list
     * @param view              The view
     * @param groupPosition     The group position
     */
    private void setBackgroundColor(View view, int groupPosition) {
        if (groupPosition == 0)
            view.setBackgroundColor(context.getResources().getColor(R.color.blue_custom));
        else if (groupPosition == 1)
            view.setBackgroundColor(context.getResources().getColor(R.color.red_custom));
        else if (groupPosition == 2)
            view.setBackgroundColor(context.getResources().getColor(R.color.green_custom));
    }

    /**
     *  Gets the list matching the group position
     * @param groupPosition     The group position
     * @return  The list matching the group position
     */
    private List<Course> getListMatchingGroupPosition(int groupPosition) {
        if (groupPosition == 0)
            return myCourses;
        else if (groupPosition == 1)
            return myMissingCoreCourses;
        else // groupPosition == 2
            return myCompletedCourses;
    }

    /**
     *  Gets the caption matching the group position
     * @param groupPosition     The group position
     * @return  The caption matching the group position
     */
    private String getCaptionMatchingGroupPosition(int groupPosition) {
        if (groupPosition == 0)
            return MY_COURSES_TITLE;
        else if (groupPosition == 1)
            return MISSING_COURSES_TITLE;
        else // groupPosition == 2
            return COMPLETED_COURSES_TITLE;
    }


    /**
     * View holder for the group view
     */
    private static class GroupViewHolder {
        TextView listCaption;
        ImageView expandIcon;

        private GroupViewHolder(View view) {
            listCaption = (TextView) view.findViewById(R.id.my_courses_list_caption);
            expandIcon = (ImageView) view.findViewById(R.id.my_courses_expandable_icon);
        }
    }

    /**
     * View holder for the child view
     */
    private static class ChildViewHolder {
        TextView id;
        TextView name;

        private ChildViewHolder(View view) {
            id = (TextView) view.findViewById(R.id.my_courses_id);
            name = (TextView) view.findViewById(R.id.my_courses_name);
        }
    }

}

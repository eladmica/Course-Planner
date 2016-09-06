package com.example.owner.courseplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.owner.courseplanner.Adapters.MyCoursesAdapter;
import com.example.owner.courseplanner.Model.CoursesDataManager;
import com.example.owner.courseplanner.Model.Student;

/**
 * An Activity for the student's course information
 */
public class MyCourses extends ActionBarActivity {
    private static Student student;
    private static CoursesDataManager coursesDataManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_courses);
        getSupportActionBar().setTitle(" " + getString(R.string.app_name));

        coursesDataManager = CoursesDataManager.getInstance();
        student = Student.getInstance();

        setCreditsText();
        setMissingCoreCoursesText();

        MyCoursesAdapter dataAdapter = new MyCoursesAdapter(this);
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.my_courses_expandable_list);
        expListView.setAdapter(dataAdapter);
        expListView.setMinimumHeight(600);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_course_planner, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case R.id.menu_my_courses:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent myCourses = new Intent(this, MyCourses.class);
                startActivity(myCourses);
                return true;
            case R.id.menu_course_archive:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent chooseArchive = new Intent(this, CourseArchive.class);
                startActivity(chooseArchive);
                return true;
            case R.id.menu_change_courses:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent chooseCourses = new Intent(this, ChooseCourses.class);
                startActivity(chooseCourses);
                return true;
            case R.id.menu_change_completed:
                if (item.isChecked())
                    item.setChecked(false);
                else
                    item.setChecked(true);
                Intent selectCompletedCourses = new Intent(this, SelectCompletedCourses.class);
                startActivity(selectCompletedCourses);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }


    /**
     * Sets the number of credits text
     */
    private void setCreditsText() {
        String total = student.getTotalCredits() + " credits";
        TextView totalCredits = (TextView) findViewById(R.id.my_courses_num_credits);
        totalCredits.setText(total);

        String threeHundredLevel = student.getCreditsFromThreeHundredLevelCourses() + " credits";
        TextView threeHundredLevelCredits = (TextView) findViewById(R.id.my_courses_three_hundred_level_credits);
        threeHundredLevelCredits.setText(threeHundredLevel);

        String fourHundredLevel = student.getCreditsOfFourHundredLevelCourses() + " credits";
        TextView fourHundredLevelCredits = (TextView) findViewById(R.id.my_courses_four_hundred_level_credits);
        fourHundredLevelCredits.setText(fourHundredLevel);
    }


    /**
     * Sets the missing core courses text
     */
    private void setMissingCoreCoursesText() {
        int numMissingCourses = student.getMissingCoreCourses(coursesDataManager.getAllCourses()).size();
        if (numMissingCourses > 0) {
            TextView missingCoreCourses = (TextView) findViewById(R.id.my_courses_missing_core_courses);
            ImageView missingCoreIcon = (ImageView) findViewById(R.id.my_courses_error_icon);
            missingCoreIcon.setVisibility(View.VISIBLE);
            missingCoreCourses.setTextColor(getResources().getColor(R.color.red_custom));
            missingCoreCourses.setVisibility(View.VISIBLE);
            if (numMissingCourses == 1) {
                String missingCourse = "Missing " + numMissingCourses + " core course!";
                missingCoreCourses.setText(missingCourse);
            } else {
                String missingCourses = "Missing " + numMissingCourses + " core courses!";
                missingCoreCourses.setText(missingCourses);
            }
        }
        else {
            TextView notMissingCoreCourses = (TextView) findViewById(R.id.my_courses_all_core_courses);
            ImageView notMissingCoreIcon = (ImageView) findViewById(R.id.my_courses_check_mark);
            notMissingCoreIcon.setVisibility(View.VISIBLE);
            notMissingCoreCourses.setTextColor(getResources().getColor(R.color.green_custom));
            notMissingCoreCourses.setVisibility(View.VISIBLE);
            notMissingCoreCourses.setText("You are not missing any core courses!");
        }
    }

}

package com.example.owner.courseplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ExpandableListView;
import android.widget.LinearLayout;
import com.example.owner.courseplanner.Adapters.CourseAdapter;
import com.example.owner.courseplanner.Adapters.CourseArchiveAdapter;

/**
 * An Activity for to Course Archive
 */
public class CourseArchive extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_course_archive);
        getSupportActionBar().setTitle(" " + getString(R.string.app_name));

        CourseAdapter adapter = new CourseArchiveAdapter(this);
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.course_archive_expandable_list);
        expListView.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
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
}

package com.example.owner.courseplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import com.example.owner.courseplanner.Adapters.ChooseCourseAdapter;
import com.example.owner.courseplanner.Adapters.CourseAdapter;
import com.example.owner.courseplanner.Model.Student;
import java.io.*;

/**
 * An Activity for choosing courses
 */
public class ChooseCourses extends ActionBarActivity {
    private CourseAdapter dataAdapter;
    private static Student student;
    private static final String FILE_NAME = "studentChosenCourses.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choose_courses);
        getSupportActionBar().setTitle(" " + getString(R.string.app_name));

        student = Student.getInstance();
        dataAdapter = new ChooseCourseAdapter(this);
        ExpandableListView expListView = (ExpandableListView) findViewById(R.id.courses_expandable_list);
        expListView.setAdapter(dataAdapter);

        // ExpandableListView Listener:
        expListView.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
            @Override
            public boolean onGroupClick(ExpandableListView parent, View v, int groupPosition, long id) {
                if (v.isSelected())
                    parent.expandGroup(groupPosition);
                else
                    parent.collapseGroup(groupPosition);
                return false;
            }
        });


        // Submit Button:
        final Button submit = (Button) findViewById(R.id.submit_chosen_courses);
        submit.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        submitButtonClicked();
                    }
                }
        );

        // Clear Button:
        final Button clear = (Button) findViewById(R.id.clear_chosen_courses);
        clear.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        clearButtonClicked();
                    }
                }
        );

    }

    /**
     * Saves data when submit button was clicked and start the MyCourses Activity
     */
    private void submitButtonClicked() {
        saveData();
        Intent myCourses = new Intent(this, MyCourses.class);
        startActivity(myCourses);
    }

    /**
     * Clears all chosen courses when clear button is clicked
     */
    private void clearButtonClicked() {
        student.clearChosenCourses();
        dataAdapter.notifyDataSetChanged();
    }

    /**
     * Save data to device's internal storage
     */
    private void saveData() {
        try {
            FileOutputStream fileOut = getApplicationContext().openFileOutput(FILE_NAME, MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(student.getChosenCourses());
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

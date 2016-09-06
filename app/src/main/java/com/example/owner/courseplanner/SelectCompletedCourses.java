package com.example.owner.courseplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import com.example.owner.courseplanner.Adapters.CompletedCourseAdapter;
import com.example.owner.courseplanner.Model.CoursesDataManager;
import com.example.owner.courseplanner.Model.Student;
import java.io.*;

/**
 * An Activity for selecting completed courses
 */
public class SelectCompletedCourses extends ActionBarActivity {
    private static Student student;
    private CompletedCourseAdapter dataAdapter;
    private static final String FILE_NAME = "studentCompletedCourses.data";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_completed_courses);
        getSupportActionBar().setTitle(" " + getString(R.string.app_name));

        student = Student.getInstance();
        CoursesDataManager coursesDataManager = CoursesDataManager.getInstance();

        student.clearChosenCourses();
        dataAdapter = new CompletedCourseAdapter(this, coursesDataManager.getAllCourses());
        ListView listView = (ListView) findViewById(R.id.prerequisite_list);
        listView.setAdapter(dataAdapter);

        // Submit Button:
        final Button submit = (Button) findViewById(R.id.submit_prerequisites);
        submit.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    submitButtonClicked(v);
                }
         });


        // Clear Button:
        final Button clear = (Button) findViewById(R.id.clear_prerequisites);
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
     * @param v     The view that was clicked
     */
    private void submitButtonClicked(View v) {
        saveData();
        Intent ChooseCourses = new Intent(this, ChooseCourses.class);
        startActivity(ChooseCourses);
    }

    /**
     * Save data to device's internal storage
     */
    private void saveData() {
        try {
            FileOutputStream fileOut = getApplicationContext().openFileOutput(FILE_NAME, MODE_PRIVATE);
            ObjectOutputStream out = new ObjectOutputStream(fileOut);
            out.writeObject(student.getCompletedCourses());
            out.close();
            fileOut.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void clearButtonClicked() {
        student.clearCompletedCourses();
        student.clearChosenCourses();
        dataAdapter.notifyDataSetChanged();
    }

}

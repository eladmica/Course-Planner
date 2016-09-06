package com.example.owner.courseplanner;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import com.example.owner.courseplanner.Model.Course;
import com.example.owner.courseplanner.Model.CourseParser;
import com.example.owner.courseplanner.Model.Student;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.util.List;

/**
 *  Fetch data and launch the appropriate Activity based on user's app usage
 */
public class AppLauncher extends ActionBarActivity {
    private static Student student;
    private List<Course> completedCourses, chosenCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        student = Student.getInstance();
        parseAllCourses();
        loadStudentData();
        launchActivity();
    }

    /**
     * Loads student data from internal storage.
     * if student never used app before, sets data to null
     */
    private void loadStudentData() {
        FileInputStream fileIn;
        ObjectInputStream in;
        try {
            fileIn = getApplicationContext().openFileInput("studentCompletedCourses.data");
            in = new ObjectInputStream(fileIn);
            completedCourses = (List<Course>) in.readObject();
            in.close();
            fileIn.close();
            fileIn = getApplicationContext().openFileInput("studentChosenCourses.data");
            in = new ObjectInputStream(fileIn);
            chosenCourses = (List<Course>) in.readObject();
            in.close();
            fileIn.close();
        } catch (Exception e) {
            completedCourses = null;
            chosenCourses = null;
            return;
        }
        student.setCompletedCourses(completedCourses);
        student.setChosenCourses(chosenCourses);
    }

    /**
     *  Launch Activity based on user previous application use.
     *  if user have never user the app before, launch the "SelectCompletedCourses" Activity
     *  otherwise, launch the "MyCourses" Activity
     */
    private void launchActivity() {
        if (completedCourses == null || chosenCourses == null) {
            Intent SelectCompletedCoursesIntent = new Intent(this, SelectCompletedCourses.class);
            startActivity(SelectCompletedCoursesIntent);
        }
        else {
            Intent myCoursesIntent = new Intent(this, MyCourses.class);
            startActivity(myCoursesIntent);
        }
    }


    /**
     *  Parses all courses from the courses data file
     */
    private void parseAllCourses() {
        InputStream is = getResources().openRawResource(R.raw.courses);
        InputStreamReader jsonInput = new InputStreamReader(is);
        try {
            CourseParser.parseCourses(jsonInput);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

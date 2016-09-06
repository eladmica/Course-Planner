package com.example.owner.courseplanner.Model;

import java.util.*;

/**
 *  A class that stores all of the courses offered by the University of
 *  British Columbia's Computer Science program.
 *
 *  Singleton pattern applied to ensure only a single instance of this class is
 *  globally accessible throughout application.
 *
 *  INVARIANT: Every course has a unique id (its course number)
 *  INVARIANT: Courses must be ordered (by course number)
 */
public class CoursesDataManager {
    private static CoursesDataManager instance;
    private Map<Integer, Course> courses;

    /**
     * Constructs course data manager with empty list of courses
     */
    private CoursesDataManager() {
        courses = new HashMap<>();
    }

    /**
     * Gets one and only instance of this class
     * @return  instance of class
     */
    public static CoursesDataManager getInstance() {
        if (instance == null)
            instance = new CoursesDataManager();
        return instance;
    }

    /**
     * Get all courses offered as ordered list
     * @return  an ordered list of all courses
     */
    public List<Course> getAllCourses() {
        List<Course> orderedCoursesList = new ArrayList<>();
        for (Map.Entry<Integer, Course> entry : courses.entrySet()) {
            orderedCoursesList.add(entry.getValue());
        }
        Collections.sort(orderedCoursesList);
        return orderedCoursesList;
    }

    /**
     * Add a course to all offered courses
     * @param c course to be added
     */
    public void addCourse(Course c) {
        if (!courses.containsKey(c.getId()))
            courses.put(c.getId(), c);
    }

    /**
     * Gets course with a particular id
     * @param id    id of the course
     * @return  course with the given id, or null if none found
     */
    public Course getCourseWithId(int id) {
        return courses.get(id);
    }
}

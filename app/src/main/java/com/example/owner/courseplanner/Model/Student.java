package com.example.owner.courseplanner.Model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Represents the student's course selections.
 *
 *  Singleton pattern applied to ensure only a single instance of this class is
 *  globally accessible throughout application.
 */
public class Student {
    private static Student instance;

    private List<Course> completedCourses;
    private List<Course> chosenCourses;

    private static final int THREE_HUNDRED_LEVEL_COURSE = 300;
    private static final int FOUR_HUNDRED_LEVEL_COURSE = 400;

    /**
     * Constructs a student with empty course selections
     */
    private Student() {
        completedCourses = new ArrayList<>();
        chosenCourses = new ArrayList<>();
    }

    /**
     * Gets one and only instance of this class
     *
     * @return  instance of class
     */
    public static Student getInstance() {
        if (instance == null)
            instance = new Student();
        return instance;
    }

    /**
     * Clears all completed courses
     */
    public void clearCompletedCourses() {
        for (Course c: completedCourses)
            c.setSelectedAsCompletedCourse(false);
        completedCourses.clear();
    }

    /**
     * Clears all chosen courses
     */
    public void clearChosenCourses() {
        for (Course c: chosenCourses)
            c.setSelectedAsChosenCourse(false);
        chosenCourses.clear();
    }

    /**
     * Gets total number of credits from all student's courses
     * @return  total number of credits
     */
    public int getTotalCredits() {
        int sum = 0;
        for (Course c: completedCourses)
            sum += c.getCredits();
        for (Course c: chosenCourses)
            sum += c.getCredits();
        return sum;
    }

    /**
     * Gets credits from three hundred level courses only
     * @return  total number of credits from three hundred level courses
     */
    public int getCreditsFromThreeHundredLevelCourses() {
        int total = 0;
        for (Course c: completedCourses) {
            if (c.getCourseLevel() == THREE_HUNDRED_LEVEL_COURSE)
                total += c.getCredits();
        }
        for (Course c: chosenCourses) {
            if (c.getCourseLevel() == THREE_HUNDRED_LEVEL_COURSE)
                total += c.getCredits();
        }
        return total;
    }

    /**
     * Gets credits from four hundred level courses only
     * @return  total number of credits from four hundred level courses
     */
    public int getCreditsOfFourHundredLevelCourses() {
        int total = 0;
        for (Course c: completedCourses) {
            if (c.getCourseLevel() == FOUR_HUNDRED_LEVEL_COURSE)
                total += c.getCredits();
        }
        for (Course c: chosenCourses) {
            if (c.getCourseLevel() == FOUR_HUNDRED_LEVEL_COURSE)
                total += c.getCredits();
        }
        return total;
    }

    /**
     * @return  an unmodifiable view of all chosen courses
     */
    public List<Course> getChosenCourses() {
        return Collections.unmodifiableList(chosenCourses);
    }

    /**
     * @return  an unmodifiable view of all completed courses
     */
    public List<Course> getCompletedCourses() {
        return Collections.unmodifiableList(completedCourses);
    }


    /**
     * Gets all of student's missing core courses
     * @return  an unmodifiable view of all missing core courses
     */


    /**
     * Gets all of missing core courses based on courses given
     * @param courses   all courses completed
     * @return          list of all the missing core courses
     */
    public List<Course> getMissingCoreCourses(List<Course> courses) {
        List<Course> missingCoreCourses = new ArrayList<>();
        for (Course c: courses) {
            if (c.isCore() && !chosenCourses.contains(c) && !completedCourses.contains(c))
                missingCoreCourses.add(c);
        }
        return Collections.unmodifiableList(missingCoreCourses);
    }


    /**
     * Adds a completed course, if not already there
     * @param c course to be added as completed
     */
    public void addCompletedCourse(Course c) {
        if (!completedCourses.contains(c))
            completedCourses.add(c);
        Collections.sort(completedCourses);
    }

    /**
     * Adds a chosen course, if not already there
     * @param c course to be added as chosen
     */
    public void addChosenCourses(Course c) {
        if (!chosenCourses.contains(c))
            chosenCourses.add(c);
        Collections.sort(chosenCourses);
    }

    /**
     * Removes a chosen course
     * @param c chosen course to be removed
     */
    public void removeChosenCourses(Course c) {
        chosenCourses.remove(c);
    }

    /**
     * Clears current completed courses and sets all given courses as completed
     * @param courses   all courses to be set as completed by the user
     */
    public void setCompletedCourses(List<Course> courses) {
        clearCompletedCourses();
        if (courses != null)
            completedCourses = courses;
    }

    /**
     * Clears current chosen courses and sets all given courses as chosen
     * @param courses   all courses to be set as chosen by the user
     */
    public void setChosenCourses(List<Course> courses) {
        clearChosenCourses();
        if (courses != null)
            chosenCourses = courses;
    }

    /**
     * Remove student's completed course
     * @param c course to be removed from the completed collection
     */
    public void removeCompletedCourse(Course c) {
        completedCourses.remove(c);
    }


    /**
     * Gets all courses the student can take based on his/hers completed courses
     * A course can be taken if:
     * (1) it was never completed successfully before
     * (2) student have all the required prerequisites
     * @param courses       all courses already taken
     * @return              an unmodifiable view of all courses that can be taken
     */
    public List<Course> getCoursesCanTake(List<Course> courses) {
        List<Course> coursesCanBeTaken = new ArrayList<>();
        for (Course c: courses) {
            if (!completedCourses.contains(c) && c.canBeTaken(completedCourses))
                coursesCanBeTaken.add(c);
        }
        return Collections.unmodifiableList(coursesCanBeTaken);
    }
}

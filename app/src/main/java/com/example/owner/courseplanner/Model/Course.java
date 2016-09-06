package com.example.owner.courseplanner.Model;

import java.util.*;

/**
 *  A class representing a Course
 */
public class Course implements Comparable<Course> {
    /** The course number */
    private int id;
    private String name;
    private String description;
    private int credits;
    private boolean core;
    private Set<Integer> prerequisites;
    /** Warning message, such as prerequisite outside the Computer Science Department  */
    private String warnings;
    /** whether only a single prerequisite is enough to fulfill course requirement  */
    private boolean prerequisiteOneOfFlag;
    /** whether course was chosen by Student  */
    private boolean selectedAsChosenCourse;
    /** whether course was selectedAsChosenCourse as completed by Student  */
    private boolean selectedAsCompletedCourse;


    /**
     * Constructor creates a course with given information
     * @param id            course id
     * @param name          course name
     * @param description   course description
     * @param credits       number of credits of the course
     */
    public Course(int id, String name, String description, int credits) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.credits = credits;
        this.core = false;
        prerequisites = new HashSet<>();
        prerequisiteOneOfFlag = false;
        selectedAsChosenCourse = false;
        selectedAsCompletedCourse = false;
    }

    /**
     * Sets course as a core course
     * @param bool  true if course is core, false otherwise
     */
    public void setCore(boolean bool) {
        core = bool;
    }

    /**
     * Sets a warning for the course
     * @param warnings  String representing the warning(s)
     */
    public void setWarnings(String warnings) {
        this.warnings = warnings;
    }

    /**
     * Sets a one-of-prerequisite flag for the course
     * @param bool  true if course requires only one-of the listed prerequisites
     */
    public void setPrerequisiteOneOfFlag(boolean bool) {
        this.prerequisiteOneOfFlag = bool;
    }

    /**
     * Sets the course as completed by Student
     * @param bool  true if course was selected; false otherwise
     */
    public void setSelectedAsCompletedCourse(boolean bool) {
        selectedAsCompletedCourse = bool;
    }

    /**
     * Sets the course as chosen by Student
     * @param bool  true if course was selected; false otherwise
     */
    public void setSelectedAsChosenCourse(boolean bool) {
        selectedAsChosenCourse = bool;
    }

    /**
     * Add prerequisite to the course
     * @param courseId    a course that is a prerequisite
     */
    public void addPrerequisite(int courseId) {
        prerequisites.add(courseId);
    }


    /**
     * Checks whether a course can be taken (All prerequisites are completed)
     * @param courses   a list of all courses that may be used as prerequisites
     * @return  true if the course can be taken; false otherwise
     */
    public boolean canBeTaken(List<Course> courses) {
        List<Integer> coursesIds = getListOfCourseIds(courses);
        if (prerequisiteOneOfFlag) {
            for (Integer i: prerequisites) {
                if (coursesIds.contains(i))
                        return true;
            }
            return false;
        }
        return coursesIds.containsAll(prerequisites);
    }

    // Convert the list of courses to a list of courses' ids
    private List<Integer> getListOfCourseIds(List<Course> courses) {
        List<Integer> coursesIds = new ArrayList<>();
        for (Course c: courses)
            coursesIds.add(c.getId());
        return coursesIds;
    }


    /**
     * Two courses are equal if their ids are equal
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id;
    }

    /**
     * Two courses are equal if their ids are equal
     */
    @Override
    public int hashCode() {
        return id;
    }


    /**
     * Order courses by their ids (smaller values ordered before longer values)
     */
    @Override
    public int compareTo(Course course) {
        return this.id - course.getId();
    }


    // Getter Methods:
    /**
     * @return The choose_course's description in point-form, with line breaks after a period
     */
    public String getDescription() {
        return " - ".concat(description.replace(". ", ".\n\n - "));
    }

    /**
     * Get the level of the course (i.e. 100, 200, 300, 400)
     * @return  The course's level
     */
    public int getCourseLevel() {
        return (id / 100) * 100;
    }

    /**
     * @return  an unmodifiable view of the prerequisites
     */
    public Set<Integer> getPrerequisites() {
        return Collections.unmodifiableSet(prerequisites);
    }

    /**
     * @return  true if this is a core course
     */
    public boolean isCore() {
        return core;
    }

    /**
     * @return  Course id
     */
    public int getId() {
        return id;
    }

    /**
     * @return  Course name
     */
    public String getName() {
        return name;
    }

    /**
     * @return  true if course was selected as chosen
     */
    public boolean isSelectedAsChosenCourse() {
        return selectedAsChosenCourse;
    }

    /**
     * @return  true if course was selected as completed
     */
    public boolean isSelectedAsCompletedCourse() {
        return selectedAsCompletedCourse;
    }

    /**
     * @return  number of credits of the course
     */
    public int getCredits() {
        return credits;
    }

    /**
     * @return  a string message with the course warnings; null if no warnings
     */
    public String getWarnings() {
        return warnings;
    }

}

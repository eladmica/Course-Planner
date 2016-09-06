package com.example.owner.courseplanner.Model;

import com.example.owner.courseplanner.Exceptions.MyJsonFileFormatException;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import java.io.InputStreamReader;

/**
 * A parser for the courses data
 */
public class CourseParser {
    private static CoursesDataManager coursesDataManager = CoursesDataManager.getInstance();

    /**
     * Parse courses from JSON response and add them to the CourseDataManager
     */
    public static void parseCourses(InputStreamReader jsonResponse) throws MyJsonFileFormatException {
        JSONParser parser = new JSONParser();
        JSONArray jsonCourses;

        // Get JSONArray of all courses:
        try {
            Object obj = parser.parse(jsonResponse);
            JSONObject jsonObject = (JSONObject) obj;
            jsonCourses = (JSONArray) jsonObject.get("courses");
        } catch (Exception e) {
            throw new MyJsonFileFormatException("Error in format of file");
        }

        // Extract a Course object for every object in the JSONArray
        for (Object next: jsonCourses) {
            JSONObject jsonCourse = (JSONObject) next;

            // ESSENTIAL INFORMATION:
            Course course = null;
            try {
                // Course id:
                Long idLong = (long) jsonCourse.get("id");
                int id = idLong.intValue();
                // Course name:
                String name = (String) jsonCourse.get("name");
                // Course description:
                String description = (String) jsonCourse.get("description");
                // Course credits:
                Long creditsLong = (long) jsonCourse.get("credits");
                int credits= creditsLong.intValue();
                // Construct new Course:
                course = new Course(id, name, description, credits);
            } catch (Exception e) {
                // if essential information is missing, skip course
                continue;
            }

            // whether course is core:
            try {
                Boolean core = (Boolean) jsonCourse.get("core");
                course.setCore(core);
            } catch (Exception e) {
                // No "core" tag. That's OK, omit.
            }

            // whether course have a prerequisiteOneOfFlag:
            try {
                Boolean prerequisiteOneOfFlag = (Boolean) jsonCourse.get("prerequisiteOneOfFlag");
                course.setPrerequisiteOneOfFlag(prerequisiteOneOfFlag);
            } catch (Exception e) {
                // No "prerequisiteOneOfFlag" tag. That's OK, omit.
            }

            // Course warnings:
            try {
                String warnings = (String) jsonCourse.get("warnings");
                course.setWarnings(warnings);
            } catch (Exception e) {
                // No warnings. That's OK, omit.
            }

            try {
                JSONArray jsonPrereqs = (JSONArray) jsonCourse.get("prerequisites");
                for (Object obj: jsonPrereqs) {
                    Long prereqIdLong = (long) obj;
                    int prereqId = prereqIdLong.intValue();
                    course.addPrerequisite(prereqId);
                }
            } catch (Exception e) {
                // no prerequisite tag. That's OK, assume course has no prerequisites.
            }

            coursesDataManager.addCourse(course);
        }
    }
}

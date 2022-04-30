/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

/**
 *
 * @author rakow
 * A class to store CourseUnit information.  
 */
public class Courses {

    private final String courseName;
    private final String groupId;
    private final String courseCreditsMin;
    private final String courseCreditsMax;
    private Boolean completed;

    /**
     * Constructs a Courses objects with the CourseUnit info such as course name, course id, course minimum credits and
     * course maximum credits. It also takes a Boolean value which will be used to determine whether the student has marked the 
     * CourseUnit as completed or not. 
     * @param courseName - name of the CourseUnit.
     * @param groupId - groupId of the CourseUnit.
     * @param courseCreditsMin - minimum credits of the CourseUnit.
     * @param courseCreditsMax - maximum credits of the CourseUnit.
     * @param booleanValue - Boolean value. 
     */
    public Courses(String courseName, String groupId, String courseCreditsMin, String courseCreditsMax,
            Boolean booleanValue) {
        this.courseName = courseName;
        this.groupId = groupId;
        this.courseCreditsMin = courseCreditsMin;
        this.courseCreditsMax = courseCreditsMax;
        this.completed = booleanValue;
    }

    /**
     * Returns the name of the CourseUnit.
     * @return - name of the CourseUnit.
     */
    public String getCourseName() {
        return this.courseName;
    }

    /**
     * Returns the groupId of the CourseUnit.
     * @return - groupId of the CourseUnit. 
     */
    public String getGroupId() {
        return this.groupId;
    }

    /**
     * Returns the minimum credits of the CourseUnit.
     * @return - minimum credits of the CourseUnit.
     */
    public int getCourseCreditsMin() {
        return Integer.parseInt(this.courseCreditsMin);
    }

    /**
     * Returns the maximum credits of the CourseUnit.
     * @return - maximum credits of the CourseUnit.
     */
    public int getCourseCreditsMax() {
        return Integer.parseInt(this.courseCreditsMax);
    }

    /**
     * Returns the Boolean value of the CourseUnit, to determine if the CourseUnit
     * has been marked as completed or not.
     * @return - Boolean value.
     */
    public Boolean getCompleted() {
        return this.completed;
    }

    /**
     * Updates the Boolean value of the CourseUnit as true if the CouresUnit has been
     * marked as completed. 
     */
    public void setCompletedToTrue() {
        this.completed = true;
    }

    /**
     * Updated the Boolean value of the CourseUnit as false if the CourseUnit has been 
     * marked as uncompleted. 
     */
    public void setCompletedToFalse() {
        this.completed = false;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu.ModulesAndCourses;

/**
 *
 * @author mahat
 */
public class Courses {
    private final String courseName;
    private final String courseId;
    private final int courseCredits;
    
    public Courses(String courseName, String courseId, int courseCredits){
        this.courseName = courseName;
        this.courseId = courseId;
        this.courseCredits = courseCredits;
    }
    
    public String getCourseName(){
        return this.courseName;
    }
    
    public String getCourseId(){
        return this.courseId;
    }
    
    public int getCourseCredits(){
        return this.courseCredits;
    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

/**
 *
 * @author rakow
 */
public class Courses {
    private final String courseName;
    private final String groupId;
    private final String courseCreditsMin;
    private final String courseCreditsMax;
    
    public Courses(String courseName, String groupId, String courseCreditsMin, String courseCreditsMax){
        this.courseName = courseName;
        this.groupId = groupId;
        this.courseCreditsMin = courseCreditsMin;
        this.courseCreditsMax = courseCreditsMax;
    }
    
    public String getCourseName(){
        return this.courseName;
    }
    
    public String getgroupId(){
        return this.groupId;
    }
    
    public int getCourseCreditsMin(){
        return Integer.parseInt(this.courseCreditsMin);
    }
    
    public int getCourseCreditsMax() {
        return Integer.parseInt(this.courseCreditsMax);    
    }
}

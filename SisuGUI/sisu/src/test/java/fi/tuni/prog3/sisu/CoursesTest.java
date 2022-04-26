/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.prog3.sisu;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mahat
 */
public class CoursesTest {
    
    public CoursesTest() {
    }

    @Test
    public void testGetCourseName() {
        System.out.println("getCourseName");
        Courses instance = new Courses("Introduction to Academic Studies",
                "tut-cu-g-45454", 
                "2", 
                "2");
        String expResult = "Introduction to Academic Studies";
        String result = instance.getCourseName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetgroupId() {
        System.out.println("getgroupId");
        Courses instance = new Courses("Introduction to Academic Studies",
                "tut-cu-g-45454", 
                "2", 
                "2");
        String expResult = "tut-cu-g-45454";
        String result = instance.getgroupId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetCourseCreditsWithEqualCredits() {
        System.out.println("getCourseCredits with min credits = max credits");
        Courses instance = new Courses("Introduction to Academic Studies",
                "tut-cu-g-45454", 
                "2", 
                "2");
        String expResult = "2";
        String result = instance.getCourseCredits();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testGetCourseCreditsWithUnequalCredits() {
        System.out.println("getCourseCredits with min credits != max credits");
        Courses instance = new Courses("Introduction to Academic Studies",
                "tut-cu-g-45454", 
                "2", 
                "3");
        String expResult = "2-3";
        String result = instance.getCourseCredits();
        assertEquals(expResult, result);
    }
    
}

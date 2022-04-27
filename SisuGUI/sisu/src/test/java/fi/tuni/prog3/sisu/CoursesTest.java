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
    public void testCompleted(){
        System.out.println("getCompleted");
        Courses instance = new Courses("Introduction to Academic Studies",
                "tut-cu-g-45454", 
                "2", 
                "2");
        Boolean expResult = false;
        Boolean result = instance.getCompleted();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetCompletedToTrue(){
        System.out.println("setCompletedToTrue");
        Courses instance = new Courses("Introduction to Academic Studies",
                "tut-cu-g-45454", 
                "2", 
                "2");
        instance.setCompletedToTrue();
        Boolean expResult = true;
        Boolean result = instance.getCompleted();
        assertEquals(expResult, result);
    }
    
    @Test
    public void testSetCompletedToFalse(){
        System.out.println("setCompletedToFalse");
        Courses instance = new Courses("Introduction to Academic Studies",
                "tut-cu-g-45454", 
                "2", 
                "2");
        instance.setCompletedToTrue();
        instance.setCompletedToFalse();
        Boolean expResult = false;
        Boolean result = instance.getCompleted();
        assertEquals(expResult, result);
    }
}
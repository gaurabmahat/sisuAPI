/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mahat
 */
public class ModulesTest {
    
    public ModulesTest() {
    }

    @Test
    public void testGetModuleName() {
        System.out.println("getModuleName");
        Modules instance = new Modules("Bachelor's Programme in Science and Engineering", 
                "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb",
                "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba", 
                "180");
        String expResult = "Bachelor's Programme in Science and Engineering";
        String result = instance.getModuleName();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetModuleId() {
        System.out.println("getModuleId");
        Modules instance = new Modules("Bachelor's Programme in Science and Engineering", 
                "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb",
                "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba", 
                "180");
        String expResult = "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb";
        String result = instance.getModuleId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetGroupId() {
        System.out.println("getGroupId");
        Modules instance = new Modules("Bachelor's Programme in Science and Engineering", 
                "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb",
                "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba", 
                "180");
        String expResult = "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba";
        String result = instance.getGroupId();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetModuleCredits() {
        System.out.println("getModuleCredits");
        Modules instance = new Modules("Bachelor's Programme in Science and Engineering", 
                "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb",
                "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba", 
                "180");
        String expResult = "180";
        String result = instance.getModuleCredits();
        assertEquals(expResult, result);
    }

    @Test
    public void testGetModuleLists() {
        System.out.println("getModuleLists");
        Modules instance = new Modules("Bachelor's Programme in Science and Engineering", 
                "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb",
                "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba", 
                "180");
         Modules addModules = new Modules("Natural Sciences and Mathematics", 
                "otm-640dcf49-18b4-4392-8226-8cc18ea32dfb",
                "otm-640dcf49-18b4-4392-8226-8cc18ea32dfb", 
                "180");
         
        List<Modules> list = new ArrayList<>();
        list.add(addModules);
        List<Modules> expResult = list;
        
        instance.setModuleLists(list);
        List<Modules> result = instance.getModuleLists();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetModuleLists() {
        System.out.println("setModuleLists");
        List<Modules> moduleLists = new ArrayList<>();
        Modules instance = new Modules("Bachelor's Programme in Science and Engineering", 
                "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb",
                "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba", 
                "180");
        Modules addModules = new Modules("Natural Sciences and Mathematics", 
                "otm-640dcf49-18b4-4392-8226-8cc18ea32dfb",
                "otm-640dcf49-18b4-4392-8226-8cc18ea32dfb", 
                "180");
        moduleLists.add(addModules);
        instance.setModuleLists(moduleLists);
    }

    @Test
    public void testGetCoursesLists() {
        System.out.println("getCoursesLists");
        Modules instance = new Modules("Bachelor's Programme in Science and Engineering", 
                "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb",
                "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba", 
                "180");
        List<Courses> expResult = new ArrayList<>();
        List<Courses> result = instance.getCoursesLists();
        assertEquals(expResult, result);
    }

    @Test
    public void testSetCoursesLists() {
        System.out.println("setCoursesLists");
        List<Courses> coursesLists = new ArrayList<>();
        Modules instance = new Modules("Bachelor's Programme in Science and Engineering", 
                "otm-df83fbbd-f82d-4fda-b819-78f6b2077fcb",
                "otm-4d4c4575-a5ae-427e-a860-2f168ad4e8ba", 
                "180");
        instance.setCoursesLists(coursesLists);
    }    
}

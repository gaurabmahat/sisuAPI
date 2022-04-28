/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.prog3.sisu.ConvertJson;

import fi.tuni.prog3.sisu.Modules;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mahat
 */
public class ReadJsonFromFileTest {
    
    public ReadJsonFromFileTest() {
    }

    @Test
    public void testReadFromFileIfFileExists() throws Exception {
        System.out.println("when file exists");
        String fileName = "StudentData";
        Modules expResult = new Modules(fileName, fileName, fileName, fileName);
        Modules result = new ReadJsonFromFile().readFromFile(fileName);
        assertEquals(expResult instanceof Modules, result instanceof Modules);
    }   
    
    @Test
    public void testReadFromFileIfFileDoesNotExists() throws Exception {
        System.out.println("when file does not exists");
        String fileName = "non";
        Modules expResult = null;
        Modules result = new ReadJsonFromFile().readFromFile(fileName);
        assertEquals(expResult, result);
    }   
}

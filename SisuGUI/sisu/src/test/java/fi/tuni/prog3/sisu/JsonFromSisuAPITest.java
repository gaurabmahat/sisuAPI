/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.SisuQuery.JsonFromSisuAPI;
import com.google.gson.JsonObject;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author mahat
 */
public class JsonFromSisuAPITest {
    
    public JsonFromSisuAPITest() {
    }

    @Test
    public void testGetJsonStringFromAPIForModuleAndGroup_id() {
        String element_type = "module";
        String id_type = "group_id";
        String element_id = "otm-87fb9507-a6dd-41aa-b924-2f15eca3b7ae";
        JsonFromSisuAPI instance = new JsonFromSisuAPI();
        JsonObject expResult = new JsonObject();
        JsonObject result = instance.getJsonObjectFromAPI(element_type, id_type, element_id);
        assertEquals(expResult instanceof JsonObject, result instanceof JsonObject);
    }
    
    @Test
    public void testGetJsonStringFromAPIForModuleAndId() {
        String element_type = "module";
        String id_type = "id";
        String element_id = "otm-87fb9507-a6dd-41aa-b924-2f15eca3b7ae";
        JsonFromSisuAPI instance = new JsonFromSisuAPI();
        JsonObject expResult = new JsonObject();
        JsonObject result = instance.getJsonObjectFromAPI(element_type, id_type, element_id);
        assertEquals(expResult instanceof JsonObject, result instanceof JsonObject);
    }
    
    @Test
    public void testGetJsonStringFromAPIForCourse() {
        String element_type = "course";
        String id_type = "id";
        String element_id = "otm-00f5c0e9-e35e-4bd7-8a55-f41028d23820";
        JsonFromSisuAPI instance = new JsonFromSisuAPI();
        JsonObject expResult = new JsonObject();
        JsonObject result = instance.getJsonObjectFromAPI(element_type, id_type, element_id);
        assertEquals(expResult instanceof JsonObject, result instanceof JsonObject);
    }
    
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.SisuQuery.JsonFromSisuAPI;
import com.google.gson.JsonNull;
import java.util.ArrayList;
import com.google.gson.JsonObject;

/**
 *
 * @author rakow
 * A class to retrieve from SisuAPI and store the main attributes of a CourseUnit
 * to facilitate creating a Courses instance.
 */
public class CourseAttributes {
    
    private final ArrayList<String> ca;
    
    /**
     * Constructs initially empty ArrayList for the main attributes of a Course 
     * (instance of Courses class): name, id, credits.
     * 
     */
    public CourseAttributes () {
        ca = new ArrayList<>();
    }
    
    /**
     * Helper to query SisuAPI and obtain a JSON file with data about the object. 
     * Not implemented in this class.
     * 
     */
    private JsonObject getJsonObjectFromAPI(String element_type, String id_type, String element_id) {
        var jsonObject = new JsonFromSisuAPI().getJsonObjectFromAPI(element_type, id_type, element_id);
        return jsonObject;
    }
    
    /**
     * Helper to check whether course has an English name.
     * @param json - JSON object.
     */
    private boolean hasValue(JsonObject json) {
        java.util.Set<java.lang.String> keys = json.keySet();
        return keys.contains("en");
    }
    
    /**
     * Populates the object with Course attributes. Calls a query from SisuAPI
     * with provided parameters and adds obtained attributes to the ArrayList.
     * @param element_type - String informing about the type of item queried.
     * @param id_type - String informing about provided id type.
     * @param element_id - String providing the id.
     */
    private void courseAttributesFromAPI(String element_type, String id_type, String element_id) {
        
        JsonObject jo = getJsonObjectFromAPI(element_type, id_type, element_id);
        JsonObject names = jo.get("name").getAsJsonObject();
        String name;
        String credits_min = "0";
        String credits_max = "0";
        if (hasValue(names)) {
            
            name = names.get("en").getAsString();
        } else {
            
            name = names.get("fi").getAsString();
        }
        
        ca.add(name);
        
        String group_id = jo.get("groupId").getAsString();
        
        ca.add(group_id);
        
        if((jo.has("credits") && !(jo.get("credits") instanceof JsonNull)) && 
                (jo.get("credits").getAsJsonObject().has("min") && !(jo.get("credits").getAsJsonObject().get("min") instanceof JsonNull)))
            credits_min = jo.get("credits").getAsJsonObject().get("min").getAsString();
        
        ca.add(credits_min);
        
        if((jo.has("credits") && !(jo.get("credits") instanceof JsonNull)) && 
                (jo.get("credits").getAsJsonObject().has("max") && !(jo.get("credits").getAsJsonObject().get("max") instanceof JsonNull)))
            credits_max = jo.get("credits").getAsJsonObject().get("max").getAsString();
        
        ca.add(credits_max);
    }
    
    /**
     * Populates the object with Course attributes. It calls the method 
     * querying the SisuAPI with provided parameters and adding the info to the object.
     * @param element_type - String informing about the type of item queried.
     * @param id_type - String informing about provided id type.
     * @param element_id - String providing the id.
     */
    public void getCourseAttributes(String element_type, String id_type, String element_id) {
        courseAttributesFromAPI(element_type, id_type, element_id);
    }
    
    /**
     * Returns ith element in the object.
     * @param i - the index of element to be retrieved.
     * @return - ith element in the object.
     */
    public String get(int i) {
        return ca.get(i);
    }
    
}

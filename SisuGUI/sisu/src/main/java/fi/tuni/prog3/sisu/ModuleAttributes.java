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
 * A class to retrieve from SisuApI and store the main attributes of a ModuleUnit
 * to facilitate creating a Modules instance
 */
public class ModuleAttributes {
    
    private final ArrayList<String> ma;
    
    /**
     * Constructs initially empty ArrayList for the main attributes of a Module 
     * (instance of Modules class): name, id, credits
     */
    public ModuleAttributes () {
        ma = new ArrayList<>();
    }
    
    /**
     * Helper to query SisuAPI and obtain a JSON file with data about the object. 
     * Not implemented in this class.
     * 
     */
    private JsonObject getJsonFromAPI(String element_type, String id_type, String element_id) {
        var jsonObject = new JsonFromSisuAPI().getJsonObjectFromAPI(element_type, id_type, element_id);
        return jsonObject;
    }
    
    /**
     * Helper to check whether course has an English name 
     * @param json JSON object
     */
    private boolean hasValue(JsonObject json) {
        java.util.Set<java.lang.String> keys = json.keySet();
        return keys.contains("en");
    }
    
    /**
     * Populates the object with Module attributes. Calls a query from SisuAPI
     * with provided parameters and adds obtained attributes to the ArrayList.
     * @param element_type String informing about the type of item queried
     * @param id_type String informing about provided id type
     * @param element_id String providing the id
     */
    private void moduleAttributesFromAPI(String element_type, String id_type, String element_id) {
        
        JsonObject jo = getJsonFromAPI(element_type, id_type, element_id);
        JsonObject names = jo.get("name").getAsJsonObject();
        String name;
        String credits_min = "null";
        if (hasValue(names)) {
            
            name = names.get("en").getAsString();
        } else {
            
            name = names.get("fi").getAsString();
        }
        
        ma.add(name);
        String id = jo.get("id").getAsString();
        
        ma.add(id);
        String group_id = jo.get("groupId").getAsString();
        
        ma.add(group_id);
        
        if((jo.has("targetCredits") && !(jo.get("targetCredits") instanceof JsonNull)) &&
                (jo.get("targetCredits").getAsJsonObject().has("min") && !(jo.get("targetCredits").getAsJsonObject().get("min") instanceof JsonNull)))
            credits_min = jo.get("targetCredits").getAsJsonObject().get("min").getAsString();
        
        ma.add(credits_min);
    }
    
    /**
     * Populates the object with Module attributes. It calls the method 
     * querying the SisuAPI with provided parameters and adding the info to the object.
     * @param element_type String informing about the type of item queried
     * @param id_type String informing about provided id type
     * @param element_id String providing the id
     */
    public void getModuleAttributes(String element_type, String id_type, String element_id) {
        moduleAttributesFromAPI(element_type, id_type, element_id);
    }
    
    /**
     * Returns ith element in the object
     * @param i the index of element to be retrieved
     * @return - ith element in the object
     */
    public String get(int i) {
        return ma.get(i);
    }
}

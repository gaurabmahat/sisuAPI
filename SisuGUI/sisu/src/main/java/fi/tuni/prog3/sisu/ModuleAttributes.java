/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.SisuQuery.JsonFromSisuAPI;
import com.google.gson.Gson;
import com.google.gson.JsonNull;
import java.util.ArrayList;
import com.google.gson.JsonObject;

/**
 *
 * @author rakow
 * gets the name, ids, and credits od a module
 */
public class ModuleAttributes {
    
    private final ArrayList<String> ma;
    
    public ModuleAttributes () {
        ma = new ArrayList<>();
    }
    
    private JsonObject getJsonFromAPI(String element_type, String id_type, String element_id) {
        var jsonObject = new JsonFromSisuAPI().getJsonObjectFromAPI(element_type, id_type, element_id);
        return jsonObject;
    }
    
    private boolean hasValue(JsonObject json) {
        java.util.Set<java.lang.String> keys = json.keySet();
        return keys.contains("en");
    }
    
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
    
    public void getModuleAttributes(String element_type, String id_type, String element_id) {
        moduleAttributesFromAPI(element_type, id_type, element_id);
    }
    
    public String get(int i) {
        return ma.get(i);
    }
}

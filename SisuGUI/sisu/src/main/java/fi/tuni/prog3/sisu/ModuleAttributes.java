/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
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
        String st = new JsonFromSisuAPI().getJsonStringFromAPI(element_type, id_type, element_id);
        JsonObject jo = new Gson().fromJson(st, JsonObject.class);
        return jo;
    }
    
    private boolean hasValue(JsonObject json) {
        java.util.Set<java.lang.String> keys = json.keySet();
        return keys.contains("en");
    }
    
    private void moduleAttributesFromAPI(String element_type, String id_type, String element_id) {
        System.out.println("getting attributes of the following module " + element_type + " " + id_type + " " + element_id);
        JsonObject jo = getJsonFromAPI(element_type, id_type, element_id);
        JsonObject names = jo.get("name").getAsJsonObject();
        String name;
        if (hasValue(names)) {
            System.out.println("Has English name, selecting this.");
            name = names.get("en").getAsString();
        } else {
            System.out.println("Does not have English name, selecting Finnish name.");
            name = names.get("fi").getAsString();
        }
        System.out.println("Name of the module: " + name);
        ma.add(name);
        String id = jo.get("id").getAsString();
        System.out.println("ID of the module: " + id);
        ma.add(id);
        String group_id = jo.get("groupId").getAsString();
        System.out.println("Group id of the module: " + group_id);
        ma.add(group_id);
        String credits_min = jo.get("targetCredits").getAsJsonObject().get("min").getAsString();
        System.out.println("Min credits of the module: " + credits_min);
        ma.add(credits_min);
    }
    
    public void getModuleAttributes(String element_type, String id_type, String element_id) {
        moduleAttributesFromAPI(element_type, id_type, element_id);
    }
    
    public String get(int i) {
        return ma.get(i);
    }
}

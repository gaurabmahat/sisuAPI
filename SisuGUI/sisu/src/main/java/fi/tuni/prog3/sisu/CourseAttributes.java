/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.SisuQuery.JsonFromSisuAPI;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonNull;
import java.util.ArrayList;
import com.google.gson.JsonObject;

/**
 *
 * @author rakow
 * gets name, id and credits of a course
 */
public class CourseAttributes {
    
    private final ArrayList<String> ca;
    
    public CourseAttributes () {
        ca = new ArrayList<>();
    }
    
    private JsonObject getJsonObjectFromAPI(String element_type, String id_type, String element_id) {
        var jsonObject = new JsonFromSisuAPI().getJsonObjectFromAPI(element_type, id_type, element_id);
        return jsonObject;
    }
    
    private boolean hasValue(JsonObject json) {
        java.util.Set<java.lang.String> keys = json.keySet();
        return keys.contains("en");
    }
    
    private void courseAttributesFromAPI(String element_type, String id_type, String element_id) {
        
        JsonObject jo = getJsonObjectFromAPI(element_type, id_type, element_id);
        JsonObject names = jo.get("name").getAsJsonObject();
        String name;
        String credits_min = "null";
        String credits_max = "null";
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
    
    public void getCourseAttributes(String element_type, String id_type, String element_id) {
        courseAttributesFromAPI(element_type, id_type, element_id);
    }
    
    public String get(int i) {
        return ca.get(i);
    }
    
    public static void main(String[] args){
        var a = new CourseAttributes();
        a.getCourseAttributes("course", "nothing", "tut-cu-g-49138");
        var course = a.get(0);
        var groupId = a.get(1);
        var minCredits = a.get(2);
        var maxCredits = a.get(3);
        
        System.out.println("");
        System.out.println("Printing value course name: " + course);
        System.out.println("Printing value group id: " + groupId);
        System.out.println("Printing value min credits: " + minCredits);
        System.out.println("Printing value max credits: " + maxCredits);
    }
}
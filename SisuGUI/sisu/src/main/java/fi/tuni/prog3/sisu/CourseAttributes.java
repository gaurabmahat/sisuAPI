/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
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
        var jsonObject = new JsonFromSisuAPI().getJsonStringFromAPI(element_type, id_type, element_id);
        return jsonObject;
    }
    
    private boolean hasValue(JsonObject json) {
        java.util.Set<java.lang.String> keys = json.keySet();
        return keys.contains("en");
    }
    
    private void courseAttributesFromAPI(String element_type, String id_type, String element_id) {
        System.out.println("getting attributes of the following course " + element_type + " " + id_type + " " + element_id);
        JsonObject jo = getJsonObjectFromAPI(element_type, id_type, element_id);
        JsonObject names = jo.get("name").getAsJsonObject();
        String name;
        if (hasValue(names)) {
            System.out.println("Has English name, selecting this.");
            name = names.get("en").getAsString();
        } else {
            System.out.println("Does not have English name, selecting Finnish name.");
            name = names.get("fi").getAsString();
        }
        System.out.println("Name of the course: " + name);
        ca.add(name);
        
        String group_id = jo.get("groupId").getAsString();
        System.out.println("Group id of the course: " + group_id);
        ca.add(group_id);
        String credits_min = jo.get("credits").getAsJsonObject().get("min").getAsString();
        System.out.println("Min credits of the course: " + credits_min);
        ca.add(credits_min);
        String credits_max = jo.get("credits").getAsJsonObject().get("max").getAsString();
        System.out.println("Max credits of the course: " + credits_max);
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

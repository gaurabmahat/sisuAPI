/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;

/**
 *
 * @author rakow 
 * A class to traverse data from SisuAPI for one module and get the IDs
 * of this module's constituent submodules and courses.
 */
public class ModuleTraversal {

    private final ArrayList<String> subModule_ids;
    private final ArrayList<String> course_ids;

    /**
     * Create initially empty ArrayLists to store Strings with courses' and 
     * modules' GroupIDs retrieved from SisuAPI.
     */
    public ModuleTraversal() {
        subModule_ids = new ArrayList<>();
        course_ids = new ArrayList<>();
    }

    /**
     * Get the IDs of constituent modules.
     * @return - ArrayList of constituent modules' IDs.
     */
    public ArrayList<String> getSubModuleIds() {
        return subModule_ids;
    }

    /**
     * Get IDs of constituent courses.
     * @return - ArrayList of constituent courses' IDs.
     */
    public ArrayList<String> getCourseIds() {
        return course_ids;
    }

    /**
     * Traverse JsonElement to find modules' and courses' IDs. Traverses
     * a JsonElement, searches for IDs in it and saves them into corresponding 
     * ArrayLists in the parent module.
     * @param rules - JsonElement, which is either JsonObject or JsonArray .
     */
    private void traverseRules(JsonElement rules) {
        // takes JsonElement as argument, because module's tree structure can contain both JsonObjects and JsonArrays
        if (rules.isJsonArray()) { // so first check which one is in question.
            JsonArray units = rules.getAsJsonArray();

            for (int i = 0; i < units.size(); i++) { //check if it is a study module or a course; if it is one of those, add to the map

                if (units.get(i).getAsJsonObject().get("type").getAsString().equals("ModuleRule")) {
                    java.util.Set<java.lang.String> keys = units.get(i).getAsJsonObject().keySet();
                    String[] k = new String[keys.size()];
                    keys.toArray(k);
                    String id_type = k[2];
                    String id = units.get(i).getAsJsonObject().get(id_type).getAsString();
                    subModule_ids.add(id);

                } else if (units.get(i).getAsJsonObject().get("type").getAsString().equals("CourseUnitRule")) {
                    java.util.Set<java.lang.String> keys = units.get(i).getAsJsonObject().keySet();
                    String[] k = new String[keys.size()];
                    keys.toArray(k);
                    String id_type = k[2];
                    String id = units.get(i).getAsJsonObject().get(id_type).getAsString();
                    course_ids.add(id);

                } else {
                    JsonObject temp = units.get(i).getAsJsonObject();
                    java.util.Set<java.lang.String> keys = temp.keySet();

                    if (keys.contains("rules")) {

                        if (temp.get("rules").isJsonObject()) {
                            JsonObject jarray = temp.getAsJsonObject("rules");
                            traverseRules(jarray);

                        } else if (temp.get("rules").isJsonArray()) {
                            JsonArray jarray = temp.getAsJsonArray("rules");
                            traverseRules(jarray);

                        }
                    } else if (keys.contains("rule")) {

                        if (temp.get("rule").isJsonObject()) {
                            JsonObject jarray = temp.getAsJsonObject("rule");
                            traverseRules(jarray);

                        } else if (temp.get("rule").isJsonArray()) {
                            JsonArray jarray = temp.getAsJsonArray("rule");
                            traverseRules(jarray);

                        }
                    }
                }
            }
        } else if (rules.isJsonObject()) {

            JsonObject object = rules.getAsJsonObject();
            String type = object.get("type").getAsString();

            if (!type.equals("ModuleRule") && !type.equals("CourseUnitRule")) {
                java.util.Set<java.lang.String> keys = object.keySet();

                if (keys.contains("rules")) {

                    if (object.get("rules").isJsonObject()) {
                        JsonObject jarray = object.getAsJsonObject("rules");
                        traverseRules(jarray);

                    } else if (object.get("rules").isJsonArray()) {
                        JsonArray jarray = object.getAsJsonArray("rules");
                        traverseRules(jarray);

                    }
                } else if (keys.contains("rule")) {
                    if (object.get("rule").isJsonObject()) {
                        JsonObject jarray = object.getAsJsonObject("rule");
                        traverseRules(jarray);

                    } else if (object.get("rule").isJsonArray()) {
                        JsonArray jarray = object.getAsJsonArray("rule");
                        traverseRules(jarray);
                    }
                }
            } else { //it loks like modules and courses are always in arrays, but just in case here I check whether the object is not what we need
                java.util.Set<java.lang.String> keys = object.keySet();
                String[] k = new String[keys.size()];
                keys.toArray(k);
                String id_type = k[2];
                String id = object.get(id_type).getAsString();

                if (id_type.equals("moduleGroupId")) {
                    subModule_ids.add(id);

                } else if (id_type.equals("CourseUnitGroupId")) {
                    course_ids.add(id);
                }

            }
        }

    }

    /**
     * Retrieve IDs of modules and courses.
     * Calls a method that traverses a JsonElement to get the IDs.
     * @param rules - JsonElement, which is either JsonObject or JsonArray
     */
    public void doModuleTraversal(JsonElement rules) {
        traverseRules(rules);
    }

}

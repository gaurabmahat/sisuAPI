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
 * @author rakow This class traverses one module of SisuAPI data and gets the
 * ids of this module's submodules and courses and saves them into ArrayLists
 */
public class ModuleTraversal {

    private final ArrayList<String> subModule_ids;
    private final ArrayList<String> course_ids;

    public ModuleTraversal() {
        subModule_ids = new ArrayList<>();
        course_ids = new ArrayList<>();
    }

    public ArrayList<String> getSubModuleIds() {
        return subModule_ids;
    }

    public ArrayList<String> getCourseIds() {
        return course_ids;
    }

    //takes JsonElement as argument, because module's tree structure can contain both JsonObjects and JsonArrays
    private void traverseRules(JsonElement rules) {
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

    public void doModuleTraversal(JsonElement rules) {
        traverseRules(rules);
    }

}

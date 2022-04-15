/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 *
 * @author rakow
 */
public class SubModulesFromSisuAPI {
    private TreeMap<String, String> submodule_ids;
    
    public SubModulesFromSisuAPI(){
        submodule_ids = new TreeMap<>();
    }
    
    private void submodulesFromSisuAPI(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                + "modules"
                + "/otm-3858f1d8-4bf9-4769-b419-3fee1260d7ff"))
                .build();
        String st = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
        JsonElement urlElement = JsonParser.parseString(st);
        JsonObject jo = urlElement.getAsJsonObject();
        JsonElement rules = jo.get("rule");
        System.out.println(rules);
        traverseRules(rules);
        
        
        for (Map.Entry<String, String> entry : submodule_ids.entrySet()) {
            System.out.println("Key: " + entry.getKey() + ". Value: " + entry.getValue());
        }
    }
    
    public void getSubmodulesFromSisuAPI(){
        submodulesFromSisuAPI();   
    }

    //takes JsonElement as argument, because module's tree structure can contain both JsonObjects and JsonArrays
    public void traverseRules (JsonElement rules) {
        if (rules.isJsonArray()) { // so first check which one is in question.
            JsonArray units = rules.getAsJsonArray();
            System.out.println("Is JsonArray. Printing array.");
            System.out.println(units);
            for (int i = 0; i < units.size(); i++) { //check if it is a study module or a course; if it is, add to the map
                if (units.get(i).getAsJsonObject().get("type").getAsString().equals("ModuleRule") || 
                        units.get(i).getAsJsonObject().get("type").getAsString().equals("CourseUnitRule")) {
                    java.util.Set<java.lang.String> keys = units.get(i).getAsJsonObject().keySet();
                    System.out.println("Is ModuleRule or CourseUnitRule.Printing keys.");
                    System.out.println(keys);
                    String[] k = new String[keys.size()];
                    keys.toArray(k);
                    String id_type = k[2];
                    System.out.println("Printing id type");
                    System.out.println(id_type);
                    String id = units.get(i).getAsJsonObject().get(id_type).getAsString();
                    System.out.println("Printing id");
                    System.out.println(id);
                    submodule_ids.put(id_type, id);
                } else {
                    JsonObject temp = units.get(i).getAsJsonObject();
                    java.util.Set<java.lang.String> keys = temp.keySet();
                    System.out.println("Is not ModuleRule nor CourseUnitRule. Printing keys.");
                    System.out.println(keys);
                    if (keys.contains("rules")) {
                        if (temp.get("rules").isJsonObject()) {
                            JsonObject jarray = temp.getAsJsonObject("rules");
                            System.out.println("Cotains rules. Printing new object that will be now traversed");
                            System.out.println(jarray);
                            traverseRules(jarray);
                        } else if ( temp.get("rules").isJsonArray()) {
                            JsonArray jarray = temp.getAsJsonArray("rules");
                            System.out.println("Cotains rules. Printing new object that will be now traversed");
                            System.out.println(jarray);
                            traverseRules(jarray);
                        }
                    } else if (keys.contains("rule")) {
                        if (temp.get("rule").isJsonObject()) {
                            JsonObject jarray = temp.getAsJsonObject("rule");
                            System.out.println("Cotains rule. Printing new object that will be now traversed");
                            System.out.println(jarray);
                            traverseRules(jarray);
                        } else if ( temp.get("rule").isJsonArray()) {
                            JsonArray jarray = temp.getAsJsonArray("rule");
                            System.out.println("Cotains rule. Printing new object that will be now traversed");
                            System.out.println(jarray);
                            traverseRules(jarray);
                        }
                    }
                }
            }
        } else if (rules.isJsonObject()){
            JsonObject object = rules.getAsJsonObject();
            String type = object.get("type").getAsString();
            System.out.println("Is not JsonArray. Printing object");
            System.out.println(type);
            if (!type.equals("ModuleRule") || !type.equals("CourseUnitRule")) {
                java.util.Set<java.lang.String> keys = object.keySet();
                //List<String> k = new ArrayList<>();
                //k.addAll(keys);
                System.out.println("Is not ModuleRule nor CourseUnitRule. Printing keys.");
                System.out.println(keys);
                if (keys.contains("rules")) {
                    if (object.get("rules").isJsonObject()) {
                        JsonObject jarray = object.getAsJsonObject("rules");
                        System.out.println("Cotains rules. Printing new object that will be now traversed");
                        System.out.println(jarray);
                        traverseRules(jarray);
                    } else if ( object.get("rules").isJsonArray()) {
                        JsonArray jarray = object.getAsJsonArray("rules");
                        System.out.println("Cotains rules. Printing new object that will be now traversed");
                        System.out.println(jarray);
                        traverseRules(jarray);
                    }
                } else if (keys.contains("rule")) {
                    if (object.get("rule").isJsonObject()) {
                        JsonObject jarray = object.getAsJsonObject("rule");
                        System.out.println("Cotains rule. Printing new object that will be now traversed");
                        System.out.println(jarray);
                        traverseRules(jarray);
                    } else if ( object.get("rule").isJsonArray()) {
                        JsonArray jarray = object.getAsJsonArray("rule");
                        System.out.println("Cotains rule. Printing new object that will be now traversed");
                        System.out.println(jarray);
                        traverseRules(jarray);
                    }
                }
            } else { //it loks like modules and courses are always in arrays, but just in case here I check whether the object is not what we need
                java.util.Set<java.lang.String> keys = object.keySet();
                String[] k = new String[keys.size()];
                System.out.println("Is not JsonArray, but is ModuleRule or CourseUnitRule. Printing keys");
                System.out.println(keys);
                keys.toArray(k);
                String id_type = k[2];
                System.out.println("Printing id type");
                System.out.println(id_type);
                String id = object.get(id_type).getAsString();
                System.out.println("Printing id");
                System.out.println(id);
                submodule_ids.put(id_type, id);
            }
        }
        
    }
    
    // TO-DO: void getSubmoduleNames(TreeMap<String, String>) to get names based on the map with ids

}

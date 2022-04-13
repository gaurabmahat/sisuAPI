/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.net.URI;
import java.util.TreeMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author PC
 */
public class DataFromSisuAPI {
    private final TreeMap<String, String> degreeIdAndName;
    
    public DataFromSisuAPI(){
        degreeIdAndName = new TreeMap<>();
    }
    
    private void dataFromSisuAPI(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                + "module-search?"
                + "curriculumPeriodId=uta-lvv-2021&"
                + "universityId=tuni-university-root-id&moduleType=DegreeProgramme&limit=1000"))
                .build();
        String st = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
        JsonElement urlElement = JsonParser.parseString(st);
        JsonObject jo = urlElement.getAsJsonObject();
        JsonArray jArray = jo.get("searchResults").getAsJsonArray();

        for(int i = 0; i < jArray.size(); i++){
            var degreeId = jArray.get(i).getAsJsonObject().get("id").getAsString();
            var degreeName = jArray.get(i).getAsJsonObject().get("name").getAsString();
            degreeIdAndName.put(degreeName, degreeId);
        }
    }
    
    private void ModulesFromSisuAPI(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                + "modules"
                + "/otm-1d25ee85-df98-4c03-b4ff-6cad7b09618b"))
                .build();
        String st = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
        JsonElement urlElement = JsonParser.parseString(st);
        JsonObject jo = urlElement.getAsJsonObject();
        System.out.println(jo);
        /*JsonArray jArray = jo.get("contentDescription").getAsJsonArray();
        System.out.println(jArray);*
        /*for(int i = 0; i < jArray.size(); i++){
            var degreeId = jArray.get(i).getAsJsonObject().get("id").getAsString();
            var degreeName = jArray.get(i).getAsJsonObject().get("name").getAsString();
            degreeIdAndName.put(degreeName, degreeId);
        }*/
    }
    
    public void getDataFromSisuAPI(){
        dataFromSisuAPI();
        //return this.degreeIdAndName;
    }
    
    public void getModulesFromSisuAPI(){
        ModulesFromSisuAPI();
        //return this.degreeIdAndName;
    }
    
    public ObservableList<String> getDegrees(){
        ObservableList<String> degree_programs = FXCollections.observableArrayList();
        
        //TreeMap<String, String> allDegrees = getDataFromSisuAPI();
        for(String degree_program : this.degreeIdAndName.keySet()){
            degree_programs.add(degree_program);
        }
                
                
        /*"Option 1 nhhfhhhhhhhhhhhhuohhh",
        "Option 2",
        "Option 3"
        );  */
        
        return degree_programs;
    }

    
    /**
     * Example: pulling the data from DataFromSisuAPI class.
     *  
     */
    /*public static void main(String[] args){
        DataFromSisuAPI newData = new DataFromSisuAPI();
        
        newData.getDataFromSisuAPI();
        newData.getModulesFromSisuAPI();
        var map = newData.getDataFromSisuAPI();
        
        for(var item: map.keySet()){
            
            System.out.println("Degree Program: "+item + ", id: " + map.get(item));

        }
    }*/
}

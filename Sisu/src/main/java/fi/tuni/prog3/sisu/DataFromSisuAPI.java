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
import java.util.TreeMap;

/**
 *
 * Example: extracting map from the class
 *      DataFromSisuAPI newData = new DataFromSisuAPI();
        var map = newData.getDataFromSisuAPI();
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
    
    public TreeMap<String, String> getDataFromSisuAPI(){
        dataFromSisuAPI();
        return this.degreeIdAndName;
    }
    
    /**
     * Example: pulling the data from DataFromSisuAPI class.
     *  
     */
    public static void main(String[] args){
        DataFromSisuAPI newData = new DataFromSisuAPI();
        var map = newData.getDataFromSisuAPI();
        
        for(var item: map.keySet()){
            
            System.out.println("Degree Program: "+item + ", id: " + map.get(item));

        }
    }
}

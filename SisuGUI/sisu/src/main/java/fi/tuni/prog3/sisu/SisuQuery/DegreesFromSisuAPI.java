/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package fi.tuni.prog3.sisu.SisuQuery;

import java.net.URI;
import java.util.TreeMap;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author PC
 * A class to get all the degree name and degree id from the Sisu API.
 */
public class DegreesFromSisuAPI implements iSisuQuery{
    private final TreeMap<String, String> degreeIdAndName;
    
    /**
     * Constructs an initially empty TreeMap.
     */
    public DegreesFromSisuAPI(){
        degreeIdAndName = new TreeMap<>();
    }
    
    /**
     * Searches the Sisu API and adds the degree name as key and degree id as value to the TreeMap.
     */
    private void degreesFromSisuAPI(){
        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("https://sis-tuni-test.funidata.fi/kori/api/"
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
    
    /**
     * Returns the TreeMap with the degree name and its id. It calls the method that 
     * searches the Sisu API and add the info to the TreeMap.
     * @return - TreeMap.
     */
    @Override
    public TreeMap<String, String> getDegreeNameAndId() {
        degreesFromSisuAPI();
        return degreeIdAndName; 
    }

    /**
     * Not implemented in this class.
     * @param element_type
     * @param id_type
     * @param element_id
     * @return - null.
     */
    @Override
    public JsonObject getJsonObjectFromAPI(String element_type, String id_type, String element_id) {
        return null;
    }
}

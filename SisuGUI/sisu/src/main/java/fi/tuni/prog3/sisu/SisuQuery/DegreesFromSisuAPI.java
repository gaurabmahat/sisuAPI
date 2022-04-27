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
 */
public class DegreesFromSisuAPI implements iSisuQuery{
    private final TreeMap<String, String> degreeIdAndName;
    
    public DegreesFromSisuAPI(){
        degreeIdAndName = new TreeMap<>();
    }
    
    private void degreesFromSisuAPI(){
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

    @Override
    public TreeMap<String, String> getDegreeNameAndId() {
        degreesFromSisuAPI();
        return degreeIdAndName; 
    }

    @Override
    public JsonObject getJsonObjectFromAPI(String element_type, String id_type, String element_id) {
        return null;
    }
}

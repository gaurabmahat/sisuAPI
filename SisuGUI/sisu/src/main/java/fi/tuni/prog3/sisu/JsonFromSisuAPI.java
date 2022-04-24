/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author rakow
 * Is supposed to get object from SisuAPI, however returns only JsonObject, so it should be changed.
 * Takes three parameters to build query: the type of element(course or module), then type of id(id or groupId) and finally the id itself
 */
public class JsonFromSisuAPI {
    
    private JsonObject jo;
    
    public JsonFromSisuAPI() {
        jo = new JsonObject();
    }
    
    private void jsonFromSisuAPI(String element_type, String id_type, String element_id){
        HttpClient client = HttpClient.newHttpClient();
        if (element_type.equals("module")) {
            if (id_type.equals("group_id")) {
                System.out.println("Query for module with group id, getting json");
                HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                + "modules"
                + "/by-group-id?groupId=" + element_id + "&universityId=tuni-university-root-id"))
                .build();
                String st = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
                JsonElement urlElement = JsonParser.parseString(st);
                this.jo = urlElement.getAsJsonObject();
            } else if (id_type.equals("id")) {
                System.out.println("Query for module with module id, getting json");
                HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                + "modules"
                + "/" + element_id))
                .build();
                String st = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
                JsonElement urlElement = JsonParser.parseString(st);
                this.jo = urlElement.getAsJsonObject();
            }
        } else if (element_type.equals("course")) {
            System.out.println("Query for course with group id, getting json");
            HttpRequest request =  HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                + "course-units"
                + "/by-group-id?groupId=" + element_id + "&universityId=tuni-university-root-id"))
                .build();
                String st = client.sendAsync(request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
                JsonElement urlElement = JsonParser.parseString(st);
                this.jo = urlElement.getAsJsonObject();
        }
    }
    
    public JsonObject getJsonFromSisuAPI(String element_type, String id_type, String element_id) {
        jsonFromSisuAPI(element_type, id_type, element_id);
        return this.jo;
    }
}



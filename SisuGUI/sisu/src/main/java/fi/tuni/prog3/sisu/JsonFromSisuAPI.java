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
 * @author rakow Gets JSON from SisuAPI. Takes three parameters to build query:
 * the type of element(course or module), then type of id(id or groupId) and
 * finally the id itself
 */
public class JsonFromSisuAPI {

    private HttpRequest request;
    private JsonObject jsonObject;
    
    public JsonFromSisuAPI(){
        this.jsonObject = new JsonObject();
    }

    private void getJsonStringFromSisuAPI(String element_type, String id_type, String element_id) {
        HttpClient client = HttpClient.newHttpClient();
        if (element_type.equals("module")) {
            if (id_type.equals("group_id")) {
                System.out.println("Query for module with group id, getting json");
                this.request = HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                        + "modules"
                        + "/by-group-id?groupId=" + element_id + "&universityId=tuni-university-root-id"))
                        .build();
            } else if (id_type.equals("id")) {
                System.out.println("Query for module with module id, getting json");
                this.request = HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                        + "modules"
                        + "/" + element_id))
                        .build();
            }
        } else if (element_type.equals("course")) {
            System.out.println("Query for course with group id, getting json");
            this.request = HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                    + "course-units"
                    + "/by-group-id?groupId=" + element_id + "&universityId=tuni-university-root-id"))
                    .build();
        }

        String jString = client.sendAsync(this.request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
        
        JsonElement jElement = JsonParser.parseString(jString);
        if(jElement.isJsonArray()){
            System.out.println("\nConverting JsonArray to JsonObject!\n");
            jsonObject = jElement.getAsJsonArray().get(0).getAsJsonObject();
        } else {
            System.out.println("\nIs JsonObject!\n");
            jsonObject = jElement.getAsJsonObject();
        }     
    }

    public JsonObject getJsonStringFromAPI(String element_type, String id_type, String element_id) {
        getJsonStringFromSisuAPI(element_type, id_type, element_id);
        return jsonObject;
    }

//    public static void main(String[] args){
//        var jArray = new JsonFromSisuAPI().getJsonStringFromAPI("course", "nothing", "tut-cu-g-49138");
//        var jObject = new JsonFromSisuAPI().getJsonStringFromAPI("module", "id", "otm-b9c586d3-a08d-463d-b824-935460da9b79");    
//    }
}

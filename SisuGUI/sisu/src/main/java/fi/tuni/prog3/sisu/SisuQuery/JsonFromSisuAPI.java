/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu.SisuQuery;

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
 * @author rakow Gets JSON from SisuAPI. Takes three parameters to build query:
 * the type of element(course or module), then type of id(id or groupId) and
 * finally the id itself
 */
public class JsonFromSisuAPI implements iSisuQuery {

    private HttpRequest request;
    private JsonObject jsonObject;
    
    public JsonFromSisuAPI(){
        this.jsonObject = new JsonObject();
    }

    private void getJsonObjectFromSisuAPI(String element_type, String id_type, String element_id) {
        HttpClient client = HttpClient.newHttpClient();
        if (element_type.equals("module")) {
            if (id_type.equals("group_id")) {
                
                this.request = HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                        + "modules"
                        + "/by-group-id?groupId=" + element_id + "&universityId=tuni-university-root-id"))
                        .build();
            } else if (id_type.equals("id")) {
                
                this.request = HttpRequest.newBuilder().uri(URI.create("https://sis-tuni.funidata.fi/kori/api/"
                        + "modules"
                        + "/" + element_id))
                        .build();
            }
        } else if (element_type.equals("course")) {
            
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
            
            jsonObject = jElement.getAsJsonArray().get(0).getAsJsonObject();
        } else {
            
            jsonObject = jElement.getAsJsonObject();
        }     
    }

    @Override
    public JsonObject getJsonObjectFromAPI(String element_type, String id_type, String element_id) {
        getJsonObjectFromSisuAPI(element_type, id_type, element_id);
        return jsonObject;
    }
    
        @Override
    public TreeMap<String, String> getDegreeNameAndId() {
        return null;
    }

//    public static void main(String[] args){
//        var jArray = new JsonFromSisuAPI().getJsonStringFromAPI("course", "nothing", "tut-cu-g-49138");
//        var jObject = new JsonFromSisuAPI().getJsonStringFromAPI("module", "id", "otm-b9c586d3-a08d-463d-b824-935460da9b79");    
//    }
}

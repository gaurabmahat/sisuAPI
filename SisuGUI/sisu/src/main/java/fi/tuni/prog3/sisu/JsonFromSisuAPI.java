/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

/**
 *
 * @author rakow Is supposed to get object from SisuAPI, however returns only
 * JsonObject, so it should be changed. Takes three parameters to build query:
 * the type of element(course or module), then type of id(id or groupId) and
 * finally the id itself
 */
public class JsonFromSisuAPI {

    private HttpRequest request;
    private String jsonString;

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

        this.jsonString = client.sendAsync(this.request, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();
    }

    public String getJsonStringFromAPI(String element_type, String id_type, String element_id) {
        getJsonStringFromSisuAPI(element_type, id_type, element_id);
        return jsonString;
    }

//    public static void main(String[] args){
//        var courseString = new JsonFromSisuAPI().getJsonString("course", "nothing", "tut-cu-g-49138");
//        
//        JsonArray ja = new Gson().fromJson(courseString, JsonArray.class);
//        JsonObject jo = ja.get(0).getAsJsonObject();
//    }
}

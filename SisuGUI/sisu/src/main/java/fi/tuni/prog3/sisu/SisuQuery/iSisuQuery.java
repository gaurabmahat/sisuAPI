/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fi.tuni.prog3.sisu.SisuQuery;

import com.google.gson.JsonObject;
import java.util.TreeMap;

/**
 *
 * @author mahat
 * Interface for creating Sisu Queries. 
 */

public interface iSisuQuery {
    TreeMap<String, String> getDegreeNameAndId();
    JsonObject getJsonObjectFromAPI(String element_type, String id_type, String element_id);
}

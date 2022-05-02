/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.tuni.prog3.sisu.SisuQuery.JsonFromSisuAPI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rakow
 * A class to retrieve and store a list of degree orientations of one Degree 
 * Program, if such exist
 */
public class DegreeOptions {
    private final List<String> optionsList;
    
    /**
     * Construct an initially empty list
     */
    public DegreeOptions() {
        optionsList = new ArrayList<>();
    }
        
    /**
     * Return the list containing the names of the degree options
     * @return - List containing the names of the degree options
     */
    public List<String> getOptions() {
        return this.optionsList;
    }
    
    /**
     * Helper to query SisuAPI and obtain a JSON file with data about the object. 
     * Not implemented in this class.
     * 
     */
    private JsonObject getJsonFromAPI(String element_type, String id_type, String element_id) {
        var jsonObject = new JsonFromSisuAPI().getJsonObjectFromAPI(element_type, id_type, element_id);
        return jsonObject;
    }
    
    /**
     * Helper to traverse one level of data from SisuAPI. 
     * Not implemented in this class
     * 
     */
    private ModuleTraversal traverseModule(JsonElement je) {
        ModuleTraversal mt = new ModuleTraversal();
        mt.doModuleTraversal(je);
        return mt;
    }
    
    /**
     * Populate the list of the degree options of one degree of interest. Calls 
     * method which retrieves data from SisuAPI, and then another one that traverses
     * the first level of degree data. Finally, first level of modules is stored
     * in a global variable, and their names in a list, if they are degree options. 
     * If no options were found, the degree itself is treated as the only option.
     * @param degree the degree of interest
     */
    private void getFirstLevelStructure(Modules degree) {
        String degree_id = degree.getModuleId();

        JsonObject jo = getJsonFromAPI("module", "id", degree_id);
        JsonElement rules = jo.get("rule");

        ModuleTraversal first_level = traverseModule(rules);
        ArrayList<String> first_level_ids = first_level.getSubModuleIds();       
        
        for (String submodule_id : first_level_ids) {

            ModuleAttributes attributes = new ModuleAttributes();
            attributes.getModuleAttributes("module", "group_id", submodule_id);

            Modules submodule = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3), 0);
            
            if (submodule.getModuleCredits().equals(degree.getModuleCredits())) {
                this.optionsList.add(submodule.getModuleName());
                degree.addModuleLists(submodule);
            }

        }
        
        if (this.optionsList.isEmpty()) {
            this.optionsList.add(degree.getModuleName());
        }
    }
    
    /**
     * Populate the list of the degree options of one degree of interest. Calls 
     * method which retrieves data from SisuAPI and populates the list of 
     * degree options
     * @param degree the degree of interest
     */
    public void getDegreeOptions(Modules degree) {
        getFirstLevelStructure(degree);
    }
    
}


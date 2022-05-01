/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

/**
 *
 * @author rakow
 */
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import fi.tuni.prog3.sisu.SisuQuery.JsonFromSisuAPI;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rakow
 */
public class DegreeOptions {
    private final List<String> optionsList;
    
    public DegreeOptions() {
        optionsList = new ArrayList<>();
    }
        
    public List<String> getOptions() {
        return this.optionsList;
    }
    
    private JsonObject getJsonFromAPI(String element_type, String id_type, String element_id) {
        var jsonObject = new JsonFromSisuAPI().getJsonObjectFromAPI(element_type, id_type, element_id);
        return jsonObject;
    }
    
    private ModuleTraversal traverseModule(JsonElement je) {
        ModuleTraversal mt = new ModuleTraversal();
        mt.doModuleTraversal(je);
        return mt;
    }
    
    private void getFirstLevelStructure(Modules degree) {
        String degree_id = degree.getModuleId();

        JsonObject jo = getJsonFromAPI("module", "id", degree_id);
        JsonElement rules = jo.get("rule");

        ModuleTraversal first_level = traverseModule(rules);
        ArrayList<String> first_level_ids = first_level.getSubModuleIds();       
        
        for (String submodule_id : first_level_ids) {

            ModuleAttributes attributes = new ModuleAttributes();
            attributes.getModuleAttributes("module", "group_id", submodule_id);

            Modules submodule = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3), degree);
            
            if (submodule.getModuleCredits().equals(degree.getModuleCredits())) {
                this.optionsList.add(submodule.getModuleName());
                degree.addModuleLists(submodule);
            }

        }
        
        if (this.optionsList.isEmpty()) {
            this.optionsList.add(degree.getModuleName());
        }
    }
    
    public void getDegreeOptions(Modules degree) {
        getFirstLevelStructure(degree);
    }
    
}


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.SisuQuery.JsonFromSisuAPI;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rakow
 * 
 * Gets the structure of a module - first gets json, then traverses it to get 
 * modules and courses ids and then gets their attributes, creates instances of 
 * Courses and Modules and saves them into lists, which are then saved as attributes
 * of the parent module
 */
public class ModuleStructure {
    private final List<Modules> moduleList;
    private final List<Courses> courseList;
    
    public ModuleStructure() {
        moduleList = new ArrayList<>();
        courseList = new ArrayList<>();
    }
    
    public List<Modules> getModules() {
        return moduleList;
    }
    
    public List<Courses> getCourses() {
        return courseList;
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
    
    private void getStructure(Modules module) {
        
        String module_id = module.getModuleId();
        
        
        JsonObject jo = getJsonFromAPI("module", "id", module_id); //first get json with the module
        JsonElement rules = jo.get("rule"); // get its rules
        
        
        ModuleTraversal module_traversal = traverseModule(rules); // traverse rules
        ArrayList<String> submodule_ids = module_traversal.getSubModuleIds(); // get module ids
        ArrayList<String> course_ids = module_traversal.getCourseIds(); // get course ids
        
        for (String id : course_ids) { // get course attributes, create instances of Courses and save to the list
            CourseAttributes attributes = new CourseAttributes();
            attributes.getCourseAttributes("course", "group_id", id);

            Courses course = new Courses(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3), false, module); // course id skipped because it is not used anywhere

            this.courseList.add(course);

        }
        module.setCoursesLists(courseList); // right away update parent module's coursesList

        
        for (String mod_id : submodule_ids) { // get module attributes, create instances of Modules and save to the list

            ModuleAttributes attributes = new ModuleAttributes();
            attributes.getModuleAttributes("module", "group_id", mod_id);

            Modules submodule = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3), module);

            this.moduleList.add(submodule);

            ModuleStructure ms = new ModuleStructure();
            ms.getModuleStructure(submodule); // for every submodule get its structure as well recursively

        }
        
        module.setModuleLists(moduleList); // right away update parent module's modulesList
        
    }
    
    public void getModuleStructure(Modules module) {
        getStructure(module);
    }
    
}

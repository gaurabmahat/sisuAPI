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
 * A class to get the entire structure of a module.
 */
public class ModuleStructure {
    private final List<Modules> moduleList;
    private final List<Courses> courseList;
    
    /**
     * Creates initially empty ArrayLists to store instances of Modules and Courses
     * as parts of the module
     */
    public ModuleStructure() {
        moduleList = new ArrayList<>();
        courseList = new ArrayList<>();
    }
    
    /**
     * Retrieve the list of modules from the object.
     * @return - List of Modules instances
     */
    public List<Modules> getModules() {
        return moduleList;
    }
    
    /**
     * Retrieve the list of courses from the object
     * @return - List of Courses instances
     */
    public List<Courses> getCourses() {
        return courseList;
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
    * Gets the entire tree structure of a module recursively. First gets JSON 
    * object from SisuAPI, then traverses it to get modules' and courses' ids 
    * and then gets their attributes, creates instances of Courses and Modules 
    * and saves them into lists, which are then saved as attributes of the parent module
    * @param instance of Modules class
    */
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

            Courses course = new Courses(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3), false, false);

            this.courseList.add(course);

        }
        module.setCoursesLists(courseList); // right away update parent module's coursesList

        
        for (String mod_id : submodule_ids) { // get module attributes, create instances of Modules and save to the list

            ModuleAttributes attributes = new ModuleAttributes();
            attributes.getModuleAttributes("module", "group_id", mod_id);

            Modules submodule = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3), 0);

            this.moduleList.add(submodule);

            ModuleStructure ms = new ModuleStructure();
            ms.getModuleStructure(submodule); // for every submodule get its structure as well recursively

        }
        
        module.setModuleLists(moduleList); // right away update parent module's modulesList
        
    }
    
    /**
     * Populates the ArrayLists of courses and modules. Calls a method which
     * gets the entire tree structure of module of interest.
     * @param module instance of Modules class
     */
    public void getModuleStructure(Modules module) {
        getStructure(module);
    }
    
}

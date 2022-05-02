/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu.ConvertJson;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import fi.tuni.prog3.sisu.Courses;
import fi.tuni.prog3.sisu.Modules;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author mahat
 * A class to read the JSON from a file.
 */
public class ReadJsonFromFile implements iReadAndWriteJson{

    private Modules Data;
    private final String fileName;
    
    /**
     * A constructor that takes the name of the file. 
     * @param fileName - name of the file.
     */
    public ReadJsonFromFile(String fileName){
        this.fileName = fileName;
    }

    /**
     * Returns the degree info in Modules class format. If the file exits it creates 
     * the Modules based on the info from the file.
     * @return - Modules class if the file exists, otherwise null. 
     * @throws FileNotFoundException - if the file cannot be found. 
     */
    @Override
    public Modules readFromFile() throws FileNotFoundException {
        var jsonObject = getParsedDocument(this.fileName);
        if (jsonObject == null) {
            return null;
        }
        this.Data = new Modules(jsonObject.get("moduleName").getAsString(),
                jsonObject.get("moduleId").getAsString(),
                jsonObject.get("groupId").getAsString(),
                jsonObject.get("moduleCredits").getAsString()
        );

        var moduleList = jsonObject.get("moduleLists").getAsJsonArray();
        if (!moduleList.isEmpty()) {
            for (int i = 0; i < moduleList.size(); i++) {
                var allSubModules = addSubModulesToModules(moduleList.get(i).getAsJsonObject());
                this.Data.addModuleLists(allSubModules);
            }
        }
        var courseList = jsonObject.get("coursesLists").getAsJsonArray();
        if (!courseList.isEmpty()) {
            for (int j = 0; j < courseList.size(); j++) {
                var course = addCourses(courseList.get(j).getAsJsonObject());
                this.Data.addCoursesLists(course);
            }
        }

        return this.Data;
    }
    
    /**
     * Not implemented in this class.
     * @throws IOException 
     */
    @Override
    public void convertToJsonAndWriteToFile() throws IOException {
    }

    /**
     * Returns subModules that contains the inner subModules and courses.
     * @param jsonObject - JSON object that contains the subModule info.
     * @return - Modules class.
     */
    private Modules addSubModulesToModules(JsonObject jsonObject) {
        Modules subModules = new Modules(jsonObject.get("moduleName").getAsString(),
                jsonObject.get("moduleId").getAsString(),
                jsonObject.get("groupId").getAsString(),
                jsonObject.get("moduleCredits").getAsString()
        );

        var courseArray = jsonObject.getAsJsonArray("coursesLists");
        if (!courseArray.isEmpty()) {
            for (int i = 0; i < courseArray.size(); i++) {
                var course = addCourses(courseArray.get(i).getAsJsonObject());
                subModules.addCoursesLists(course);
            }
        }

        var moduleArray = jsonObject.getAsJsonArray("moduleLists");
        if (!moduleArray.isEmpty()) {
            for (int j = 0; j < moduleArray.size(); j++) {
                var module = addSubModulesToModules(moduleArray.get(j).getAsJsonObject());
                subModules.addModuleLists(module);
            }
        }
        return subModules;
    }

    /**
     * Returns Courses class from the course info of the module.
     * @param jsonObject - JSON object that contains the course info.
     * @return - Courses class.
     */
    private Courses addCourses(JsonObject jsonObject) {
        return new Courses(jsonObject.get("courseName").getAsString(),
                jsonObject.get("groupId").getAsString(),
                jsonObject.get("courseCreditsMin").getAsString(),
                jsonObject.get("courseCreditsMax").getAsString(),
                jsonObject.get("completed").getAsBoolean()
        );
    }

    /**
     * Reads file from the local device. If the file with the given name exists, it returns the JSON object 
     * that is in the file. If the file does not exists, returns null;
     * @param fileName - name of the file to read.
     * @return - JSON object if the file exists, otherwise null.
     * @throws FileNotFoundException - if it cannot read the file.
     */
    private JsonObject getParsedDocument(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        if (!f.exists()) {
            return null;
        }
        JsonElement jsonElement = JsonParser.parseReader(new FileReader(fileName));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject;
    }

}

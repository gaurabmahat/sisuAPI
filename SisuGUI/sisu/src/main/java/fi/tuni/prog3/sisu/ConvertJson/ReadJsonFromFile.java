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

/**
 *
 * @author mahat
 */
public class ReadJsonFromFile {

    private Modules Data;

    public Modules readFromFile(String fileName) throws FileNotFoundException {
        var jsonObject = getParsedDocument(fileName);
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

    private Courses addCourses(JsonObject jsonObject) {
        return new Courses(jsonObject.get("courseName").getAsString(),
                jsonObject.get("groupId").getAsString(),
                jsonObject.get("courseCreditsMin").getAsString(),
                jsonObject.get("courseCreditsMax").getAsString(),
                jsonObject.get("completed").getAsBoolean()
        );
    }

    private JsonObject getParsedDocument(String fileName) throws FileNotFoundException {
        File f = new File(fileName);
        if (!f.exists()) {
            return null;
        }
        JsonElement jsonElement = JsonParser.parseReader(new FileReader(fileName));
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        return jsonObject;
    }

//    public static void main(String[] args) throws FileNotFoundException, IOException {
//        var a = new ReadJsonFromFile().readFromFile("Student");
//        if(a != null){
//            System.out.println("File available");
//            WriteJsonToFile wjtf = new WriteJsonToFile(a, "Student2");
//            wjtf.convertToJsonAndWriteToFile();
//        }else {
//            System.out.println("File not available");
//        }
//        
//    }
}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu.ConvertJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fi.tuni.prog3.sisu.Modules;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author mahat
 */
public class WriteJsonToFile {
    private final Modules modules;
    private final String fileName;
    
    public WriteJsonToFile(Modules module, String fileName){
        this.modules = module;
        this.fileName = fileName;
    }
    
    public void convertToJsonAndWriteToFile() throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this.modules);
        writeToFile(json);
    }
    
    private void writeToFile(String json) throws IOException{
        File file = new File(this.fileName);
        FileWriter fw = new FileWriter(file);
        try(PrintWriter pw = new PrintWriter(fw)){
            pw.print(json);
        }
    }
    
//    public static void main(String[] args) throws IOException{
//        
//        ModuleAttributes attributes = new ModuleAttributes(); // get degree's attaributes to create a Modules instance
//        attributes.getModuleAttributes("module", "id", "otm-6ab4ce4a-4eb7-4c76-8ed7-9ab3a2faa5b6");
//
//        var Degree = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3)); 
//        ModuleStructure ms = new ModuleStructure(); 
//        ms.getModuleStructure(Degree); // this step should handle fetching the entire structure of the degree
//        new WriteJsonToFile(Degree, "Student").convertToJsonAndWriteToFile();
//    }
}

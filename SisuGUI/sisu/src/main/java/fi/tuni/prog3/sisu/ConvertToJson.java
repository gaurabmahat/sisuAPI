/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author mahat
 */
public class ConvertToJson {
    private Modules modules;
    
    public ConvertToJson(Modules module){
        this.modules = module;
    }
    
    public void convertToJson() throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this.modules);
        System.out.println(json);
        writeToFile(json);
    }
    
    private void writeToFile(String json) throws IOException{
        File file = new File("StudentData");
        FileWriter fw = new FileWriter(file);
        try(PrintWriter pw = new PrintWriter(fw)){
            pw.print(json);
        }
    }
    
    public static void main(String[] args) throws IOException{
        
        ModuleAttributes attributes = new ModuleAttributes(); // get degree's attaributes to create a Modules instance
        attributes.getModuleAttributes("module", "id", "otm-87fb9507-a6dd-41aa-b924-2f15eca3b7ae");

        var Degree = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3)); 
        ModuleStructure ms = new ModuleStructure(); 
        ms.getModuleStructure(Degree); // this step should handle fetching the entire structure of the degree
        new ConvertToJson(Degree).convertToJson();
    }
}

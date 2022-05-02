/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu.ConvertJson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import fi.tuni.prog3.sisu.ModuleAttributes;
import fi.tuni.prog3.sisu.ModuleStructure;
import fi.tuni.prog3.sisu.Modules;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

/**
 *
 * @author mahat
 * A class to write JSON to a file.
 */
public class WriteJsonToFile implements iReadAndWriteJson{
    private final Modules modules;
    private final String fileName;
    
    /**
     * Constructor that takes the Modules class and name of the file.
     * @param module - the Modules class that contains the data to write in JSON. 
     * @param fileName - name of the file. 
     */
    public WriteJsonToFile(Modules module, String fileName){
        this.modules = module;
        this.fileName = fileName;
    }
    
    /**
     * Not implemented in this class.
     * @return - null. 
     * @throws FileNotFoundException  
     */
    @Override
    public Modules readFromFile() throws FileNotFoundException{
        return null;
    }
    
    /**
     * Converts the class to JSON and writes the JSON to a file. 
     * @throws IOException - if it cannot write to a file.
     */
    @Override
    public void convertToJsonAndWriteToFile() throws IOException{
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String json = gson.toJson(this.modules);
        
        System.out.println("Writing started ...");
        File file = new File(this.fileName);
        FileWriter fw = new FileWriter(file);
        try(PrintWriter pw = new PrintWriter(fw)){
            pw.print(json);
        }
        System.out.println("Writing completed!");
    }
    

}

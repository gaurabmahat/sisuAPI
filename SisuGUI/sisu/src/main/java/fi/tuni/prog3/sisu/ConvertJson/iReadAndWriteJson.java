/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package fi.tuni.prog3.sisu.ConvertJson;

import fi.tuni.prog3.sisu.Modules;
import java.io.FileNotFoundException;
import java.io.IOException;

/**
 *
 * @author mahat
 * Interface to implement reading JSON from a file and writing JSON to a file.
 */
public interface iReadAndWriteJson {
    Modules readFromFile() throws FileNotFoundException;
    void convertToJsonAndWriteToFile() throws IOException;
}

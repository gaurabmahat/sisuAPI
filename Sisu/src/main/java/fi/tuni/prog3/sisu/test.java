/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.TreeMap;

/**
 *
 * @author PC
 */
public class test {
    private ArrayList<String> listElements; 
    private TreeMap<String, ArrayList<String> > treeList;
    
    public test(){
        listElements = new ArrayList<>(); 
        listElements.add("item 1");
        listElements.add("item 2");
        listElements.add("item 3");
        listElements.add("item 4");
        
        treeList = new TreeMap<>();
        treeList.put("Course 1", listElements);
        treeList.put("Course 2", listElements);
        treeList.put("Course 3", listElements);
        treeList.put("Course 4", listElements);
    }
    
    public TreeMap<String, ArrayList<String> > getData(){
        return this.treeList;
    }
            
    
}

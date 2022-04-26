/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author rakow
 */
public class Modules {
    private final String moduleName;
    private final String moduleId;
    private final String groupId;
    private final String moduleCredits;
    private int completedCredits;
    private List<Modules> moduleLists;
    private List<Courses> coursesLists;
    
    public Modules(String moduleName, String moduleId, String groupId, 
            String moduleCredits) {
        this.moduleName = moduleName;
        this.moduleId = moduleId;
        this.groupId = groupId;
        this.moduleCredits = moduleCredits;
        this.completedCredits = 0;
        this.moduleLists = new ArrayList<>();
        this.coursesLists = new ArrayList<>();
    }
    

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleId() {
        return moduleId;
    }
    
    public String getGroupId() {
        return groupId;
    }

    public String getModuleCredits() {
            return moduleCredits;
    }
    
    public List<Modules> getModuleLists() {
        return moduleLists;
    }

    public void setModuleLists(List<Modules> moduleLists) {
        this.moduleLists = moduleLists;
    }

    public List<Courses> getCoursesLists() {
        return coursesLists;
    }

    public void setCoursesLists(List<Courses> coursesLists) {
        this.coursesLists = coursesLists;
    }
    
    public void setCompletedCredits(int credits) {
        this.completedCredits = credits;
    }
    
    public int getCompletedCredits() {
        return this.completedCredits;
    }

}

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package fi.tuni.prog3.sisu.ModulesAndCourses;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mahat
 */
public class Modules {
    private final String moduleName;
    private final String moduleId;
    private final int moduleCredits;
    private List<Modules> moduleLists;
    private List<Courses> coursesLists;
    
    public Modules(String moduleName, String moduleId, int moduleCredits) {
        this.moduleName = moduleName;
        this.moduleId = moduleId;
        this.moduleCredits = moduleCredits;
        this.moduleLists = new ArrayList<>();
        this.coursesLists = new ArrayList<>();
    }

    public String getModuleName() {
        return moduleName;
    }

    public String getModuleId() {
        return moduleId;
    }

    public int getModuleCredits() {
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
}

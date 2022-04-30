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
    private String moduleNameWithCredits;
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
        this.moduleNameWithCredits = this.moduleName + " " + this.completedCredits
                + "op/" + this.moduleCredits + "op";
    }

    public String getModuleName() {
        return moduleNameWithCredits;
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

    public void addModuleLists(Modules module) {
        this.moduleLists.add(module);
    }

    public List<Courses> getCoursesLists() {
        return coursesLists;
    }

    public void setCoursesLists(List<Courses> coursesLists) {
        this.coursesLists = coursesLists;
    }

    public void addCoursesLists(Courses course) {
        this.coursesLists.add(course);
    }

    public void setCompletedCredits(int credits) {
        this.completedCredits += credits;
        this.moduleNameWithCredits = this.moduleName + " " + this.completedCredits
                + "op/" + this.moduleCredits + "op";
    }

    public int getCompletedCredits() {
        return this.completedCredits;
    }
}

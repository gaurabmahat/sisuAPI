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
 * A class to store StudyModule information. 
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

    /**
     * Constructs a Modules object with the StudyModule info such as module name, module id, group id and module credits. 
     * @param moduleName - name of the StudyModule.
     * @param moduleId - id of the StudyModule.
     * @param groupId - group id of the StudyModule.
     * @param moduleCredits - credits of the StudyModule.
     */
    public Modules(String moduleName, String moduleId, String groupId,
            String moduleCredits) {
        this.moduleName = moduleName;
        this.moduleId = moduleId;
        this.groupId = groupId;
        this.moduleCredits = moduleCredits;
        this.completedCredits = 0;
        this.moduleLists = new ArrayList<>();
        this.coursesLists = new ArrayList<>();
        if (moduleCredits.equals("0") || moduleCredits.equals("null")) {
            this.moduleNameWithCredits = this.moduleName;
        } else {
            this.moduleNameWithCredits = this.moduleName + " " + this.completedCredits
                    + "op/" + this.moduleCredits + "op";
        }
    }

    /**
     * Returns the name of the StudyModule along with its credits information.
     * If the StudyModule credit is null or 0, it simply returns the name of the 
     * StudyModule. 
     * @return - name of the StudyModule.
     */
    public String getModuleName() {
        return moduleNameWithCredits;
    }

    /**
     * Returns the id of the StudyModule.
     * @return - id of the StudyModule.
     */
    public String getModuleId() {
        return moduleId;
    }

    /**
     * Returns the groupId of the StudyModule.
     * @return - groupdId of the StudyModule.
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * Returns the credits of the StudyModule.
     * @return - credits of the StudyModule.
     */
    public String getModuleCredits() {
        return moduleCredits;
    }

    /**
     * Returns a list that contains the Modules objects.
     * @return - list containing the Modules objects. 
     */
    public List<Modules> getModuleLists() {
        return moduleLists;
    }

    /**
     * Updates the list with the given list of Modules objects.
     * @param moduleLists - list containing the Modules objects.
     */
    public void setModuleLists(List<Modules> moduleLists) {
        this.moduleLists = moduleLists;
    }

    /**
     * Add Modules object to the list.
     * @param module - Modules object.
     */
    public void addModuleLists(Modules module) {
        this.moduleLists.add(module);
    }
    
    /**
     * Returns a list that contains the Courses objects.
     * @return - list containing the Courses objects.
     */
    public List<Courses> getCoursesLists() {
        return coursesLists;
    }

    /**
     * Updates the list with the given list of Courses objects.
     * @param coursesLists - list containing the Courses objects.
     */
    public void setCoursesLists(List<Courses> coursesLists) {
        this.coursesLists = coursesLists;
    }

    /**
     * Adds Courses object to the list.
     * @param course - Courses object.
     */
    public void addCoursesLists(Courses course) {
        this.coursesLists.add(course);
    }

    /**
     * Updates the completed credits information of the StudyModule.
     * @param credits - number of credits completed. 
     */
    public void setCompletedCredits(int credits) {
        this.completedCredits += credits;
        if (this.moduleCredits.equals("0") || this.moduleCredits.equals("null")) {
            this.moduleNameWithCredits = this.moduleName + " " + this.completedCredits
                + "op";
        } else {
            this.moduleNameWithCredits = this.moduleName + " " + this.completedCredits
                    + "op/" + this.moduleCredits + "op";
        }
    }

    /**
     * Returns the number of completed credits of the StudyModule.
     * @return - completed credits of the StudyModule. 
     */
    public int getCompletedCredits() {
        return this.completedCredits;
    }
    
}

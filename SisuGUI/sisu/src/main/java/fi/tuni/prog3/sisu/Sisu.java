package fi.tuni.prog3.sisu;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;


/**
 * JavaFX Sisu
 */
public class Sisu extends Application {

    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        // create data object
        DegreesFromSisuAPI Data = new DegreesFromSisuAPI();
        Data.getDegreesFromSisuAPI(); // get a full list of Degree programmes
        String degree_of_interest = Data.getDegreeId("Akuuttilääketieteen erikoislääkärikoulutus (55/2020)"); // here we need the name of degree chosen by the user from dropdown list
        System.out.println("Id of chosen degree: " + degree_of_interest);
        
        ModuleAttributes attributes = new ModuleAttributes(); // get degree's attaributes to create a Modules instance
        attributes.getModuleAttributes("module", "id", degree_of_interest);
        
        var Degree = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3)); 
        System.out.println(Degree.getModuleName());
        System.out.println(Degree.getModuleCredits());
        
        ModuleStructure ms = new ModuleStructure();
        ms.getModuleStructure(Degree); // this step should handle fetching the entire structure of the degree
        
        // begin grid
        GridPane grid = new GridPane();
        var scene = new Scene(grid, 640, 480);
        stage.setScene(scene);
        
        Label top_title = new Label("Student");
        top_title.setId("top_title");
        grid.add(top_title, 0, 1, 4, 1);
        
        Label sub_title = new Label("choose a degree program");
        sub_title.setId("sub_title");
        grid.add(sub_title, 0, 3);
        
        ObservableList<String> degree_programs = FXCollections.observableArrayList();
        //Data.getModulesFromSisuAPI();
        degree_programs = Data.getDegrees();

        final ComboBox programs = new ComboBox(degree_programs);
        grid.add(programs, 0, 5, 4, 1);

        
        Label option_title = new Label("choose option");
        option_title.setId("option_title");
        grid.add(option_title, 0, 7);
        
        
        ObservableList<String> degree_options = 
            FXCollections.observableArrayList(
                "Option 1 nhhfhhhhhhhhhhhhuohhh",
                "Option 2",
                "Option 3"
            );
        final ComboBox options = new ComboBox(degree_options);
        grid.add(options, 0, 9, 2, 1);
        
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
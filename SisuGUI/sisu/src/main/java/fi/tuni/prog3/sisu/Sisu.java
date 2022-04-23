package fi.tuni.prog3.sisu;


import java.util.ArrayList;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;


/**
 * JavaFX Sisu
 */
public class Sisu extends Application {
    
    TreeItem<String> rootNode;
    
    /*Node rootIcon = new ImageView(
        new Image(getClass().getResourceAsStream("folder.png"))
    );*/
    
    /*private final Image depIcon = 
        new Image(getClass().getResourceAsStream("folder.png"));*/
    
    @Override
    public void start(Stage stage) {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        // create data object
        DataFromSisuAPI Data = new DataFromSisuAPI();
        Data.getDataFromSisuAPI();
        
        // set two grid for two tabs
        GridPane grid = new GridPane();
        GridPane grid2 = new GridPane();
        
        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Degree Program", grid);
        Tab tab2 = new Tab("Courses", grid2);
   
        tabPane.getTabs().add(tab1);  
        tabPane.getTabs().add(tab2); 
        var scene = new Scene(tabPane, 640, 480);
        stage.setScene(scene);
        
        // grid 1
        
        Label top_title = new Label("Student");
        top_title.setId("top_title");
        grid.add(top_title, 0, 1, 4, 1);
        
        Label sub_title = new Label("choose a degree program");
        sub_title.setId("sub_title");
        grid.add(sub_title, 0, 3);
        
        ObservableList<String> degree_programs = FXCollections.observableArrayList();
        //Data.getModulesFromSisuAPI(program_name);
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
        
        // grid 2 
        
        this.rootNode = new TreeItem<> ("Bachelor's progamme");
        rootNode.setExpanded(true);
        
        TreeItem<String> program = new TreeItem<> ("Computig and Electrical Engineering");
        rootNode.getChildren().add(program);
        
        TreeItem<String> joint = new TreeItem<> ("Joint Studies");
        program.getChildren().add(joint);
        
        // loop over the map
        
        test testObject = new test();
        TreeMap<String, ArrayList<String> > testData = testObject.getData();
        
        
        for(String category : testData.keySet()){  
            TreeItem<String> rootItem = new TreeItem<> (category);
            program.getChildren().add(rootItem);
            
            ArrayList<String> courses = testData.get(category);
            for(String course: courses ){
                TreeItem<String> leaf = new TreeItem<> (course);
                rootItem.getChildren().add(leaf);
            }
            //degree_programs.add(degree_program);
        }
                
        TreeItem<String> item2 = new TreeItem<> ("Course " + 2);
        rootNode.getChildren().add(item2);
        
        // try selecting tree item
        
        /*for (int i = 1; i < 6; i++) {
            TreeItem<String> item = new TreeItem<> ("Message" + i);            
            rootItem.getChildren().add(item);
        }  */      
        TreeView<String> tree = new TreeView<> (rootNode);        
        StackPane root = new StackPane();
        root.getChildren().add(tree);
        grid2.add(root, 0, 3);
        // second scene
        /*GridPane grid2 = new GridPane();
        Scene scene2 = new Scene(grid2, 350, 275);
        stage.setScene(scene2);*/
        
        /*tree.getSelectionModel().setCellSelectionEnabled(true);
        ObservableList selectedCells = tree.getSelectionModel().getSelectedCells();

        selectedCells.addListener(new ListChangeListener() {
            @Override
            public void onChanged(Change c) {
                TablePosition tablePosition = (TablePosition) selectedCells.get(0);
                Object val = tablePosition.getTableColumn().getCellData(tablePosition.getRow());
                System.out.println("Selected Value" + val);
            }
        });*/
        
        
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }

}
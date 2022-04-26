package fi.tuni.prog3.sisu;


import java.util.ArrayList;
import java.util.TreeMap;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
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
        HBox root = new HBox();
        root.setSpacing(20);

        TabPane tabPane = new TabPane();
        Tab tab1 = new Tab("Degree Program", grid);
        Tab tab2 = new Tab("Courses", root);
   
        tabPane.getTabs().add(tab1);  
        tabPane.getTabs().add(tab2); 
        var scene = new Scene(tabPane, 640, 480);
        
        
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

        //create vbox to hold treeView list
        //StackPane root = new StackPane();
        VBox leftPanel = new VBox();
        leftPanel.setSpacing(10);
        leftPanel.getChildren().add(tree);
        //grid2.add(leftPanel, 0, 3);
        
        
        VBox rightPanel = new VBox();
        Button buttonItem = new Button("testing button");
        Text text1 = new Text("");
         
        //CheckBox studentChoice = new CheckBox("first item");
        //CheckBox studentChoice2 = new CheckBox("second item");
        
        
        
        rightPanel.getChildren().add(text1);
        
        ArrayList<CheckBox> studentChoices = new ArrayList<>();
        
        for(int i =0; i<3; i++){
            CheckBox studentChoice = new CheckBox("item "+i+1);
            studentChoices.add(studentChoice);
            rightPanel.getChildren().add(studentChoice);
        }
        
        for(int i = 0; i<studentChoices.size(); i++){
            studentChoices.get(i).selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if(new_val){
                text1.setText("Welcome to Tutorilaspoint");
            }else{
                text1.setText("");
            }
         
        }); 
        }
        //rightPanel.getChildren().add(studentChoice);
        //rightPanel.getChildren().add(studentChoice2);
        rightPanel.getChildren().add(buttonItem);
        
        // button event
        buttonItem.setOnAction(new EventHandler<ActionEvent>(){
            public void handle(ActionEvent event){
                addCourse(rightPanel, text1);
            }
        });
        
        // listner for check box
        /*studentChoice.selectedProperty().addListener(
        (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if(new_val){
                text1.setText("Welcome to Tutorilaspoint");
            }else{
                text1.setText("");
            }
         
        });*/
        
        /*studentChoice2.selectedProperty().addListener(
        (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
            if(new_val){
                text1.setText("Welcome to Tutorilaspoint");
            }else{
                text1.setText("");
            }
         
        });*/
        //grid2.add(rightPanel, 0, 4);
        
        //HBox root = new HBox();
        root.getChildren().add(leftPanel);
        root.getChildren().add(rightPanel);
        
        //grid2.add(root, 0, 4);
        
        
        
        /*tree.getSelectionModel().getSelectedItem().addListener(new ChangeListener(){
            public void changed(ObservableValue observable, Object oldValue,
                    Object newValue) {
                
            }
            //TreeItem selecteItem = tree.getSelectionModel().getSelectedItem();

            @Override
            public void stateChanged(ChangeEvent e) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }
        });*/
        
        //TreeView<String> treeView = new TreeView<>();
        //treeView.setRoot(item2);
           
        //tree.getRoot().getChildren().addListener(childrenChanged);
        //tree.getSelectionModel().selectedItemProperty().addListener(childrenChanged);
        
        //TreeItem selecteItem = tree.getSelectionModel().getSelectedItem();
        
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
        
        stage.setScene(scene);
        stage.show();
    }
    
    private void addCourse(VBox vbox, Text text1){
       CheckBox studentChoice = new CheckBox("first item");
            studentChoice.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                if(new_val){
                    text1.setText("Welcome to Tutorilaspoint");
                }else{
                    text1.setText("");
                }
             });
       vbox.getChildren().add(studentChoice);
    }
    
    /*private final ListChangeListener<TreeItem<String>> childrenChanged 
        = new ListChangeListener<TreeItem<String>>() {

            @Override
            public void onChanged(
                    javafx.collections.ListChangeListener.Change<? extends TreeItem<String>> change) {
                System.out.println("Tree Iten clicked");
            }

        };*/
    
    /*public ChangeListener childrenChanged = new ChangeListener(){

            //@Override
            public void Changed(ObservableValue observable){
  
                System.out.println("Tree Iten clicked");
            }

        @Override
        public void stateChanged(ChangeEvent e) {
            System.out.println("Tree Iten clicked");
            //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        }

    };*/

    public static void main(String[] args) {
        launch();
    }


}
package fi.tuni.prog3.sisu;

import com.sun.source.tree.Tree;
import fi.tuni.prog3.sisu.ConvertJson.ReadJsonFromFile;
import fi.tuni.prog3.sisu.ConvertJson.WriteJsonToFile;
import fi.tuni.prog3.sisu.SisuQuery.DegreesFromSisuAPI;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeMap;
import java.util.concurrent.CompletableFuture;
import javafx.application.Application;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * JavaFX Sisu
 */
public class Sisu extends Application {
    
    //creating two scenes
    private Stage stage;
    
    private Scene scene1;
    private Scene scene2;
    private Scene scene3;
    
    //*********************************************************************
    
    //saving student number
    private String studentNumber;

    // data handling containers

    private ObservableList<String> program_modules = FXCollections.observableArrayList();
    //private ObservableList<Modules> program_modules_structure = FXCollections.observableArrayList();
    ObservableList<String> degree_info;

    // courses

    private String main_degree_program;
    private String main_degree_option;


    //Degree name and id map
    private TreeMap<String, String> DataMap;
    private TreeItem<String> rootNode;
    private TreeView<String> tree;
    
    ArrayList<TreeItem<String>> courseTreeItems; // all course tree items
    String selected_checkbox = null; // selected checkbox
    ArrayList<CheckBox> studentChoices = new ArrayList<>(); // all created chebox items

    private Modules Degree;
    private Integer index_of_main_option;
    private TreeMap<String, Courses> temporarySelectedItems = new TreeMap<>();

    
    Image icon = new Image("https://opportunityforum.info/wp-content/uploads/2022/04/folder.png");
    Image icon2 = new Image("https://opportunityforum.info/wp-content/uploads/2022/05/folder2.png");
     private final Node rootIcon = new ImageView(icon);

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        //Trigger an action when the program is being closed
        stage.setOnCloseRequest(e -> {
            try {
                e.consume();
                closeProgram();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        scene1 = createSceneOne();
        scene2 = createSceneTwo();
        scene3 = createSceneThree();

        stage.setScene(scene1);

        stage.show();

    }

    private Scene createSceneOne(){
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(10));

        //Top of BorderPane
        var topHBox = new HBox();
        topHBox.setAlignment(Pos.CENTER);
        borderPane.setTop(topHBox);
        BorderPane.setMargin(topHBox, new Insets(10));
        //Center of BorderPane
        var vBox = new VBox();
        vBox.setAlignment(Pos.TOP_CENTER);
        borderPane.setCenter(vBox);

        //add Text to the top of BorderPane
        Text universityName = new Text("University of Tampere");
        universityName.setFont(Font.font("Verdana", FontWeight.BOLD, 33));
        universityName.setFill(Color.PURPLE);

        topHBox.getChildren().add(universityName);

        //add Label to the center of the BorderPane
        Label studentText = new Label("Student");
        studentText.setFont(Font.font("Sans Serif", FontWeight.BOLD, 25));

        vBox.getChildren().add(studentText);
        VBox.setMargin(studentText, new Insets(20));

        //text field
        Label studentId = new Label("Student Number: ");
        studentId.setFont(Font.font("Verdana", FontWeight.NORMAL, 15));
        TextField userInput = new TextField();

        //add text field to the horizontal box
        var centerHBox = new HBox();
        centerHBox.getChildren().add(studentId);
        centerHBox.getChildren().add(userInput);
        centerHBox.setAlignment(Pos.CENTER);
        vBox.getChildren().add(centerHBox);
        VBox.setMargin(centerHBox, new Insets(10));

        //message to the user
        Label invalidMessage = new Label();
        vBox.getChildren().add(invalidMessage);

        //login button
        Button login = new Button("Login");
        vBox.getChildren().add(login);
        VBox.setMargin(login, new Insets(10));

        login.setOnAction((e) -> {
            if(!userInput.getText().isEmpty() && !userInput.getText().trim().isEmpty()){
                studentNumber = userInput.getText();
                try {
                    Degree = new ReadJsonFromFile(studentNumber).readFromFile();
                } catch (FileNotFoundException ex) {
                    ex.printStackTrace();
                }
                if(Degree == null) {
                    switchScenes(scene2);
                } else {
                    switchScenes(scene3);
                }
            } else {
                invalidMessage.setText("Please enter your Student Number!");
            }
        });

        scene1 = new Scene(borderPane, 600, 580);

        return scene1;
    }

    private Scene createSceneTwo() {
        var javaVersion = SystemInfo.javaVersion();
        var javafxVersion = SystemInfo.javafxVersion();

        // Window1: set grid base for window
        GridPane grid = new GridPane();
        // Window 2: create HBox base for window 
        HBox root = new HBox();
        root.setSpacing(20);

        // create Tab pane and tabs for both windows
        TabPane tabPane = new TabPane();
        Tab tabWindow_1 = new Tab("Degree Program", grid);
        Tab tabWindow_2 = new Tab("Courses", root);

        // add tabs to the tab pane
        tabPane.getTabs().add(tabWindow_1);
        tabPane.getTabs().add(tabWindow_2);

        // create scene and at the main tab pane
        scene2 = new Scene(tabPane, 1000, 680);

        // populate window 1
        Label top_title = new Label("Student");
        top_title.setId("top_title");
        grid.add(top_title, 0, 1, 4, 1);

        Label sub_title = new Label("choose a degree program");
        sub_title.setId("sub_title");
        grid.add(sub_title, 0, 3);

        /**
         * *********************************************************************
         */
        //get map of full list of Degree Programmes 
        DataMap = new DegreesFromSisuAPI().getDegreeNameAndId();

        //load degree programs 
        loadDegrees();

        // add combo box to select degree programmes
        final ComboBox programs = new ComboBox(degree_info);
        grid.add(programs, 0, 5, 4, 1);

        Label option_title = new Label("choose option");
        option_title.setId("option_title");
        grid.add(option_title, 0, 7);       

        // add combo box to select degree opions
        final ComboBox options = new ComboBox(program_modules);
        grid.add(options, 0, 9, 2, 1);
        
        // load degree modules when a degree programme is selected
        programs.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                        Number old_val, Number new_val) -> {
                    // get selected drgree program
                    String selected_degree = degree_info.get(new_val.intValue());
                    String degree_of_interest = DataMap.get(selected_degree);
                    loadFirstLevel(degree_of_interest);
                });

        /**
         * *********************************************************************
         */
        // populate window 2
        //create left paanel
        VBox leftPanel = new VBox();
        leftPanel.setPrefWidth(500);

        // create right panel
        VBox rightPanel = new VBox();

        //add left and right pannel to the root base
        root.getChildren().add(leftPanel);
        root.getChildren().add(rightPanel);

        /**
         * *********************************************************************
         */
        // select degree option and draw degree structure on left panel
        options.getSelectionModel().selectedIndexProperty().addListener(
                (ObservableValue<? extends Number> ov,
                        Number old_val, Number new_val) -> {
                    // start degree structure display
                    this.rootNode = new TreeItem<>();
                    this.rootNode.setValue(main_degree_program);
                    rootNode.setExpanded(true);

                    // set degree option & get its structure
                    main_degree_option = program_modules.get(new_val.intValue());
                    System.out.println("Fetching degree option...");
                    if (Degree.getModuleLists().isEmpty()) {
                        loadStructure(Degree);
                        TreeItem<String> program = new TreeItem<>(Degree.getModuleName(), rootIcon);
                        for (Modules module : Degree.getModuleLists()) {
                            TreeItem<String> structure = getLevelStructure(module);
                            program.getChildren().add(structure);
                        }
                        rootNode.getChildren().add(program);
                    } else {
                        for (Modules option : Degree.getModuleLists()) {
                            if (option.getModuleName().equalsIgnoreCase(main_degree_option)) {
                                index_of_main_option = Degree.getModuleLists().indexOf(option);
                                loadStructure(option);
                                TreeItem<String> program = new TreeItem<>(option.getModuleName(), rootIcon);
                                for (Modules module : option.getModuleLists()) {
                                    TreeItem<String> structure = getLevelStructure(module);
                                    program.getChildren().add(structure);
                                }
                                rootNode.getChildren().add(program);
                            }
                        }
                    }
                    System.out.println("Fetching option completed!");

                    tree = new TreeView<>(rootNode);

                    //create vbox to hold treeView list
                    //VBox leftPanel = new VBox();
                    leftPanel.getChildren().clear();
                    leftPanel.setSpacing(10);
                    leftPanel.getChildren().add(tree);

                });

        /**
         * *************************************************************************
         */
        //create top and botton sections on the right panel
        // top section
        VBox rightPanelTop = new VBox();
        rightPanelTop.setSpacing(5);
        rightPanelTop.setPrefHeight(400);
        rightPanelTop.setPrefWidth(400);
        // set background color
        rightPanelTop.getStyleClass().add("color-palette");
        rightPanelTop.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        // set margins around the two sections
        VBox.setMargin(rightPanelTop, new Insets(10, 10, 10, 10));

        // botton section
        HBox rightPanelBottom = new HBox();
        rightPanelBottom.setSpacing(20);

        // add top and button sections to the right panel
        rightPanel.getChildren().add(rightPanelTop);
        rightPanel.getChildren().add(rightPanelBottom);

        // create buttons and add them to the bottom section of the right panel
        Button btnAddCourse = new Button("Add Course");
        rightPanelBottom.getChildren().add(btnAddCourse);

        Button btnRemoveCourse = new Button("Remove Course");
        rightPanelBottom.getChildren().add(btnRemoveCourse);
        
        Button btnCompleteCourse = new Button("Complete Course");
        rightPanelBottom.getChildren().add(btnCompleteCourse);
        
 /*****************************************************************************/       
        // display selected courses
        Text text1 = new Text("");
        rightPanelTop.getChildren().add(text1);

        ArrayList<CheckBox> studentChoices = new ArrayList<>();

        for (int i = 0; i < 3; i++) {
            CheckBox studentChoice = new CheckBox("item " + i + 1);
            studentChoices.add(studentChoice);
            rightPanelTop.getChildren().add(studentChoice);
        }

        for (int i = 0; i < studentChoices.size(); i++) {
            studentChoices.get(i).selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        if (new_val) {
                            text1.setText("Welcome to Tutorilaspoint");
                        } else {
                            text1.setText("");
                        }

                    });
        }

        /**
         * ***************************************************************************
         */
        // add action event for adding course buttion
        btnAddCourse.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                TreeItem selecteItem = tree.getSelectionModel().getSelectedItem();
                //TreeItem selecteItem = rootNode.getChildren().get(0);
                String s = selecteItem.getValue().toString();
                List<String> selecedCourses = getSelectedCourses(s);
                for (String Course : selecedCourses) {
                    CheckBox studentChoice = new CheckBox(Course);
                    addCheckboxEvent(studentChoice); // add event listener
                    studentChoices.add(studentChoice); // add selected choice to list of choices
                    rightPanelTop.getChildren().clear();
                    rightPanelTop.getChildren().addAll(studentChoices);
                }

            }
        });

        /**
         * *********************************************************************
         */
        // add action event for adding course buttion
        btnRemoveCourse.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                TreeItem<String>selected_checkbox_item = null;
                for(TreeItem<String> item: courseTreeItems){
                    System.out.println(item.getValue());
                    System.out.println(selected_checkbox);
                    if(item.getValue().equalsIgnoreCase(selected_checkbox)){
                        selected_checkbox_item = item;
                    }
                }
                if(selected_checkbox_item != null){
                    //rightPanelTop.getChildren().rem;
                    for(CheckBox course : studentChoices){
                        if(selected_checkbox_item.getValue().equals(course.getText())){
                            studentChoices.remove(course);
                            rightPanelTop.getChildren().clear();
                            rightPanelTop.getChildren().addAll(studentChoices);
                        }
                    }
                    //courseTreeItems.remove(selected_checkbox_item);
                    //selected_checkbox_item = new TreeItem(selected_checkbox_item.getValue());
                    //selected_checkbox_item.setGraphic(new ImageView(icon2));
                }
                selected_checkbox_item.setGraphic(new ImageView(icon2));
            }
        });
        
        btnCompleteCourse.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                //String selected_course = null;
                TreeItem<String>selected_checkbox_item = null;
                for(TreeItem<String> item: courseTreeItems){
                    System.out.println(item.getValue());
                    System.out.println(selected_checkbox);
                    if(item.getValue().equalsIgnoreCase(selected_checkbox)){
                        selected_checkbox_item = item;
                    }
                }
                if(selected_checkbox_item != null){
                    selected_checkbox_item.setGraphic(new ImageView(icon));
                }
                
                
                // get the selectd course

                TreeItem selecteItem = tree.getSelectionModel().getSelectedItem();
                //TreeItem selecteItem = rootNode.getChildren().get(0);
                String s = selecteItem.getValue().toString();
                List<String> selecedCourses = getSelectedCourses(s);
                for (String Course : selecedCourses) {
                    CheckBox studentChoice = new CheckBox(Course);
                    rightPanelTop.getChildren().add(studentChoice);
                }

            }
        });
        /*
         * *************************************************************************
        */

        return scene2;
    }

    private Scene createSceneThree(){
        // Window1: set grid base for window
        GridPane grid = new GridPane();
        // Window 2: create HBox base for window
        HBox root = new HBox();
        root.setSpacing(20);

        Button showProgress = new Button("Show My Progress");
        grid.getChildren().add(showProgress);

        // create Tab pane and tabs for both windows
        TabPane tabPane = new TabPane();
        Tab tabWindow_1 = new Tab("Degree Program", grid);
        Tab tabWindow_2 = new Tab("Courses", root);

        // add tabs to the tab pane
        tabPane.getTabs().add(tabWindow_1);
        tabPane.getTabs().add(tabWindow_2);

        // create scene and at the main tab pane
        scene3 = new Scene(tabPane, 1000, 680);

        //after user presses the "Show my Progress" button
        showProgress.setOnAction((e) -> {
            // populate window 1
            Label top_title = new Label("Student");
            top_title.setId("top_title");
            grid.add(top_title, 0, 1, 4, 1);

            Label sub_title = new Label("choose a degree program");
            sub_title.setId("sub_title");
            grid.add(sub_title, 0, 3);

            //filling up ComboBox with the one degree
            ObservableList<String> oneDegree = FXCollections.observableArrayList();
            oneDegree.add(Degree.getModuleName());

            //add combo box to selected degree programme
            final ComboBox programs = new ComboBox(oneDegree);
            grid.add(programs, 0, 5, 4, 1);

            Label option_title = new Label("choose option");
            option_title.setId("option_title");
            grid.add(option_title, 0, 7);

            //add combo box to selected degree option
            for(var subModules : Degree.getModuleLists()){
                if(subModules.getModuleLists().size() != 0) {
                    program_modules.add(subModules.getModuleName());
                }
            }

            final ComboBox options = new ComboBox(program_modules);
            grid.add(options, 0, 9, 2, 1);

            // populate window 2
            //create left paanel
            VBox leftPanel = new VBox();
            leftPanel.setPrefWidth(500);

            // create right panel
            VBox rightPanel = new VBox();

            //add left and right pannel to the root base
            root.getChildren().add(leftPanel);
            root.getChildren().add(rightPanel);

            //start degree structure display
            this.rootNode = new TreeItem<>();
            this.rootNode.setValue(Degree.getModuleName());
            rootNode.setExpanded(true);

            // set degree option & get its structure
            if(!Degree.getModuleLists().isEmpty()) {
                //check to see which option is chosen by the User
                Modules studentChosenOption = null;
                for(var subModule : Degree.getModuleLists()){
                    if(!subModule.getModuleLists().isEmpty()){
                        studentChosenOption = subModule;
                        break;
                    }
                }
                if(studentChosenOption != null) {
                    TreeItem<String> program = new TreeItem<>(studentChosenOption.getModuleName(), rootIcon);

                    for (var subModule : studentChosenOption.getModuleLists()) {
                        TreeItem<String> structure = getLevelStructure(subModule);
                        program.getChildren().add(structure);
                    }

                    rootNode.getChildren().add(program);
                }
            }

            tree = new TreeView<>(rootNode);

            //create vbox to hold treeView list
            //VBox leftPanel = new VBox();
            leftPanel.getChildren().clear();
            leftPanel.setSpacing(10);
            leftPanel.getChildren().add(tree);

            //create top and bottom sections on the right panel
            // top section
            VBox rightPanelTop = new VBox();
            rightPanelTop.setSpacing(5);
            rightPanelTop.setPrefHeight(400);
            rightPanelTop.setPrefWidth(400);
            // set background color
            rightPanelTop.getStyleClass().add("color-palette");
            rightPanelTop.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
            // set margins around the two sections
            VBox.setMargin(rightPanelTop, new Insets(10, 10, 10, 10));

            // botton section
            HBox rightPanelBottom = new HBox();
            rightPanelBottom.setSpacing(20);

            // add top and button sections to the right panel
            rightPanel.getChildren().add(rightPanelTop);
            rightPanel.getChildren().add(rightPanelBottom);

            // create buttons and add them to the bottom section of the right panel
            Button btnAddCourse = new Button("Add Course");
            rightPanelBottom.getChildren().add(btnAddCourse);

            Button btnRemoveCourse = new Button("Remove Course");
            rightPanelBottom.getChildren().add(btnRemoveCourse);

            Button btnCompleteCourse = new Button("Complete Course");
            rightPanelBottom.getChildren().add(btnCompleteCourse);

        });
        return scene3;
    }

    /**
     * ************************************************************************
    */
    // helper functions
    private void addCourse(VBox vbox, Text text1) {
        CheckBox studentChoice = new CheckBox("first item");
        studentChoice.selectedProperty().addListener(
                (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                    if (new_val) {
                        text1.setText("Welcome to Tutorilaspoint");
                    } else {
                        text1.setText("");
                    }
                });
        vbox.getChildren().add(studentChoice);
    }

    // load degree programs
    private void loadDegrees() {
        degree_info = FXCollections.observableArrayList();

        //TreeMap<String, String> allDegrees = getDataFromSisuAPI();
        for (String degree_program : DataMap.keySet()) {
            degree_info.add(degree_program);
        }
    }

    // load degree program modules -> called by select action event
    private void loadFirstLevel(String degree_program) {
        temporarySelectedItems.clear();
        program_modules.clear();

        ModuleAttributes attributes = new ModuleAttributes(); // get degree's attaributes to create a Modules instance
        attributes.getModuleAttributes("module", "id", degree_program);

        Degree = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3));
        main_degree_program = Degree.getModuleName(); // set
        
        //get degree options only
        var first_level = new DegreeOptions();
        first_level.getDegreeOptions(Degree);
        List<String> options = first_level.getOptions();
        
        for (var option : options) {
            program_modules.add(option);
        }


    }
    
    private void loadStructure (Modules degree_option) {
        
        ModuleStructure ms = new ModuleStructure();
        ms.getModuleStructure(degree_option); // this step fetches the rest of the degree structure

    }
    
    private TreeItem<String> getLevelStructure(Modules module) {
        TreeItem<String> structure = new TreeItem<>(module.getModuleName());
        
        for (Courses course : module.getCoursesLists()) {
            TreeItem<String> sub_course = new TreeItem<>(course.getCourseName());
            structure.getChildren().add(sub_course);
        }
        
        for (Modules submodule : module.getModuleLists()) {
            TreeItem<String> sub_module = getLevelStructure(submodule);
            structure.getChildren().add(sub_module);
        }

        return structure;
    }

    // capture selcted courses
    private List<String> getSelectedCourses(String selectedItem) {
        List<String> selectedCourses = new ArrayList<>();
        System.out.println("Initial state of selectedCourses: " + selectedCourses);
        
        if (selectedItem.equals(main_degree_program) || selectedItem.equals(main_degree_option)) {
            if (index_of_main_option == null) {
                for (Modules submodule : Degree.getModuleLists()) {
                    selectedCourses = selectModule(submodule, selectedCourses);
                }
                return selectedCourses;
            } else {
                for (Modules submodule : Degree.getModuleLists().get(index_of_main_option).getModuleLists()) {
                    selectedCourses = selectModule(submodule, selectedCourses);
                }
                return selectedCourses;
            }    
        }
        else {
            if (index_of_main_option == null) {
                for (Modules submodule : Degree.getModuleLists()) {
                    if (submodule.getModuleName().equals(selectedItem)) {
                        selectedCourses = selectModule(submodule, selectedCourses);
                        return selectedCourses;
                    }
                    else {
                        ArrayList<String> selected = checkModuleItems(submodule, selectedItem);
                        if (selected.isEmpty()) {
                            continue;
                        }
                        else {
                            selectedCourses.addAll(selected);
                            return selectedCourses;
                        }
                    }
                }
                
            } else {
                for (Modules submodule : Degree.getModuleLists().get(index_of_main_option).getModuleLists()) {
                    if (submodule.getModuleName().equals(selectedItem)) {
                        selectedCourses = selectModule(submodule, selectedCourses);
                        return selectedCourses;
                    }
                    else {
                        ArrayList<String> selected = checkModuleItems(submodule, selectedItem);
                        if (selected.isEmpty()) {
                            continue;
                        }
                        else {
                            selectedCourses.addAll(selected);
                            return selectedCourses;
                        }
                    }
                }
            }
        }
        return selectedCourses;
    }
    
    private ArrayList<String> checkModuleItems(Modules module, String name) {
        ArrayList<String> selected = new ArrayList<>();
        for (Courses course : module.getCoursesLists()) {
            if (course.getCourseName().equals(name)) {
                selected.add(course.getCourseName());
                temporarySelectedItems.put(course.getCourseName(), course);
                return selected;
                }
            }
        for (Modules submodule : module.getModuleLists()) {
            selected = checkModuleItems(submodule, name);
            return selected;
        }
        return selected;
    }
    
    private List<String> selectModule(Modules module, List<String> selectedCourses) {
        System.out.println("Running selectModule ");
        for (Courses course : module.getCoursesLists()) {
            selectedCourses.add(course.getCourseName());
            temporarySelectedItems.put(course.getCourseName(), course);
        }
        for (Modules submodule : module.getModuleLists()) {
            selectedCourses = selectModule(submodule, selectedCourses);
        }
        return selectedCourses;
    }
    

    private void addCheckboxEvent(CheckBox studentChoice){
        studentChoice.selectedProperty().addListener(
                    (ObservableValue<? extends Boolean> ov, Boolean old_val, Boolean new_val) -> {
                        if (new_val) {
                            selected_checkbox = studentChoice.getText();
                        } 

        });
    }

    public void switchScenes(Scene scene){
        stage.setScene(scene);
    }

    private void closeProgram() throws IOException {
        if(studentNumber != null){
            int conformation = JOptionPane.showConfirmDialog(null, "Do you want to save your changes?",
                    "Confirm", JOptionPane.YES_NO_OPTION);

            int degreeOptionSize = 0;
            if(Degree != null) {
                for (var module : Degree.getModuleLists()) {
                    if(module.getModuleLists().size() != 0) {
                        degreeOptionSize = module.getModuleLists().size();
                    }
                }
            }

            if(conformation == 0){
                if(Degree == null){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select One Degree Program!");
                    alert.show();
                } else if(degreeOptionSize == 0){
                    Alert alert = new Alert(Alert.AlertType.ERROR, "Please Select At Least One Degree Option!");
                    alert.show();
                } else {
                    System.out.println("File name: " + studentNumber);
                    new WriteJsonToFile(Degree, studentNumber).convertToJsonAndWriteToFile();
                    System.out.println("Exiting the program.");
                    stage.close();
                }
            } else if(conformation == 1) {
                System.out.println("Exiting the program.");
                stage.close();
            }
        } else {
            System.out.println("Exiting the program.");
            stage.close();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}

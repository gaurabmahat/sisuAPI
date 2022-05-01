package fi.tuni.prog3.sisu;

import fi.tuni.prog3.sisu.SisuQuery.DegreesFromSisuAPI;
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
    
    //*********************************************************************
    
    //saving student number
    private String studentNumber;

    // data handling containers
    //private ObservableList<String> degree_programs = FXCollections.observableArrayList();
    //private ObservableList<Modules> degree_program_structure = FXCollections.observableArrayList();

    private ObservableList<String> program_modules = FXCollections.observableArrayList();
    //private ObservableList<Modules> program_modules_structure = FXCollections.observableArrayList();
    ObservableList<String> degree_info;

    // courses
    //private ObservableList<String> orientation_modules = FXCollections.observableArrayList();
    private String main_degree_program;
    //private String main_degree_id;
    private String main_degree_option;

    // save all the treeView courses as Modules
    //private TreeMap<String, TreeMap< TreeItem<String>, List< TreeItem<String>>>> program_courses;

    //Degree name and id map
    private TreeMap<String, String> DataMap;
    private TreeItem<String> rootNode;
    private TreeView<String> tree;
    
    ArrayList<TreeItem<String>> courseTreeItems; // all course tree items
    String selected_checkbox = null; // selected checkbox
    ArrayList<CheckBox> studentChoices = new ArrayList<>(); // all created chebox items
<<<<<<< HEAD

    private Modules Degree;
    private ArrayList<Courses> temporarySelectedItems;
=======
>>>>>>> cb73ed0b9252b4f8209fee121333f5026ed2c7a6
    
    Image icon = new Image("https://opportunityforum.info/wp-content/uploads/2022/04/folder.png");
    Image icon2 = new Image("https://opportunityforum.info/wp-content/uploads/2022/05/folder2.png");
     private final Node rootIcon = new ImageView(icon);

    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;

        //Trigger an action when the program is being closed
        stage.setOnCloseRequest(e -> {
            //e.consume();
            closeProgram();
        });

        scene1 = createSceneOne();
        scene2 = createSceneTwo();

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
                switchScenes(scene2);
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
<<<<<<< HEAD
                    CompletableFuture<Void> waiting = CompletableFuture.runAsync(() -> {
                        System.out.println("Fetching the degree...");
                        loadFirstLevel(degree_of_interest);
                        System.out.println("Fetching completed!");
                    });
=======
                    program_modules.clear();
                    loadFirstLevel(degree_of_interest);
>>>>>>> cb73ed0b9252b4f8209fee121333f5026ed2c7a6
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
<<<<<<< HEAD

=======
                    
                    
>>>>>>> cb73ed0b9252b4f8209fee121333f5026ed2c7a6
                    // build submodule with degree option
                    /*TreeItem<String> program = new TreeItem<>(main_degree_option, rootIcon);
                    rootNode.getChildren().add(program);
                    
                    // add courses as tree items
                    courseTreeItems = new ArrayList<>();

                    // find degree options
                    for (Modules module : program_modules_structure) {
                        //if (module.getModuleName().equalsIgnoreCase(main_degree_option)) {
                            //for (Modules submodule : module.getModuleLists()) {

                                // add course options
                                TreeItem<String> course_option = new TreeItem<>(module.getModuleName());
                                program.getChildren().add(course_option);

                                for (Courses course_module : module.getCoursesLists()) {
                                    TreeItem<String> course = new TreeItem<>(course_module.getCourseName());
                                    courseTreeItems.add(course); // add courses as tree items
                                    course_option.getChildren().add(course);

                                }

                                // save all courses under their modules (as treeItems)
                                //program_courses = new TreeMap<>();
                                //TreeMap< TreeItem<String>, List< TreeItem<String>>> courseTreeItemData = new TreeMap<>();
                                //courseTreeItemData.put(course_option, courseTreeItems);
                                //program_courses.put(submodule.getModuleName(), courseTreeItemData);

                            //}
                        //}
                    }*/

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
        /*Text text1 = new Text("");
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
        }*/

        /**
         * ***************************************************************************
         */
        // add action event for adding course buttion
        /*btnAddCourse.setOnAction(new EventHandler<ActionEvent>() {
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

<<<<<<< HEAD
        }
=======
            }
        });*/

>>>>>>> cb73ed0b9252b4f8209fee121333f5026ed2c7a6
        /**
         * *********************************************************************
         */
        // add action event for adding course buttion
        /*btnRemoveCourse.setOnAction(new EventHandler<ActionEvent>() {
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

                /*TreeItem selecteItem = tree.getSelectionModel().getSelectedItem();
                //TreeItem selecteItem = rootNode.getChildren().get(0);
                String s = selecteItem.getValue().toString();
                List<String> selecedCourses = getSelectedCourses(s);
                for (String Course : selecedCourses) {
                    CheckBox studentChoice = new CheckBox(Course);
                    rightPanelTop.getChildren().add(studentChoice);
                }

            }
        });*/
        /**
         * *************************************************************************
         */

        return scene2;
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
        //degree_program_structure.clear();
        //program_modules_structure.clear();
        program_modules.clear();

        ModuleAttributes attributes = new ModuleAttributes(); // get degree's attaributes to create a Modules instance
        attributes.getModuleAttributes("module", "id", degree_program);

        Degree = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3), null);
        main_degree_program = Degree.getModuleName(); // set
        
        //get degree options only
        var first_level = new DegreeOptions();
        first_level.getDegreeOptions(Degree);
        List<String> options = first_level.getOptions();
        
        for (var option : options) {
            program_modules.add(option);
        }
        
        /*for (Modules module : Degree.getModuleLists()) {
            degree_program_structure.add(module);
        }
        
        if (degree_program_structure.isEmpty()) {
            degree_program_structure.add(Degree);
        }*/

        
        /*ModuleStructure ms = new ModuleStructure();
        ms.getModuleStructure(Degree); // this step should handle fetching the entire structure of the degree
        List<Modules> m = Degree.getModuleLists();

        program_modules.clear();
        for (Modules module : m) {
            if (module.getModuleCredits().equals(Degree.getModuleCredits())) {
                program_modules.add(module.getModuleName());
                program_modules_structure.add(module);
            }
            //program_modules_structure.add(module);
            //program_modules.add(module.getModuleName());
        }

        if (program_modules.isEmpty()) {
            program_modules.add(Degree.getModuleName());
            program_modules_structure.add(Degree);
        }*/

    }
    
    private void loadStructure (Modules degree_option) {
        //Degree.clearModuleLists();
        //Degree.addModuleLists(degree_option);
        ModuleStructure ms = new ModuleStructure();
        ms.getModuleStructure(degree_option); // this step fetches the rest of the degree structure
        
        /*for (Modules module : degree_option.getModuleLists()) {
            program_modules_structure.add(module);
        }*/
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


    /*private void loadCourseModules(Modules module) {
        List<Modules> course_modules = module.getModuleLists();

        //add all courses
    }*/

    // capture selcted courses
    private List<String> getSelectedCourses(String selectedItem) {
        List<String> selectedCourses = new ArrayList<>();

        // loads all courses
        if (selectedItem.equals(main_degree_program) || selectedItem.equals(main_degree_option)) {
            //for (Modules module : program_modules_structure) {
                //if (module.getModuleName().equalsIgnoreCase(main_degree_option)) {
                    for (Modules submodule : Degree.getModuleLists()) {
                        // add courses
                        for (Courses course_module : submodule.getCoursesLists()) {
                            selectedCourses.add(course_module.getCourseName());
                        }

                    }
                //}
            //}
        } // loads all courses under a certain module
        else {
            for (Modules module : program_modules_structure) {
                //if (module.getModuleName().equalsIgnoreCase(main_degree_option)) {
                    //for (Modules submodule : module.getModuleLists()) {

                        if (module.getModuleName().equals(selectedItem)) {  // add al the courses under a module
                            for (Courses course_module : module.getCoursesLists()) {
                                selectedCourses.add(course_module.getCourseName());
                            }
                        } else { // add just one selected course
                            for (Courses course_module : module.getCoursesLists()) {
                                if (selectedItem.equals(course_module.getCourseName())) {
                                    selectedCourses.add(course_module.getCourseName());
                                }
                            }
                        }
                    }
                //}
           // }
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

    private void closeProgram(){
        if(studentNumber != null){
            int conformation = JOptionPane.showConfirmDialog(null, "Do you want to save your changes?",
                    "Confirm", JOptionPane.YES_NO_OPTION);
            if(conformation == 0){
                System.out.println(studentNumber);
            }
        }
        System.out.println("Exiting the program.");

    }
<<<<<<< HEAD

=======
>>>>>>> cb73ed0b9252b4f8209fee121333f5026ed2c7a6

    public static void main(String[] args) {
        launch();
    }

}

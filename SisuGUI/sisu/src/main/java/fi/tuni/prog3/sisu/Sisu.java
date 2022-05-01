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
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * JavaFX Sisu
 */
public class Sisu extends Application {

    // data handling containers
    private ObservableList<String> degree_programs = FXCollections.observableArrayList();
    private ObservableList<Modules> degree_program_structure = FXCollections.observableArrayList();

    private ObservableList<String> program_modules = FXCollections.observableArrayList();
    private ObservableList<Modules> program_modules_structure = FXCollections.observableArrayList();
    ObservableList<String> degree_info;

    // courses
    private ObservableList<String> orientation_modules = FXCollections.observableArrayList();
    private String main_degree_program;
    private String main_degree_id;
    private String main_degree_option;

    // save all the treeView courses as Modules
    private TreeMap<String, TreeMap< TreeItem<String>, List< TreeItem<String>>>> program_courses;

    //Degree name and id map
    private TreeMap<String, String> DataMap;
    private TreeItem<String> rootNode;
    private TreeView<String> tree;
    
    ArrayList<TreeItem<String>> courseTreeItems; // all course tree items
    String selected_checkbox = null; // selected checkbox
    ArrayList<CheckBox> studentChoices = new ArrayList<>(); // all created chebox items
    
    Image icon = new Image("https://opportunityforum.info/wp-content/uploads/2022/04/folder.png");
    Image icon2 = new Image("https://opportunityforum.info/wp-content/uploads/2022/05/folder2.png");
     private final Node rootIcon = new ImageView(icon);

    @Override
    public void start(Stage stage) {
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
        var scene = new Scene(tabPane, 1000, 680);

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

        // add add combo box to select degree opions
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

                    // set degree option
                    main_degree_option = program_modules.get(new_val.intValue());
                    for (Modules option : degree_program_structure) {
                        if (option.getModuleName().equalsIgnoreCase(main_degree_option)) {
                            loadStructure(option);
                        }
                    }


                    // build submodule with degree option
                    TreeItem<String> program = new TreeItem<>(main_degree_option, rootIcon);
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
                    }

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

        // add top and botton sections to the right panel
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

                /*TreeItem selecteItem = tree.getSelectionModel().getSelectedItem();
                //TreeItem selecteItem = rootNode.getChildren().get(0);
                String s = selecteItem.getValue().toString();
                List<String> selecedCourses = getSelectedCourses(s);
                for (String Course : selecedCourses) {
                    CheckBox studentChoice = new CheckBox(Course);
                    rightPanelTop.getChildren().add(studentChoice);
                }*/

            }
        });
        /**
         * *************************************************************************
         */

        stage.setScene(scene);
        stage.show();
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
        degree_program_structure.clear();
        program_modules_structure.clear();
        program_modules.clear();

        ModuleAttributes attributes = new ModuleAttributes(); // get degree's attaributes to create a Modules instance
        attributes.getModuleAttributes("module", "id", degree_program);

        var Degree = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3));
        main_degree_program = Degree.getModuleName(); // set
        
        //get degree options only
        var first_level = new DegreeOptions();
        first_level.getDegreeOptions(Degree);
        List<String> options = first_level.getOptions();
        
        for (var option : options) { // add options to the global list
            program_modules.add(option);
        }
        
        for (Modules module : Degree.getModuleLists()) {
            degree_program_structure.add(module);
            System.out.println(module.getModuleName());
        }
        
        if (degree_program_structure.isEmpty()) {
            degree_program_structure.add(Degree);
        }

        
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
        ModuleStructure ms = new ModuleStructure();
        ms.getModuleStructure(degree_option); // this step fetches the rest of the degree structure
        
        for (Modules module : degree_option.getModuleLists()) {
            program_modules_structure.add(module);
        }
    }


    private void loadCourseModules(Modules module) {
        List<Modules> course_modules = module.getModuleLists();

        //add all courses
    }

    // capture selcted courses
    private List<String> getSelectedCourses(String selectedItem) {
        List<String> selectedCourses = new ArrayList<>();

        // loads all courses
        if (selectedItem.equals(main_degree_program) || selectedItem.equals(main_degree_option)) {
            //for (Modules module : program_modules_structure) {
                //if (module.getModuleName().equalsIgnoreCase(main_degree_option)) {
                    for (Modules submodule : program_modules_structure) {
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

    public static void main(String[] args) {
        launch();
    }

}

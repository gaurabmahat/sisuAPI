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
    private ObservableList<Modules> degree_programs_structure = FXCollections.observableArrayList();

    private ObservableList<String> program_modules = FXCollections.observableArrayList();
    private ObservableList<Modules> program_modules_structure = FXCollections.observableArrayList();
    ObservableList<String> degree_info;

    // courses
    private ObservableList<String> orientation_modules = FXCollections.observableArrayList();
    private String main_degree_program;
    private String main_degree_option;

    // save all the treeView courses as Modules
    private TreeMap<String, TreeMap< TreeItem<String>, List< TreeItem<String>>>> program_courses;

    //Degree name and id map
    private TreeMap<String, String> DataMap;

    TreeItem<String> rootNode;
    TreeView<String> tree;

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
                    loadModules(degree_of_interest);
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

                    // build submodule with degree option
                    TreeItem<String> program = new TreeItem<>(main_degree_option);
                    rootNode.getChildren().add(program);

                    // find degree options
                    for (Modules module : program_modules_structure) {
                        if (module.getModuleName().equalsIgnoreCase(main_degree_option)) {
                            for (Modules submodule : module.getModuleLists()) {

                                // add course options
                                TreeItem<String> course_option = new TreeItem<>(submodule.getModuleName());
                                program.getChildren().add(course_option);

                                // add courses
                                List<TreeItem<String>> courseTreeItems = new ArrayList<>();
                                for (Courses course_module : submodule.getCoursesLists()) {
                                    TreeItem<String> course = new TreeItem<>(course_module.getCourseName());
                                    courseTreeItems.add(course);
                                    course_option.getChildren().add(course);

                                }

                                // save all courses under their modules (as treeItems)
                                program_courses = new TreeMap<>();
                                TreeMap< TreeItem<String>, List< TreeItem<String>>> courseTreeItemData = new TreeMap<>();
                                //courseTreeItemData.put(course_option, courseTreeItems);
                                //program_courses.put(submodule.getModuleName(), courseTreeItemData);

                            }
                        }
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

        /**
         * **************************************************************************
         */
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
                    rightPanelTop.getChildren().add(studentChoice);
                }

            }
        });

        /**
         * *********************************************************************
         */
        // add action event for adding course buttion
        btnRemoveCourse.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

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
    private void loadModules(String degree_program) {

        ModuleAttributes attributes = new ModuleAttributes(); // get degree's attaributes to create a Modules instance
        attributes.getModuleAttributes("module", "id", degree_program);

        var Degree = new Modules(attributes.get(0), attributes.get(1), attributes.get(2), attributes.get(3));
        main_degree_program = Degree.getModuleName(); // set
        ModuleStructure ms = new ModuleStructure();
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
            for (Modules module : program_modules_structure) {
                if (module.getModuleName().equalsIgnoreCase(main_degree_option)) {
                    for (Modules submodule : module.getModuleLists()) {
                        // add courses
                        for (Courses course_module : submodule.getCoursesLists()) {
                            selectedCourses.add(course_module.getCourseName());
                        }

                    }
                }
            }
        } // loads all courses under a certain module
        else {
            for (Modules module : program_modules_structure) {
                if (module.getModuleName().equalsIgnoreCase(main_degree_option)) {
                    for (Modules submodule : module.getModuleLists()) {

                        if (submodule.getModuleName().equals(selectedItem)) {  // add al the courses under a module
                            for (Courses course_module : submodule.getCoursesLists()) {
                                selectedCourses.add(course_module.getCourseName());
                            }
                        } else { // add just one selected course
                            for (Courses course_module : submodule.getCoursesLists()) {
                                if (selectedItem.equals(course_module.getCourseName())) {
                                    selectedCourses.add(course_module.getCourseName());
                                }
                            }
                        }
                    }
                }
            }
        }
        return selectedCourses;
    }

    public static void main(String[] args) {
        launch();
    }

}

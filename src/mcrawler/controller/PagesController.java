package mcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.transitions.hamburger.HamburgerBackArrowBasicTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import mcrawler.MainApp;

import java.io.File;

/**
 * Created by jonathan on 10/02/17.
 */
public class PagesController {

    private MainApp mainApp;

    @FXML
    private JFXButton btnNewPage;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private GridPane pagesContainer;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        scrollPane.fitToWidthProperty();

    }


    @FXML
    public void btnNewPageClick() {
        System.out.println("BTN New page click");
        mainApp.showNewPage();
    }


    public void scanPath(String path) {

        pagesContainer.setPadding(new Insets(5));
        pagesContainer.setHgap(10);
        pagesContainer.setVgap(10);

        if (mainApp.getRootPath().equals("")) {
            chooseDirectory();
        } else {

            int c = 0;
            int r = 0;

            File actual = new File(path);
            for( File f : actual.listFiles()){

                String name =  f.getName();

                System.out.println();

                JFXButton button = new JFXButton();
                button.setText(name);
                button.setPrefWidth(170);
                button.setPrefHeight(170);
                button.getStyleClass().add("primary-btn");

                button.setOnAction(new EventHandler<ActionEvent>() {
                    @Override
                    public void handle(ActionEvent event) {
                        System.out.println("Click page: " + name);
                        mainApp.showPage(name);
                    }
                });


                pagesContainer.add(button, c, r);

                c++;

                if(c % 2 == 0) {
                    r++;
                    c = 0;
                }

//            pagesContainer.addRow();


            }



        }


    }


    public void chooseDirectory() {


        JFXButton buttonChoose = new JFXButton();
        buttonChoose.setText("Select Directory");
        buttonChoose.setPrefWidth(400);
        buttonChoose.setPrefHeight(100);
        buttonChoose.getStyleClass().add("primary-btn");
        buttonChoose.setStyle("-fx-font-size: 25px");

        buttonChoose.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                System.out.println("Click choose dir");


                DirectoryChooser directoryChooser = new DirectoryChooser();

                directoryChooser.setTitle("This is my file ch");

                //Show open file dialog

                File file = directoryChooser.showDialog(null);

                if(file!=null) {

                    System.out.println(file.getPath());

                    mainApp.setRootPath(file.getPath());

                    mainApp.showPages();
                }




            }
        });


        pagesContainer.add(buttonChoose, 0, 0);




    }



}

package mcrawler.controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import mcrawler.MainApp;

/**
 * Created by jonathan on 10/02/17.
 */
public class PageController {

    private MainApp mainApp;

    @FXML
    GridPane pageContainer;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }


    public void showDetails(String name) {

        Label title = new Label();
        title.setText(name);
        title.getStyleClass().add("title");

        pageContainer.add(title, 0, 0);
       // pageName.setText(name);

    }






}

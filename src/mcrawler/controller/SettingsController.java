package mcrawler.controller;

import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.GridPane;
import javafx.stage.FileChooser;
import mcrawler.MainApp;

import java.io.File;

/**
 * Created by jonathan on 10/02/17.
 */
public class SettingsController {

    private MainApp mainApp;

    @FXML
    ScrollPane scrollPane;

    @FXML
    private GridPane settingsContainer;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        scrollPane.fitToWidthProperty();

        settingsContainer.setPadding(new Insets(5));
        settingsContainer.setHgap(10);
        settingsContainer.setVgap(10);
    }


}

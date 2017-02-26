package mcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXDialog;
import com.jfoenix.controls.JFXDialogLayout;
import com.jfoenix.controls.JFXHamburger;
import com.jfoenix.transitions.hamburger.HamburgerBasicCloseTransition;
import com.jfoenix.transitions.hamburger.HamburgerSlideCloseTransition;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import mcrawler.MainApp;

import java.util.Optional;

/**
 * Created by jonathan on 08/02/17.
 */
public class RootLayoutController {

    private MainApp mainApp;

    @FXML
    private JFXHamburger hamburger;

    @FXML
    private Pane panel;

    @FXML
    private StackPane stackPane;


    private HamburgerBasicCloseTransition burgerTask2;


    /* LINKS */

    @FXML
    private JFXButton linkPages;

    @FXML
    private JFXButton linkNewPage;



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;


        // Init burger menu
        //BackArrow
        burgerTask2 = new HamburgerBasicCloseTransition(hamburger);
        burgerTask2.setRate(-1);


        // hide panel on launch
        panel.setMaxWidth(0);
    }



    public void hidePanel() {
        panel.setMaxWidth(0);
    }



    @FXML
    public void hamburgerClick() {
        //System.out.println("hamburger click");

        burgerTask2.setRate(burgerTask2.getRate() * -1);
        burgerTask2.play();

        // show/hide panel
        if(burgerTask2.getRate() < 0) {
            hidePanel();
        } else {
            panel.setMaxWidth(200);
        }

    }


    @FXML
    public void linkPagesClick() {
        System.out.println("Pages click");
        hamburgerClick();
        mainApp.showPages();
    }

    @FXML
    public void linkNewPageClick() {
        System.out.println("New page click");
        hamburgerClick();
        mainApp.showNewPage();
    }

    @FXML
    public void linkDownloadsClick() {
        System.out.println("Downloads click");
        hamburgerClick();
        mainApp.showDownloads();
    }

    @FXML
    public void linkSettingsClick() {
        System.out.println("Settings click");
        hamburgerClick();
        mainApp.showSettings();
    }

    @FXML
    public void linkExitClick() {
        System.out.println("Exit click");

        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Exit MCrawler");
        alert.setHeaderText("Exit application ?");
        alert.setContentText("Are you sure to exit application ?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.get() == ButtonType.OK){
            hamburgerClick();
            mainApp.handleExit();
        }

    }




}

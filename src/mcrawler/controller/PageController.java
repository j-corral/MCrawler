package mcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import mcrawler.MainApp;
import java.io.File;
import java.util.Optional;

import static mcrawler.lib.Tools.delete;
import static mcrawler.lib.Tools.deleteDir;
import static mcrawler.lib.Tools.file_exists;

/**
 * Created by jonathan on 10/02/17.
 */
public class PageController {

    private MainApp mainApp;

    private JFXListView<Label> list = new JFXListView<Label>();

    @FXML
    Label title;

    @FXML
    JFXButton deletePage;

    @FXML
    ImageView preview = new ImageView();

    @FXML
    ScrollPane scrollPane;

    @FXML
    GridPane pageContainer;

    @FXML
    JFXButton btnDelete;

    @FXML
    JFXButton btnView;



    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        scrollPane.fitToWidthProperty();

        btnDelete.disableProperty().bind(Bindings.isEmpty(list.getSelectionModel().getSelectedItems()));
        btnView.disableProperty().bind(Bindings.isEmpty(list.getSelectionModel().getSelectedItems()));
    }


    public void showDetails(String name) {

        title.setText(name);
        title.getStyleClass().add("title");


        String previewFile = mainApp.getRootPath() + "/" + name + "/" + "preview.png";

        if(file_exists(previewFile)) {
            Image image = new Image("file:" + previewFile);
            preview.setImage(image);
            preview.setFitWidth(370);
            preview.setPreserveRatio(true);
            pageContainer.add(preview, 0, 0);
        }

        list.setLayoutY(14);
        list.setPrefHeight(300);
        list.setPrefWidth(370);


        initItems(name);



        pageContainer.add(list, 0, 1);

    }


    private void initItems(String name) {

        File actual = new File(mainApp.getRootPath() + "/" + name);

        for( File file : actual.listFiles()){
            list.getItems().add(new Label(file.getName()));
        }

    }


    @FXML
    public void btnDeleteClick() {
        System.out.println("BTN delete click");


        String file = "index.html";

        if( list.getSelectionModel().getSelectedItems().size() > 0){
            file = list.getSelectionModel().getSelectedItem().getText();
        }


        String filename =  mainApp.getRootPath() + "/" + title.getText() + "/" + file;


        if (file_exists(filename)) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete item");
            alert.setHeaderText("Delete file : " + file);
            alert.setContentText("Are you sure to delete this file ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){

                if(delete(filename)) {
                    list.getItems().clear();
                    initItems(title.getText());
                }

            }

        }

    }


    @FXML
    public void btnViewClick() {
        System.out.println("BTN view click");

        String name = title.getText();
        String file = "index.html";

        if( list.getSelectionModel().getSelectedItems().size() > 0){
            file = list.getSelectionModel().getSelectedItem().getText();
        }


        browse(name, file);
    }


    @FXML
    public void deletePageClick(){
        System.out.println("delete page click");


        String filename =  mainApp.getRootPath() + "/" + title.getText();


        if (file_exists(filename)) {

            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Delete this page");
            alert.setHeaderText("Delete page : " + title.getText());
            alert.setContentText("Are you sure to delete this page ?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){

                if(deleteDir(new File(filename))) {
                    mainApp.showPages();
                }

            }

        }





    }

    private void browse(String name, String file) {

        final WebView browser = new WebView();
        final WebEngine webEngine = browser.getEngine();

        System.out.println("Open file: " + file);

        String filepath =  mainApp.getRootPath() + "/" + name + "/" + file;

        if(file_exists(filepath)) {

            webEngine.load("file:" + filepath);

            Stage stage = new Stage();
            stage.setTitle(title.getText());

            BorderPane webpane = new BorderPane();

            webpane.setCenter(browser);

            Scene scene = new Scene(webpane);
            stage.setScene(scene);

            stage.show();

        } else {
            System.out.println("Error : file not found !");
        }

    }




}

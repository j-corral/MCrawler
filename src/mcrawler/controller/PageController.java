package mcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXListView;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import mcrawler.MainApp;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import static mcrawler.lib.Tools.*;

/**
 * Created by jonathan on 10/02/17.
 */
public class PageController {

    private MainApp mainApp;

    private JFXListView<Label> list = new JFXListView<Label>();

    private TreeView<String> tree = new TreeView<>();

    private String path;

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

        btnDelete.disableProperty().bind(Bindings.isEmpty(tree.getSelectionModel().getSelectedItems()));
        //btnView.disableProperty().bind(Bindings.isEmpty(tree.getSelectionModel().getSelectedItems()));
        btnView.setDisable(true);


        tree.getSelectionModel().selectedItemProperty().addListener(new javafx.beans.value.ChangeListener<TreeItem<String>>() {
            @Override
            public void changed(ObservableValue<? extends TreeItem<String>> observable, TreeItem<String> oldValue, TreeItem<String> newValue) {

                String filename = mainApp.getRootPath() + getSelectedPath();
                File file = new File(filename);

                //System.out.println("listen: " + filename);


                if(file.isDirectory()) {
                    btnView.setDisable(true);
                }
                else {
                    btnView.setDisable(false);
                }
            }
        });


    }





    public void showDetails(String name) {

        this.path = mainApp.getRootPath() + "/" + name;

        title.setText(name);
        title.getStyleClass().add("title");


        String previewFile = path + "/" + "preview.png";

        if(file_exists(previewFile)) {
            Image image = new Image("file:" + previewFile);
            preview.setImage(image);
            preview.setFitWidth(370);
            preview.setPreserveRatio(true);
            pageContainer.add(preview, 0, 0);
        }

        tree.setLayoutY(14);
        tree.setPrefHeight(300);
        tree.setPrefWidth(370);


        File file = new File(path);

        findFiles(file,null);

        pageContainer.add(tree, 0 , 1);

    }


    private void findFiles(File dir, TreeItem<String> parent) {
        TreeItem<String> root = new TreeItem<String>(dir.getName());
        root.setExpanded(true);
        try {
            File[] files = dir.listFiles();
            for (File file : files) {
                if (file.isDirectory()) {
                    System.out.println("directory:" + file.getCanonicalPath());
                    findFiles(file,root);
                } else {
                    System.out.println("     file:" + file.getCanonicalPath());
                    root.getChildren().add(new TreeItem<String>(file.getName()));
                }

            }
            if(parent==null){
                tree.setRoot(root);
            } else {
                parent.getChildren().add(root);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private String getSelectedPath() {
        StringBuilder pathBuilder = new StringBuilder();
        for (TreeItem<String> item = tree.getSelectionModel().getSelectedItem(); item != null ; item = item.getParent()) {

            pathBuilder.insert(0, item.getValue());
            pathBuilder.insert(0, "/");
        }
        String path = pathBuilder.toString();

        return path;
    }

    @FXML
    public void btnDeleteClick() {
        System.out.println("BTN delete click");


        String file = "index.html";
        String filename = null;

        if( tree.getSelectionModel().getSelectedItems().size() > 0){
            file = getSelectedPath();

            filename = mainApp.getRootPath() + file;
            System.out.println("Selected : " + filename);
            System.out.println("path : " + path);

        }



        if (filename != null && file_exists(filename)) {

            if(filename.equals(path)) {
                deletePageClick();
            } else {

                Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                alert.setTitle("Delete item");
                alert.setHeaderText("Delete file : " + file);
                alert.setContentText("Are you sure to delete this file ?");

                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK){

                    File rootFile = new File(path);
                    File dir = new File(filename);

                    if(delete(filename) || deleteDir(dir)) {
                        tree.setRoot(null);
                        findFiles(rootFile, null);
                        tree.refresh();
                    }

                }

            }


        }

    }


    @FXML
    public void btnViewClick() {
        System.out.println("BTN view click");

        String name = title.getText();
        String file = "index.html";

        if( tree.getSelectionModel().getSelectedItems().size() > 0){
            file = tree.getSelectionModel().getSelectedItem().getValue();
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

        String filepath =  path + "/" + file;

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

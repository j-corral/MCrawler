package mcrawler;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import mcrawler.controller.*;

import java.io.File;
import java.io.IOException;

public class MainApp extends Application {

    private Stage primaryStage;
    private BorderPane rootLayout;

    private String rootPath = "/home/jonathan/Bureau/testcrawler";
//    private String rootPath = ".";

    @Override
    public void start(Stage primaryStage) throws Exception{
        //Parent root = FXMLLoader.load(getClass().getResource("view/rootLayout.fxml"));
        this.primaryStage = primaryStage;
        this.primaryStage.setTitle("MCrawler");
        this.primaryStage.setResizable(false);
        //primaryStage.setScene(new Scene(root, 400, 600));
        //primaryStage.show();

        initRootLayout();


        showPages();


    }



    public void initRootLayout() {
        try {
            // Load root layout from fxml file.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class
                    .getResource("view/rootLayout.fxml"));
            rootLayout = (BorderPane) loader.load();

            // Show the scene containing the root layout.
            Scene scene = new Scene(rootLayout);

            // get style page.css
            String style = getClass().getResource("assets/css/page.css").toExternalForm();

            // apply stylesheet to the scene graph
            scene.getStylesheets().addAll(style);


            primaryStage.setScene(scene);

            // Give the controller access to the main app.
            RootLayoutController controller = loader.getController();
            controller.setMainApp(this);

            primaryStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    public void showPages() {
        try {
            // Load pages overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Pages.fxml"));
            AnchorPane pages = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(pages);

            // Give the controller access to the main app.
            PagesController controller = loader.getController();
            controller.setMainApp(this);


            controller.scanPath(rootPath);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showPage(String name) {
        try {
            // Load pages overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Page.fxml"));
            AnchorPane pages = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(pages);

            // Give the controller access to the main app.
            PageController controller = loader.getController();
            controller.setMainApp(this);


            controller.showDetails(name);


        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showNewPage() {
        try {
            // Load new page overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/NewPage.fxml"));
            AnchorPane newPage = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(newPage);

            // Give the controller access to the main app.
            NewPageController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public void showDownloads() {
        try {
            // Load new page overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Downloads.fxml"));
            AnchorPane downloads = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(downloads);

            // Give the controller access to the main app.
            DownloadsController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void showSettings() {
        try {
            // Load new page overview.
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(MainApp.class.getResource("view/Settings.fxml"));
            AnchorPane settings = (AnchorPane) loader.load();

            // Set person overview into the center of root layout.
            rootLayout.setCenter(settings);

            // Give the controller access to the main app.
            SettingsController controller = loader.getController();
            controller.setMainApp(this);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Stage getPrimaryStage() {
        return primaryStage;
    }

    public BorderPane getRootLayout() {
        return rootLayout;
    }


    public String getRootPath() {
        return rootPath;
    }

    public void setRootPath(String rootPath) {
        this.rootPath = rootPath;
    }

    public static void main(String[] args) {
        launch(args);
    }

    @FXML
    public void handleExit() {
        System.exit(0);
    }
}

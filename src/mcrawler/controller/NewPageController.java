package mcrawler.controller;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXCheckBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import mcrawler.MainApp;
import mcrawler.lib.Crawler;

import java.io.IOException;

/**
 * Created by jonathan on 10/02/17.
 */
public class NewPageController {

    private MainApp mainApp;


    @FXML
    private JFXTextField pageName;

    @FXML
    private JFXTextField pageUrl;

    @FXML
    private JFXTextField pageDepth;

    @FXML
    private JFXCheckBox keepMedias;

    @FXML
    private JFXButton btnSave;


    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

    }



    @FXML
    public void savePage() {

        String name = pageName.getText();
        String url = pageUrl.getText();
        String depth = pageDepth.getText();
        Boolean medias =  keepMedias.isSelected();

        String path = "/home/jonathan/Bureau/testcrawler/titi/" + name;
        /*String host =  "www.korben.info";
        String dir = "/home/jonathan/Bureau/testcrawler";*/

        //http://stackoverflow.com/questions/3341516/how-we-can-download-a-html-page-using-java


        System.out.println("Saving page...");
        System.out.println("Name: " + name);
        System.out.println("Url: " + url);
        System.out.println("Depth: " + depth);
        System.out.println("Keep medias: " + medias);


        Crawler crawler = new Crawler();

        String data = "";

        try {
            data = crawler.curl(url);

            crawler.createDirs(path);

            System.out.println("Data : " + data);

            crawler.save(path, data, "html");

            //crawler.takeScreenshot("preview", url);

        } catch (IOException e) {
            e.printStackTrace();
        }


        System.out.println("Page saved :)");



        //mainApp.showDownloads();
    }


}

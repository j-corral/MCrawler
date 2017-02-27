package mcrawler.controller;

import com.jfoenix.controls.*;
import com.jfoenix.validation.RequiredFieldValidator;
import de.jensd.fx.fontawesome.AwesomeIcon;
import de.jensd.fx.fontawesome.Icon;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.paint.Paint;
import mcrawler.MainApp;
import mcrawler.lib.Crawler;

import java.io.IOException;

import static mcrawler.lib.Tools.file_exists;
import static mcrawler.lib.Tools.isNumeric;
import static mcrawler.lib.Tools.validateUrl;


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


    RequiredFieldValidator validatorName;
    RequiredFieldValidator validatorUrl;
    RequiredFieldValidator validatorDepth;

    public void setMainApp(MainApp mainApp) {
        this.mainApp = mainApp;

        initValidator();
    }


    private void initValidator() {

        validatorName = new RequiredFieldValidator();
        validatorName.setMessage("Name is Required");
        validatorName.setIcon(new Icon(AwesomeIcon.WARNING,"2em",";","error"));


        pageName.getValidators().add(validatorName);
        pageName.focusedProperty().addListener((o,oldVal,newVal)->{
//            if(!newVal) pageName.validate();
            if(!newVal) getPageName();
        });


        validatorUrl = new RequiredFieldValidator();
        validatorUrl.setMessage("Valid URL is Required");
        validatorUrl.setIcon(new Icon(AwesomeIcon.WARNING,"2em",";","error"));

        pageUrl.getValidators().add(validatorUrl);
        pageUrl.focusedProperty().addListener((o,oldVal,newVal)->{
//            if(!newVal) pageUrl.validate();
            if(!newVal) getPageUrl();
        });


        validatorDepth = new RequiredFieldValidator();
        validatorDepth.setMessage("Positive number is Required");
        validatorDepth.setIcon(new Icon(AwesomeIcon.WARNING,"2em",";","error"));

        pageDepth.getValidators().add(validatorDepth);
        pageDepth.focusedProperty().addListener((o,oldVal,newVal)->{
//            if(!newVal) pageDepth.validate();
            if(!newVal) getPageDepth();
        });


    }

    private String getPageName() {

        String name = "";

        if(pageName.validate()) {

            name = pageName.getText();

            String filename = mainApp.getRootPath() + "/" + name;

            pageName.setFocusColor(Paint.valueOf("green"));
            pageName.setUnFocusColor(Paint.valueOf("green"));

            if(file_exists(filename)) {
                name = "";
                pageName.setFocusColor(Paint.valueOf("red"));
                pageName.setUnFocusColor(Paint.valueOf("red"));
            }


        } else {
            pageName.setFocusColor(Paint.valueOf("red"));
            pageName.setUnFocusColor(Paint.valueOf("red"));
        }

        return name;
    }


    private String getPageUrl() {

        String url = "";

        if(pageUrl.validate() && validateUrl(pageUrl.getText())) {

            url = pageUrl.getText();

            pageUrl.setFocusColor(Paint.valueOf("green"));
            pageUrl.setUnFocusColor(Paint.valueOf("green"));

        } else {
            pageUrl.setFocusColor(Paint.valueOf("red"));
            pageUrl.setUnFocusColor(Paint.valueOf("red"));
        }

        return url;
    }


    private int getPageDepth() {

        int depth = 0;

        if(pageDepth.validate() && isNumeric(pageDepth.getText())) {

            depth = Integer.parseInt(pageDepth.getText());

            pageDepth.setFocusColor(Paint.valueOf("green"));
            pageDepth.setUnFocusColor(Paint.valueOf("green"));

        } else {
            pageDepth.setFocusColor(Paint.valueOf("red"));
            pageDepth.setUnFocusColor(Paint.valueOf("red"));
        }

        return depth;
    }

    @FXML
    public void savePage() {

        String name = getPageName();
        String url = getPageUrl();
        int depth = getPageDepth();
        Boolean medias =  keepMedias.isSelected();


        if (name.length() > 0 && url.length() > 0 && depth >= 0) {

            String path = mainApp.getRootPath() + "/" + name;


            System.out.println("Saving page...");
            System.out.println("Name: " + name);
            System.out.println("Url: " + url);
            System.out.println("Depth: " + depth);
            System.out.println("Keep medias: " + medias);

            Crawler crawler = new Crawler();

            String data = "";

            try {
                crawler.createDirs(path);


                data = crawler.curl(url);


                System.out.println("Data : " + data);

                crawler.save(path, data, "html");

//            crawler.takeScreenshot(name, url);

            } catch (IOException e) {
                e.printStackTrace();
            }


            System.out.println("Page saved :)");


        } else {
            System.out.println("Error !");
        }





        //mainApp.showDownloads();
    }


}

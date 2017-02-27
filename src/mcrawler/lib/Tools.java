package mcrawler.lib;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

/**
 * Created by jonathan on 27/02/17.
 */
public class Tools {

    public static boolean file_exists(String filename) {

        File f = new File(filename);

        if(f.exists()) {
            return true;
        }

        return false;
    }


    public static boolean validateUrl(String url) {

        URL u = null;

        try {
            u = new URL(url);
        } catch (MalformedURLException e) {
            System.out.println("Invalid URL: " + url);
            return false;
        }

        try {
            u.toURI();
        } catch (URISyntaxException e) {
            System.out.println("Invalid URL: " + url);
            return false;
        }


        System.out.println("Valid URL: " + url);


        return true;
    }


    public static boolean isNumeric(String str) {
        return str.matches("\\d+");
    }


}

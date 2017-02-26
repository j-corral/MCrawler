package mcrawler.lib;

import java.io.*;
import java.net.URL;

/**
 * Created by jonathan on 26/02/17.
 */
public class Crawler {

    private URL url;

    public String curl(final String URL) throws IOException {
        String line = "", all = "";
        BufferedReader in = null;
        try {
            url = new URL(URL);
            in = new BufferedReader(new InputStreamReader(url.openStream()));

            while ((line = in.readLine()) != null) {
                all += line;
            }
        } finally {
            if (in != null) {
                in.close();
            }
        }

        return all;
    }


    public void takeScreenshot(String name, String url) {

        String apiKey = "71914252cd1da671c9573015575436a4";
//        String secretKey = "a921d59c48d10622a1275e01c7e13a41";
        String viewport = "1366x768";
        String width = "800";

        String request = "http://api.screenshotlayer.com/api/capture?access_key="+apiKey+"&url="+url+"&viewport="+viewport+"&width="+width;

        String destination = "/home/jonathan/Bureau/testcrawler/toto/" + name + ".png";


        try {
            saveImage(request, destination);

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    public boolean save(String path, String data, String format) {

        if(format == null) {
            format = "html";
        }


        String filename = path + "." + format;

        try{
            PrintWriter writer = new PrintWriter(filename, "UTF-8");
            writer.println(data);
            writer.close();

            return true;
        } catch (IOException e) {
            // do something
        }

        return false;
    }

    public void saveImage(String imageUrl, String destinationFile) throws IOException {
        URL url = new URL(imageUrl);
        InputStream is = url.openStream();
        OutputStream os = new FileOutputStream(destinationFile);

        byte[] b = new byte[2048];
        int length;

        while ((length = is.read(b)) != -1) {
            os.write(b, 0, length);
        }

        is.close();
        os.close();
    }


    public boolean createDirs(String path) {

        File file = new File(path);

        boolean result = file.mkdirs();

        System.out.println("Status = " + result);

        return result;
    }


}

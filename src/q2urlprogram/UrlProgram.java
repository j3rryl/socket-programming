package q2urlprogram;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class UrlProgram {
    public static URL url;
    public static BufferedReader urlReader;
    public static void main(String[] args) {
        try {
            url = new URL("http://buyya.com/");
            urlReader = new BufferedReader(new InputStreamReader(url.openStream()));
            String line;
            while ((line = urlReader.readLine()) != null) {
                System.out.println(line);
            }
            urlReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

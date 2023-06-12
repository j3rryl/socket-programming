package sampleexercises.urlencoding;

import java.io.*;
import java.net.*;
public class TextBasedSearchEngine {
    private String searchEngine;
    public TextBasedSearchEngine(String searchEngine) {
        this.searchEngine = searchEngine;
    }
    public void doSearch(String queryString) {
        try {
            // open a url connection
            URL url = new URL(searchEngine);
            URLConnection connection = url.openConnection();
            connection.setDoOutput(true);
            // write the query string to the search engine
            PrintStream ps = new PrintStream(connection.getOutputStream());
            ps.println(queryString);
            ps.close();
            // read the result
            DataInputStream input =
                    new DataInputStream(connection.getInputStream());
            String inputLine = null;
            while((inputLine = input.readLine())!= null) {
                System.out.println(inputLine);

            }
        }
        catch(Exception e) {
            e.printStackTrace();

        }
    }
    public static void main(String[] args) throws Exception{
        QueryStringFormatter formatter =
                new QueryStringFormatter("https://search.yahoo.com/search"); //change from http to https
        formatter.addQuery("newwindow","1");
                formatter.addQuery("q","Xingchen Chu & Rajkumar Buyya");
        // search it via yahoo
        TextBasedSearchEngine search =
                new TextBasedSearchEngine(formatter.getEngine());
        search.doSearch(formatter.getQueryString());
    }
}

package crawler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Crawler {
    private static final String URL_REGEX = "<a\\s+(?:[^>]*?\\s+)?href=\"([^\"]*)\"";
    private static final String DOMAIN_REGEX = "^(https?://)?([a-zA-Z0-9.-]+\\.[a-zA-Z]{2,6}).*";

    private Set<String> visitedUrls;

    public Crawler() {
        visitedUrls = new HashSet<>();
    }

    public void crawl(String startUrl) {
        crawlURL(startUrl);
    }

    private void crawlURL(String url) {
        if (!visitedUrls.contains(url)) {
            try {
                visitedUrls.add(url);
                System.out.println("Crawling: " + url);

                URL targetUrl = new URL(url);
                BufferedReader reader = new BufferedReader(new InputStreamReader(targetUrl.openStream()));
                StringBuilder pageContent = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    pageContent.append(line);
                }

                Set<String> links = extractLinks(pageContent.toString(), targetUrl.getHost());
                for (String link : links) {
                    crawlURL(link);
                }

                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private Set<String> extractLinks(String pageContent, String host) {
        Set<String> links = new HashSet<>();

        Pattern pattern = Pattern.compile(URL_REGEX);
        Matcher matcher = pattern.matcher(pageContent);

        while (matcher.find()) {
            String link = matcher.group(1);
            if (!link.startsWith("http")) {
                if (link.startsWith("/")) {
                    link = host + link;
                } else {
                    link = host + "/" + link;
                }
            }
            if (isValidURL(link)) {
                links.add(link);
            }
        }

        return links;
    }

    private boolean isValidURL(String url) {
        Pattern pattern = Pattern.compile(DOMAIN_REGEX);
        Matcher matcher = pattern.matcher(url);
        return matcher.matches();
    }

    public static void main(String[] args) {
        Crawler crawler = new Crawler();
        crawler.crawl("https://www.alvocatfresh.co.ke");
    }
}

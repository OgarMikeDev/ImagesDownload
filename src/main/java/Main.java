import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class Main {
    public static void main(String[] args) throws IOException {
        String pathDirectory = "data/images/";
        String url = "https://kollagen.life/";
        Document document = Jsoup.connect(url).get();
        Elements elements = document.select("img");
        int number = 1;
        for (Element elem : elements) {
            String linkImage = elem.attr("src");
            System.out.println(linkImage);
            String extension = linkImage
                    .replaceAll("^.+\\.", "")
                    .replace("?.+$", "");

            String filePath = pathDirectory + number++ + "." + extension;
            try {
                URLConnection urlConnection = new URL(linkImage).openConnection();
                InputStream inputStream = urlConnection.getInputStream();
                FileOutputStream fileOutputStream = new FileOutputStream(filePath);
                int readByte;
                while ((readByte = inputStream.read()) != -1) {
                    fileOutputStream.write(readByte);
                }
                fileOutputStream.flush();
                inputStream.close();
                fileOutputStream.close();
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }
        }
    }
}

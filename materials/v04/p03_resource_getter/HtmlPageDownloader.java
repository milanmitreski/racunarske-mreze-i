package url.g1.resourcegetter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class HtmlPageDownloader {

    private static final String URL_STRING = "http://poincare.matf.bg.ac.rs/~ivan.ristovic";

    public static void main(String[] args) throws IOException {
        URL u = new URL(URL_STRING);

        /* try ( BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        u.openStream()
                )
        )) {
            String line;
            while((line = in.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } */

        URLConnection uconn = u.openConnection();
        String encoding = uconn.getContentEncoding();

        // Ako server nije specifikovao encoding, onda postavljamo UTF-8 kao defaultni
        if(encoding == null)
            encoding = "UTF-8";

        try( BufferedReader in = new BufferedReader(
                new InputStreamReader(
                        uconn.getInputStream(),
                        encoding
                )
        )) {
            String line;
            while((line = in.readLine()) != null)
                System.out.println(line);
        } catch (IOException e) {

        }
    }
}

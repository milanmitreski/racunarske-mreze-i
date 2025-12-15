package v04.resource_getter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;

public class BinaryFileGetter {

    private static final String BINARY_FILE_URL = "https://www.matf.bg.ac.rs/wp-content/uploads/uvod-u-informatiku.pdf";

    public static void main(String[] args) {
        try {
            URL u = new URL(BINARY_FILE_URL);
            URLConnection uconn = u.openConnection();

            String contentType = uconn.getContentType();
            int contentLength = uconn.getContentLength();

            System.out.println(contentType);
            System.out.println(contentLength);

            if(contentLength == -1 || contentType.startsWith("text")) {
                System.err.println("Nije u pitanju binarna datoteka");
                return;
            }

            String fileName = u.getFile();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
            System.out.println(fileName);

            try(BufferedInputStream in = new BufferedInputStream(uconn.getInputStream());
                BufferedOutputStream out = new BufferedOutputStream(
                        new FileOutputStream(
                                "materials/v04/resource_getter/" + fileName
                        )
                )
            ) {
                for(int i = 0; i < contentLength; i++) {
                    int b = in.read();
                    out.write(b);
                }
            }
        } catch (IOException e) {
            System.err.println("Greska pri radu!");
        }
    }

}

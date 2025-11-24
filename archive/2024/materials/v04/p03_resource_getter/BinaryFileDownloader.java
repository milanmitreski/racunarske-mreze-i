package materials.v04.p03_resource_getter;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class BinaryFileDownloader {

    private static final String BINARY_FILE_URL = "http://www.matf.bg.ac.rs/images/matf.gif";

    public static void main(String[] args) {
        try {
            URL u = new URL(BINARY_FILE_URL);
            URLConnection uconn = u.openConnection();

            // Tip fajla koji se nalazi na datom URL-u
            String contentType = uconn.getContentType();
            // Duzina fajla koji se nalazi na datom URL-u
            int contentLength = uconn.getContentLength();

            // Ukoliko je contentLength -1 (to znaci da se desila neka greska
            // pri ocitavanju duzine fajla (najverovatnije u slucaju da fajl ne postoji itd))
            // ili ukoliko je tip fajla tekstualan - bacamo gresku jer moramo da imamo fajl
            // da bi otvorili input stream i taj fajl mora biti binarni a ne tekstualni
            if(contentLength == -1 || contentType.startsWith("text"))
                throw new IOException("Content is not a binary file!");

            BufferedInputStream input = new BufferedInputStream(
                    uconn.getInputStream()
            );

            String fileName = u.getFile();
            fileName = fileName.substring(fileName.lastIndexOf("/") + 1);
            System.out.println(fileName);

            try(BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream("src/url/resourcegetter/" + fileName)
            )) {
                for(int i = 0; i < contentLength; i++) {
                    int b = input.read();
                    out.write(b);
                }
            }
        } catch (IOException e) {
            // TODO
        }
    }
}

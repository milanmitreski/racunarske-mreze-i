package v04.resource_getter;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;

public class TextFileGetter {
    private static final String TEXTUAL_FILE_URL = "https://poincare.matf.bg.ac.rs/~milan.bankovic/preuzimanje/ar/pravila_igre.txt";

    public static void main(String[] args) {
        try {
            URL u = new URL(TEXTUAL_FILE_URL);
            URLConnection uconn = u.openConnection();

            String contentType = uconn.getContentType();
            String encoding = uconn.getContentEncoding();

            System.out.println(contentType);
            System.out.println(encoding);

            if(!contentType.startsWith("text")) {
                System.err.println("Nije tekstualna datoteka");
                return;
            }

            if(encoding == null) {
                encoding = "UTF-8";
            }

            String fileName = u.getFile();
            fileName = fileName.substring(fileName.lastIndexOf('/') + 1);
            System.out.println(fileName);

            try(Scanner in = new Scanner(new BufferedInputStream(uconn.getInputStream()));
                PrintStream out = new PrintStream(
                        new FileOutputStream(
                                "materials/v04/resource_getter/" + fileName
                        ),
                        true,
                        encoding
                )
            ) {
                while(in.hasNextLine()) {
                    out.println(in.nextLine());
                }
            }
        } catch (IOException e) {
            System.err.println("Greska pri radu!");
        }
    }
}

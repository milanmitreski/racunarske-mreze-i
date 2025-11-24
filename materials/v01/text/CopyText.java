package materials.v01.text;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class CopyText {
    private static final String IN_TXT = "materials/v01/text/in.txt";
    private static final String OUT_TXT = "materials/v01/text/out.txt";
    private static final int BUFF_SIZE = 512;

    public static void main(String[] args) {
        try (
                BufferedReader bfin = new BufferedReader(
                        new InputStreamReader(
                            new FileInputStream(IN_TXT),
                            StandardCharsets.UTF_8
                        )
                );
                BufferedWriter bfout = new BufferedWriter(
                        new OutputStreamWriter(
                            new FileOutputStream(OUT_TXT),
                            StandardCharsets.UTF_8
                        )
                )
        ) {
            char[] buf = new char[BUFF_SIZE];
            int bytesRead = 0;
            while((bytesRead = bfin.read(buf)) != -1) {
                bfout.write(buf, 0, bytesRead);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Greska pri otvaranju fajlova!");
        } catch (IOException e) {
            System.err.println("Greska pri Input/Output operacijama!");
        }
    }
}

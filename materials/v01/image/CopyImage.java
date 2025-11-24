package materials.v01.image;

import java.io.*;

public class CopyImage {
    private static final String IN_IMG = "materials/v01/image/in.PNG";
    private static final String OUT_IMG = "materials/v01/image/out.PNG";
    private static final int BUFF_SIZE = 512;

    public static void main(String[] args) {
        try (
                BufferedInputStream bfin = new BufferedInputStream(
                      new FileInputStream(IN_IMG)
                );
                BufferedOutputStream bfout = new BufferedOutputStream(
                      new FileOutputStream(OUT_IMG)
                )
        ) {
            byte[] buf = new byte[BUFF_SIZE];
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

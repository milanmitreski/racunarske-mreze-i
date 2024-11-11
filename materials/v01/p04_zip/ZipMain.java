package p04_zip;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.GZIPOutputStream;

final class ZipMain {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();

            // Shows how easy it is to write to a different stream type - the API is the same, the only
            // difference is the type of the stream we wish to read from or write to

            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream("c01_Streams/p04_zip/in.txt")
            );
            BufferedOutputStream out = new BufferedOutputStream(
                    new GZIPOutputStream(
                            new FileOutputStream("c01_Streams/p04_zip/out.gz")
                    )
            );

            byte[] buf = new byte[512];
            int bytesRead = 0;
            while ((bytesRead = in.read(buf)) != -1)
                out.write(buf, 0, bytesRead);

            in.close();
            out.close();

            long stop = System.currentTimeMillis();
            System.out.println("Finished in: " + (stop - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

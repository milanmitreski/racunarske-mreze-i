package p02_copy_image;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

final class CopyImageBufferedMain {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();

            BufferedInputStream in = new BufferedInputStream(
                    new FileInputStream("c01_Streams/p02_copy_image/in.PNG")
            );
            BufferedOutputStream out = new BufferedOutputStream(
                    new FileOutputStream("c01_Streams/p02_copy_image/out.PNG")
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

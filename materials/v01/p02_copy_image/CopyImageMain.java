package p02_copy_image;

import java.io.FileInputStream;
import java.io.FileOutputStream;

final class CopyImageMain {

    public static void main(String[] args) {
        try {
            long start = System.currentTimeMillis();

            // We don't work with characters here, so we operate on the streams directly
            FileInputStream in = new FileInputStream("c01_Streams/i02_copy_image/in.PNG");
            FileOutputStream out = new FileOutputStream("c01_Streams/i02_copy_image/out.PNG");

            int b;
            while ((b = in.read()) != -1)
                out.write(b);

            in.close();
            out.close();

            long stop = System.currentTimeMillis();
            System.out.println("Finished in: " + (stop - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

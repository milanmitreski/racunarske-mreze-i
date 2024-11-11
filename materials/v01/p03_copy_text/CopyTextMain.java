package p03_copy_text;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;

final class CopyTextMain {

    public static void main(String[] args) {

        // Naive implementation

        try {
            long start = System.currentTimeMillis();

            // Note that we do not need to use Readers here - we are just copying bytes and there is no
            // need for viewing the data on a "higher" level than bytes
            InputStreamReader in = new InputStreamReader(new FileInputStream("c01_Streams/p03_copy_text/in.txt"));
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream("c01_Streams/p03_copy_text/out.txt"));

            // read() returns int which contains the next byte value, -1 indicates the end of the stream
            int b;
            while ((b = in.read()) != -1)
                out.write(b);

            // Notice the return type of read() method - it is `int` instead of `byte`. There is a specific
            // reason for this, for those who do not wish to dive into reasons: only the least significant bytes
            // are filled when read() is called, for example if we were to read bytes: 0xABCDEF, calling read
            // would return: 0xAB (1 byte). Same goes for write(), only the least significant byte is used.
            // Details: https://stackoverflow.com/questions/4659659/why-does-inputstreamread-return-an-int-and-not-a-byte

            in.close();
            out.close();

            long stop = System.currentTimeMillis();
            System.out.println("Finished in: " + (stop - start));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

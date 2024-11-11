package p01_intro;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.zip.GZIPInputStream;

final class Main {

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        // Use autocomplete and JDoc to experiment with these classes

        try {
            // OutputStreams notable methods - write(), flush()
            OutputStream out;
            // Subclasses
            FileOutputStream fout = new FileOutputStream("path/to/whatever.txt");
            ByteArrayOutputStream bout;

            // InputStreams notable methods - read()
            InputStream in;
            // Subclasses
            FileInputStream fin = new FileInputStream("path/to/whatever.txt");
            ByteArrayInputStream bin;
            GZIPInputStream gz;

            // Buffered variants - can be constructed from an existing stream
            BufferedInputStream bfin = new BufferedInputStream(fin);
            BufferedOutputStream bfout = new BufferedOutputStream(fout);

            // In applications, you will usually have complex character sets (which use multiple
            // bytes for encoding characters) and working with bytes is not recommended.
            // There are classes which work with characters, rather than individual bytes - Readers
            // and Writers. They have similar hierarchy as streams
            Reader r;
            Writer w;
            InputStreamReader sr = new InputStreamReader(fin, StandardCharsets.UTF_8);
            OutputStreamWriter sw = new OutputStreamWriter(fout, StandardCharsets.UTF_8);

            // There are more subclasses, but we will not use them
            FileReader fi;
            FileWriter fw;

            // When done, release all resources and close open streams
            sr.close();
            sw.close();

            // Stream class implements AutoCloseable interface, therefore they can be used
            // in the following syntax (instead of manually closing the streams)
            try (InputStream sAutoClosed = new FileInputStream("file.txt")) {
                // do work here
            } catch (Exception e) {
                // handle exception here
            } finally {
                // no need to close the stream here, it will be done automatically
            }

            // Use PrintStream when you want to "format" common data types. It allows you to
            // use print() and println() methods. PrintStream extends FilterOutputStream,
            // System.out is a PrintStream for example.
            // Beware to pass correct buffered streams, otherwise you will not have real buffering
            // like in example below (BufferedOutputStream should have been used instead):
            PrintStream ps1 = new PrintStream(
                    new FileOutputStream("file.txt"), false
            );  // no buffering
            PrintStream ps2 = new PrintStream(
                    new BufferedOutputStream(
                        new FileOutputStream("file.txt")
                    ),
                    false
            );  // has buffering
            ps2.println("Hello");
            ps2.println(2);
            // Needs to be explicitly flushed, unless autoFlush argument is set to true during construction
            ps2.flush();
            // PrintStream can flush automatically on `newline` as well

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}

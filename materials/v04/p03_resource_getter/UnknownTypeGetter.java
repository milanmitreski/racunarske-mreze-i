package materials.v04.p03_resource_getter;

import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.net.MalformedURLException;
import java.net.URL;

public class UnknownTypeGetter {

    private static final String URL_STRING = "http://alas.matf.bg.ac.rs/~mr241073/index.html";

    public static void main(String[] args) {

        try {
            URL u = new URL(URL_STRING);
            System.out.println("Dobijeni tip je: " + u.getContent().getClass().getName());

            Class<?>[] types = new Class[3];
            types[0] = String.class;
            types[1] = Reader.class;
            types[2] = InputStream.class;
            Object o = u.getContent(types);

            if(o instanceof String) {
                System.out.println("STRING");
                System.out.println(o);
            } else if(o instanceof Reader) {
                System.out.println("READER");
                int c;
                Reader r = (Reader) o;
                while((c = r.read()) != -1)
                    System.out.print((char)c);
                r.close();
            } else if(o instanceof InputStream) {
                System.out.println("INPUT STREAM");
                int c;
                InputStream in = (InputStream) o;
                while((c = in.read()) != -1)
                    System.out.println(c);
                in.close();
            } else {
                System.out.println("UNEXPECTED TYPE " + u.getContent().getClass());
            }
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

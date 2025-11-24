package materials.v01.intro;

import java.io.*;

public class Main {
    public static void main(String[] args) {
        OutputStream out;

        try (
                FileOutputStream fout = new FileOutputStream("materials/v01/intro/file.txt");
                BufferedOutputStream bfout = new BufferedOutputStream(fout);
        ) {
            bfout.write(97);
            bfout.write(new byte[] {97, 98, 99}, 1, 2); // ispisuje abc
        } catch (FileNotFoundException e) {
            System.err.println("Fajl ne moze da se otvori!");
        } catch (IOException e) {
            System.err.println("Greska pri Input/Output");
        }

        InputStream in;

        try (
                FileInputStream fin = new FileInputStream("materials/v01/intro/file.txt");
                BufferedInputStream bfin = new BufferedInputStream(fin);
        ) {
            int b = bfin.read();
            System.out.println(b);
            byte[] read = new byte[2];
            int bb = bfin.read(read);
            for(int i = 0; i < bb; i++) {
                System.out.println(read[i]);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Fajl ne moze da se otvori!");
        } catch (IOException e) {
            System.err.println("Greska pri Input/Output");
        }

        Writer w;
        Reader r;

        try (
                FileWriter fout = new FileWriter("materials/v01/intro/file-rw.txt");
                BufferedWriter bfout = new BufferedWriter(fout);
        ) {
            bfout.write('a'); // a
            bfout.write(new char[] {'a', 'b', 'c'}, 1, 2); // bc
            bfout.write("def"); // def
        } catch (FileNotFoundException e) {
            System.err.println("Fajl ne moze da se otvori!");
        } catch (IOException e) {
            System.err.println("Greska pri Input/Output");
        }

        try (
                FileReader fin = new FileReader("materials/v01/intro/file-rw.txt");
                BufferedReader bfin = new BufferedReader(fin);
        ) {
            // System.out.println(bfin.readLine());
            char[] buf = new char[6];
            int len = bfin.read(buf);
            System.out.println(buf);
        } catch (FileNotFoundException e) {
            System.err.println("Fajl ne moze da se otvori!");
        } catch (IOException e) {
            System.err.println("Greska pri Input/Output");
        }
    }
}

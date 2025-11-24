package materials.v01.advanced;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        try (PrintStream ps = new PrintStream(
                new BufferedOutputStream(
                    new FileOutputStream("materials/v01/advanced/file.txt")
                ),
                true
            )
        ) {
            ps.println("Hello World!");
            ps.println(250);
            ps.println(true);
            ps.print("Great! ");
            ps.println(3.2);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        try (Scanner sc = new Scanner(
                new FileInputStream("materials/v01/advanced/file.txt")
            )
        ) {
            // Opcija 1 - po tokenima
            /* while(sc.hasNext()) {
                System.out.println(sc.next());
            } */

            // Opcija 2 - po linijama
            while(sc.hasNextLine()) {
                System.out.println(sc.nextLine());
            }
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}

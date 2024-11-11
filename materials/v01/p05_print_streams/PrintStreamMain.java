package p05_print_streams;

import java.io.*;
import java.util.Scanner;

final class PrintStreamMain {

    public static void main(String[] args) {

        try (PrintStream pw = new PrintStream(
                new BufferedOutputStream(
                        new FileOutputStream("c01_Streams/p05_print_streams/pw_out.txt")
                )
            )
        ) {
            pw.print("Hello");
            pw.println(" world!");
            pw.println(1);
            pw.println(3.4444);

            float temp = 30.2f;
            pw.printf("Today is %4.2f C.", temp);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        // We can also use Scanner to read from any InputStream
        try (Scanner sc = new Scanner(new FileInputStream("c01_Streams/p05_print_streams/pw_out.txt"))) {
            if (sc.hasNextLine())
                System.out.println(sc.nextLine());
            System.out.println(sc.nextInt());  // 1
            System.out.println(sc.nextLine()); // NOTE: we are yet to scan the next line!
            System.out.println(sc.nextLine()); // 3.4444
            System.out.println(sc.nextLine());
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}

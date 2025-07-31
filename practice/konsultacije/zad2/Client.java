package konsultacije.zad2;

import java.io.*;
import java.net.Socket;
import java.util.Scanner;

public class Client {


    public static void main(String[] args) {
        try (Socket client = new Socket("localhost", Server.PORT);
            Scanner sc = new Scanner(System.in)) {

            String request;
            while (!(request = sc.nextLine()).equals("bye")) {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(client.getOutputStream()),true);
                writer.println(request);
                BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()));
                System.out.println(reader.readLine());
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

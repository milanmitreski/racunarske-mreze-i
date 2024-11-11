package url.g2.querybuilder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Random;
import java.util.Scanner;

final class QueryBuilderTest {

    public static void main(String[] args) {
        int jokeId = new Random().nextInt(500) + 1;
        String endpoint = "http://api.icndb.com/jokes/" + jokeId  + "/";

        QueryBuilder qb = new QueryBuilder(endpoint);

        try (Scanner sc = new Scanner(System.in)) {
            System.out.println("Enter your first name:");
            qb.appendQuery("firstName", sc.nextLine());
            System.out.println("Enter your last name:");
            qb.appendQuery("lastName", sc.nextLine());
        }

        System.err.print("Sending GET request: ");
        System.err.println(qb);

        try {
            URL u = qb.toUrl();

            String json;
            try (BufferedReader in = new BufferedReader(new InputStreamReader(u.openStream()))) {
                json = in.readLine();
            }

            // Extract joke field value from json response
            int jokeFieldPos = json.indexOf("joke") + 8;
            String joke = json.substring(jokeFieldPos, json.indexOf('"', jokeFieldPos));

            System.out.println("A random joke to brighten up your day:");
            System.out.println(joke);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

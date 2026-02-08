package ShoppingCart;

import javax.xml.crypto.Data;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public class CartServer {

    public static final int PORT = 5555;
    public static final int BUFF_SIZE = 1024;

    private static final List<Item> groceries = new ArrayList<>();
    private static final Map<String,List<Item>> carts = new TreeMap<>();

    public static void main(String[] args) {
        loadGroceries();

        try (DatagramSocket datagramSocket = new DatagramSocket(PORT)) {
            while (true) {


                byte[] buff = new byte[BUFF_SIZE];
                DatagramPacket req = new DatagramPacket(buff, buff.length);
                datagramSocket.receive(req);
                String reqString = new String(req.getData(), 0, req.getLength());

                String[] parts = reqString.split(" ");

                String name = parts[0];
                String responseString = "";

                if (parts.length == 1) {
                    if (carts.containsKey(name)) {
                        responseString = "Vec postoji to ime, postavite drugo!";

                    } else {
                        carts.put(name, new ArrayList<>());
                        StringBuilder sb = new StringBuilder();
                        sb.append("Welcome " + name + "\n");
                        sb.append("Avaialble groceries: \n");
                        for (var item : groceries) {
                            sb.append("- " + item.getName() + " -- " + item.getPrice() + "$\n");
                        }
                        responseString = sb.toString();
                    }
                } else {
                    switch (parts[1]) {
                        case "ADD":
                            if (parts.length != 3) {
                                responseString = "Pogresna komanda";
                                break;
                            }
                            String groceryName = parts[2];
                            double price = 0;
                            boolean found = false;
                            for (var item : groceries) {
                                if (item.getName().equals(groceryName)) {
                                    price = item.getPrice();
                                    found = true;
                                }
                            }

                            if (!found) {
                                responseString = "Ne postoji u prodavnici ta namirnica!";
                                break;
                            }

                            carts.get(name).add(new Item(groceryName, price));
                            responseString = "Added to cart: " + groceryName;
                            break;
                        case "VIEW":
                            if (parts.length != 2) {
                                responseString = "Pogresna komanda";
                                break;
                            }

                            double totalPrice = 0;
                            responseString = "[";
                            for (var item : carts.get(name)) {
                                totalPrice += item.getPrice();
                                responseString += item.getName() + ",";
                            }
                            responseString += "]";
                            responseString += " Total: " + totalPrice + "$";

                            break;
                        case "PAY":
                            if (parts.length != 2) {
                                responseString = "Pogresna komanda";
                                break;
                            }

                            double totalPricePay = 0;
                            for (var item : carts.get(name)) {
                                totalPricePay += item.getPrice();
                            }

                            responseString = "Total: " + totalPricePay + "$";
                            carts.get(name).clear();
                            break;
                        default:
                            responseString = "Pogresna komanda!";
                            break;
                    }
                }

                System.out.println(responseString);
                DatagramPacket resp = new DatagramPacket(responseString.getBytes(), responseString.length(), req.getAddress(), req.getPort());
                datagramSocket.send(resp);
            }

        } catch (IOException e){
            e.printStackTrace();
        }
    }

    private static void loadGroceries() {
        groceries.add(new Item("bread",1.0));
        groceries.add(new Item("milk",1.5));
        groceries.add(new Item("apple",0.7));
        groceries.add(new Item("eggs",2.0));
    }

}

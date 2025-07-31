package konsultacije.zad2;

import java.io.*;
import java.net.Socket;
import java.util.List;
import java.util.Optional;

public class ObradaKlijenta implements Runnable{

    private Socket client;
    private List<Sahista> sahisti;

    ObradaKlijenta(Socket client, List<Sahista> sahisti) {
        this.client = client;
        this.sahisti = sahisti;
    }

    public Optional<Sahista> select(int id) {
        synchronized (sahisti) {
            return sahisti.stream().filter(s -> s.getId() == id).findFirst();
        }
    }

    public void insert(String naziv) {
        synchronized (sahisti) {
            int lastId = sahisti.size() >0 ? sahisti.getLast().getId() : 0;
            sahisti.add(new Sahista(naziv, lastId +1,Server.DFAULT_ELO ));
        }
    }

    public void update(int id, int deltae) {
        synchronized (sahisti) {
            for(Sahista s: sahisti) {
                if (s.getId() == id) {
                    s.setElo(s.getElo() + deltae);
                }
            }
        }
    }

    @Override
    public void run() {

        try (BufferedReader fromClient = new BufferedReader(new InputStreamReader(client.getInputStream()));
             PrintWriter toClient = new PrintWriter(new OutputStreamWriter(client.getOutputStream()), true)) {
            System.out.println("Konektovan je klijent " + client.getInetAddress() + ":" + client.getPort());

            while (true) {
                String request = fromClient.readLine();
                String [] partsOfRequest = request.split(" ");

                if (partsOfRequest[0].equals("sel")) {
                    try {
                        if (partsOfRequest.length == 2) {
                            int id = Integer.parseInt(partsOfRequest[1]);
                            Optional<Sahista> odabraniSahista = select(id);
                            if (odabraniSahista.isPresent()) {
                                toClient.println(odabraniSahista.get());
                            } else {
                                toClient.println("Ne postoji sahista sa ovim id-em");
                            }
                        } else {
                            toClient.println("Los format zahteva");
                        }
                    } catch (NumberFormatException e) {
                        toClient.println("Id mora ceo biti ceo broj");
                    }
                } else if (partsOfRequest[0].equals("ins")) {

                    String naziv = "";
                    for (int i=1; i<partsOfRequest.length;i++) {
                        naziv += partsOfRequest[i] + " ";
                    }

                    insert(naziv);
                    toClient.println("ins je uspesno izvrsen");
                } else if (partsOfRequest[0].equals("upd")) {
                    try {
                        if (partsOfRequest.length == 3) {
                            int id = Integer.parseInt(partsOfRequest[1]);
                            int deltae = Integer.parseInt(partsOfRequest[2]);
                            update(id, deltae);
                            toClient.println("upd uspesno izvrsen");
                        } else {

                            toClient.println("Los format zahteva");
                        }
                    } catch (NumberFormatException e) {
                        toClient.println("id i deltae moraju biti brojevi");
                    }
                } else {
                    toClient.println("Los format zahteva");
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}

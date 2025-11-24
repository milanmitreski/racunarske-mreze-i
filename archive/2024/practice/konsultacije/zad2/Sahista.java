package konsultacije.zad2;

public class Sahista {

    private final String naziv;
    private final int id;
    private int elo;

    Sahista(String naziv, int id, int elo) {
        this.naziv = naziv;
        this.id = id;
        this.elo = elo;
    }

    public String getNaziv() {
        return naziv;
    }

    public int getElo() {
        return elo;
    }

    public int getId() {
        return id;
    }

    public void setElo(int elo) {
        this.elo = elo;
    }

    @Override
    public String toString() {
        return  this.naziv +": " + this.elo;
    }
}

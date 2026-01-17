package v08.chess;

public class ChessPlayer {
    private final String name;
    private Integer elo;

    public ChessPlayer(String name, Integer elo) {
        this.name = name;
        this.elo = elo;
    }

    public String getName() {
        return name;
    }

    public Integer getElo() {
        return elo;
    }

    public synchronized void setElo(Integer elo) {
        this.elo = elo;
    }

    @Override
    public String toString() {
        return getName() + " " + getElo();
    }
}

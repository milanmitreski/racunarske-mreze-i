package konsultacije0908.jun2_zadatak2;

import java.util.*;

public class Spojnice {
    private Map<String, String> correctPairs;
    private List<String> leftColumn;
    private List<String> rightColumn;
    private int guessedPairs = 0;

    public Spojnice(Map<String, String> pairs) {
        this.correctPairs = Map.copyOf(pairs);
        initColumns();
    }

    private void initColumns() {
        this.leftColumn = new ArrayList<>();
        this.rightColumn = new ArrayList<>();

        for(Map.Entry<String, String> pair : this.correctPairs.entrySet()) {
            this.leftColumn.add(pair.getKey());
            this.rightColumn.add(pair.getValue());
        }

        Collections.shuffle(this.rightColumn);
    }

    public boolean guess(int key, char guessed) {
        int index = guessed - 'A';
        String leftPair = this.leftColumn.get(key);
        this.leftColumn.set(key, "");
        if(this.correctPairs.get(leftPair).equals(this.rightColumn.get(index))) {
            this.rightColumn.set(index, "");
            this.guessedPairs++;
            return true;
        }
        return false;
    }

    public String getLeftColumnString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Leva kolona:;");
        for(String s : this.leftColumn) {
            if(s.isEmpty())
                continue;
            sb.append(s);
            sb.append(';');
        }
        return sb.toString();
    }

    public String getRightColumnString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Desna kolona:;");
        char letter = 'A';
        for(int i = 0; i < this.rightColumn.size(); i++) {
            if(this.rightColumn.get(i).isEmpty())
                continue;
            char l = (char) (letter + i);
            sb.append(l);
            sb.append(". ");
            sb.append(this.rightColumn.get(i));
            sb.append(";");
        }
        return sb.toString();
    }

    public String getLeftPair(int key) {
        return this.leftColumn.get(key);
    }

    public int getGuessedPairs() {
        return guessedPairs;
    }
}

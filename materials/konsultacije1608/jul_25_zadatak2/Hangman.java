package konsultacije1608.jul_25_zadatak2;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

public class Hangman {
    private String name;
    private int lives;
    private String word;
    private char[] guessed;
    private List<Character> lettersGuessed;
    private boolean gameOver;

    private static List<String> words;

    public static void loadWords(String fileName) {
        words = new ArrayList<>();
        try(Scanner in = new Scanner(new BufferedInputStream(new FileInputStream(fileName)))) {
            while(in.hasNextLine()) {
                words.add(in.nextLine());
            }
        } catch (FileNotFoundException e) {
            System.err.println("FATAL ERROR: Words loading failed.");
            throw new RuntimeException(e);
        }
    }

    public Hangman(String name, int lives) {
        this.name = name;
        this.lives = lives;
        this.word = pickWord();
        this.guessed = word.replaceAll("[A-Z]", "-").toCharArray();
        this.lettersGuessed = new ArrayList<>();
        this.gameOver = false;
    }

    public String guess(char letter) {
        if(lettersGuessed.contains(letter))
            return "You have already guessed that number.";
        lettersGuessed.add(letter);

        boolean letterGuessed = false;

        for(int i = 0; i < word.length(); i++) {
            if(word.charAt(i) == letter) {
                guessed[i] = letter;
                letterGuessed = true;
            }
        }

        if(!letterGuessed) lives--;

        if(lives == 0) {
            gameOver = true;
            return "GAME OVER! " + this.name + ", vise srece drugi put! WORD: " + word;
        }
        if(!(new String(guessed)).contains("-")) {
            gameOver = true;
            return "GAME OVER! Bravo, " + this.name + "! WORD: " + word;
        }

        return currentState();
    }

    private String pickWord() {
        ThreadLocalRandom random = ThreadLocalRandom.current();
        return words.get(random.nextInt(words.size()));
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public String currentState() {
        return "Rec: " + Arrays.toString(guessed) + "; Broj zivota: " + lives + "; Isprobana slova: " + lettersGuessed.toString();
    }
}

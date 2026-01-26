package v10.quiz;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.SocketException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class QuizServer {

    private static final String QUESTIONS_FILE = "materials/v10/quiz/questions.txt";
    private static final List<Question> questions = new ArrayList<>();
    private static final HashMap<Client, Session> sessions = new HashMap<>();
    private static final Integer PORT = 9000;
    private static final Integer BUFF_SIZE = 2048;

    public static void main(String[] args) {
        try {
            loadQuestions();
        } catch (FileNotFoundException e) {
            System.err.println("Unable to load questions. Check the file with question data.");
            return;
        }

        try(DatagramSocket server = new DatagramSocket(PORT)) {
            byte[] inputBuff = new byte[BUFF_SIZE], outputBuff;
            DatagramPacket input, output;
            while(true) {
                input = new DatagramPacket(inputBuff, BUFF_SIZE);
                server.receive(input);

                Client c = new Client(input.getAddress(), input.getPort());
                if(sessions.containsKey(c)) {
                    Session s = sessions.get(c);
                    String answer = new String(
                            input.getData(),
                            input.getOffset(),
                            input.getLength(),
                            StandardCharsets.UTF_8
                    );
                    boolean isCorrect = s.getActiveQuestion().checkAnswer(answer);
                    StringBuilder responseBuilder = new StringBuilder(isCorrect ? "Tacno" : "Netacno");
                    if(s.hasNextQuestion()) {
                        Question next = s.nextQuestion();
                        responseBuilder.append(" ").append(next.getQuestion());
                        s.setActiveQuestion(next);
                    } else {
                        sessions.remove(c);
                    }

                    outputBuff = responseBuilder.toString().getBytes(StandardCharsets.UTF_8);
                } else {
                    Session s = new Session(questions);
                    sessions.put(c, s);
                    Question first = s.nextQuestion();
                    s.setActiveQuestion(first);

                    outputBuff = first.getQuestion().getBytes(StandardCharsets.UTF_8);
                }
                output = new DatagramPacket(outputBuff, outputBuff.length, c.getClientAddress(), c.getClientPort());
                server.send(output);
            }
        } catch (SocketException e) {
            System.err.println("Unable to create Socket");
            return;
        } catch (IOException e) {
            System.err.println("Network error");
            return;
        }
    }

    private static void loadQuestions() throws FileNotFoundException {
        try(Scanner fin = new Scanner(new FileInputStream(QUESTIONS_FILE))) {
            String[] parts;
            while(fin.hasNextLine()) {
                parts = fin.nextLine().split("\\|");
                questions.add(new Question(parts[0], parts[1]));
            }
        }
    }

}

package v10.quiz;

import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Session {
    private Question activeQuestion;
    private List<Question> questionsToBeAnswered;

    public Session(List<Question> questions) {
        this.questionsToBeAnswered = new ArrayList<>(questions);
    }

    public Question getActiveQuestion() {
        return activeQuestion;
    }

    public void setActiveQuestion(Question activeQuestion) {
        this.activeQuestion = activeQuestion;
    }

    public boolean hasNextQuestion() {
        return !questionsToBeAnswered.isEmpty();
    }

    public Question nextQuestion() {
        return questionsToBeAnswered.removeFirst();
    }
}

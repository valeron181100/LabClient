package PhoneNTalks;


public class Question {
    private String question;
    private String answer;
    public Question(String question_text, String question_answer){
        this.answer = question_answer;
        this.question = question_text;
    }

    public String getAnswer() {
        return answer;
    }

    public String getQuestion() {
        return question;
    }

    @Override
    public int hashCode() {
        int prime = 28;
        int result = 1;
        result = prime * result + question.length() + answer.length();
        return result;
    }

    @Override
    public String toString() {
        return question + "-" + answer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Question other = (Question) obj;
        if (question.intern() != other.question.intern())
            return false;
        if (answer.intern() != other.answer.intern())
            return false;
        return true;
    }
}


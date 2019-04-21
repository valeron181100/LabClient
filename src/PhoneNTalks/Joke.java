package PhoneNTalks;

public class Joke {
    private String joke;
    private String answer;
    public Joke(String joke_text, String joke_answer){
        this.answer = joke_answer;
        this.joke = joke_text;
    }

    public String getAnswer() {
        return answer;
    }

    public String getJoke() {
        return joke;
    }

    @Override
    public int hashCode() {
        int prime = 18;
        int result = 1;
        result = prime * result + joke.length() + answer.length();
        return result;
    }

    @Override
    public String toString() {
        return joke + "-" + answer;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Joke other = (Joke) obj;
        if (joke.intern() != other.joke.intern())
            return false;
        if (answer.intern() != other.answer.intern())
            return false;
        return true;
    }
}

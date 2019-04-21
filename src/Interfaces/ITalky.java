package Interfaces;

import Humanlike.Human;
import PhoneNTalks.Joke;
import PhoneNTalks.JokeNQuestionMachine;
import PhoneNTalks.Question;
import mainpkg.Main;

import java.util.concurrent.TimeUnit;

public interface ITalky {
    default void ask(Human hm){
        set_current_quest(JokeNQuestionMachine.getRandomQuestion());
        Main.pause(hm.toString() + ": \""+ getCurrent_question().getQuestion() + "\"");
    }
    default void answer(Human hm){
        if (getCurrent_question() != null){
            Main.pause(hm.toString() + ": \""+ getCurrent_question().getAnswer() + "\"");
        }
        else{
            Main.pause(hm.toString() + ": \"Я не знаю, что тебе сказать(((\"");
        }
    }
    default void say_joke(Human hm){
        Joke joke = JokeNQuestionMachine.get_joke(hm.get_humorlvl());
        set_current_joke(joke);
        Main.pause(hm.toString() + ": \""+joke.getJoke() + "\"");
    }
    default void answ_joke(Human hm){
        if (getCurrent_joke() != null){
            Main.pause(hm.toString() + ": \""+ getCurrent_joke().getAnswer() + "\"");
        }
        else{
            Main.pause(hm.toString() + ": \"Я не знаю, что тебе сказать(((\"");
        }
    }
    default void say(Human hm, String message){
        Main.pause(hm.toString()+": "+message);
    }
    void set_current_quest(Question current_question);
    void set_current_joke(Joke current_joke);
    Question getCurrent_question();
    Joke getCurrent_joke();
    void end();
}

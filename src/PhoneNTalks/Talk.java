package PhoneNTalks;

import Buildings.House;
import Buildings.Room;
import Humanlike.*;
import Interfaces.ITalky;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class Talk implements ITalky {
    private ArrayList<Human> members;
    private Question current_question;
    private Joke current_joke;
    private int talk_id;
    private House house;

    public Talk(House house, Room room, Human ... people){
        members = new ArrayList<>();
        for(Human p : people){
            if (p.get_position().getRoom().equals(room)){
                members.add(p);
            }
        }
        if (this.members.size() > 0) {
            talk_id = house.getTalks().size();
            this.house = house;
            house.addTalk(this);
            members.forEach(p -> p.set_cur_talk(this));
        }
    }

    @Override
    public void set_current_quest(Question current_question) {
        this.current_question = current_question;
    }

    @Override
    public void set_current_joke(Joke current_joke) {
        this.current_joke = current_joke;
    }

    @Override
    public Question getCurrent_question() {
        return current_question;
    }

    @Override
    public Joke getCurrent_joke() {
        return current_joke;
    }

    public void add_members(Human ... people){
        for(Human p : people){
            if (p.get_position().getRoom().equals(members.get(0).get_position().getRoom())){
                members.add(p);
            }
        }
    }

    public void remove_members(Human ... people){
        members.removeAll(Arrays.asList(people));
    }

    @Override
    public void end(){
        house.removeTalk(talk_id);
    }

    public int getTalk_id() {
        return talk_id;
    }

    @Override
    public int hashCode() {
        int index = this.talk_id;
        return index;
    }

    @Override
    public String toString() {
        return "Talk no. " + this.talk_id;
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

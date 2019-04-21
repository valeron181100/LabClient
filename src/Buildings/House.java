package Buildings;

import Humanlike.Human;
import PhoneNTalks.Phone;
import PhoneNTalks.PhoneStation;
import PhoneNTalks.Talk;
import mainpkg.Main;

import java.security.spec.ECField;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;

public class House {
    private Room lobby;
    private ArrayList<Human> people;
    private ArrayList<Talk> talks;
    private PhoneStation station;

    public House(Room lobby, Human... people) throws HouseException {
        talks = new ArrayList<>();
        this.people = new ArrayList<>();
        if (lobby.IsLobby())
            this.lobby = lobby;
        else {
            throw new HouseException("Room is not lobby!!!");
        }
        this.people.addAll(Arrays.asList(people));
        lobby.set_house(this);
        for(Room r : lobby.get_rooms()){
            r.set_house(this);
        }
        station = new PhoneStation();
        Main.pause("Дом создан.");
    }

    public PhoneStation getStation() {
        return station;
    }

    public void clear() {
        people.clear();
    }



    public void add(Human... people) {
        this.people.addAll(Arrays.asList(people));
        Position pa = this.people.get(0).get_position();
        String k = "";
        for (int i = 0; i < this.people.size(); i++){
            Human p = this.people.get(i);
            Position pos = p.get_position();
            pos.setHouse(this);
            p.set_position(pos);
            p.setPhone(new Phone(p.get_position().getHouse().getStation(), p));
        }
        Main.pause("Люди добавлены в дом.");
    }

    public ArrayList<Human> get_people() {
        return people;
    }

    public Room getLobby() {
        return lobby;
    }

    public ArrayList<Talk> getTalks(){
        return talks;
    }

    public void addTalk(Talk talk){
        talks.add(talk.getTalk_id(), talk);
    }

    public Talk getTalk(int id){
        ArrayList<Talk> b = this.talks;
        return talks.get(id);
    }

    public void removeTalk(Talk talk){
        talks.remove(talk);
    }

    public void removeTalk(int id){
        talks.remove(id);
    }

    @Override
    public String toString() {
        return "The House number " + this.hashCode();
    }

    @Override
    public int hashCode() {
        int prime = 18;
        int result = 0;
        result = prime * result + lobby.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        House other = (House) obj;
        if (!lobby.equals(other.lobby))
            return false;
        if (!people.equals(other.people))
            return false;
        return true;

    }



}





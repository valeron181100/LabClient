package Buildings;

import Enums.RoomType;
import Humanlike.Human;
import PhoneNTalks.Joke;

import java.util.ArrayList;
import java.util.Arrays;

public class Room {
    private ArrayList<Human> people;
    private RoomType type;
    private Room[] rooms;
    private boolean is_lobby;
    private House house;

    public Room(RoomType roomType, Human ... people){
        this.people = new ArrayList<>();
        if (people != null) {
            this.people.addAll(Arrays.asList(people));
        }
        type = roomType;
        is_lobby = roomType == RoomType.Lobby;
        this.people.forEach(p->p.set_position(new Position(house, this)));
    }

    public ArrayList<Room> get_rooms(){
        ArrayList<Room> roomArrayList = new ArrayList<Room>();
        roomArrayList.addAll(Arrays.asList(rooms));
        return roomArrayList;
    }

    public boolean IsLobby(){
        return is_lobby;
    }

    public RoomType get_roomType(){
        return type;
    }

    public void add_people(Human ... people){
        this.people.addAll(Arrays.asList(people));
        this.people.forEach(p->p.set_position(new Position(this.house, this)));
    }

    public ArrayList<Human> get_people(){
        return people;
    }

    public void set_house(House house){
        this.house = house;
    }

    public void add_rooms(Room ... rooms){
        if (this.is_lobby){
            this.rooms = rooms;
        }
    }

    @Override
    public String toString() {
        return type.toString();
    }

    public void rmPeople(Human ... people){
        this.people.removeAll(Arrays.asList(people));
    }

    @Override
    public int hashCode() {
        int prime = 17;
        int result = 0;
        int isLobby = is_lobby ? 1 : 0;
        result = prime * result + type.toString().length() + isLobby;
        for(Human h : people){
            result += h.hashCode();
        }
        if (is_lobby)
            for(Room r : rooms){
                result += r.hashCode();
            }
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
        Room other = (Room) obj;
        if (is_lobby != other.is_lobby)
            return false;
        if (!people.equals(other.people))
            return false;
        if (type != other.type){
            return false;
        }
        if (!rooms.equals(other.rooms)){
            return false;
        }
        return true;

    }
}

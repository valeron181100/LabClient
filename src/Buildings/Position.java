package Buildings;


public class Position {
    private Room room;
    private House house;

    public Position(House house, Room room){
        this.house = house;
        this.room = room;
    }

    public House getHouse() {
        return house;
    }

    public Room getRoom() {
        return room;
    }

    public void setHouse(House house) {
        this.house = house;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    @Override
    public String toString() {
        return "Room: " + room.toString() + ", House: " + house.toString();
    }

    @Override
    public int hashCode() {
        int prime = 19;
        int result = 1;
        result = prime * result + room.hashCode() + house.hashCode();
        return result;
    }


}

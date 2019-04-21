package Humanlike;

import Buildings.*;
import Clothes.*;
import Enums.*;
import Interfaces.*;
import PhoneNTalks.Phone;
import PhoneNTalks.Talk;
import mainpkg.Main;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

public abstract class Human {
    private String Name;
    private boolean isHair;
    private int Years;
    private Color HairColor;
    private boolean isMan;
    private BodyType bodyType;
    private Mood mood;
    private boolean is_drink;
    private IPhone phone;
    private int humor_lvl;
    private int current_talk_id;
    private Position position;
    private boolean is_busy;
    private Costume costume;

    public Human(String name, boolean is_hair, int years, Color hairColor,
                 boolean is_man, BodyType bodyType, boolean isDrink,
                 int humor_lvl){
        if (name.length() == 0) throw new NameException(this);
        Name = name;
        isHair = is_hair;
        Years = years;
        HairColor = hairColor;
        isMan = is_man;
        this.bodyType = bodyType;
        is_drink = isDrink;
        this.humor_lvl = humor_lvl;
        costume = new Costume();
        mood = Mood.Good;

        Main.pause("Человек под именем " + this.getName() + " создан.");
    }
    public Human(String name, boolean is_hair, int years,
                 boolean is_man, BodyType bodyType, boolean isDrink,
                 int humor_lvl){
        if (name.length() == 0) throw new NameException(this);
        Name = name;
        isHair = is_hair;
        Years = years;
        HairColor = null;
        isMan = is_man;
        this.bodyType = bodyType;
        is_drink = isDrink;
        this.humor_lvl = humor_lvl;
        costume = new Costume();
        mood = Mood.Good;
        Main.pause("Человек под именем " + this.getName() + " создан.");
    }

    public String getName(){
        return Name;
    }

    public boolean isHairs(){
        return isHair;
    }

    public int getYears() {
        return Years;
    }

    public boolean isMan() {
        return isMan;
    }

    public Color getHairColor() {
        return HairColor;
    }

    public void changeHairColor(Color color){
        HairColor = color;
        Main.pause(String.format("Цвет волос у персонажа %s был изменён на %s",
                this.toString(), this.HairColor.toString()));
    }

    public BodyType getBodyType() {
        return bodyType;
    }

    public void changeBodyType(BodyType type){
        bodyType = type;
        Main.pause(String.format("Тело персонажа %s изменёно на %s",
                this.toString(), type.toString()));
    }

    public void setPhone(IPhone phone) {
        this.phone = phone;
    }

    public void drink_alcohol(){
        if (is_drink) {
            Mood[] moods = Mood.values();
            int first_border = 0;
            int second_border = 9;
            int rand_index = first_border + (int) (Math.random() *
                    ((second_border - first_border) + 1));
            mood = moods[rand_index];
            Main.pause("Персонаж "+ this.toString() + " выпил алкоголь.");
            Main.pause("Настроение персонажа " + this.toString() + " было изменено на " + this.getMood().toString());
        }
        else{
            Main.pause(String.format("Персонаж %s не пьёт.", this.toString()));
        }
    }

    public Mood getMood() {
        return mood;
    }

    public void change_mood(Mood type){
        mood = type;
        Main.pause(String.format("Настроение персонажа %s было изменёно на %s",
                this.toString(), type.toString()));
    }

    public void set_positive_mood(){
        Mood[] moods = Mood.values();
        int first_border = 0;
        int second_border = 4;
        int rand_index = first_border + (int) (Math.random() *
                ((second_border - first_border) + 1));
        mood = moods[rand_index];
        Main.pause(String.format("Настроение персонажа %s было изменёно на %s",
                this.toString(), mood.toString()));
    }

    public void set_negative_mood(){
        Mood[] moods = Mood.values();
        int first_border = 5;
        int second_border = 9;
        int rand_index = first_border + (int) (Math.random() *
                ((second_border - first_border) + 1));
        mood = moods[rand_index];
        Main.pause(String.format("Настроение персонажа %s было изменёно на %s",
                this.toString(), mood.toString()));
    }

    public IPhone get_phone(){
        return phone;
    }

    public void set_humorlvl(int lvl){
        humor_lvl = lvl;
        Main.pause(String.format("Уровень юмора персонажа %s был изменён на %s",
                this.toString(), lvl));
    }

    public int get_humorlvl(){
        return humor_lvl;
    }

    public void set_position(Position pos){
        this.position = pos;

    }

    public boolean isDrink(){
        return is_drink;
    }

    public Talk get_cur_talk(){
        return this.get_position().getHouse().getTalk(current_talk_id);
    }

    public void set_cur_talk(Talk talk){
        if (this.get_position().getHouse().getTalk(talk.getTalk_id()) != null) {
            current_talk_id = talk.getTalk_id();
        }
    }

    public Position get_position(){
        return position;
    }

    public void Goto(Room room){
        if (!position.getRoom().IsLobby()){
            position.getRoom().rmPeople(this);
            position.setRoom(position.getHouse().getLobby());
            position.getRoom().add_people(this);
            Main.pause(this.toString() + " перешёл в " + position.getRoom().toString());
            if (position.getHouse().getLobby().get_rooms().contains(room)){
                position.getRoom().rmPeople(this);
                position.setRoom(room);
                position.getRoom().add_people(this);
                Main.pause(this.toString() + " перешёл в " + position.getRoom().toString());
            }
        }
        else{
            if (position.getHouse().getLobby().get_rooms().contains(room)){
                position.getRoom().rmPeople(this);
                position.setRoom(room);
                position.getRoom().add_people(this);
                Main.pause(this.toString() + " перешёл в " + position.getRoom().toString());
            }
        }
    }

    public boolean search_here(Human human){
        Main.pause(String.format("%s: Ищу %s", this.toString(), human.toString()));
        ArrayList<Human> people_here = this.position.getRoom().get_people();
        Talk monolog = new Talk(this.get_position().getHouse(),
                this.get_position().getRoom(), this);
        if(!people_here.contains(human)){
            monolog.say(this, "Персонажа "+human.toString() + " здесь нет(((");
            monolog.end();
            return false;
        }
        else{
            monolog.say(this, "Персонаж "+human.toString() + " найден)))");
            monolog.end();
            return true;
        }
    }

    public Costume get_costume(){
        return costume;
    }

    public void set_costume(Costume costume){
        this.costume = costume;
        Main.pause(String.format("Костюм персонажа %s было изменёно на %s",
                this.toString(), costume.toString()));
    }

    public boolean is_busy(){
        return is_busy;
    }

    public void becomeBusy(){
        is_busy = true;
    }

    public void becomeFree(){
        is_busy = false;
    }

    @Override
    public int hashCode() {
        int prime = 20;
        int result = 1;
        int is_hair = isHair ? 1 : 0;
        int is_man = isMan ? 1 : 0;
        int isDrink = is_drink ? 1 : 0;
        int busy = is_busy ? 1 : 0;
        result = prime * result + Name.length() + is_hair + Years +
                HairColor.toString().length() + is_man + bodyType.toString().length() +
                mood.toString().length() + isDrink + phone.hashCode() + humor_lvl +
                current_talk_id + position.hashCode() + busy +costume.hashCode();
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
        Human other = (Human) obj;
        if (Name.intern() != other.Name.intern()) {
            return false;
        }
        if (isHair != other.isHair) {
            return false;
        }
        if (Years != other.Years) {
            return false;
        }
        if (HairColor != other.HairColor) {
            return false;
        }
        if (isMan != other.isMan) {
            return false;
        }
        if (bodyType != other.bodyType) {
            return false;
        }
        if (mood != other.mood) {
            return false;
        }
        if (is_drink != other.is_drink) {
            return false;
        }
        if (!phone.equals(other.phone)) {
            return false;
        }
        if (humor_lvl != other.humor_lvl) {
            return false;
        }
        if (current_talk_id != other.current_talk_id) {
            return false;
        }
        if (!position.equals(other.position)) {
            return false;
        }
        if (is_busy != other.is_busy) {
            return false;
        }
        if (!costume.equals(other.costume)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return Name;
    }
}
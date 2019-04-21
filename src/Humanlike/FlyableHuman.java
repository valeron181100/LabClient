package Humanlike;

import Enums.*;
import mainpkg.Main;

import java.util.concurrent.TimeUnit;

public abstract class FlyableHuman extends Human {
    private PropellerModel propellerModel;

    public FlyableHuman(String name, boolean is_hair, int years,
                        Color hairColor, boolean is_man,
                        BodyType bodyType, boolean isDrink, int humor_lvl) {
        super(name, is_hair, years, hairColor, is_man, bodyType, isDrink, humor_lvl);
    }

    public FlyableHuman(String name, boolean is_hair, int years, boolean is_man,
                        BodyType bodyType, boolean isDrink, int humor_lvl) {
        super(name, is_hair, years, is_man, bodyType, isDrink, humor_lvl);
    }


    public PropellerModel getPropellerModel() {
        return propellerModel;
    }

    public void setPropellerModel(PropellerModel propellerModel) {
        this.propellerModel = propellerModel;
    }

    public void fly(){
        Main.pause("Персонаж" + this.toString() + " взлетел.");
    }

    public void land(){
        Main.pause("Персонаж" + this.toString() + " приземлился.");
    }
}

package Humanlike;

import Enums.BodyType;
import Enums.Color;

public class Witch extends FlyableHuman {
    public Witch(String name, boolean is_hair, int years,
                        Color hairColor, boolean is_man,
                        BodyType bodyType, boolean isDrink, int humor_lvl) {
        super(name, is_hair, years, hairColor, is_man, bodyType, isDrink, humor_lvl);
    }

    public Witch(String name, boolean is_hair, int years, boolean is_man,
                        BodyType bodyType, boolean isDrink, int humor_lvl) {
        super(name, is_hair, years, is_man, bodyType, isDrink, humor_lvl);
    }

    public Witch transformate(FlyableHuman hm){
        if (hm.isHairs()){
            return new Witch(hm.getName(), hm.isHairs(), hm.getYears(), getHairColor(), hm.isMan(),
                    hm.getBodyType(), hm.isDrink(), get_humorlvl());
        }
        else{
            return new Witch(hm.getName(), hm.isHairs(), hm.getYears(), hm.isMan(),
                    hm.getBodyType(), hm.isDrink(), get_humorlvl());
        }
    }
}

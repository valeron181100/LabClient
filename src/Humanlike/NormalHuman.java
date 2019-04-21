package Humanlike;

import Enums.BodyType;
import Enums.Color;

public class NormalHuman extends Human {

    public NormalHuman(String name, boolean is_hair, int years,
                        Color hairColor, boolean is_man,
                        BodyType bodyType, boolean isDrink, int humor_lvl) {
        super(name, is_hair, years, hairColor, is_man, bodyType, isDrink, humor_lvl);
    }

    public NormalHuman(String name, boolean is_hair, int years, boolean is_man,
                        BodyType bodyType, boolean isDrink, int humor_lvl) {
        super(name, is_hair, years, is_man, bodyType, isDrink, humor_lvl);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return super.toString();
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
}

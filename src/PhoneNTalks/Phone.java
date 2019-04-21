package PhoneNTalks;

import Humanlike.*;
import Interfaces.*;
import mainpkg.Main;

import java.util.ArrayList;

public class Phone implements IPhone, IBusy {
    private long number;
    private PhoneStation station;
    private boolean is_busy;
    private ITalky current_call;
    private Human owner;
    private PhoneStation current_station;

    public Phone(PhoneStation station, Human man){
        // number gen
        int secondNumPart = 100 + (int) (Math.random() *
                        ((999 - 100) + 1));
        int thirdNumPart = 10 + (int) (Math.random() *
                ((99 - 10) + 1));
        int fourthNumPart = 10 + (int) (Math.random() *
                ((99 - 10) + 1));
        String numstr = "8962" + secondNumPart + thirdNumPart + fourthNumPart;
        //
        number = Long.parseLong(numstr);
        is_busy = false;
        owner = man;
        station.regPhone(this);
        current_station = station;
    }

    @Override
    public void putDown() {
        if (current_call != null) {
            current_call.end();
            current_call = null;
        }
    }

    @Override
    public ITalky call(long phone_num) {
        IPhone phone1 = null;
        for(IPhone p : station.getPhones()){
            if (p.get_number() == phone_num){
                phone1 = p;
                break;
            }
        }
        current_call = new PhoneCall(this, phone1, owner.get_position().getHouse());
        Main.pause("Связь установлена: " + owner.toString() + " и " + phone1.getOwner().toString());
        return current_call;
    }

    @Override
    public long get_number() {
        return number;
    }

    @Override
    public boolean is_busy() {
        if (current_call != null)
            return getStation().getCalls().contains(current_call);
        return false;
    }

    @Override
    public Human getOwner(){
        return owner;
    }

    @Override
    public void setCurrent_call(ITalky call ){
        current_call = call;
    }

    @Override
    public void setStation(PhoneStation station) {
        this.station = station;
    }

    @Override
    public PhoneStation getStation() {
        return station;
    }

    @Override
    public String toString() {
        return "Phone: " + number;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Phone other = (Phone) obj;
        if (number != other.number)
            return false;
        if (is_busy != other.is_busy)
            return false;
        if (!current_call.equals(other.current_call))
            return false;
        if (!current_station.equals(other.current_station))
            return false;
        if (!owner.equals(other.owner))
            return false;
        return true;
    }

    @Override
    public int hashCode() {
        int prime = 21;
        int result = 1;
        int busy = is_busy ? 1 : 0;
        result = prime * result + busy + (int)number +
                current_station.hashCode() + current_call.hashCode() +
                owner.hashCode();
        return result;
    }
}

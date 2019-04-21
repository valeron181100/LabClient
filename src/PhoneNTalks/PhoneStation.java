package PhoneNTalks;

import Buildings.House;
import Interfaces.IPhone;
import Interfaces.ITalky;

import java.util.ArrayList;

public class PhoneStation {
    private ArrayList<IPhone> phones;
    private ArrayList<ITalky> calls;

    public PhoneStation(){
        phones = new ArrayList<>();
        calls = new ArrayList<>();
    }

    public void regPhone(IPhone phone){
        if (phone != null) {
            phones.add(phone);
            phone.setStation(this);
        }
    }

    public void createCall(ITalky phoneCall){
        calls.add(phoneCall);
        ((PhoneCall)phoneCall).getPhone1().setCurrent_call(phoneCall);
        ((PhoneCall)phoneCall).getPhone1().setCurrent_call(phoneCall);
    }

    public ArrayList<IPhone> getPhones() {
        return phones;
    }

    public ArrayList<ITalky> getCalls() {
        return calls;
    }

    public void rmCall(ITalky call){
        calls.remove(call);
    }

    public void rmPhone(IPhone phone){
        phones.remove(phone);
    }

    @Override
    public int hashCode() {
        int prime = 23;
        int result = 1;
        result = prime * result + phones.size() + calls.size();
        return result;
    }

    @Override
    public String toString() {
        return "Phone Station number " + this.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        PhoneStation other = (PhoneStation) obj;
        if (!calls.equals(other.calls))
            return false;
        if (!phones.equals(other.phones))
            return false;
        return true;
    }
}

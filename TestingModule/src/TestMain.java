import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.lang.reflect.Method;
import java.util.Date;
import javax.swing.Timer;

class MyString{
    public String val;
}

public class TestMain {



    public static void main(String[] args){
        A a = new A();
        inc(a);
        System.out.println(a.getB());
    }

    static void inc(A a){
        a.b += 1;
    }

    static int get_rand(int first_border, int end_border){
        return first_border + (int) (Math.random() *
                ((end_border - first_border) + 1));
    }

}

class A{
    public int b = 2;

    public int getB() {
        return b;
    }
}
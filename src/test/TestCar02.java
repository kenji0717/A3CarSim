package test;

import com.github.kenji0717.a3cs.*;

/**
 * CarButtle用のテストカー。
 */
public class TestCar02 extends CarBase {
    protected void exec() {
        setForce(100,0,0);
        if (Math.random()<0.03)
            shoot();
        //System.out.println("test car exec");
    }
}

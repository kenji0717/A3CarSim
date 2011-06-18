package test;

import java.util.ArrayList;

import javax.vecmath.Vector3d;

import com.github.kenji0717.a3cs.*;

/**
 * CarBattle用のテストカー。
 */
public class TestCar02 extends BattleCarBase {
    public void exec() {
        setForce(100,0.1,0);
        if (Math.random()<0.03) {
            myShoot();
        }
        //System.out.println("test car exec");
    }
    void myShoot() {
        ArrayList<CarData> cars = getAllCarData();
        if (cars.size()!=2)
            return;
        CarData cd = cars.get(0);
        if (cd.carID==this.getCarID())
            cd = cars.get(1);
        Vector3d v = cd.loc;
        v.sub(this.getLoc());
        shoot(v);
        //System.out.println("車:"+getCarID()+":shoot!?");
    }
}

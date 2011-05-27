package com.github.kenji0717.a3cs;

import java.util.ArrayList;

import javax.vecmath.*;

/**
 * このクラスを拡張してレースorバトルするための車を作成します。
 * これを拡張して作成する車クラスは必ず引数無しの
 * コンストラクタを持つ必要があります。
 */
public abstract class CarBase implements ActiveObject {
    static int carIDCount=0; 
    MyCar car;
    PhysicalWorld pw;
    CarSim carSim;
    int carID;
    protected CarBase() {
        carID=carIDCount++;
    }
    final void init(Vector3d loc,Vector3d rot,String a3url,PhysicalWorld pw,CarSim cs) {
        this.pw = pw;
        carSim = cs;
        car = new MyCar(loc,rot,a3url,pw);
        car.setCarBase(this);
    }
    protected int getCarID() {
        return carID;
    }
    protected void setForce(double gEngineForce,double gVehicleSteering,double gBreakingForce) {
        car.setForce((float)gEngineForce,(float)gVehicleSteering,(float)gBreakingForce);
    }
    protected Vector3d getLoc() {
        return car.a3.getTargetLoc();
    }
    protected Quat4d getQuat() {
        return car.a3.getTargetQuat();
    }
    protected Vector3d getRot() {
        return car.a3.getTargetRot();
    }
    protected Vector3d getUnitVecX() {
        return car.a3.getUnitVecX();
    }
    protected Vector3d getUnitVecY() {
        return car.a3.getUnitVecY();
    }
    protected Vector3d getUnitVecZ() {
        return car.a3.getUnitVecZ();
    }
    protected void shoot(Vector3d d) {
        if (d.length()<0.0001)
            return;
        d.normalize();
        Vector3d front = getUnitVecZ();
        if (d.dot(front)<0.707)//0.707=1/1.41421356
            return;
        Vector3d l = getLoc();
        l.add(new Vector3d(0.0,0.2,0.0));
        l.add(d);
        l.add(d);
        //d.scale(1.0);
        MyBullet b = new MyBullet(l,d,pw);
        pw.add(b);
        carSim.addActiveObject(b);
    }
    protected ArrayList<CarData> getAllCarData() {
        ArrayList<CarData> ret = new ArrayList<CarData>();
        for (CarBase c:carSim.getAllCar()) {
            CarData cd = new CarData();
            cd.carID = c.getCarID();
            cd.loc = c.getLoc();
            cd.unitVecX = c.getUnitVecX();
            cd.unitVecY = c.getUnitVecY();
            cd.unitVecZ = c.getUnitVecZ();
            ret.add(cd);
        }
        return ret;
    }
    void hit() {
        System.out.println("車:"+carID+":いて!!!");
    }
    public abstract void exec();
}

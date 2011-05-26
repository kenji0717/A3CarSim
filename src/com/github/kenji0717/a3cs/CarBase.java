package com.github.kenji0717.a3cs;

import javax.vecmath.*;

/**
 * このクラスを拡張してレースorバトルするための車を作成します。
 * これを拡張して作成する車クラスは必ず引数無しの
 * コンストラクタを持つ必要があります。
 */
public abstract class CarBase {
    MyCar car;
    PhysicalWorld pw;
    final void init(Vector3d loc,Vector3d rot,String a3url,PhysicalWorld pw) {
        this.pw = pw;
        car = new MyCar(loc,rot,a3url,pw);
        car.setCarBase(this);
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
    protected void shoot() {
        Vector3d l = getLoc();
        l.add(new Vector3d(0.0,0.7,0.0));
        Vector3d v = getUnitVecZ();
        Vector3d up = getUnitVecY();
        up.scale(0.3);
        v.add(up);
        v.scale(10.0);
        MyBullet b = new MyBullet(l,new Vector3d(),pw);
        b.setVel(v.x,v.y,v.z);
        pw.add(b);
    }
    void hit() {
        //System.out.println("Bullet Hit!!!");
    }
    protected abstract void exec();
}

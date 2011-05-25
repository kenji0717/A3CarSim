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
        car = new MyCar(loc.x,loc.y,loc.z,a3url,pw);
    }
    protected void setForce(double gEngineForce,double gVehicleSteering,double gBreakingForce) {
        car.setForce((float)gEngineForce,(float)gVehicleSteering,(float)gBreakingForce);
    }
    protected abstract void exec();
}

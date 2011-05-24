package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.*;
import javax.vecmath.*;
import jp.sourceforge.acerola3d.a3.*;

//car
public class MyCar extends A3CollisionObject {
	CarMotion motion;
    public MyCar(double x,double y,double z,PhysicalWorld pw) throws Exception {
        super(x,y,z,COType.DYNAMIC,pw);
        group = 1;
        mask = 3;
        a3.setUserData("車");
    }

    public A3Object makeA3Object() throws Exception {
        return new Action3D("x-res:///res/stk_tux.a3");
    }
    //
    public CollisionObject makeCollisionObject() {
    	motionState.setAutoUpdate(false);//MotionデータでコントロールするのでAutoUpdate不要
    	motion = new CarMotion(motionState,pw.dynamicsWorld);
    	((Action3D)a3).setMotion("default",motion);
    	((Action3D)a3).transControlUsingRootBone(true);//rootの骨の情報でA3Objectの変換を制御
        return motion.carChassis;
    }
    public void setForce(float gEngineForce,float gVehicleSteering,float gBreakingForce) {
    	motion.setForce(gEngineForce,gVehicleSteering,gBreakingForce);
    }
}

package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.*;
import javax.vecmath.*;
import jp.sourceforge.acerola3d.a3.*;

//car
public class MyCar extends A3RigidBody {
	CarMotion motion;
    public MyCar(double x,double y,double z,PhysicalWorld pw) throws Exception {
        super(x,y,z,pw);
    }

    public A3Object makeA3Object() throws Exception {
        return new Action3D("x-res:///res/stk_tux.a3");
    }
    //
    public RigidBody makeRigidBody() {
    	DefaultMotionState dms = new DefaultMotionState();
    	motion = new CarMotion(dms,pw.dynamicsWorld);
    	//motion = new CarMotion(motionState,pw.dynamicsWorld); //これでも動くが、処理が二重になる
    	((Action3D)a3).setMotion("default",motion);
    	((Action3D)a3).transControlUsingRootBone(true); //この方が自然。moationStateの方を使うなら必須
        return motion.carChassis;
    }
    public void setForce(float gEngineForce,float gVehicleSteering,float gBreakingForce) {
    	motion.setForce(gEngineForce,gVehicleSteering,gBreakingForce);
    }
}

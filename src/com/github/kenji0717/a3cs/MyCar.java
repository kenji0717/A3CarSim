package com.github.kenji0717.a3cs;

import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;
import jp.sourceforge.acerola3d.a3.*;

//car
class MyCar extends A3CollisionObject {
    String a3url;
    CarMotion motion;
    CarBase carBase;
    public MyCar(Vector3d l,Vector3d r,String a3url,PhysicalWorld pw) {
        super(l,r,COType.DYNAMIC,pw,a3url);
        //this.a3url = a3url;
        //group = 1;
        //mask = 3;
        a3.setUserData("車");
    }

    public A3Object makeA3Object(Object...args) throws Exception {
        a3url = (String)args[0];
        return new Action3D(a3url);
    }
    public MotionState makeMotionState(Vector3d l,Vector3d r) {
        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set((float)l.x,(float)l.y,(float)l.z);
        transform.setRotation(new Quat4f(Util.euler2quat(r)));
        MotionState ms = new CarMotionState(transform);
        return ms;
    }
    public CollisionObject makeCollisionObject(Object...args) {
        //motionState.setAutoUpdate(false);//MotionデータでコントロールするのでAutoUpdate不要
        motion = new CarMotion(motionState,pw.dynamicsWorld);
        ((CarMotionState)motionState).setCarMotion(motion);
        ((Action3D)a3).setMotion("default",motion);
        ((Action3D)a3).transControlUsingRootBone(true);//rootの骨の情報でA3Objectの変換を制御
        return motion.carChassis;
    }
    public void setCarBase(CarBase cb) {
        carBase = cb;
    }
    public void setForce(float gEngineForce,float gVehicleSteering,float gBreakingForce,float drift) {
        motion.setForce(gEngineForce,gVehicleSteering,gBreakingForce,drift);
    }

    //車輪の更新?不必要かもしれない
    void updateWheelTransform() {
        motion.updateWheelTransform();
    }
}

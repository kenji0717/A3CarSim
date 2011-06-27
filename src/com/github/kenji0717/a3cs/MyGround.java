package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.*;

import jp.sourceforge.acerola3d.a3.*;

//地面を表すクラス
class MyGround extends A3CollisionObject {
    public MyGround(PhysicalWorld pw) {
        super(new Vector3d(0.0,-50.0,0.0),new Vector3d(),COType.STATIC,pw);
    }

    public A3Object makeA3Object(Object...args) throws Exception {
        A3Object a = new Action3D("x-res:///res/background0.a3");
        a.setScale(5.0);
        a.setLoc(0.0,0.0,0.0);
        return a;
    }
    public MotionState makeMotionState(Vector3d l,Vector3d r) {
        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set((float)l.x,(float)l.y,(float)l.z);
        transform.setRotation(new Quat4f(Util.euler2quat(r)));
        return new A3MotionState(a3,transform);
    }
    //地面用の剛体を作る
    public RigidBody makeCollisionObject(Object...args) {
        CollisionShape groundShape =
            new BoxShape(new Vector3f(50.0f,50.0f,50.0f));
        Vector3f localInertia = new Vector3f(0,0,0);
        RigidBodyConstructionInfo groundRBInfo =
            new RigidBodyConstructionInfo(0.0f,motionState,
                groundShape,localInertia);
        RigidBody groundBody = new RigidBody(groundRBInfo);
        return groundBody;
    }
}

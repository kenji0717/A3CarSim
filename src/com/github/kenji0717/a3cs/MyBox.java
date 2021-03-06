package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.*;

import jp.sourceforge.acerola3d.a3.*;

//立方体を表すクラス
class MyBox extends A3CollisionObject {
    public MyBox(double x,double y,double z,PhysicalWorld pw) {
        super(new Vector3d(x,y,z),new Vector3d(),COType.DYNAMIC,pw);
        a3.setUserData("サイコロ");
    }

    public A3Object makeA3Object(Object...args) throws Exception {
        return new Action3D("x-res:///res/SimpleBox.a3");
    }
    public MotionState makeMotionState(Vector3d l,Vector3d r) {
        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set((float)l.x,(float)l.y,(float)l.z);
        transform.setRotation(new Quat4f(Util.euler2quat(r)));
        return new A3MotionState(a3,transform);
    }
    //立方体の剛体を作る
    public RigidBody makeCollisionObject(Object...args) {
        CollisionShape shape = new BoxShape(new Vector3f(1.0f,1.0f,1.0f));
        Vector3f localInertia = new Vector3f(0,0,0);
        shape.calculateLocalInertia(1.0f,localInertia);
        RigidBodyConstructionInfo rbcInfo =
                new RigidBodyConstructionInfo(1.0f,motionState,
                                              shape,localInertia);
        RigidBody rb = new RigidBody(rbcInfo);
        return rb;
    }
}

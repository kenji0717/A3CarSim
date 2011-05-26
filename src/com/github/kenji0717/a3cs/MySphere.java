package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import javax.vecmath.*;
import jp.sourceforge.acerola3d.a3.*;

//球を表すクラス
class MySphere extends A3CollisionObject {
    public MySphere(double x,double y,double z,PhysicalWorld pw) {
        super(new Vector3d(x,y,z),new Vector3d(),COType.DYNAMIC,pw);
    }

    public A3Object makeA3Object(Object...args) throws Exception {
        return new Action3D("x-res:///res/earth.a3");
    }
    //球状の剛体を作る
    public RigidBody makeCollisionObject(Object...args) {
        CollisionShape shape = new SphereShape(1.0f);
        Vector3f localInertia = new Vector3f(0,0,0);
        shape.calculateLocalInertia(1.0f,localInertia);
        RigidBodyConstructionInfo rbcInfo =
                new RigidBodyConstructionInfo(1.0f,motionState,
                                              shape,localInertia);
        RigidBody rb = new RigidBody(rbcInfo);
        return rb;
    }
}

package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import javax.vecmath.*;
import jp.sourceforge.acerola3d.a3.*;

//弾丸を表すクラス
class MyBullet extends A3CollisionObject implements ActiveObject {
    Vector3d l = new Vector3d();
    Vector3d v = new Vector3d();
    public MyBullet(Vector3d l,Vector3d v,PhysicalWorld pw) {
        super(l,new Vector3d(),COType.KINEMATIC,pw);
        this.l.set(l);
        this.v.set(v);
    }

static int gaha;
    public A3Object makeA3Object(Object...args) throws Exception {
        Action3D a = new Action3D("x-res:///res/bullet.a3");
        a.setScale(2.0);
        a.setUserData("bullet"+(gaha++)+":"+System.currentTimeMillis());
        return a;
    }
    //球状の剛体を作る
    public RigidBody makeCollisionObject(Object...args) {
        CollisionShape shape = new SphereShape(0.2f);
        Vector3f localInertia = new Vector3f(0,0,0);
        shape.calculateLocalInertia(1.0f,localInertia);
        RigidBodyConstructionInfo rbcInfo =
                new RigidBodyConstructionInfo(1.0f,motionState,
                                              shape,localInertia);
        RigidBody rb = new RigidBody(rbcInfo);
        rb.setCollisionFlags(rb.getCollisionFlags()|CollisionFlags.KINEMATIC_OBJECT);
        rb.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
        return rb;
    }

    public void exec() {
        l.add(v);
        this.setLoc2(l.x,l.y,l.z);
    }
}

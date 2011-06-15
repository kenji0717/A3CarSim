package com.github.kenji0717.a3cs;

import javax.vecmath.Quat4d;
import javax.vecmath.Quat4f;
import javax.vecmath.Vector3d;
import javax.vecmath.Vector3f;

import jp.sourceforge.acerola3d.a3.A3Object;
import jp.sourceforge.acerola3d.a3.Util;
import jp.sourceforge.acerola3d.a3.VRML;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.GhostObject;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

class MyCheckPoint extends A3CollisionObject {
    public MyCheckPoint(Vector3d l,Vector3d r,PhysicalWorld pw) {
        super(l,r,COType.GHOST,pw);
        //group = 2;
        //mask = 2;
        a3.setUserData("CheckPoint");
    }

    public A3Object makeA3Object(Object...args) throws Exception {
        VRML vrml = new VRML("x-rzip:x-res:///res/ClearBlocks2.a3!/blockBlack.wrl");
        vrml.setScale(8);
        return vrml;
    }
    public MotionState makeMotionState(Vector3d l,Vector3d r) {
        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set((float)l.x,(float)l.y,(float)l.z);
        transform.setRotation(new Quat4f(Util.euler2quat(r)));
        return new A3MotionState(a3,transform);
    }
    public CollisionObject makeCollisionObject_BAK(Object...args) {
        //CollisionShape shape = Util.makeConvexHullShape(a3.getNode());
        CollisionShape shape = new BoxShape(new Vector3f(40,40,20));
        GhostObject body = new GhostObject();
        body.setCollisionShape(shape);
        body.setCollisionFlags(CollisionFlags.NO_CONTACT_RESPONSE);//ポイント
        Transform trans = new Transform();
        //trans.origin.set(motionState.graphicsWorldTrans.origin);
        //trans.setRotation(motionState.qTmp);
        body.setWorldTransform(trans);
        //double x = motionState.graphicsWorldTrans.origin.x;
        //double y = motionState.graphicsWorldTrans.origin.y;
        //double z = motionState.graphicsWorldTrans.origin.z;
        double x = trans.origin.x;
        double y = trans.origin.y;
        double z = trans.origin.z;
        a3.setLocImmediately(x,y,z);
        //x = motionState.qTmp.x;
        //y = motionState.qTmp.y;
        //z = motionState.qTmp.z;
        //double w = motionState.qTmp.w;
        //a3.setQuat(x,y,z,w);
        Quat4d qTmp = new Quat4d();
        qTmp.set(trans.basis);
        a3.setQuat(qTmp);
        return body;
    }

    //なんかこっちでもOKぽいぞ。どうする？
    public CollisionObject makeCollisionObject(Object...args) {
        //CollisionShape shape = Util.makeConvexHullShape(a3.getNode());
        CollisionShape shape = new BoxShape(new Vector3f(4,4,2));
        Vector3f localInertia = new Vector3f(0,0,0);
        shape.calculateLocalInertia(1.0f,localInertia);
        RigidBodyConstructionInfo rbcInfo =
            new RigidBodyConstructionInfo(1.0f,motionState,shape,localInertia);
        RigidBody rb = new RigidBody(rbcInfo);
        rb.setCollisionFlags(CollisionFlags.NO_CONTACT_RESPONSE);//ここがポイント
        return rb;
    }
}

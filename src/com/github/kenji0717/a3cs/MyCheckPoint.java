package com.github.kenji0717.a3cs;

import javax.vecmath.Vector3f;

import jp.sourceforge.acerola3d.a3.A3Object;
import jp.sourceforge.acerola3d.a3.Util;
import jp.sourceforge.acerola3d.a3.VRML;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.dispatch.GhostObject;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;

public class MyCheckPoint extends A3CollisionObject {
    public MyCheckPoint(double x,double y,double z,PhysicalWorld pw) throws Exception {
        super(x,y,z,COType.GHOST,pw);
        //group = 2;
        //mask = 3;
        a3.setUserData("CheckPoint");
    }

    public A3Object makeA3Object() throws Exception {
        VRML vrml = new VRML("x-rzip:x-res:///res/ClearBlocks2.a3!/blockBlack.wrl");
        return vrml;
    }

    public CollisionObject makeCollisionObject() {
        CollisionShape shape = Util.makeConvexHullShape(a3.getNode());
        GhostObject body = new GhostObject();
        body.setCollisionShape(shape);
        double x = motionState.graphicsWorldTrans.origin.x;
        double y = motionState.graphicsWorldTrans.origin.y;
        double z = motionState.graphicsWorldTrans.origin.z;
        a3.setLocImmediately(x,y,z);
        x = motionState.qTmp.x;
        y = motionState.qTmp.y;
        z = motionState.qTmp.z;
        double w = motionState.qTmp.w;
        a3.setQuat(x,y,z,w);
        return body;
    }
}

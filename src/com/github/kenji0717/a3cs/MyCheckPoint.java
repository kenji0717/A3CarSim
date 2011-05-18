package com.github.kenji0717.a3cs;

import javax.vecmath.Vector3f;

import jp.sourceforge.acerola3d.a3.A3Object;
import jp.sourceforge.acerola3d.a3.Util;
import jp.sourceforge.acerola3d.a3.VRML;

import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;

public class MyCheckPoint extends A3RigidBody {
    public MyCheckPoint(double x,double y,double z,PhysicalWorld pw) throws Exception {
        super(x,y,z,pw);
        group = 2;
        mask = 2;
    }

    public A3Object makeA3Object() throws Exception {
        VRML vrml = new VRML("x-rzip:x-res:///res/ClearBlocks2.a3!/blockBlack.wrl");
        return vrml;
    }

    public RigidBody makeRigidBody() {
        CollisionShape groundShape = Util.makeConvexHullShape(a3.getNode());
        RigidBodyConstructionInfo cInfo = new RigidBodyConstructionInfo(0.0f, motionState, groundShape, new Vector3f());
        RigidBody body = new RigidBody(cInfo);
        return body;
    }
}

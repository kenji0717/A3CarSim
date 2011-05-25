package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import javax.vecmath.*;
import jp.sourceforge.acerola3d.a3.*;

//地面を表すクラス
class MyGround extends A3CollisionObject {
    public MyGround(PhysicalWorld pw) {
        super(0.0,-50.0,0.0,COType.STATIC,pw);
    }

    public A3Object makeA3Object(Object...args) throws Exception {
        A3Object a = new Action3D("x-res:///res/background0.a3");
        a.setScale(5.0);
        a.setLoc(0.0,0.0,0.0);
        return a;
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

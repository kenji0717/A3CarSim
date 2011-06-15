package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import javax.vecmath.*;

import jp.sourceforge.acerola3d.a3.*;

//地面を表すクラス
class MyGround3 extends A3CollisionObject {
    static CollisionShape groundShape;
    public MyGround3(PhysicalWorld pw) {
        super(new Vector3d(0,0,0),new Vector3d(),COType.STATIC,pw);
        a3.setUserData("地面3");
    }

    public A3Object makeA3Object(Object...args) throws Exception {
        VRML vrml = new VRML("x-rzip:x-res:///res/BattleField01.a3!/base.wrl");
        return vrml;
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
        if (groundShape==null)
            groundShape = Util.makeBvhTriangleMeshShape(a3.getNode());
        RigidBodyConstructionInfo cInfo = new RigidBodyConstructionInfo(0.0f, motionState, groundShape, new Vector3f());
        RigidBody body = new RigidBody(cInfo);
        return body;
    }
}

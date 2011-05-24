package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.dispatch.CollisionFlags;
import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.*;
import javax.vecmath.*;

import jp.sourceforge.acerola3d.a3.*;

//このプログラムで剛体を表すクラス
public abstract class A3CollisionObject {
	PhysicalWorld pw;
	A3Object a3;
    A3MotionState motionState;//JBulletと座標をやりとりするオブジェクト
    CollisionObject body;//JBulletにおける剛体などを表すオブジェクト
    Vector3f locRequest;
    Vector3f velRequest;
    COType coType = COType.DYNAMIC;
    short group = 1;
    short mask = 1;

    //Acerola3DファイルのURLと初期座標で初期化
    public A3CollisionObject(double x,double y,double z,COType t,PhysicalWorld pw) throws Exception {
        this.pw = pw;
        this.coType = t;
        a3 = makeA3Object();
        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set((float)x,(float)y,(float)z);
        motionState = new A3MotionState(a3,transform);
        body = makeCollisionObject();
        body.setUserPointer(this);
    }

    public abstract A3Object makeA3Object() throws Exception ;
    public abstract CollisionObject makeCollisionObject();

    public void changeCOType(COType t) {
        ;
    }
    public void changeCOType_BAK(COType t) {
        //RigidBodyとGhostObject間の変換は不可
        if ((t==COType.GHOST)&&(coType!=COType.GHOST)) {
            System.exit(0);
            throw new IllegalArgumentException();
        }
        if ((coType==COType.GHOST)&&(t!=COType.GHOST)) {
            System.exit(0);
            throw new IllegalArgumentException();
        }

        if (t==COType.DYNAMIC) {
            body.setCollisionFlags(body.getCollisionFlags()&(~CollisionFlags.KINEMATIC_OBJECT));
            //rb.body.setActivationState(CollisionObject.ACTIVE_TAG);
            body.forceActivationState(CollisionObject.ACTIVE_TAG);
            body.setDeactivationTime(0.0f);
            ((RigidBody)body).clearForces();
            ((RigidBody)body).setLinearVelocity(new Vector3f());
            ((RigidBody)body).setAngularVelocity(new Vector3f());
        } else if (t==COType.STATIC) {
            body.setCollisionFlags(body.getCollisionFlags()|CollisionFlags.KINEMATIC_OBJECT);
            body.setActivationState(CollisionObject.DISABLE_SIMULATION);
            ((RigidBody)body).clearForces();
            ((RigidBody)body).setLinearVelocity(new Vector3f());
            ((RigidBody)body).setAngularVelocity(new Vector3f());
        } else if (t==COType.KINEMATIC){
            body.setCollisionFlags(body.getCollisionFlags()|CollisionFlags.KINEMATIC_OBJECT);
            body.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
            ((RigidBody)body).clearForces();
            ((RigidBody)body).setLinearVelocity(new Vector3f());
            ((RigidBody)body).setAngularVelocity(new Vector3f());
        } else if (t==COType.KINEMATIC_TEMP){
            body.setCollisionFlags(body.getCollisionFlags()|CollisionFlags.KINEMATIC_OBJECT);
            body.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
            ((RigidBody)body).clearForces();
            ((RigidBody)body).setLinearVelocity(new Vector3f());
            ((RigidBody)body).setAngularVelocity(new Vector3f());
        }
        coType = t;
    }
    //座標変更．副作用で力や速度がリセットされる
    public void setLoc2(double x,double y,double z) {
        locRequest = new Vector3f((float)x,(float)y,(float)z);
    }
    public void setVel(double x,double y,double z) {
        velRequest = new Vector3f((float)x,(float)y,(float)z);
    }
}

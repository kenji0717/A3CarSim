package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.linearmath.*;
import javax.vecmath.*;
import jp.sourceforge.acerola3d.a3.*;

//このプログラムで剛体を表すクラス
public abstract class A3RigidBody {
	PhysicalWorld pw;
	A3Object a3;
    A3MotionState motionState;//JBulletと座標をやりとりするオブジェクト
    RigidBody body;//JBulletにおける剛体を表すオブジェクト
    Vector3f locRequest;
    Vector3f velRequest;

    //Acerola3DファイルのURLと初期座標で初期化
    public A3RigidBody(double x,double y,double z,PhysicalWorld pw) throws Exception {
        this.pw = pw;
        a3 = makeA3Object();
        Transform transform = new Transform();
        transform.setIdentity();
        transform.origin.set((float)x,(float)y,(float)z);
        motionState = new A3MotionState(a3,transform);
        body = makeRigidBody();
    }

    public abstract A3Object makeA3Object() throws Exception ;
    public abstract RigidBody makeRigidBody();

    //座標変更．副作用で力や速度がリセットされる
    public void setLoc2(double x,double y,double z) {
        locRequest = new Vector3f((float)x,(float)y,(float)z);
    }
    public void setVel(double x,double y,double z) {
        velRequest = new Vector3f((float)x,(float)y,(float)z);
    }
}

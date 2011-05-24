package com.github.kenji0717.a3cs;

import com.bulletphysics.linearmath.*;
import jp.sourceforge.acerola3d.a3.*;
import javax.vecmath.*;

//JBulletのシステムと座標などのやりとりをするためのクラス
//A3Objectの座標を自動で変更する役割もしている．
public class A3MotionState extends MotionState {
    public final Transform graphicsWorldTrans = new Transform();
    public final Transform centerOfMassOffset = new Transform();
    public final Transform startWorldTrans = new Transform();
    //public final Transform tmpT = new Transform();
    A3Object a3;
    boolean autoUpdate = true; //CarMotionを使うような場合だけfalseにする
    public A3MotionState(A3Object a) {
        a3 = a;
        graphicsWorldTrans.setIdentity();
        centerOfMassOffset.setIdentity();
        startWorldTrans.setIdentity();
        //tmpT.setIdentity();
    }
    public A3MotionState(A3Object a,Transform startTrans) {
        a3 = a;
        graphicsWorldTrans.set(startTrans);
        centerOfMassOffset.setIdentity();
        startWorldTrans.set(startTrans);
        //tmpT.setIdentity();
    }
    public A3MotionState(A3Object a,Transform startTrans,Transform centerOfMassOffset) {
        a3 = a;
        graphicsWorldTrans.set(startTrans);
        centerOfMassOffset.set(centerOfMassOffset);
        startWorldTrans.set(startTrans);
        //tmpT.setIdentity();
    }
    public Transform getWorldTransform(Transform out) {
        out.inverse(centerOfMassOffset);
        out.mul(graphicsWorldTrans);
        return out;
    }
    Quat4f qTmp = new Quat4f(0.0f,0.0f,0.0f,1.0f);
    public void setWorldTransform(Transform centerOfMassWorldTrans) {
        graphicsWorldTrans.set(centerOfMassWorldTrans);
        graphicsWorldTrans.mul(centerOfMassOffset);
        graphicsWorldTrans.getRotation(qTmp);
        if (autoUpdate) {
            a3.setLoc(graphicsWorldTrans.origin.x,graphicsWorldTrans.origin.y,graphicsWorldTrans.origin.z);
            a3.setQuat(qTmp.x,qTmp.y,qTmp.z,qTmp.w);
        }
    }
    public void setAutoUpdate(boolean b) {
        autoUpdate = b;
    }
}

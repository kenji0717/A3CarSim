package com.github.kenji0717.a3cs;

import com.bulletphysics.linearmath.*;
import javax.vecmath.*;

class CarMotionState extends MotionState {
    public final Transform graphicsWorldTrans = new Transform();
    public final Transform centerOfMassOffset = new Transform();
    public final Transform startWorldTrans = new Transform();
    //public final Transform tmpT = new Transform();
    CarMotion carMotion;
    public CarMotionState() {
        graphicsWorldTrans.setIdentity();
        centerOfMassOffset.setIdentity();
        startWorldTrans.setIdentity();
        //tmpT.setIdentity();
    }
    public CarMotionState(Transform startTrans) {
        graphicsWorldTrans.set(startTrans);
        centerOfMassOffset.setIdentity();
        startWorldTrans.set(startTrans);
        //tmpT.setIdentity();
    }
    public CarMotionState(Transform startTrans,Transform centerOfMassOffset) {
        graphicsWorldTrans.set(startTrans);
        centerOfMassOffset.set(centerOfMassOffset);
        startWorldTrans.set(startTrans);
        //tmpT.setIdentity();
    }
    public void setCarMotion(CarMotion cm) {
        carMotion = cm;
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
        if (carMotion!=null)
            carMotion.rootTransform.set(graphicsWorldTrans);
    }
}

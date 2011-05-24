package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.dispatch.CollisionObject;
import com.bulletphysics.collision.shapes.BoxShape;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.collision.shapes.CompoundShape;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.vehicle.*;
import com.bulletphysics.linearmath.MotionState;
import com.bulletphysics.linearmath.Transform;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import com.bulletphysics.linearmath.DefaultMotionState;
import jp.sourceforge.acerola3d.a3.*;
import javax.vecmath.*;
import javax.media.j3d.*;

public class CarMotion implements Motion {
    DynamicsWorld dynamicsWorld;

    RigidBody carChassis;
    VehicleRaycaster vehicleRayCaster;
    RaycastVehicle vehicle;

    public CarMotion(MotionState ms,DynamicsWorld dw) {
        dynamicsWorld = dw;

        //Transform tr = new Transform();
        //tr.setIdentity();

        CollisionShape chassisShape = new BoxShape(new Vector3f(0.4f, 0.25f, 0.75f));

        CompoundShape compound = new CompoundShape();
        Transform localTrans = new Transform();
        localTrans.setIdentity();
        localTrans.origin.set(0, 0.25f, 0);

        compound.addChildShape(localTrans, chassisShape);

        //tr.origin.set(0, 0, 0);

        carChassis = localCreateRigidBody(100, ms, compound);
        carChassis.setDamping(0.5f,0.5f);//空気抵抗を設定

        // create vehicle
        {
            VehicleTuning tuning = new VehicleTuning();

            vehicleRayCaster = new DefaultVehicleRaycaster(dynamicsWorld);
            vehicle = new RaycastVehicle(tuning, carChassis, vehicleRayCaster);

            //無効化の禁止
            carChassis.setActivationState(CollisionObject.DISABLE_DEACTIVATION);

            dynamicsWorld.addVehicle(vehicle);

            //座標系の設定
            int rightIndex = 0;
            int upIndex = 1;
            int forwardIndex = 2;
            vehicle.setCoordinateSystem(rightIndex, upIndex, forwardIndex);

            //各タイヤごとのパラメータ
            Vector3f connectionPointCS0 = new Vector3f();//接続ポイント
            Vector3f wheelDirectionCS0 = new Vector3f(0,-1,0);//たぶんサスペンションの方向
            Vector3f wheelAxleCS = new Vector3f(-1,0,0);//車輪の回転軸方向
            float suspensionRestLength;//サスペンションの長さ？
            float wheelRadius;//車輪半径
            boolean isFrontWheel;//前輪かどうか？

            //##### 左前車輪生成 #####
            isFrontWheel = true;
            wheelRadius = 0.1f;
            suspensionRestLength = 0.2f;
            connectionPointCS0.set( 0.3f,0.1f, 0.4f);
            vehicle.addWheel(connectionPointCS0, wheelDirectionCS0, wheelAxleCS, suspensionRestLength, wheelRadius, tuning, isFrontWheel);

            //##### 右前車輪生成 #####
            isFrontWheel = true;
            wheelRadius = 0.1f;
            suspensionRestLength = 0.2f;
            connectionPointCS0.set(-0.3f,0.1f, 0.4f);
            vehicle.addWheel(connectionPointCS0, wheelDirectionCS0, wheelAxleCS, suspensionRestLength, wheelRadius, tuning, isFrontWheel);

            //##### 右後車輪生成 #####
            isFrontWheel = false;
            wheelRadius = 0.15f;
            suspensionRestLength = 0.2f;
            connectionPointCS0.set(-0.35f,0.15f,-0.35f);
            vehicle.addWheel(connectionPointCS0, wheelDirectionCS0, wheelAxleCS, suspensionRestLength, wheelRadius, tuning, isFrontWheel);

            //##### 左後車輪生成 #####
            isFrontWheel = false;
            wheelRadius = 0.15f;
            suspensionRestLength = 0.2f;
            connectionPointCS0.set(0.35f,0.15f,-0.35f);
            vehicle.addWheel(connectionPointCS0, wheelDirectionCS0, wheelAxleCS, suspensionRestLength, wheelRadius, tuning, isFrontWheel);

            //車輪の細かい設定
            float suspensionStiffness = 20.f;//サスペンションの硬さ
            float suspensionDamping = 2.3f;//サスペンションの減衰
            float suspensionCompression = 4.4f;//サスペンションの圧縮?
            float wheelFriction = 1000;//摩擦係数
            float rollInfluence = 0.1f;//回転影響?
            for (int i = 0; i < vehicle.getNumWheels(); i++) {
                WheelInfo wheel = vehicle.getWheelInfo(i);
                wheel.suspensionStiffness = suspensionStiffness;
                wheel.wheelsDampingRelaxation = suspensionDamping;
                wheel.wheelsDampingCompression = suspensionCompression;
                wheel.frictionSlip = wheelFriction;
                wheel.rollInfluence = rollInfluence;
            }
        }
    }

    //車をコントロールするためのメソッド
    public void setForce(float gEngineForce,float gVehicleSteering,float gBreakingForce) {
        int wheelIndex = 2;
        vehicle.applyEngineForce(gEngineForce,wheelIndex);
        vehicle.setBrake(gBreakingForce,wheelIndex);
        wheelIndex = 3;
        vehicle.applyEngineForce(gEngineForce,wheelIndex);
        vehicle.setBrake(gBreakingForce,wheelIndex);

        wheelIndex = 0;
        vehicle.setSteeringValue(gVehicleSteering,wheelIndex);
        wheelIndex = 1;
        vehicle.setSteeringValue(gVehicleSteering,wheelIndex);
    }

    //public RigidBody localCreateRigidBody(float mass, Transform startTransform, CollisionShape shape) {
    public RigidBody localCreateRigidBody(float mass, MotionState ms, CollisionShape shape) {
        boolean isDynamic = (mass != 0f);

        Vector3f localInertia = new Vector3f(0f, 0f, 0f);
        if (isDynamic) {
            shape.calculateLocalInertia(mass, localInertia);
        }

        //DefaultMotionState myMotionState = new DefaultMotionState(startTransform);
        
        RigidBodyConstructionInfo cInfo = new RigidBodyConstructionInfo(mass, ms, shape, localInertia);
        
        RigidBody body = new RigidBody(cInfo);
        
        //dynamicsWorld.addRigidBody(body);

        return body;
    }
    
    //------------------------
    //以下Acerola3DのMotionインターフェースの実装部
    //------------------------
    public double getMotionLength() {
        return -1.0;
    }
    public double getDefaultFrameTime() {
        return 0.033;
    }
    public String getRootBone() {
        return "root";
    }
    public String getParentBone(String b) {
        if (b.equals("root"))
            return null;
        else
            return "root";
    }
    static final String cbs[] = {"chassis","frontRight","frontLeft","rearRight","rearLeft"};
    public String[] getChildBones(String b) {
        if (b.equals("root")) {
            return cbs;
        } else {
            return new String[0];
        }
    }
    static final String abs[] = {"root","chassis","frontRight","frontLeft","rearRight","rearLeft"};
    public String[] getAllBones() {
        return abs;
    }
    public Transform3D getTransform3D(String bone,double time) {
        Transform rootTrans = new Transform();
        vehicle.getChassisWorldTransform(rootTrans);
        Transform retTrans = new Transform();
        if (bone.equals("root")) {
System.out.println("gaha:"+rootTrans);
            retTrans.set(rootTrans);
        } else if (bone.equals("chassis")) {
            retTrans.setIdentity();
        } else {
            int wheelIndex = 0;
            if (bone.equals("frontRight"))
                wheelIndex= 0;//なんで？左右が逆？
            else if (bone.equals("frontLeft"))
                wheelIndex= 1;//なんで？左右が逆？
            else if (bone.equals("rearRight"))
                wheelIndex= 3;//なんで？左右が逆？
            else if (bone.equals("rearLeft"))
                wheelIndex= 2;//なんで？左右が逆？

            Transform rootTransInv = new Transform(rootTrans);
            rootTransInv.inverse();
            vehicle.updateWheelTransform(wheelIndex,true);
            Transform trans = vehicle.getWheelInfo(wheelIndex).worldTransform;
            retTrans.mul(rootTransInv,trans);
        }
        return new Transform3D(retTrans.basis,retTrans.origin,1.0f);
    }
}

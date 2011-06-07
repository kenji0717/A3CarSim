package com.github.kenji0717.a3cs;

import java.util.ArrayList;

import javax.vecmath.*;

/**
 * このクラスを拡張してレースorバトルするための車を作成します。
 * これを拡張して作成する車クラスは必ず引数無しの
 * コンストラクタを持つ必要があります。レースでは関係ありませんが、
 * この車は初期値100のエネルギーを持ち、被弾すると5づつエネルギーが
 * 減少します。また弾丸を発射した時も1づつエネルギーを消費します。
 */
public abstract class CarBase implements ActiveObject {
    static int carIDCount=0; 
    MyCar car;
    PhysicalWorld pw;
    CarSim carSim;
    int carID;
    int energy = 100;

    /**
     * CarBaseのコンストラクタです。
     */
    protected CarBase() {
        carID=carIDCount++;
    }
    final void init(Vector3d loc,Vector3d rot,String a3url,PhysicalWorld pw,CarSim cs) {
        this.pw = pw;
        carSim = cs;
        car = new MyCar(loc,rot,a3url,pw);
        car.setCarBase(this);
    }

    /**
     * この車のID(整数値)を取得します。
     */
    protected int getCarID() {
        return carID;
    }

    /**
     * 前進のための力(gEngineForce)、ハンドルの方向(gVehicleSteering:正->右,負->左)、
     * そしてブレーキの力(gBreakingForce)を指定して車をコントロールします。
     */
    protected void setForce(double gEngineForce,double gVehicleSteering,double gBreakingForce) {
        car.setForce((float)gEngineForce,(float)gVehicleSteering,(float)gBreakingForce);
    }

    /**
     * この車の現在の座標を取得します。
     */
    protected Vector3d getLoc() {
        return car.a3.getTargetLoc();
    }

    /**
     * この車の現在の回転を四元数で取得します。
     */
    protected Quat4d getQuat() {
        return car.a3.getTargetQuat();
    }

    /**
     * この車の現在の回転をオイラー角で取得します。
     */
    protected Vector3d getRot() {
        return car.a3.getTargetRot();
    }

    /**
     * この車の現在の進行方向左向きの単位ベクトルを取得します。
     */
    protected Vector3d getUnitVecX() {
        return car.a3.getUnitVecX();
    }

    /**
     * この車の現在の進行方向上向きの単位ベクトルを取得します。
     */
    protected Vector3d getUnitVecY() {
        return car.a3.getUnitVecY();
    }

    /**
     * この車の現在の進行方向の単位ベクトルを取得します。
     */
    protected Vector3d getUnitVecZ() {
        return car.a3.getUnitVecZ();
    }

    /**
     * 弾丸を発射します。引数のベクトルは弾丸を発射したい方向を世界座標で
     * 指定します。ただしこの車の進行方向と弾丸の発射方向が45度以上開いて
     * いる場合は弾丸は発射されません。このメソッドを呼び出すと無条件で
     * エネルギーを1消費します。
     */
    protected void shoot(Vector3d d) {
        energy = energy - 1;
        if (d.length()<0.0001)
            return;
        d.normalize();
        Vector3d front = getUnitVecZ();
        if (d.dot(front)<0.707)//0.707=1/1.41421356
            return;
        Vector3d l = getLoc();
        l.add(new Vector3d(0.0,0.2,0.0));
        l.add(d);
        l.add(d);
        //d.scale(1.0);
        MyBullet b = new MyBullet(l,d,pw);
        pw.add(b);
        carSim.addActiveObject(b);
    }

    /**
     * 現在のフィールドに存在している全ての車の情報を取得します。
     * データの詳細についてはCarDataクラスを参照して下さい。
     */
    protected ArrayList<CarData> getAllCarData() {
        ArrayList<CarData> ret = new ArrayList<CarData>();
        for (CarBase c:carSim.getAllCar()) {
            CarData cd = new CarData();
            cd.carID = c.getCarID();
            cd.loc = c.getLoc();
            cd.unitVecX = c.getUnitVecX();
            cd.unitVecY = c.getUnitVecY();
            cd.unitVecZ = c.getUnitVecZ();
            cd.energy = c.energy;
            ret.add(cd);
        }
        return ret;
    }

    /**
     * この車の現在のエネルギー値を取得します。
     */
    protected int getEnergy() {
        return energy;
    }
    void hit() {
        energy = energy - 5;
        System.out.println("車:"+carID+":いて!!!");
    }

    /**
     * この車をコントロールするプログラムを記述するメソッドです。
     * このCarBaseを継承するクラスでは必ず、このメソッドを実装して
     * 下さい。
     */
    public abstract void exec();
}

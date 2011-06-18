package com.github.kenji0717.a3cs;

import javax.vecmath.*;

/**
 * このクラスを拡張してレースするための車を作成します。
 * これを拡張して作成する車クラスは必ず引数無しの
 * コンストラクタを持つ必要があります。
 */
public abstract class RaceCarBase extends CarBase {

    /**
     * RaceCarBaseのコンストラクタです。
     */
    protected RaceCarBase() {
    }

    /**
     * この車のID(整数値)を取得します。
     */
    protected int getCarID() {
        return super.getCarID();
    }

    /**
     * 前進のための力(gEngineForce)、ハンドルの方向(gVehicleSteering:正->右,負->左)、
     * そしてブレーキの力(gBreakingForce)を指定して車をコントロールします。
     */
    protected void setForce(double gEngineForce,double gVehicleSteering,double gBreakingForce) {
        super.setForce(gEngineForce,gVehicleSteering,gBreakingForce);
    }

    /**
     * この車の現在の座標を取得します。
     */
    protected Vector3d getLoc() {
        return super.getLoc();
    }

    /**
     * この車の現在の回転を四元数で取得します。
     */
    protected Quat4d getQuat() {
        return super.getQuat();
    }

    /**
     * この車の現在の回転をオイラー角で取得します。
     */
    protected Vector3d getRot() {
        return super.getRot();
    }

    /**
     * この車の現在の進行方向左向きの単位ベクトルを取得します。
     */
    protected Vector3d getUnitVecX() {
        return super.getUnitVecX();
    }

    /**
     * この車の現在の進行方向上向きの単位ベクトルを取得します。
     */
    protected Vector3d getUnitVecY() {
        return super.getUnitVecY();
    }

    /**
     * この車の現在の進行方向の単位ベクトルを取得します。
     */
    protected Vector3d getUnitVecZ() {
        return super.getUnitVecZ();
    }

    /**
     * シミュレーション開始からの時間(秒)を返します。
     */
    protected double getTime() {
        return super.getTime();
    }

    /**
     * この車をコントロールするプログラムを記述するメソッドです。
     * このCarBaseを継承するクラスでは必ず、このメソッドを実装して
     * 下さい。
     */
    public abstract void exec();
}

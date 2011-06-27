package com.github.kenji0717.a3cs;

import java.util.ArrayList;
import javax.vecmath.*;

/**
 * このクラスを拡張してバトルするための車を作成します。
 * これを拡張して作成する車クラスは必ず引数無しの
 * コンストラクタを持つ必要があります。
 * この車は初期値100のエネルギーを持ち、被弾すると5づつエネルギーが
 * 減少します。また弾丸を発射した時も1づつエネルギーを消費します。
 */
public abstract class BattleCarBase extends CarBase {

    /**
     * BattleCarBaseのコンストラクタです。
     */
    protected BattleCarBase() {
    }

    /**
     * この車のID(整数値)を取得します。
     */
    protected int getCarID() {
        return super.getCarID();
    }

    /**
     * 前進のための力(engine)、ハンドルの方向(steering:正->左,負->右)、
     * そしてブレーキの力(breaking)を指定して車をコントロールします。
     */
    protected void setForce(double engine,double steering,double breaking) {
        super.setForce(engine,steering,breaking,0.0);
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
     * 弾丸を発射します。引数のベクトルは弾丸を発射したい方向を世界座標で
     * 指定します。ただしこの車の進行方向と弾丸の発射方向が45度以上開いて
     * いる場合は弾丸は発射されません。このメソッドを呼び出すと無条件で
     * エネルギーを1消費します。
     */
    protected void shoot(Vector3d d) {
        super.shoot(d);
    }

    /**
     * 現在のフィールドに存在している全ての車の情報を取得します。
     * データの詳細についてはCarDataクラスを参照して下さい。
     */
    protected ArrayList<CarData> getAllCarData() {
        return super.getAllCarData();
    }

    /**
     * この車の現在のエネルギー値を取得します。
     */
    protected int getEnergy() {
        return super.getEnergy();
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

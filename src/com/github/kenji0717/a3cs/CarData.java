package com.github.kenji0717.a3cs;

import javax.vecmath.*;

/**
 * 車に関するデータをひとまとめにして受け渡しをするための
 * クラスです。
 */
public class CarData {
    /**
     * 対象の車のIDです。
     */
    public int carID;

    /**
     * 対象の車の位置座標です。
     */
    public Vector3d loc;

    /**
     * 対象の車の左方向単位ベクトルです。
     */
    public Vector3d unitVecX;

    /**
     * 対象の車の上方向単位ベクトルです。
     */
    public Vector3d unitVecY;

    /**
     * 対象の車の進行方向単位ベクトルです。
     */
    public Vector3d unitVecZ;

    /**
     * 対象の車のエネルギー値です。
     */
    public int energy;
}

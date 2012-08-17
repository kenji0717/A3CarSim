package com.github.kenji0717.a3cs;

import java.util.*;
import java.net.URL;
import java.io.*;
import javax.vecmath.*;

/**
 * このクラスを拡張してレースするための車を作成します。
 * これを拡張して作成する車クラスは必ず引数無しの
 * コンストラクタを持つ必要があります。
 */
public abstract class RaceCarBase extends CarBase {

    ArrayList<Vector3d> points = new ArrayList<Vector3d>();
    /**
     * RaceCarBaseのコンストラクタです。
     */
    protected RaceCarBase() {
        try {
            URL url = new URL("x-res:///res/lines.txt");
            InputStream is = url.openStream();
            InputStreamReader isr = new InputStreamReader(is);
            BufferedReader br = new BufferedReader(isr);
            String s = "";
            while (true) {
                String line = br.readLine();
                if (line==null) break;
                s = s + line;
            }
            String ss[] = s.split(",");
            for (int i=0;i<ss.length;i++) {
                String sss[] = ss[i].trim().split("\\s+");
                double x = Double.parseDouble(sss[0]);
                double y = Double.parseDouble(sss[1]);
                double z = Double.parseDouble(sss[2]);
                points.add(new Vector3d(x,y,z));
            }
        } catch(Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * この車のID(整数値)を取得します。
     */
    protected int getCarID() {
        return super.getCarID();
    }

    /**
     * 前進のための力(engine)、ハンドルの方向(steering:正->左,負->右)、
     * ブレーキの力(breaking)、そしてドリフト(drift)を指定して車をコントロールします。
     */
    protected void setForce(double engine,double steering,double breaking,double drift) {
        super.setForce(engine,steering,breaking,drift);
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

    /**
     * コースのセンターライン上のi番目のポイント情報を取り出します。
     * 引数iは0以上689以下の数字でなければならず、それ以外の数値を指定した場合はnullを返します。
     */
    protected Vector3d getPoint(int i) {
        if (i<0 || i>=points.size()) return null;
        return new Vector3d(points.get(i));
    }

    int getNearestPointIndex() {
        Vector3d loc = getLoc();
        Vector3d tmp = new Vector3d();
        double min = Double.MAX_VALUE;
        int index = 0;
        for (int i=0;i<points.size();i++) {
            tmp.sub(points.get(i),loc);
            double dis = tmp.length();
            if (dis<min) {
                min = dis;
                index = i;
            }
        }
        return index;
    }
    /**
     * コースのセンターライン上の690個のポイントのうち、現在位置に最も近いポイントを返します。
     */
    protected Vector3d getNearestPoint() {
        int index = getNearestPointIndex();
        return new Vector3d(points.get(index));
    }

    /**
     * コースのセンターライン上の690個のポイントのうち、現在位置に
     * 最も近いポイントにおけるコースの進行方向を計算し単位ベクトルで返します。
     */
    protected Vector3d getDirection() {
        Vector3d tmp = new Vector3d();
        int index = getNearestPointIndex();
        if (index==689) return new Vector3d(0,0,-1);
        tmp.sub(points.get(index+1),points.get(index));
        tmp.normalize();
        return tmp;
    }
    int getNearestPointPlusIndex(double plus) {
        double d = 0.0;
        int index = getNearestPointIndex();
        Vector3d tmp = new Vector3d();
        while (true) {
            int index2 = index+1; index2 = index2 % 690;
            tmp.sub(points.get(index),points.get(index2));
            d = d + tmp.length();
            if (d>=plus)
                return index;
            index = index + 1;
            index = index % 690;
        }
    }
    /**
     * コースのセンターライン上の690個のポイントのうち、現在位置に最も近いポイントを検索した後、
     * そのポイントからplusの距離前方のポイントを返します。
     * 厳密にはplus距離以上前方で最も近いポイントとなります。
     */
    protected Vector3d getNearestPointPlus(double plus) {
        int index = getNearestPointPlusIndex(plus);
        return new Vector3d(points.get(index));
    }

    /**
     * コースのセンターライン上の690個のポイントのうち、現在位置に最も近いポイントを検索した後、
     * そのポイントからplusの距離前方のポイントを算出し、そのポイントにおけるコースの進行方向を
     * 計算し単位ベクトルで返します。
     */
    protected Vector3d getDirectionPlus(double plus) {
        Vector3d tmp = new Vector3d();
        int index = getNearestPointPlusIndex(plus);
        if (index==689) return new Vector3d(0,0,-1);
        tmp.sub(points.get(index+1),points.get(index));
        tmp.normalize();
        return tmp;
    }
}

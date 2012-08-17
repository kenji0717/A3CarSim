package test;

import com.github.kenji0717.a3cs.*;
import javax.vecmath.Vector3d;

public class TestCar01 extends RaceCarBase {
    public TestCar01() {
    }
    public void exec() {
        Vector3d loc = getLoc();
        
        Vector3d mae1 = getNearestPointPlus(10.0);//前方の座標1
        Vector3d mae2 = getNearestPointPlus(30.0);//前方の座標2
        mae1.sub(loc);
        mae1.normalize();
        mae2.sub(loc);
        mae2.normalize();
        Vector3d left = getUnitVecX();
        Vector3d front = getUnitVecZ();

        boolean noborizaka = mae1.y>0.1;
        boolean kyuukaabu = front.dot(mae2)<0.85;

        double engineForce = 600.0; //エンジン出力
        if (noborizaka) engineForce += 150; //上り坂では加速
        if (kyuukaabu) engineForce = 300; //急カーブでは減速
        double steering = 0.3*mae1.dot(left); //左にハンドルを切る量
        double breakingForce = kyuukaabu?10.0:0.0;//急カーブの時ブレーキ
        double drift=0.0; //ドリフトしない。

        setForce(engineForce,steering,breakingForce,drift);

if (engineForce>700.0)System.out.println("加速");
if (breakingForce>1.0)System.out.println("ブレーキ");
//System.out.println("Debug: "+getNearestPoint());
//System.out.println("Debug: "+getDirectionPlus(10.0));
//System.out.println("Debug: "+getVel());
System.out.println("-");
    }
}

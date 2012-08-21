title: Sample Program of CarRace

Sample Program of CarRace
================================================

サンプルプログラムです．

    import com.github.kenji0717.a3cs.*;
    import javax.vecmath.Vector3d;
    
    public class S1012000 extends RaceCarBase {
        public void exec() {
            Vector3d loc = getLoc();//現在の車の座標
    
            Vector3d point = getPoint(13.0);//前方13メートルの座標
            Vector3d dir = getDirection(13.0);//前方13メートルの方向
            point.sub(loc);//車から見たポイントの方向
            point.normalize();//長さを1に正規化
            Vector3d left = getUnitVecX();//車の左向き方向
            Vector3d front = getUnitVecZ();//車の前方方向
    
            boolean noborizaka = point.y>0.1;//10%以上の上り坂
            boolean kyuukaabu = front.dot(dir)<0.9;//ポイント方向と車の方向の一致度が0.9以下
    
            double engineForce = 700.0; //エンジン出力
            if (noborizaka) engineForce += 200; //上り坂では加速
            if (kyuukaabu) engineForce = 300; //急カーブでは減速
            double steering = 0.3*point.dot(left); //左にハンドルを切る量
            double breakingForce = kyuukaabu?10.0:0.0;//急カーブの時ブレーキ
            double drift=0.0; //ドリフトしない。
    
            setForce(engineForce,steering,breakingForce,drift);//実際にパラメータを設定
    //以下デバッグ用のプリント文(必要に応じてコメントを外す)
    //if (noborizaka)System.out.println("上り坂");
    //if (kyuukaabu)System.out.println("急カーブ");
    //System.out.println("Debug: "+getPoint());
    //System.out.println("Debug: "+getDirection(10.0));
    //System.out.println("Debug: "+getVel());
    //System.out.println("Debug: "+getDist());
    //System.out.println("-");
        }
    }

このソースファイルで「ベクトル変数.dot(ベクトル変数)」という
プログラムが2箇所ありますが，ここでは2つのベクトルの内積を計算
しています．内積を簡単に説明すると，2つのベクトルの方向の一致度と
それぞれのベクトルの長さを掛け合せた値です．上のプログラム
では内積を計算しているベクトルの長さはいずれも1.0に正規化されて
いるので，単純に2つのベクトルの方向の一致度と考えることができます．
方向がまったく同じなら1.0，完全に逆向きなら-1.0になります．

その他，各メソッドの説明はAPIのページのRaceCarBaseクラスを参照
して下さい．

* [APIのページ](http://kenji0717.github.com/A3CarSim/doc/api/)


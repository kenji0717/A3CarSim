title: Sample Program of CarRace

Sample Program of CarRace
================================================

サンプルプログラムです．

    import com.github.kenji0717.a3cs.*;
    import javax.vecmath.Vector3d;
    import java.util.ArrayDeque;
    
    public class S1012000 extends RaceCarBase {
	ArrayDeque<Vector3d> points = new ArrayDeque<Vector3d>();
	Vector3d target;
	public S1012000() {
	    points.addLast(new Vector3d(  0,  0,-25));//cp00
	    points.addLast(new Vector3d(-13,  0,-52));
	    points.addLast(new Vector3d(-37,  0,-56));//cp01
	    points.addLast(new Vector3d(-68,  0,-49));
	    points.addLast(new Vector3d(-76,  0,  9));//cp02
	    points.addLast(new Vector3d(-30,  0,  9));
	    points.addLast(new Vector3d(-21,  0, 20));//cp03
	    points.addLast(new Vector3d(-45,  0, 27));
	    points.addLast(new Vector3d(-54,  0, 35));//cp04
	    points.addLast(new Vector3d(-34,  0, 44));
	    points.addLast(new Vector3d(-16,  0, 35));//cp05
	    points.addLast(new Vector3d( -9,  0,-28));
	    points.addLast(new Vector3d(-23,  0,-39));//cp06
	    points.addLast(new Vector3d(-36,  0,-29));
	    points.addLast(new Vector3d(-36,  0, -6));
	    points.addLast(new Vector3d(-43,  0,  0));//cp07
	    points.addLast(new Vector3d(-52,  0, -4));
	    points.addLast(new Vector3d(-47,  0,-28));
	    points.addLast(new Vector3d(-53,  0,-38));//cp08
	    points.addLast(new Vector3d(-64,  0,-32));
	    points.addLast(new Vector3d(-65,  5, 11));//cp09
	    points.addLast(new Vector3d(-60,  0, 49));
	    points.addLast(new Vector3d(-40,  0, 55));//cp10
	    points.addLast(new Vector3d( -9,  0, 46));
	    points.addLast(new Vector3d(  0,  0, 10));//cp11
    
	    target = points.removeFirst();
	}
	public void exec() {
	    Vector3d v = new Vector3d(target);
	    v.sub(this.getLoc());
	    Vector3d left = getUnitVecX();
    
	    if ((v.length()<1.0)&&(!points.isEmpty()))
		target = points.removeFirst();
    
	    v.normalize();
	    setForce(200,0.3*v.dot(left),0,0);
	}
    }

とりあえず一周するだけです．

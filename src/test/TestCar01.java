package test;

import com.github.kenji0717.a3cs.*;
import javax.vecmath.Vector3d;
import java.util.ArrayDeque;

public class TestCar01 extends CarBase {
    ArrayDeque<Vector3d> points = new ArrayDeque<Vector3d>();
    Vector3d target;
    public TestCar01() {
        points.push(new Vector3d(  0,  0, 10));
        points.push(new Vector3d(-40,  0, 55));
        points.push(new Vector3d(-65,  4, 11));
        points.push(new Vector3d(-53,  0,-38));
        points.push(new Vector3d(-43,  0,  0));
        points.push(new Vector3d(-23,  0,-39));
        points.push(new Vector3d(-16,  0, 35));
        points.push(new Vector3d(-54,  0, 35));
        points.push(new Vector3d(-21,  0, 20));
        points.push(new Vector3d(-76,  0,  9));
        points.push(new Vector3d(-37,  0,-56));
        points.push(new Vector3d(-10,  0,-40));
        points.push(new Vector3d(  0,  0,-25));

        target = points.pop();
    }
    public void exec() {
        Vector3d v = new Vector3d(target);
        v.sub(this.getLoc());
        Vector3d left = getUnitVecX();

        if ((v.length()<0.5)&&(!points.isEmpty()))
            target = points.pop();

        v.normalize();
        setForce(200,0.3*v.dot(left),0);
    }
}

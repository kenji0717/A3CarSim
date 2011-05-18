package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.CollisionDispatcher;
import com.bulletphysics.collision.dispatch.DefaultCollisionConfiguration;
import com.bulletphysics.collision.shapes.CollisionShape;
import com.bulletphysics.dynamics.DiscreteDynamicsWorld;
import com.bulletphysics.dynamics.RigidBody;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.Transform;

import com.bulletphysics.dynamics.DynamicsWorld;
import com.bulletphysics.linearmath.Clock;
import com.bulletphysics.linearmath.DefaultMotionState;
import com.bulletphysics.dynamics.RigidBodyConstructionInfo;
import jp.sourceforge.acerola3d.a3.*;
import javax.vecmath.*;
import java.awt.event.*;

/**
 * jbulletのVehicleモデルを使ったシミュレーション
 * SuperTuxKartのクリエータに感謝！
 */
public class SimpleCR implements Runnable, KeyListener {
    public BroadphaseInterface overlappingPairCache;
    public CollisionDispatcher dispatcher;
    public ConstraintSolver constraintSolver;
    public DefaultCollisionConfiguration collisionConfiguration;
    protected DynamicsWorld dynamicsWorld = null;
    protected Clock clock = new Clock();

    A3Window w;
    Action3D a3car;
    VRML backgroundVRML;
    CarMotion carMotion;

    boolean keyUp = false;
    boolean keyDown = false;
    boolean keyRight = false;
    boolean keyLeft = false;
    boolean keySpace = false;
    
    private static float gEngineForce = 0.0f;
    private static float gBreakingForce = 0.0f;
    private static float maxEngineForce = 500.f;
    private static float maxBreakingForce = 100.f;
    private static float gVehicleSteering = 0.0f;
    private static float steeringClamp = 0.1f;

    public SimpleCR() throws Exception {
        backgroundVRML = new VRML("x-rzip:x-res:///res/stk_racetrack.a3!/racetrack.wrl");

        initPhysics();

        w = new A3Window(600,400);
        w.setCameraLocImmediately(0.0,3.0,-10.0);
        w.setCameraLookAtPointImmediately(0.0,0.0,30.0);
        w.addKeyListener(this);
        w.add(backgroundVRML);

        //Action3D bgm = new Action3D("x-res:///stk_Boom3.a3");
        //w.add(bgm);

        a3car = new Action3D("x-res:///res/stk_tux.a3");
        DefaultMotionState dms = new DefaultMotionState();
        carMotion = new CarMotion(dms, dynamicsWorld);
        a3car.setMotion("default",carMotion);//この行次の行より先に実行
        w.add(a3car);//この行上の行より後に実行
        dynamicsWorld.addRigidBody(carMotion.carChassis);
        //a3car.transControlUsingRootBone(true);
        w.setAvatar(a3car);
        Vector3d lookAt = new Vector3d(0.0,0.0,30.0);
        Vector3d camera = new Vector3d(0.0,3.0,-10.0);
        Vector3d up = new Vector3d(0.0,1.0,0.0);
        w.setNavigationMode(A3CanvasInterface.NaviMode.CHASE,lookAt,camera,up,1.0);

        Thread t = new Thread(this);
        t.start();
    }

    public void initPhysics() {

        collisionConfiguration = new DefaultCollisionConfiguration();
        dispatcher = new CollisionDispatcher(collisionConfiguration);
        Vector3f worldMin = new Vector3f(-1000, -1000, -1000);
        Vector3f worldMax = new Vector3f(1000, 1000, 1000);
        overlappingPairCache = new DbvtBroadphase();
        constraintSolver = new SequentialImpulseConstraintSolver();
        dynamicsWorld = new DiscreteDynamicsWorld(dispatcher, overlappingPairCache, constraintSolver, collisionConfiguration);

        CollisionShape groundShape = Util.makeBvhTriangleMeshShape(backgroundVRML.getNode());
        Transform tr = new Transform();
        tr.setIdentity();
        localCreateRigidBody(0, tr, groundShape);
    }

    public void run() {
        while (true) {
            try {Thread.sleep(33);}catch(Exception e){;}
            float dt = getDeltaTimeMicroseconds() * 0.000001f;
            if (dynamicsWorld != null) {
                int maxSimSubSteps = 2;
                int numSimSteps = dynamicsWorld.stepSimulation(dt,maxSimSubSteps);
            }
            gEngineForce=0.0f;
            if (keyUp) gEngineForce+=maxEngineForce;
            if (keyDown) gEngineForce-=maxEngineForce;
            gVehicleSteering = 0.0f;
            if (keyRight) gVehicleSteering -= steeringClamp;
            if (keyLeft) gVehicleSteering += steeringClamp;
            if (keySpace)
                gBreakingForce=maxBreakingForce;
            else
                gBreakingForce=0.0f;
            carMotion.setForce(gEngineForce,gVehicleSteering,gBreakingForce);
        }
    }
    
    public static void main(String[] args) throws Exception {
        new SimpleCR();
    }
    //--------------------------------

    public RigidBody localCreateRigidBody(float mass, Transform startTransform, CollisionShape shape) {
        boolean isDynamic = (mass != 0f);

        Vector3f localInertia = new Vector3f(0f, 0f, 0f);
        if (isDynamic) {
            shape.calculateLocalInertia(mass, localInertia);
        }

        DefaultMotionState myMotionState = new DefaultMotionState(startTransform);
        
        RigidBodyConstructionInfo cInfo = new RigidBodyConstructionInfo(mass, myMotionState, shape, localInertia);
        
        RigidBody body = new RigidBody(cInfo);
        
        dynamicsWorld.addRigidBody(body);

        return body;
    }
    
    public float getDeltaTimeMicroseconds() {
        float dt = clock.getTimeMicroseconds();
        clock.reset();
        return dt;
    }

    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
        case KeyEvent.VK_UP:    keyUp = true;    break;
        case KeyEvent.VK_DOWN:  keyDown = true;  break;
        case KeyEvent.VK_RIGHT: keyRight = true; break;
        case KeyEvent.VK_LEFT:  keyLeft = true;  break;
        case KeyEvent.VK_SPACE: keySpace = true; break;
        }
    }
    public void keyReleased(KeyEvent ke) {
        switch(ke.getKeyCode()) {
        case KeyEvent.VK_UP:    keyUp = false;    break;
        case KeyEvent.VK_DOWN:  keyDown = false;  break;
        case KeyEvent.VK_RIGHT: keyRight = false; break;
        case KeyEvent.VK_LEFT:  keyLeft = false;  break;
        case KeyEvent.VK_SPACE: keySpace = false; break;
        }
    }
    public void keyTyped(KeyEvent ke) {;}
}

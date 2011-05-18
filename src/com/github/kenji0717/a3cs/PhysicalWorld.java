package com.github.kenji0717.a3cs;

import com.bulletphysics.collision.broadphase.AxisSweep3;
import com.bulletphysics.collision.broadphase.BroadphaseInterface;
import com.bulletphysics.collision.broadphase.DbvtBroadphase;
import com.bulletphysics.collision.dispatch.*;
import com.bulletphysics.collision.shapes.*;
import com.bulletphysics.dynamics.*;
import com.bulletphysics.dynamics.constraintsolver.ConstraintSolver;
import com.bulletphysics.dynamics.constraintsolver.SequentialImpulseConstraintSolver;
import com.bulletphysics.linearmath.*;
import java.util.*;
import javax.vecmath.*;
import jp.sourceforge.acerola3d.a3.*;

//物理計算をしてくれるクラス
public class PhysicalWorld implements Runnable {
    static int MAX_PROXIES = 1024;
    DiscreteDynamicsWorld dynamicsWorld;
    ArrayList<A3RigidBody> rigidBodies = new ArrayList<A3RigidBody>();
    ArrayList<A3RigidBody> newBodies = new ArrayList<A3RigidBody>();
    ArrayList<A3RigidBody> delBodies = new ArrayList<A3RigidBody>();
    A3Window window;

    //物理世界の初期化
    public PhysicalWorld() {
    	window = new A3Window(300,300);
        CollisionConfiguration collisionConfiguration =
                new DefaultCollisionConfiguration();
        CollisionDispatcher dispatcher =
                new CollisionDispatcher(collisionConfiguration);
        /*
        Vector3f worldAabbMin = new Vector3f(-10000,-10000,-10000);
        Vector3f worldAabbMax = new Vector3f(10000,10000,10000);
        int maxProxies = MAX_PROXIES;
        AxisSweep3 overlappingPairCache =
                new AxisSweep3(worldAabbMin, worldAabbMax, maxProxies);
        SequentialImpulseConstraintSolver solver =
                new SequentialImpulseConstraintSolver();
        */
        BroadphaseInterface overlappingPairCache = new DbvtBroadphase();
        ConstraintSolver solver = new SequentialImpulseConstraintSolver();

        dynamicsWorld =
                new DiscreteDynamicsWorld(dispatcher,overlappingPairCache,
                                          solver,collisionConfiguration);
        dynamicsWorld.setGravity(new Vector3f(0,-10,0));

        Thread t = new Thread(this);
        t.start();
    }

    //新規の剛体を加える
    public void add(A3RigidBody rb) {
        synchronized (newBodies) {
            newBodies.add(rb);
        }
    }

    //既存の剛体を削除
    public void del(A3RigidBody rb) {
        synchronized (delBodies) {
            delBodies.add(rb);
        }
    }

    //物理計算を進める処理
    //座標を変更するのがちょっとやっかい
    public void run() {
        while (true) {
            synchronized (newBodies) {
                for (A3RigidBody rb : newBodies) {
                    dynamicsWorld.addRigidBody(rb.body,rb.group,rb.mask);
                    //if (rb instanceof MyCar)
                    //    dynamicsWorld.removeVehicle(((MyCar)rb).motion.vehicle);
                    if (window!=null)
                        window.add(rb.a3);
                    rigidBodies.add(rb);
                }
                newBodies.clear();
            }
            synchronized (delBodies) {
                for (A3RigidBody rb : delBodies) {
                    dynamicsWorld.removeRigidBody(rb.body);
                    //if (rb instanceof MyCar)
                    //    dynamicsWorld.addVehicle(((MyCar)rb).motion.vehicle);
                    if (window!=null)
                        window.del(rb.a3);
                    rigidBodies.remove(rb);
                }
                delBodies.clear();
            }

            for (A3RigidBody rb : rigidBodies) {
                if (rb.locRequest==null)
                    continue;
                Transform t = new Transform();
                t.origin.set(rb.locRequest);
                rb.motionState.setWorldTransform(t);
                rb.body.setCollisionFlags(rb.body.getCollisionFlags()|CollisionFlags.KINEMATIC_OBJECT);
                rb.body.setActivationState(CollisionObject.DISABLE_DEACTIVATION);
                rb.body.clearForces();
                rb.body.setLinearVelocity(new Vector3f());
                rb.body.setAngularVelocity(new Vector3f());
            }

            dynamicsWorld.stepSimulation(1.0f/30.0f,10);
            //dynamicsWorld.stepSimulation(1.0f/30.0f,2);

            for (A3RigidBody rb : rigidBodies) {
                if (rb.locRequest==null)
                    continue;
                rb.body.setCollisionFlags(rb.body.getCollisionFlags()&(~CollisionFlags.KINEMATIC_OBJECT));
                //rb.body.setActivationState(CollisionObject.ACTIVE_TAG);
                rb.body.forceActivationState(CollisionObject.ACTIVE_TAG);
                rb.body.setDeactivationTime(0.0f);
                rb.body.clearForces();
                rb.body.setLinearVelocity(new Vector3f());
                rb.body.setAngularVelocity(new Vector3f());
                rb.locRequest=null;
            }

            for (A3RigidBody rb : rigidBodies) {
                if (rb.velRequest==null)
                    continue;
                rb.body.setLinearVelocity(rb.velRequest);
                rb.velRequest=null;
            }
            try{Thread.sleep(33);}catch(Exception e){;}
        }
    }
}

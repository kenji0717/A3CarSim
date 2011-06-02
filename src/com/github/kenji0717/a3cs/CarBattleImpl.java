package com.github.kenji0717.a3cs;

import java.util.ArrayList;
import javax.vecmath.Vector3d;

class CarBattleImpl implements Runnable, CollisionListener, CarSim {
    PhysicalWorld pw;
    CarBase car1;
    CarBase car2;
    ArrayList<ActiveObject> activeObjects = new ArrayList<ActiveObject>();
    Object waitingRoom = new Object();
    boolean battleRunning = false;//一時停止中でもtrue
    boolean simRunning = false;//一時停止中はfalse
    boolean pauseRequest = true;

    CarBattleGUI gui;

    CarBattleImpl(String args[]) {
        pw = new PhysicalWorld();
        pw.addCollisionListener(this);

        gui = new CarBattleGUI(this,args);
        gui.pack();
        gui.setVisible(true);

        pw.setMainCanvas(gui.mainCanvas);
        pw.addSubCanvas(gui.car1Canvas);
        pw.addSubCanvas(gui.car2Canvas);

        Thread t = new Thread(this);
        pauseRequest = true;
        t.start();
    }

    //clearの処理
    void clearBattle() {
        if (simRunning)
            throw new IllegalStateException();
        if (battleRunning)
            throw new IllegalStateException();
        pw.clear();
        activeObjects.clear();
        car1 = null;
        car2 = null;
    }
    void initBattle() {
        if (simRunning)
            throw new IllegalStateException();
        if (battleRunning)
            throw new IllegalStateException();
        MyGround2 g = new MyGround2(pw);
        pw.add(g);
        //MyGround g = new MyGround(pw);
        //pw.add(g);

        String carClass1 = gui.car1classTF.getText();
        String carClass2 = gui.car2classTF.getText();
        try {
            ClassLoader cl = this.getClass().getClassLoader();
            Class<?> theClass = cl.loadClass(carClass1);
            Class<? extends CarBase> tClass = theClass.asSubclass(CarBase.class);
            car1 = tClass.newInstance();

            theClass = cl.loadClass(carClass2);
            tClass = theClass.asSubclass(CarBase.class);
            car2 = tClass.newInstance();
        } catch(Exception e) {
            System.out.println("Class Load Error!!!");
        }
        car1.init(new Vector3d( 1,0.5,-10),new Vector3d(),"x-res:///res/stk_tux.a3",pw,this);
        car2.init(new Vector3d(-1,0.5,10),new Vector3d(0,3.14,0),"x-res:///res/stk_tux.a3",pw,this);

        pw.add(car1.car);
        pw.add(car2.car);
        gui.setCar1(car1);
        gui.setCar2(car2);
        activeObjects.add(car1);
        activeObjects.add(car2);
        gui.updateCar1Info(car1);
        gui.updateCar2Info(car2);

    }
    void startBattle() {
        if (simRunning) {
            return;
            //throw new IllegalStateException();
        }
        if (battleRunning) {
            pauseRequest = false;
            synchronized (waitingRoom) {
                waitingRoom.notifyAll();
            }
            pw.resume();
        } else {
            clearBattle();
            initBattle();
            pauseRequest = false;
            synchronized (waitingRoom) {
                waitingRoom.notifyAll();
            }
            pw.resume();
            gui.setParamEditable(false);
            battleRunning = true;
        }
    }
    void pauseBattle() {
        if (simRunning) {
            pauseRequest = true;
            pw.pause();
        } else {
            pauseRequest = false;
            synchronized (waitingRoom) {
                waitingRoom.notifyAll();
            }
            pw.resume();
        }
    }
    void stopBattle() {
        pauseRequest = true;
        try{Thread.sleep(300);}catch(Exception e){;}
        battleRunning = false;
        clearBattle();
        pw.pause();
        gui.setParamEditable(true);
    }
    public void run() {
        ArrayList<ActiveObject> tmp = new ArrayList<ActiveObject>();
        simRunning = true;
        while (true) {
            synchronized (waitingRoom) {
                try {
                    if (pauseRequest) {
                        simRunning = false;
                        waitingRoom.wait();
                        simRunning = true;
                    }
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
            synchronized (activeObjects) {
                tmp.clear();
                tmp.addAll(activeObjects);
            }
            for (ActiveObject o: tmp) {
                o.exec();
            }
            gui.updateCar1Info(car1);
            gui.updateCar2Info(car2);
            try{Thread.sleep(33);}catch(Exception e){;}
        }
    }

    @Override
    public void collided(A3CollisionObject a, A3CollisionObject b) {
        if ((a instanceof MyBullet)||(b instanceof MyBullet)) {
            MyBullet bullet = null;
            A3CollisionObject other = null;
            if (a instanceof MyBullet) {
                bullet = (MyBullet)a;
                other = b;
            } else {
                bullet = (MyBullet)b;
                other = a;
            }
            if (other instanceof MyBullet) {
                pw.del(other);
                this.delActiveObject((MyBullet)other);
            } else if (other instanceof MyCar) {
                ((MyCar)other).carBase.hit();
            } else if (other instanceof MyGround2){
                ;
            } else {
                ;
            }
            pw.del(bullet);
            this.delActiveObject(bullet);
        }
        //System.out.println("gaha a:"+a.a3.getUserData()+" b:"+b.a3.getUserData());
    }

    @Override
    public ArrayList<CarBase> getAllCar() {
        ArrayList<CarBase> ret = new ArrayList<CarBase>();
        ret.add(car1);
        ret.add(car2);
        return ret;
    }

    @Override
    public void addActiveObject(ActiveObject o) {
        synchronized (activeObjects) {
            activeObjects.add(o);
        }
    }

    @Override
    public void delActiveObject(ActiveObject o) {
        synchronized (activeObjects) {
            activeObjects.remove(o);
        }
    }
}

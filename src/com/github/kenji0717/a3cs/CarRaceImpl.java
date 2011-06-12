package com.github.kenji0717.a3cs;

import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLClassLoader;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.prefs.Preferences;

import javax.swing.JOptionPane;
import javax.vecmath.Vector3d;

class CarRaceImpl implements Runnable, CollisionListener, CarSim {
    PhysicalWorld pw;
    Preferences prefs;
    CarBase car;
    String carClasspath;
    String workDir;
    String workDirURL;
    ArrayList<ActiveObject> activeObjects = new ArrayList<ActiveObject>();
    Object waitingRoom = new Object();
    boolean battleRunning = false;//一時停止中でもtrue
    boolean simRunning = false;//一時停止中はfalse
    boolean pauseRequest = true;
    ScheduledThreadPoolExecutor executor = new ScheduledThreadPoolExecutor(1);
    URLClassLoader classLoader;
    CarRaceGUI gui;

    CarRaceImpl(String args[]) {
        prefs = Preferences.userNodeForPackage(this.getClass());
        String carClass = prefs.get("carClass","test.TestCar01");
        if (args.length>=1) {
            carClass = args[0];
            prefs.put("carClass",carClass);
        }
        carClasspath = prefs.get("arClasspath","System");
        workDir = prefs.get("workDir",null);
        workDirURL = prefs.get("workDirURL",null);

        pw = new PhysicalWorld();
        pw.addCollisionListener(this);

        gui = new CarRaceGUI(this,carClass);
        gui.pack();
        gui.setVisible(true);

        pw.setMainCanvas(gui.mainCanvas);
        pw.addSubCanvas(gui.carCanvas);

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
        car = null;
        classLoader = null;
        System.gc();
        gui.clearTA();
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

        classLoader = makeClassLoader(carClasspath);

        String carClass = gui.carClassTF.getText();
        prefs.put("carClass",carClass);
        //try{prefs.flush();}catch(Exception e){;}
        try {
            Class<?> theClass = classLoader.loadClass(carClass);
            Class<? extends CarBase> tClass = theClass.asSubclass(CarBase.class);
            car = tClass.newInstance();
        } catch(Exception e) {
            System.out.println("Class Load Error!!!");
            e.printStackTrace();
        }
        //たぶん車のスタート位置を少し上にして地面に埋まらないようにしておいた方が良いと思う
        car.init(new Vector3d( 1,1.5,-10),new Vector3d(),"x-res:///res/stk_tux.a3",pw,this);

        pw.add(car.car);
        gui.setCar(car);
        activeObjects.add(car);
        gui.updateCarInfo(car);

    }
    
    URLClassLoader makeClassLoader(String s) {
        try {
            URLClassLoader cl = null;
            URL urls[] = null;
            if (s==null) {
                urls = new URL[0];
            } else if (s.equals("System")) {
                urls = new URL[0];
            } else if (s.equals("IDE")) {
                if (workDirURL==null) {
                    urls = new URL[0];
                } else {
                    urls = new URL[]{new URL(workDirURL)};
                }
            } else {
                urls = new URL[]{new URL(s)};
            }
            final URL urlsF[] = urls;
            final ClassLoader pCL = CarBase.class.getClassLoader();
            cl = AccessController.doPrivilegedWithCombiner(new PrivilegedAction<URLClassLoader>() {
                public URLClassLoader run() {
                    return new URLClassLoader(urlsF,pCL);
                }
            });

            return cl;
        } catch (MalformedURLException e) {
            return null;
        } catch (Error e) {
            e.printStackTrace();
            return null;
        }
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
            pw.resume();
            try{Thread.sleep(100);}catch(Exception e){;}//gaha:落ち着くまで待つ
            pauseRequest = false;
            synchronized (waitingRoom) {
                waitingRoom.notifyAll();
            }
            gui.setParamEditable(false);
            battleRunning = true;
        }
    }
    void pauseBattle() {
        if (!battleRunning)
            return;
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
    @Override
    public ArrayList<CarBase> getAllCar() {
        ArrayList<CarBase> ret = new ArrayList<CarBase>();
        ret.add(car);
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
            gui.updateCarInfo(car);

            if (car.energy==0) {
                pauseRequest = true;
                pw.pause();
                Runnable r = new Runnable() {
                    public void run() {
                        finishBattle();
                    }
                };
                executor.schedule(r,100,TimeUnit.MILLISECONDS);
            }
            try{Thread.sleep(33);}catch(Exception e){;}
        }
    }

    void finishBattle() {
        String message = "????????????";
        JOptionPane.showMessageDialog(gui,message);

        stopBattle();
    }
    void changeCP(String cp) {
        carClasspath = cp;
        prefs.put("carClasspath",carClasspath);
    }
    void setWorkDirURL(String wdu) {
        workDirURL = wdu;
        prefs.put("workDirURL",workDirURL);
    }
    void setWorkDir(String wd) {
        workDir = wd;
        prefs.put("workDir",workDir);
    }
}

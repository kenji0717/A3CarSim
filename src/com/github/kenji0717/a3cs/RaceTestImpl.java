package com.github.kenji0717.a3cs;

import java.util.ArrayList;
import jp.sourceforge.acerola3d.a3.*;
import javax.vecmath.*;

class RaceTestImpl implements CarSim, Runnable {
    PhysicalWorld pw;
    KeyboardRaceCar car;
    A3Window window;

    RaceTestImpl() {
        pw = new PhysicalWorld();
        window = new A3Window(500,300);
        pw.setMainCanvas(window);

        MyGround2 g = new MyGround2(pw);
        pw.add(g);

        for (double x=-3.0;x<=3.0;x+=2.0) {
            for (double y=1.0;y<=7.0;y+=2.0) {
                for (double z=-3.0;z<=3.0;z+=2.0) {
                    MyBox b = new MyBox(x,y,z+20,pw);//立方体
                    pw.add(b);
                }
            }
        }

        car = new KeyboardRaceCar();
        car.init(new Vector3d(0,0.8,-1),new Vector3d(0,3.1,0),"x-res:///res/stk_tux.a3",pw,this);
        window.addKeyListener(car);
        pw.add(car.car);

        window.setAvatar(car.car.a3);
        Vector3d lookAt = new Vector3d(0.0,0.0,6.0);
        Vector3d camera = new Vector3d(0.0,3.0,-6.0);
        Vector3d up = new Vector3d(0.0,1.0,0.0);
        window.setNavigationMode(A3CanvasInterface.NaviMode.CHASE,lookAt,camera,up,1.0);
        //window.setNavigationMode(A3CanvasInterface.NaviMode.SIMPLE,3.0);

        pw.addTask(this);

        pw.resume();
        window.insertTaskIntoTimerLoop(pw);
        window.insertTaskIntoRenderingLoop(new Runnable(){
            public void run() {
                System.out.println("GAHA:-----");
            }
        });
    }

    @Override
    public ArrayList<CarBase> getAllCar() {
        ArrayList<CarBase> al = new ArrayList<CarBase>();
        al.add(car);
        return al;
    }

    @Override
    public void addActiveObject(ActiveObject o) {;}

    @Override
    public void delActiveObject(ActiveObject o) {;}

    @Override
    public void run() {
        car.exec();
    }
}

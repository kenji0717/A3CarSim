package com.github.kenji0717.a3cs;

import java.awt.BorderLayout;

import jp.sourceforge.acerola3d.a3.*;
import javax.swing.*;
import javax.vecmath.Vector3d;

class CarBattleImpl implements Runnable {
    PhysicalWorld pw;
    String carClass1;
    String carClass2;
    CarBase car1;
    CarBase car2;

    JFrame f;
    A3Canvas mainCanvas;
    A3SubCanvas car1Canvas;
    A3SubCanvas car2Canvas;

    CarBattleImpl(String args[]) {
        carClass1 = args[0];
        carClass2 = args[1];

        pw = new PhysicalWorld();

        f = new JFrame("CarBattle");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setLayout(new BorderLayout());
        HBox baseBox = new HBox();
        f.add(baseBox,BorderLayout.CENTER);

        mainCanvas = A3Canvas.createA3Canvas(400,400);
        mainCanvas.setCameraLocImmediately(0.0,100.0,0.0);
        mainCanvas.setCameraLookAtPoint(0.0,0.0,1.0);
        mainCanvas.setNavigationMode(A3CanvasInterface.NaviMode.EXAMINE);
        pw.setMainCanvas(mainCanvas);
        baseBox.myAdd(mainCanvas,1);
        VBox subBox = new VBox();
        baseBox.myAdd(subBox,0);
        car1Canvas = A3SubCanvas.createA3SubCanvas(200,200);
        pw.addSubCanvas(car1Canvas);
        subBox.myAdd(car1Canvas,1);
        car2Canvas = A3SubCanvas.createA3SubCanvas(200,200);
        pw.addSubCanvas(car2Canvas);
        subBox.myAdd(car2Canvas,1);

        f.pack();
        f.setVisible(true);

        MyGround2 g = new MyGround2(pw);
        pw.add(g);

        initCars();

        Thread t = new Thread(this);
        t.start();
    }

    void initCars() {
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
        car1.init(new Vector3d(-1.0,2.0,0.0),new Vector3d(),"x-res:///res/stk_tux.a3",pw);
        car2.init(new Vector3d(-1.0,2.0,0.0),new Vector3d(),"x-res:///res/stk_tux.a3",pw);

        pw.add(car1.car);
        pw.add(car2.car);

        car1Canvas.setAvatar(car1.car.a3);
        car1Canvas.setNavigationMode(A3CanvasInterface.NaviMode.CHASE);
        car2Canvas.setAvatar(car2.car.a3);
        car2Canvas.setNavigationMode(A3CanvasInterface.NaviMode.CHASE);
    }

    public void run() {
        while (true) {
            car1.exec();
            car2.exec();
            try{Thread.sleep(33);}catch(Exception e){;}
        }
    }
}

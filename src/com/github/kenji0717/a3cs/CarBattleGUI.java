package com.github.kenji0717.a3cs;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import jp.sourceforge.acerola3d.a3.*;
import javax.swing.*;
import javax.vecmath.Vector3d;

class CarBattleGUI extends JFrame implements ActionListener {
    private static final long serialVersionUID = 1L;
    CarBattleImpl impl;
    A3Canvas mainCanvas;
    A3SubCanvas car1Canvas;
    A3SubCanvas car2Canvas;
    JTextField car1classTF;
    JTextField car2classTF;
    JLabel generalInfoL;
    JButton startB;
    JButton pauseB;
    JButton stopB;
    JLabel car1EnergyL;
    JLabel car2EnergyL;

    CarBattleGUI(CarBattleImpl i,String args[]) {
        super("CarBattle");
        impl = i;
        String carClass1 = "test.TestCar02";
        String carClass2 = "test.TestCar02";
        if (args.length>=1)
            carClass1 = args[0];
        if (args.length>=2)
            carClass2 = args[1];

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLayout(new BorderLayout());
        VBox baseBox = new VBox();
        this.add(baseBox,BorderLayout.CENTER);

        HBox controlBox = new HBox();
        baseBox.myAdd(controlBox,0);
        VBox classNameBox = new VBox();
        controlBox.myAdd(classNameBox,1);
        HBox classNameBox1 = new HBox();
        classNameBox.myAdd(classNameBox1,0);
        classNameBox1.myAdd(new JLabel("CAR1:"),0);
        car1classTF = new JTextField(carClass1);
        classNameBox1.myAdd(car1classTF,1);
        HBox classNameBox2 = new HBox();
        classNameBox.myAdd(classNameBox2,0);
        classNameBox2.myAdd(new JLabel("CAR2:"),0);
        car2classTF = new JTextField(carClass2);
        classNameBox2.myAdd(car2classTF,1);
        VBox controlBox2 = new VBox();
        controlBox.myAdd(controlBox2,0);
        HBox generalInfoBox = new HBox();
        controlBox2.myAdd(generalInfoBox,0);
        generalInfoL = new JLabel("Time:");
        generalInfoBox.myAdd(generalInfoL,1);
        HBox mainButtonsBox = new HBox();
        controlBox2.myAdd(mainButtonsBox,0);
        startB = new JButton("START");
        startB.addActionListener(this);
        mainButtonsBox.myAdd(startB,1);
        pauseB = new JButton("PAUSE");
        pauseB.addActionListener(this);
        mainButtonsBox.myAdd(pauseB,1);
        stopB = new JButton("STOP");
        stopB.addActionListener(this);
        mainButtonsBox.myAdd(stopB,1);

        HBox displayBox = new HBox();
        baseBox.myAdd(displayBox,1);
        mainCanvas = A3Canvas.createA3Canvas(400,400);
        mainCanvas.setCameraLocImmediately(0.0,150.0,0.0);
        mainCanvas.setCameraLookAtPointImmediately(-50.0,0.0,1.0);
        A3CSController c = new A3CSController(150.0);
        mainCanvas.setA3Controller(c);
        //mainCanvas.setNavigationMode(A3CanvasInterface.NaviMode.SIMPLE,150.0);
        displayBox.myAdd(mainCanvas,1);
        VBox subBox = new VBox();
        displayBox.myAdd(subBox,1);
        HBox car1Box = new HBox();
        subBox.myAdd(car1Box,1);
        car1Canvas = A3SubCanvas.createA3SubCanvas(200,200);
        car1Box.myAdd(car1Canvas,1);
        VBox car1InfoBox = new VBox();
        car1Box.myAdd(car1InfoBox,0);
        car1InfoBox.myAdd(new JLabel("CAR1:"),0);
        car1EnergyL = new JLabel("Energy: 000");
        car1InfoBox.myAdd(car1EnergyL,0);
        
        HBox car2Box = new HBox();
        subBox.myAdd(car2Box,1);
        car2Canvas = A3SubCanvas.createA3SubCanvas(200,200);
        car2Box.myAdd(car2Canvas,1);
        VBox car2InfoBox = new VBox();
        car2Box.myAdd(car2InfoBox,0);
        car2InfoBox.myAdd(new JLabel("CAR2:"),0);
        car2EnergyL = new JLabel("Energy: 000");
        car2InfoBox.myAdd(car2EnergyL,0);

        //this.pack();
        //this.setVisible(true);
    }

    void setCar1(CarBase c1) {
        car1Canvas.setAvatar(c1.car.a3);
        Vector3d lookAt = new Vector3d(0.0,0.0,6.0);
        Vector3d camera = new Vector3d(0.0,3.0,-6.0);
        Vector3d up = new Vector3d(0.0,1.0,0.0);
        car1Canvas.setNavigationMode(A3CanvasInterface.NaviMode.CHASE,lookAt,camera,up,10.0);
    }
    void setCar2(CarBase c2) {
        car2Canvas.setAvatar(c2.car.a3);
        Vector3d lookAt = new Vector3d(0.0,0.0,6.0);
        Vector3d camera = new Vector3d(0.0,3.0,-6.0);
        Vector3d up = new Vector3d(0.0,1.0,0.0);
        car2Canvas.setNavigationMode(A3CanvasInterface.NaviMode.CHASE,lookAt,camera,up,10.0);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object s = ae.getSource();
        if (s==startB) {
            impl.startBattle();
        } else if (s==pauseB) {
            impl.pauseBattle();
        } else if (s==stopB) {
            impl.stopBattle();
        }
    }
    void setParamEditable(boolean b) {
        car1classTF.setEditable(b);
        car2classTF.setEditable(b);
    }
    void updateCar1Info(CarBase c) {
        car1EnergyL.setText("Energy: "+c.energy);
    }
    void updateCar2Info(CarBase c) {
        car2EnergyL.setText("Energy: "+c.energy);
    }
}

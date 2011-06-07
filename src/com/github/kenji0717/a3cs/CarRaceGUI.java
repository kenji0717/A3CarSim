package com.github.kenji0717.a3cs;

import javax.swing.*;

public class CarRaceGUI extends JFrame {
    private static final long serialVersionUID = 1L;
    CarRaceImpl impl;
    CarRaceGUI(CarRaceImpl i,String args[]) {
        super("CarRace");
        impl = i;
        this.add(new JLabel("準備中"));

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
}

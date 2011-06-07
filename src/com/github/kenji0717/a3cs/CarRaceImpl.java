package com.github.kenji0717.a3cs;

import java.util.ArrayList;

class CarRaceImpl implements CarSim {
    CarRaceGUI gui;
    CarRaceImpl(String args[]) {
        gui = new CarRaceGUI(this,args);
        gui.pack();
        gui.setVisible(true);
    }

    @Override
    public ArrayList<CarBase> getAllCar() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void addActiveObject(ActiveObject o) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delActiveObject(ActiveObject o) {
        // TODO Auto-generated method stub
        
    }
}

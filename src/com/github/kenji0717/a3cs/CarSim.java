package com.github.kenji0717.a3cs;

import java.util.*;

interface CarSim {
    public ArrayList<CarBase> getAllCar();
    public void addActiveObject(ActiveObject o);
    public void delActiveObject(ActiveObject o);
}

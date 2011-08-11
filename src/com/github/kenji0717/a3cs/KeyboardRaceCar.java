package com.github.kenji0717.a3cs;

import java.awt.event.*;

class KeyboardRaceCar extends RaceCarBase implements KeyListener {
    boolean keyUp;
    boolean keyDown;
    boolean keyRight;
    boolean keyLeft;
    boolean keySpace;
    boolean keyShift;
    boolean keyZ;
    boolean keyX;

    public void exec() {
        double engine = 0.0;
        double steering = 0.0;
        double breaking = 0.0;
        double drift = 0.0;

        if (keyUp) engine += 500.0;
        if (keyDown) engine -= 500.0;

        if (keyRight) steering -= 0.1;
        if (keyLeft)  steering += 0.1;

        if (keySpace) breaking = 10.0;

        if (keyShift) drift = 1.0;

        if (keyZ) steering=1.57;//test前輪を90度左に向ける
        if (keyX) steering=-1.57;//test前輪を90度右に向ける

        setForce(engine,steering,breaking,drift);

    }

    @Override
    public void keyPressed(KeyEvent ke) {
        switch(ke.getKeyCode()) {
        case KeyEvent.VK_UP:    keyUp=true;    break;
        case KeyEvent.VK_DOWN:  keyDown=true;  break;
        case KeyEvent.VK_RIGHT: keyRight=true; break;
        case KeyEvent.VK_LEFT:  keyLeft=true;  break;
        case KeyEvent.VK_SPACE: keySpace=true; break;
        case KeyEvent.VK_SHIFT: keyShift=true; break;
        case KeyEvent.VK_Z:     keyZ=true;     break;
        case KeyEvent.VK_X:     keyX=true;     break;
        }
    }

    @Override
    public void keyReleased(KeyEvent ke) {
        switch(ke.getKeyCode()) {
        case KeyEvent.VK_UP:    keyUp=false;    break;
        case KeyEvent.VK_DOWN:  keyDown=false;  break;
        case KeyEvent.VK_RIGHT: keyRight=false; break;
        case KeyEvent.VK_LEFT:  keyLeft=false;  break;
        case KeyEvent.VK_SPACE: keySpace=false; break;
        case KeyEvent.VK_SHIFT: keyShift=false; break;
        case KeyEvent.VK_Z:     keyZ=false;     break;
        case KeyEvent.VK_X:     keyX=false;     break;
        }
    }

    @Override
    public void keyTyped(KeyEvent arg0) {;}
}

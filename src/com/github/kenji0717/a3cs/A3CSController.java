package com.github.kenji0717.a3cs;

import java.awt.event.*;
import javax.vecmath.*;
import jp.sourceforge.acerola3d.a3.*;

/**
 * Acerola3DのSimpleControllerをコピーしてマウスの左と真ん中のボタンを
 * 替えただけ。
 */
class A3CSController extends A3Controller {
    //注視点
    Vector3d fixationPoint= null;
    double distance;
    Quat4d cameraQuat = null;
    int lastMouseX;
    int lastMouseY;

    /**
     * デフォルトのパラメータを使用してA3CSControllerを生成します。
     */
    public A3CSController() {
        distance=0.0;
    }
    /**
     * 固定点の距離のパラメータを指定してA3CSControllerを生成します。
     */
    public A3CSController(Object...params) {
        if (params.length==1)
            distance=((Double)params[0]);
        else
            distance=0.0;
    }
    /**
     * 初期化します。
     */
    public void init() {
        if (distance==0.0)
            distance = a3canvas.getCameraScale()*3.0;
        cameraQuat = a3canvas.getCameraQuat();
        Quat4d cameraQuatC = new Quat4d();
        cameraQuatC.conjugate(cameraQuat);
        Quat4d tmpQ = new Quat4d(cameraQuat);
        tmpQ.mul(new Quat4d(0.0,0.0,1.0,0.0));
        tmpQ.mul(cameraQuatC);
        tmpQ.scale(distance);
        fixationPoint = new Vector3d();
        Vector3d cameraLoc = a3canvas.getCameraLoc();
        fixationPoint.sub(cameraLoc,new Vector3d(tmpQ.x,tmpQ.y,tmpQ.z));
        setCamera();
    }
    /**
     * マウスが押された時のイベントをキャッチします。
     */
    public void mousePressed(A3Event ae) {
        MouseEvent me = ae.getMouseEvent();
        lastMouseX = me.getX();
        lastMouseY = me.getY();
    }

    /**
     * マウスがドラッグされた時のイベントをキャッチします。
     */
    public void mouseDragged(A3Event ae) {
        MouseEvent me = ae.getMouseEvent();
        int shiftX = me.getX() - lastMouseX;
        int shiftY = me.getY() - lastMouseY;
        if ((me.getModifiersEx()&MouseEvent.BUTTON2_DOWN_MASK)!=0) {
            //回転
            Quat4d q0 = new Quat4d(0.0,0.0,0.0,1.0);
            double t = - shiftX * 0.01;
            Quat4d q1 = new Quat4d(0.0,Math.sin(t),0.0,Math.cos(t));
            t = - shiftY * 0.01;
            Quat4d q2 = new Quat4d(Math.sin(t),0.0,0.0,Math.cos(t));
            q0.mul(q1);
            q0.mul(q2);
            cameraQuat.mul(q0);
        } else if ((me.getModifiersEx()&MouseEvent.BUTTON1_DOWN_MASK)!=0) {
            //平行移動(Shift+で奥行も)
            Vector3d v = a3canvas.virtualCSToPhysicalCS(fixationPoint);
            Tuple3d shift = a3canvas.canvasToPhysicalCS(lastMouseX,lastMouseY,-v.z); 
            shift.sub(a3canvas.canvasToPhysicalCS(me.getX(),me.getY(),-v.z));
            if ((me.getModifiers()&MouseEvent.SHIFT_MASK)!=0) {
                shift.set(shift.x,0.0,-shift.y);
            }
            Vector3d vx = a3canvas.getCameraUnitVecX();
            Vector3d vy = a3canvas.getCameraUnitVecY();
            Vector3d vz = a3canvas.getCameraUnitVecZ();
            vx.scale(shift.x);
            vy.scale(shift.y);
            vz.scale(shift.z);
            fixationPoint.add(vx);
            fixationPoint.add(vy);
            fixationPoint.add(vz);
        } else if ((me.getModifiersEx()&MouseEvent.BUTTON3_DOWN_MASK)!=0) {
            //拡大縮小
            distance = distance*(100+shiftY)/100.0;
        }
        setCamera();
        lastMouseX = me.getX();
        lastMouseY = me.getY();
    }

    void setCamera() {
        Quat4d tmpQ = new Quat4d(0.0,0.0,1.0,0.0);
        Quat4d cameraQuatC = new Quat4d();
        cameraQuatC.conjugate(cameraQuat);
        tmpQ.mul(cameraQuat,tmpQ);
        tmpQ.mul(cameraQuatC);
        tmpQ.scale(distance);
        Vector3d cameraLoc = new Vector3d();
        cameraLoc.set(tmpQ.x,tmpQ.y,tmpQ.z);
        cameraLoc.add(fixationPoint);
        a3canvas.setCameraLocImmediately(cameraLoc);
        a3canvas.setCameraQuatImmediately(cameraQuat);
        a3canvas.setCameraScaleImmediately(distance/3.0);
    }

    /**
     * マウスがリリースされた時のイベントをキャッチします。
     */
    public void mouseReleased(A3Event e) {
    }

    /**
     * マウスがクリックされた時のイベントをキャッチします。
     */
    public void mouseClicked(A3Event ae) {
    }

    /**
     * マウスがダブルクリックされた時のイベントをキャッチします。
     */
    public void mouseDoubleClicked(A3Event ae) {
        ;
    }

    /**
     * キーボードが押された時のイベントをキャッチします。
     */
    public void keyPressed(KeyEvent e) {
    }

    /**
     * キーボードが離された時のイベントをキャッチします。
     */
    public void keyReleased(KeyEvent e) {
    }

    /**
     * キーボードがタイプされた時のイベントをキャッチします。
     */
    public void keyTyped(KeyEvent e) {
    }
}

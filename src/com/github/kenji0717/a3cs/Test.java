package com.github.kenji0717.a3cs;

import jp.sourceforge.acerola3d.a3.*;

public class Test {
    public static void main(String args[]) throws Exception {
    	A3Window w = new A3Window(300,300);
    	Action3D a3 = new Action3D("x-res:///res/stk_tux.a3");
    	w.add(a3);
    }
}

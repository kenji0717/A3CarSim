package com.github.kenji0717.a3cs;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.tools.*;

public class SimpleIDE extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    String ideDir;
    JavaCompiler compiler;

    JLabel messageL;
    SimpleIDE(Frame owner) {
        super(owner);
        compiler = ToolProvider.getSystemJavaCompiler();

        messageL = new JLabel("message:");
        this.add(messageL);
    }
    void setEnable(boolean b) {
        
    }
    void popup(String ideDir) {
        if (compiler==null)
        this.ideDir = ideDir;

        if (compiler==null)
            messageL.setText("JDKをインストールしましょう。");
        else if (ideDir==null)
            messageL.setText("作業フォルダを指定してからIDEを起動して下さい。");
        else
            messageL.setText("準備OK。");

        this.setModal(true);
        this.pack();
        this.setVisible(true);
    }
    void compile() {
        int result = compiler.run(null,null,null,"src/HelloWorld.java");
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object s = ae.getSource();
    }
}

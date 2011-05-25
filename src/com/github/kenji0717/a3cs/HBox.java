package com.github.kenji0717.a3cs;

import javax.swing.*;
import java.awt.*;

class HBox extends JPanel {
    private static final long serialVersionUID = 1L;
    GridBagConstraints c;
    int index;
    public HBox() {
        this.setLayout(new GridBagLayout());
        c = new GridBagConstraints();
        c.fill = GridBagConstraints.BOTH;
        c.weighty = 1.0;
    }
    public void myAdd(Component component,double weight) {
        c.gridx = index;
        c.weightx = weight;
        this.add(component,c);
        index++;
    }
}

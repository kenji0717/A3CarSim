package com.github.kenji0717.a3cs;

import java.io.OutputStream;
import java.io.PrintStream;

import javax.swing.JTextArea;
import javax.swing.text.Caret;
import javax.swing.text.Document;
import javax.swing.text.Position;

class JTextAreaOutputStream extends OutputStream {
    JTextArea textArea;
    PrintStream systemOut;
    byte queue[];
    int position = 0;

    public JTextAreaOutputStream(JTextArea ta, PrintStream systemOut) {
        this.systemOut = systemOut;
        textArea = ta;
        queue = new byte[256];
    }

    public void write(int b) {
        systemOut.write(b);
        queue[position++] = (byte) b;
        if (position >= 256)
            flush();
    }

    public void flush() {
        systemOut.flush();
        String s = new String(queue, 0, position);
        position = 0;
        textArea.append(s);
        Document d = textArea.getDocument();
        Position p = d.getEndPosition();
        Caret c = textArea.getCaret();
        c.setDot(p.getOffset());
    }
}

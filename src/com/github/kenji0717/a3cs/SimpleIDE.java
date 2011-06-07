package com.github.kenji0717.a3cs;

import java.awt.*;
import java.awt.event.*;
import java.io.OutputStream;
import java.io.PrintStream;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.tools.*;

class SimpleIDE extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    String ideDir;
    JavaCompiler compiler;

    JLabel messageL;
    JButton openB;
    JButton saveB;
    JButton compileB;
    JTextArea editor;
    JTextArea outputTA;
    JTextAreaOutputStream jtaos;

    SimpleIDE(Frame owner) {
        super(owner);
        compiler = ToolProvider.getSystemJavaCompiler();

        VBox mainBox = new VBox();
        this.add(mainBox);
        messageL = new JLabel("message:");
        mainBox.myAdd(messageL,0);
        HBox buttonsBox = new HBox();
        mainBox.myAdd(buttonsBox,0);
        openB = new JButton("Open");
        openB.addActionListener(this);
        buttonsBox.myAdd(openB,0);
        saveB = new JButton("Save");
        saveB.addActionListener(this);
        buttonsBox.myAdd(saveB,0);
        compileB = new JButton("Compile");
        compileB.addActionListener(this);
        buttonsBox.myAdd(compileB,0);
        buttonsBox.myAdd(Box.createHorizontalGlue(),1);
        editor = new JTextArea(30,80);
        JScrollPane scroll = new JScrollPane(editor);
        scroll.setRowHeaderView(new LineNumberView(editor));
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_ALWAYS);
        editor.setBorder(BorderFactory.createEmptyBorder(0,2,0,0));
        mainBox.myAdd(scroll,1);
        outputTA = new JTextArea(15,80);
        mainBox.myAdd(new JScrollPane(outputTA),1);
        jtaos = new JTextAreaOutputStream(outputTA,System.out);
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
        if (s==openB) {
            System.out.println("Open");
        } else if (s==saveB) {
            System.out.println("Save");
        } else if (s==compileB) {
            System.out.println("Compile");
        }
    }
}
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

//http://terai.xrea.jp/Swing/LineNumber.html
class LineNumberView extends JComponent {
    private static final long serialVersionUID = 1L;
    private static final int MARGIN = 5;
    private final JTextArea text;
    private final FontMetrics fontMetrics;
    private final int topInset;
    private final int fontAscent;
    private final int fontHeight;

    public LineNumberView(JTextArea textArea) {
        text = textArea;
        Font font   = text.getFont();
        fontMetrics = getFontMetrics(font);
        fontHeight  = fontMetrics.getHeight();
        fontAscent  = fontMetrics.getAscent();
        topInset    = text.getInsets().top;
        text.getDocument().addDocumentListener(new DocumentListener() {
            @Override public void insertUpdate(DocumentEvent e) {
                repaint();
            }
            @Override public void removeUpdate(DocumentEvent e) {
                repaint();
            }
            @Override public void changedUpdate(DocumentEvent e) {}
        });
        text.addComponentListener(new ComponentAdapter() {
            @Override public void componentResized(ComponentEvent e) {
                revalidate();
                repaint();
            }
        });
        setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.GRAY));
        setOpaque(true);
        setBackground(Color.WHITE);
    }
    private int getComponentWidth() {
        Document doc  = text.getDocument();
        Element root  = doc.getDefaultRootElement();
        int lineCount = root.getElementIndex(doc.getLength());
        int maxDigits = Math.max(3, String.valueOf(lineCount).length());
        return maxDigits*fontMetrics.stringWidth("0")+MARGIN*2;
    }
    public int getLineAtPoint(int y) {
        Element root = text.getDocument().getDefaultRootElement();
        int pos = text.viewToModel(new Point(0, y));
        return root.getElementIndex(pos);
    }
    public Dimension getPreferredSize() {
        return new Dimension(getComponentWidth(), text.getHeight());
    }
    @Override public void paintComponent(Graphics g) {
        Rectangle clip = g.getClipBounds();
        g.setColor(getBackground());
        g.fillRect(clip.x, clip.y, clip.width, clip.height);
        g.setColor(getForeground());
        int base  = clip.y - topInset;
        int start = getLineAtPoint(base);
        int end   = getLineAtPoint(base+clip.height);
        int y = topInset-fontHeight+fontAscent+start*fontHeight;
        for(int i=start;i<=end;i++) {
            String text = String.valueOf(i+1);
            int x = getComponentWidth()-MARGIN-fontMetrics.stringWidth(text);
            y = y + fontHeight;
            g.drawString(text, x, y);
        }
    }
}

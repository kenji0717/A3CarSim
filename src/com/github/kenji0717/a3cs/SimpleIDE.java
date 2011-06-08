package com.github.kenji0717.a3cs;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipOutputStream;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.text.*;
import javax.tools.*;

class SimpleIDE extends JDialog implements ActionListener {
    private static final long serialVersionUID = 1L;
    String workDir;
    String filePath;
    JavaCompiler compiler;

    JButton openB;
    JButton saveB;
    JButton compileB;
    JButton makeJarB;
    JTextArea editor;
    JTextArea outputTA;
    JTextAreaOutputStream jtaos;

    SimpleIDE(Frame owner) {
        super(owner);
        compiler = ToolProvider.getSystemJavaCompiler();

        VBox mainBox = new VBox();
        this.add(mainBox);
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
        makeJarB = new JButton("MakeJAR");
        makeJarB.addActionListener(this);
        buttonsBox.myAdd(makeJarB,0);
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
        openB.setEnabled(b);
        saveB.setEnabled(b);
        compileB.setEnabled(b);
        makeJarB.setEnabled(b);
        editor.setEditable(b);
    }
    void popup(String workDir) {
        this.workDir = workDir;

        if (compiler==null) {
            editor.setText("JDKをインストールしましょう。");
            this.setEnable(false);
        } else if (workDir==null) {
            editor.setText("作業フォルダを指定してからIDEを起動して下さい。");
            this.setEnable(false);
        } else {
            editor.setText("");
            this.setEnable(true);
        }

        this.setModal(true);
        this.pack();
        this.setVisible(true);
    }
    @Override
    public void actionPerformed(ActionEvent ae) {
        Object s = ae.getSource();
        if (s==openB) {
            openFile();
        } else if (s==saveB) {
            saveFile();
        } else if (s==compileB) {
            compile();
        } else if (s==makeJarB) {
            makeJarFile();
        } else {
            System.out.println("gaha:????");
        }
    }
    void openFile() {
        JFileChooser chooser = new JFileChooser(workDir);
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "Java Source File", "java");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            File f = chooser.getSelectedFile();
            try {
                FileReader fr = new FileReader(f);
                BufferedReader br = new BufferedReader(fr);
                editor.setText("");
                String line = br.readLine();
                while (line!=null) {
                    editor.append(line+"\n");
                    line = br.readLine();
                }
                filePath = f.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    void saveFile() {
        try {
            FileWriter fw = new FileWriter(filePath);
            PrintWriter pw = new PrintWriter(fw);
            pw.print(editor.getText());
            pw.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    void compile() {
        String classPath = System.getProperty("java.class.path");
        int result = compiler.run(null,jtaos,jtaos,"-cp",classPath,"-d",workDir,filePath);
        if (result==0) {
            outputTA.append("コンパイル成功\n");
        }
    }
    void makeJarFile() {
        JFileChooser chooser = new JFileChooser();
        FileNameExtensionFilter filter = new FileNameExtensionFilter(
            "JAR File", "jar");
        chooser.setFileFilter(filter);
        int returnVal = chooser.showOpenDialog(this);
        if(returnVal == JFileChooser.APPROVE_OPTION) {
            try {
                File f = chooser.getSelectedFile();
                JarOutputStream jos = new JarOutputStream(new FileOutputStream(f));
                File ff = new File(workDir);
                for (File fff:ff.listFiles()) {
                    makeJarFileRec(jos,fff,"");
                }
                jos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            
        }
    }
    void makeJarFileRec(JarOutputStream jos,File f,String path) throws Exception {
        if (f.isDirectory()) {
            File files[] = f.listFiles();
            for (File ff:files) {
                makeJarFileRec(jos,ff,path+f.getName()+"/");
            }
        } else if (f.isFile()) {
            FileInputStream fis = new FileInputStream(f);
            byte[] buf = new byte[1024];
            JarEntry je = new JarEntry(path+f.getName());
            jos.putNextEntry(je);
            int len = 0;
            while ((len=fis.read(buf))!=-1) {
                jos.write(buf,0,len);
            }
            fis.close();
            jos.closeEntry();
        }
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

/*
    Pix
    Author: Rafael Rendon Pablo
    e-mail: rafaelrendonpablo@gmail.com
    Date:   Thu Mar 10 00:03:38 CST 2011
    Description:    A minimalistic paint program. The objective of this program
    is show the user how a picture is formed by a bunch of 
    individual pixels(rectangles in this program), using a big scale.
*/


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;

import java.io.*;
import java.util.*;

public class Pix extends JFrame {

    private JMenuBar menuBar;
    private JMenu fileMenu, helpMenu;
    private JMenuItem openItem, saveItem, quitItem, aboutItem;
    private JToolBar toolBar;
    private JButton openButton, saveButton, quitButton, eraserButton;
    private JSpinner rowSpinner, colSpinner;
    private ImageIcon pencilIcon, eraserIcon;
    private Grid grid;

    public Pix()
    {
        super("Pix");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(500, 300);        

        initializeComponents();

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void initializeComponents()
    {
        pencilIcon = new ImageIcon("picture/pencil.png");
        eraserIcon = new ImageIcon("picture/draw-eraser.png");
        menuBar = new JMenuBar();
        fileMenu = new JMenu("File");

        openItem = new JMenuItem("open", new ImageIcon("picture/document-open.png"));
        saveItem = new JMenuItem("save", new ImageIcon("picture/document-save.png"));
        quitItem = new JMenuItem("quit", new ImageIcon("picture/application-exit.png"));

        fileMenu.add(openItem);
        fileMenu.add(saveItem);
        fileMenu.addSeparator();
        fileMenu.add(quitItem);

        ComponentBehavior behavior = new ComponentBehavior();

        openItem.addActionListener(behavior);
        saveItem.addActionListener(behavior);
        quitItem.addActionListener(behavior);

        helpMenu = new JMenu("Help");
        aboutItem = new JMenuItem("About", new ImageIcon("picture/help-about.png"));
        helpMenu.add(aboutItem);
        aboutItem.addActionListener(behavior);

        menuBar.add(fileMenu);
        menuBar.add(helpMenu);

        setJMenuBar(menuBar);

        openButton = new JButton("open", new ImageIcon("picture/document-open.png"));
        saveButton = new JButton("save", new ImageIcon("picture/document-save.png"));
        quitButton = new JButton("quit", new ImageIcon("picture/application-exit.png"));
        eraserButton = new JButton("eraser", eraserIcon);
        rowSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 50, 1));
        colSpinner = new JSpinner(new SpinnerNumberModel(10, 5, 50, 1));

        rowSpinner.setValue(10);
        colSpinner.setValue(10);

        toolBar = new JToolBar("main tool bar");

        toolBar.add(openButton);
        toolBar.add(saveButton);
        toolBar.add(eraserButton);
        toolBar.add(quitButton);
        toolBar.add(new JLabel("rows: "));
        toolBar.add(rowSpinner);
        toolBar.add(new JLabel("cols: "));
        toolBar.add(colSpinner);

        openButton.addActionListener(behavior);
        saveButton.addActionListener(behavior);
        quitButton.addActionListener(behavior);
        eraserButton.addActionListener(behavior);

        rowSpinner.addChangeListener(behavior);
        colSpinner.addChangeListener(behavior);

        add(toolBar, BorderLayout.PAGE_START);

        grid = new Grid();
        add(grid, BorderLayout.CENTER);
    }


    public static void main(String[] args)
    {
        Pix grid = new Pix();
    }


    class ComponentBehavior implements ActionListener, ChangeListener {

        public void actionPerformed(ActionEvent event)
        {
            BufferedReader buffer = null;
            if (event.getSource() == openButton || 
                    event.getSource() == openItem) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showOpenDialog(null);
                    if( result != JFileChooser.CANCEL_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        String fileName = file.getAbsolutePath();
                        buffer = new BufferedReader(new FileReader(fileName));
                        String picture = "";
                        String line = "";
                        int rows = 0, cols = 0;
                        while ((line = buffer.readLine()) != null) {
                            picture += line;
                            picture += "\n";
                            rows++;
                            cols = line.length();
                        }
                        rowSpinner.setValue((Integer)rows);
                        colSpinner.setValue((Integer)cols);
                        grid.setPicture(picture);
                    }
                } catch(Exception e) {
                    System.out.println("ERROR -- " + e.toString());
                }
            } else if (event.getSource() == saveButton ||
                    event.getSource() == saveItem) {
                try {
                    JFileChooser fileChooser = new JFileChooser();
                    int result = fileChooser.showSaveDialog(null);
                    if( result != JFileChooser.CANCEL_OPTION) {
                        File file = fileChooser.getSelectedFile();
                        String fileName = file.getAbsolutePath();
                        FileWriter fw = new FileWriter(fileName);
                        PrintWriter pw = new PrintWriter(new BufferedWriter(fw));
                        String picture = grid.toString();
                        StringTokenizer strtk = new StringTokenizer(picture, "\n");
                        while (strtk.hasMoreTokens()) {
                            pw.print(strtk.nextToken());
                            pw.println();
                        }
                        pw.close();
                    }
                } catch(Exception e) {
                    System.out.println("ERROR -- " + e.toString());
                }                
            } else if (event.getSource() == quitButton ||
                    event.getSource() == quitItem) {
                dispose();
            } else if (event.getSource() == aboutItem) {
                About about = new About();
            } else if (event.getSource() == eraserButton) {
                System.out.println("Eraser!");
                if (grid.eraser()) {
                    grid.enableEraser(false);
                    eraserButton.setIcon(eraserIcon);
                }
                else {
                    grid.enableEraser(true);
                    eraserButton.setIcon(pencilIcon);
                }
            }
        }

        public void stateChanged(ChangeEvent event)
        {
            JSpinner tmp = (JSpinner)event.getSource();
            if (event.getSource() == rowSpinner)
                grid.setRows((Integer)tmp.getValue());
            else if (event.getSource() == colSpinner)
                grid.setCols((Integer)tmp.getValue());
        }        
    }
}



import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;

public class Grid extends JPanel {
    
    public final int MIN_DIMENSION = 10;
    public final int MAX_DIMENSION = 50;
    private Pixel grid[][];
    private int rows, cols;
    private double width, height;
    private boolean activeEraser; // if true the erase tool is active
    
    
    public Grid()
    {
        activeEraser = false;
        rows = cols = MIN_DIMENSION;
        grid = new Pixel[MAX_DIMENSION][MAX_DIMENSION];
        
         for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid[0].length; j++)
                grid[i][j] = new Pixel(0, 0);
        }
        
        addMouseListener(new MouseAdapter() {
                public void mousePressed(MouseEvent event)
                {
                    int x = event.getX();
                    int y = event.getY();
                    //System.out.println("P = (" + x + ", " + y + ")\n");
                    
                    for (int i = 0; i < cols; i++) {
                        for (int j = 0; j < rows; j++) {
                            if ((x >= grid[i][j].x() && x < grid[i][j].x() + width) &&
                                (y >= grid[i][j].y() && y < grid[i][j].y() + height)) {
                                if (activeEraser)
                                    grid[i][j].fill(false);
                                else
                                    grid[i][j].fill(true);
                                break;
                            }
                        }
                    }
                    repaint();
                }
            }
        );
        
        addMouseMotionListener(new MouseMotionListener() {
            public void mouseDragged(MouseEvent event) 
                {
                    int x = event.getX();
                    int y = event.getY();
                    
                    for (int i = 0; i < cols; i++) {
                        for (int j = 0; j < rows; j++) {
                            if ((x >= grid[i][j].x() && x <= grid[i][j].x() + width) &&
                                (y >= grid[i][j].y() && y <= grid[i][j].y() + height)) {
                                if (activeEraser)
                                    grid[i][j].fill(false);
                                else
                                    grid[i][j].fill(true);
                                break;
                            }
                        }
                    }
                    repaint();
                }
                public void mouseMoved(MouseEvent event) { }
        });
    }
    
    public void paintComponent(Graphics g)
    {
        super.paintComponent(g);
        
        width = (double)getWidth()/(double)cols;
        height = (double)getHeight()/(double)rows;
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                grid[i][j].setX((int)Math.round(i * width));
                grid[i][j].setY((int)Math.round(j * height));
            }
        }
        
        // Draw grid
        g.setColor(Color.LIGHT_GRAY);
        for (int i = 0; i < rows; i++) {
            int y = (int)Math.round(i * height);
            g.drawLine(0, y, getWidth() - 1, y);
        }
        
        for (int i = 0; i < cols; i++) {
            int x = (int)Math.round(i * width);
            g.drawLine(x, 0, x, getHeight() - 1);
        }
        
        // Fill pixels which are active
        g.setColor(Color.BLACK);
        for (int i = 0; i < cols; i++) {
            for (int j = 0; j < rows; j++) {
                if (grid[i][j].isFilled()) {
                    int w = (int)Math.round(width);
                    int h = (int)Math.round(height);
                    g.fillRect(grid[i][j].x(), grid[i][j].y(), w, h);
                }
            }
        }
        
        // Draw contour
        g.setColor(Color.RED);
        g.drawLine(0, 0, getWidth() - 1, 0);
        g.drawLine(0, getHeight() - 1, getWidth() - 1, getHeight() - 1);
        g.drawLine(0, 0, 0, getHeight() - 1);
        g.drawLine(getWidth() - 1, 0, getWidth() - 1, getHeight() - 1);
        
    }// End paintComponent()
    
    public String toString()
    {
        String picture = "";
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++)
                picture += (grid[j][i].isFilled()) ? "*" : ".";
            picture += "\n";
        }
        return picture;
    }
    
    public void setPicture(String picture)
    {
        StringTokenizer strtk = new StringTokenizer(picture, "\n");
        rows = strtk.countTokens();
        String line = strtk.nextToken();
        cols = line.length();
        int i = 0, j;
        
        if (rows >= MAX_DIMENSION || cols >= MAX_DIMENSION)
            return;
        
        while (true){
            for (j = 0; j < line.length(); j++) {
                if (line.charAt(j) == '*')
                    grid[j][i].fill(true);
                else
                    grid[j][i].fill(false);
            }
            i++;
            if (strtk.hasMoreTokens())
                line = strtk.nextToken();
            else
                break;
        }
        repaint();
    }
    
    public void setRows(int value) 
    { 
        rows = value;
        repaint();
    }
    public void setCols(int value)
    { 
        cols = value;
        repaint();
    }
    
    public void enableEraser(boolean value) { activeEraser = value; }
    public boolean eraser() { return activeEraser; }
}

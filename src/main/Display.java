package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

public class Display extends JFrame {   
    private static final int defaultWidth = 64;
    private static final int defaultHeight = 64;
    
    private static final int windowWidth = 640;
    private static final int windowHeight = 480;
    
    private final Screen frame;
    
    public Display(int w, int h) {
        super();
        
        Maze maze = Maze.create(w,h);
        
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(true);
        setVisible(true);
        setTitle("Maze");
        
        frame = new Screen(w*2+1,h*2+1);
        frame.setMaze(maze);
        
        Dimension size = new Dimension(Math.min(w*16,windowWidth),Math.min(h*16,windowHeight));
        
        if (w*16>windowWidth || h*16>windowHeight) {
            setPreferredSize(size);
            JScrollPane scrollPane = new JScrollPane(frame);
            scrollPane.getVerticalScrollBar().setUnitIncrement(4);
            scrollPane.getHorizontalScrollBar().setUnitIncrement(4);
            add(scrollPane);
        } else {
            setSize(size);
            add(frame);
        }
        pack();
    }
    
    public static void main(String[] args) {
        int width = args.length==2 ? Integer.valueOf(args[0]) : defaultWidth;
        int height = args.length==2 ? Integer.valueOf(args[1]) : defaultHeight;
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | InstantiationException
                | IllegalAccessException| UnsupportedLookAndFeelException e) {
            System.out.println("Error loading look and feel: " + e);
        }
        
        Display display = new Display(width,height);
        display.repaint();
    }
        
    @Override
    public void repaint() {
        super.repaint();
    }
}

class Screen extends JPanel {
    
    private Maze maze;
    
    public Screen(int w, int h) {
        setPreferredSize(new Dimension(w*16,h*16));
    }
    
    @Override
    public void update(Graphics g) {
        paint(g);
    }
    
    @Override
    public void paintComponent(Graphics g) {
        if (maze == null)
            return;
        
        Cell[][] cells = maze.getCells();
        
        for (int i=0;i<cells.length;i++) {
            for (int j=0;j<cells[0].length;j++) {
                g.setColor(cells[i][j].isWall ? Color.black : Color.white);
                g.fillRect(i*16,j*16,16,16);
            }
        }
    }
    
    public void setMaze(Maze m) { maze = m; }
}

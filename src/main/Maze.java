package main;

public class Maze {    
    private Cell[][] maze;
    
    public static void main(String[] args) {
        Maze maze = Maze.genMaze(10, 10);
        System.out.println(maze);
    }
    
    public static Maze genMaze(int width, int height) {
        Maze maze = new Maze(width, height);
        maze.generate();
        return maze;
    }
    
    private Maze(int width, int height) {
        maze = new Cell[width*2+1][height*2+1];
        setupCells();
    }
    
    private void setupCells() {
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new Cell();
                if(i % 2 == 1 && j % 2 == 1)
                    maze[i][j].isWall = false;
            }
        }             
    }
    
    public void generate() {
        boolean[][] past = new boolean[getWidth()][getHeight()];
        
        Cell atemp;
        Cell btemp;
        while(!isComplete(past)) {
            atemp = maze[(int)(Math.random()*getWidth()+1)][(int)(Math.random()*getHeight()+1)];
        }
    }
    
    public static boolean isComplete(boolean[][] array) {
        for(int i = 0; i < array.length; i++)
            for(int j = 0; j < array[0].length; j++)
                if(!array[i][j])
                    return false;
        return true;
    }
    
    public int getWidth() { return (maze.length-1)/2; }
    public int getHeight() { return (maze[0].length-1)/2; }
    
    @Override
    public String toString() {
        StringBuilder s = new StringBuilder();
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                if(maze[i][j].isWall)
                    s.append('#');
                else
                    s.append('.');
            }
            s.append("\n");
        }
        return s.toString();
    }
}

class Cell { 
    public Cell parent;
    public boolean isWall = true;
    
    public void merge(Cell other) {
        parent = other;
    }
    
    public Cell getParent() {
        Cell p = parent == null ? this : parent.getParent();
        if(parent != null && parent != p)
            parent = p;
        return p;
    }
}

class Point {
    public int x;
    public int y;
    
    public Point(int px, int py) {
        x = px;
        y = py;
    }
}

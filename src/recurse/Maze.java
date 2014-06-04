package recurse;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Maze {
    private static int count = 0;
    private Cell[][] maze;
    
    public static void main(String[] args) {
        Maze maze = new Maze(10, 10);
        maze.recursiveGen(0, 0, new boolean[maze.getWidth()][(maze.getHeight())]);
        System.out.println(maze);
    }
    
    public Maze(int width, int height) {
        maze = new Cell[width*2+1][height*2+1];
        setupCells();
    }
    
    private void setupCells() {
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new Cell(count);
                count++;
                if(i % 2 == 1 && j % 2 == 1)
                    maze[i][j].isWall = false;
            }
        }             
    }
    
    public void recursiveGen(int x, int y, boolean[][] past) {
        past[x][y] = true;
        if(isComplete(past))
            return;
        HashMap<Cell, Point> neighbors = new HashMap<>();
        for(int i = -1; i < 2; i++)
            for(int j = -1; j < 2; j++)
                if(Math.abs(i) != Math.abs(j))
                    if((x+i)*2+1 > 0 && (x+i)*2+1 < maze.length && (y+j)*2+1 > 0 && (y+j)*2+1 < maze[0].length)
                        neighbors.put(maze[(x+i)*2+1][(y+j)*2+1], new Point(i, j));
        
        int rand;
        Cell neighbor;
        List<Cell> neighborCells = new ArrayList<>(neighbors.keySet());
        while(true) {
            rand = (int)(Math.random() * neighborCells.size());
            if(neighborCells.isEmpty())
                break;
            neighbor = neighborCells.get(rand);
            if(maze[(x+neighbors.get(neighbor).x)*2+1][(y+neighbors.get(neighbor).y)*2+1].set != maze[x*2+1][y*2+1].set) {
                maze[x*2+1+neighbors.get(neighbor).x][y*2+1+neighbors.get(neighbor).y].isWall = false;
                maze[(x+neighbors.get(neighbor).x)*2+1][(y+neighbors.get(neighbor).y)*2+1].set = maze[x*2+1][y*2+1].set;
                recursiveGen(x+neighbors.get(neighbor).x, y+neighbors.get(neighbor).y, past);
            }
            neighborCells.remove(neighbor);
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
    public int set;
    public boolean isWall = true;
    
    public Cell(int s) {
        set = s;
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

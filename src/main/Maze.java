package main;

import java.util.ArrayList;
import java.util.List;

public class Maze {    
    private Cell[][] maze;
    private List<Cell> walls;
    
    public static void main(String[] args) {
        Maze maze = Maze.genMaze(2, 2);
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
        walls = new ArrayList<>();
        for(int i = 0; i < maze.length; i++) {
            for(int j = 0; j < maze[0].length; j++) {
                maze[i][j] = new Cell(i, j);
                if(i % 2 == 1)
                    maze[i][j].isVertical = false;
                if(i % 2 == 1 && j % 2 == 1) {
                    maze[i][j].isWall = false;
                    continue;
                }
                if(i % 2 != j % 2)
                    if(i != 0 && i != maze.length-1 && j != 0 && j != maze[i].length-1)
                        walls.add(maze[i][j]);
            }
        }             
    }
    
    public void generate() {
        int index;
        Cell wall;
        Cell atemp;
        Cell btemp;
        while(!walls.isEmpty()) {
            index = (int)(Math.random() * walls.size());
            wall = walls.get(index);
            atemp = wall.isVertical ? maze[wall.x][wall.y+1] : maze[wall.x+1][wall.y];
            btemp = wall.isVertical ? maze[wall.x][wall.y-1] : maze[wall.x-1][wall.y];
            System.out.println(wall.x + " " + wall.y);
            System.out.println(atemp.x + " " + atemp.y);
            System.out.println(btemp.x + " " + btemp.y);
            System.out.println(atemp.getRoot() + " " + btemp.getRoot());
            if(atemp.getRoot() != btemp.getRoot()) {
                if(atemp.merge(btemp))
                    wall.isWall = false;
            }
            System.out.println();
            walls.remove(index);
        }
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
    public boolean isVertical = true;
    public boolean isWall = true;
    
    public int x, y;
    
    public Cell(int i, int j) {
        x = i;
        y = j;
    }
    
    public boolean merge(Cell other) {
        getRoot();
        if(parent != other.getRoot()) {
            parent = other.getRoot();
            return true;
        }
        return false;
    }
    
    public Cell getRoot() {
        Cell p = parent == null ? this : parent.getRoot();
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

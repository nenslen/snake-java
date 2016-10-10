/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakePackage;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLayeredPane;

/**
 *
 * @author Nic
 */
public class Player extends Tile {
    
    
    private int score = 0;
    private int prevRow;
    private int prevCol;
    private String lastDirection = "up"; // The last direction that the player has travelled
    private String nextDirection = "up"; // The next desired direction the player wants to travel
    private JLayeredPane parentPanel;
    
    private List<Tail> segments = new ArrayList<Tail>();// = new ArrayList<Tail>(); // The player's tail segments
    
    
    /**
     * Constructor
     */
    public Player(JLayeredPane pnl) {
        parentPanel = pnl;
    }
    
    
    
    
    
    public String getLastDirection() {
        return lastDirection;
    }
    
    
    public void setLastDirection(String last) {
        lastDirection = last;
    }
    
    
    public void setNextDirection(String next) {
        nextDirection = next;
    }
    
    
    public String getNextDirection() {
        return nextDirection;
    }
    
    
    /**
     * Returns how many tail segments the player has
     */
    public int segmentCount() {
        return segments.size();
    }
    
    
    /**
     * Moves the player and all of their tail segments in the next direction of travel if possible
     */
    public void move(boolean wrap) {
        
        // Makes sure the player can't move the opposite direction they were just moving
        switch(nextDirection) {
            case "up":
                if(lastDirection == "down") { nextDirection = "down"; }
                break;
            case "down":
                if(lastDirection == "up") { nextDirection = "up"; }
                break;
            case "left":
                if(lastDirection == "right") { nextDirection = "right"; }
                break;
            case "right":
                if(lastDirection == "left") { nextDirection = "left"; }
                break;
        }
        
        
        // Records the last position the player was
        prevRow = row;
        prevCol = col;
        lastDirection = nextDirection;
        
        
        // Moves the player
        switch(nextDirection) {
            case "up":
                setPos(row - 1, col);
                break;
            case "down":
                setPos(row + 1, col);
                break;
            case "left":
                setPos(row, col - 1);
                break;
            case "right":
                setPos(row, col + 1);
                break;
        }
        
        
        // Wrap
        if(wrap) {
            if(row < 0) { setPos(gridSize - 1, col); }
            if(row >= gridSize) { setPos(0, col); }
            if(col < 0) { setPos(row, gridSize - 1); }
            if(col >= gridSize) { setPos(row, 0); }
        }
        
        
        // Moves the segments
        if(segments.size() > 0) { 
            
            // The tail at the very back
            Tail tempTail = segments.get(segments.size() - 1);
            segments.remove(segments.size() - 1);
            segments.add(0, tempTail);
            segments.get(0).setPos(prevRow, prevCol);
        }
    }
    
    
    /**
     * Adds a tail segment to the back of the player's tail
     */
    public void addTail() {
        
        // Finds where tail should be placed
        int r;
        int c;
        if(segments.size() > 0) {
            r = segments.get(segments.size() - 1).getRow();
            c = segments.get(segments.size() - 1).getCol();
        }
        else {
            r = prevRow;
            c = prevCol;
        }
        
        
        // Initializes new tail
        Tail newTail = new Tail(score++, prevRow, prevCol);
        newTail.setSize(gridSize, parentSize);
        newTail.setPos(r, c);
        newTail.setColor(Color.green);
        
        
        // Puts new tail piece closest to the head
        segments.add(newTail);
        
        // Adds tail piece to the panel
        parentPanel.add(newTail);
        
        
        /*
        // Initializes new tail
        Tail newTail = new Tail(score++, prevRow, prevCol);
        newTail.setSize(gridSize, parentSize);
        newTail.setPos(prevRow, prevCol);
        newTail.setColor(Color.green);
        
        
        // Puts new tail piece closest to the head
        segments.add(0, newTail);
        
        // Adds tail piece to the panel
        parentPanel.add(newTail);
        */
    }
    
    
    /**
     * Removes the last tail segment from the back of the player's tail
     * Removes the last tail segment from the parent panel
     */
    public void removeTail() {
        if(segments.size() > 0) {
            parentPanel.remove(segments.get(segments.size() - 1));
            segments.remove(segments.size() - 1);
        }
        else {
            System.out.println("No more segments to remove.");
        }
    }
    
    
    /**
     * Removes all tail segments
     * Removes all tail segments from the parent panel
     */
    public void removeAllTails() {
        
        
        if(segments.size() > 0) {
            for(int i = segments.size() - 1; i >= 0 ; i--) {
                parentPanel.remove(segments.get(i));
                segments.remove(i);
            }
        }
        else {
            System.out.println("No more segments to remove.");
        }
    }
    
    
    /**
     * Checks if player has collided with any of its tail segments
     */
    public boolean collision(int gridSize, boolean wrap) {
        
        // Checks walls
        if(row < 0) { return true; }
        if(row >= gridSize) { return true; }
        if(col < 0) { return true; }
        if(col >= gridSize) { return true; }
            
        
        // Checks segments
        if(segments.size() > 0) {
            for (Tail nextTail : segments) {
                if(row == nextTail.getRow() && col == nextTail.getCol()) {
                    return true;
                }
            }
        }
        
        return false;
    }
    
    
    /**
     * Checks if a location is valid for a gem/health piece
     * Returns true if the given location will not collide with the player or any of their tail segments
     */
    public boolean isValidLocation(int r, int c) {
        
        // Player
        if(row == r && col == c) {
            return false;
        }
        
        
        // Segments
        if(segments.size() > 0) {
            for (Tail nextTail : segments) {
                if(nextTail.getRow() == r && nextTail.getCol() == c) {
                    return false;
                }
            }
        }
        
        return true;
    }
}


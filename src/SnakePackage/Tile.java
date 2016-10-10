/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakePackage;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;

/**
 *
 * @author Nic
 */
public class Tile extends javax.swing.JPanel {
    
    protected int row;
    protected int col;
    protected int gridSize;
    protected int parentSize;
    
    public Tile() {
        
    }
    
    
    /**
     * Sets the row, col, and resizes the control appropriately
     * setSize() MUST be called before setPos
     */
    public void setPos(int r, int c) {
        int tileSize = parentSize / gridSize;
        row = r;
        col = c;
        //this.setb
        setBounds(col * tileSize, row * tileSize, tileSize, tileSize);
    }
    
    
    /**
     * Sets the size of the tile using the gridSize and the parent panel's size 
     */
    public void setSize(int _gridSize, int _parentSize) {
        gridSize = _gridSize;
        parentSize = _parentSize;
    }
    
    
    public int getRow() {
        return row;
    }
    
    
    public int getCol() {
        return col;
    }
    
    
    public void setColor(Color clr) {
        setBackground(clr);
    }
    
}

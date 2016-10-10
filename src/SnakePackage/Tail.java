/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package SnakePackage;

/**
 *
 * @author s0474955
 */
public class Tail extends Tile{
    
    private int ID;
    
    
    public Tail(int id, int r, int c) {
        ID = id;
        row = r;
        col = c;
    }
    
    
    public int getID() {
        return ID;
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basiseffekt;

import javafx.scene.shape.Line;

/**
 *
 * @author Jasmin
 */
public class Bahn extends Line
{
    public double x1;
    public double x2;
    public double y1;
    public double y2;
    public double steigung;
    public double deltaY;
    public double deltaX;
    
    public void draw(int x1, int y1, int x2, int y2)
    {
        super.setStartX(x1);
        super.setStartY(y1);
        super.setEndX(x2);
        super.setEndY(y2);
        
       deltaY = y2-y1;
       deltaX = x2- x1;
        
        steigung = deltaY / deltaX;
        System.out.println("Steigung:  " + steigung);
        System.out.println(deltaY + "  " + deltaX);
    }
    
    public double getDeltaY()
    {
        return deltaY;
    }
    public double getDeltaX()
    {
        return deltaX;
    }
    
    public double getX1()
    {
        return x1;
    }
    
    public double getX2()
    {
        return x2;
    }
    
    public double getY1()
    {
        return y1;
    }
    
    public double getY2()
    {
        return y2;
    }
}

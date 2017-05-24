/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcode;

import java.util.ArrayList;
import javafx.scene.shape.Line;

/**
 *
 * @author Jasmin
 */
public class Bahn extends Line
{
    private static ArrayList<Bahn> bahnen = new ArrayList<Bahn>();
    
    public double x1;
    public double x2;
    public double y1;
    public double y2;
    public double steigung;
    public double winkel;
    public double deltaY;
    public double deltaX;
    public double b;
    //Für Kollisionserechnung
    public double n_vektor_x;
    public double n_vektor_y;
    public double d;
    
    public Bahn(int px1, int py1, int px2, int py2){
        x1 = px1;
        x2 = px2;
        y1 = py1;
        y2 = py2;
        
        super.setStartX(x1);
        super.setStartY(y1);
        super.setEndX(x2);
        super.setEndY(y2);
        
        deltaY = y2-y1;
        deltaX = x2- x1;
        
        if (deltaX != 0){
            steigung = deltaY / deltaX;
            winkel = Math.tan(steigung);
        }else{
            winkel = 90;
        }
            
        
        b = y2 - steigung * x2;
        
        // Normalenvektor für Kollisionsberechnung 
        double n_x = -deltaY;
        double n_y = deltaX;
        double betrag_n = Math.sqrt(Math.pow(deltaY, 2)+Math.pow(deltaX, 2));
        n_vektor_x = n_x/betrag_n;
        n_vektor_y = n_y/betrag_n;
        if (n_vektor_y > 0){
            n_vektor_x = -n_vektor_x;
            n_vektor_y = -n_vektor_y;
        }
        d = x1*n_vektor_x + y1*n_vektor_y;
        bahnen.add(this);
        
    }
    
    
    public double getFunktionNachX(double x)
    {
        return steigung * x + b;
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
    
    public static ArrayList<Bahn> getBahnen(){
        return bahnen;
    }
}

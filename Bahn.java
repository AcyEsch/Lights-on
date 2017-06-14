/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcode;

import java.util.ArrayList;
import javafx.geometry.Point2D;
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
    public double winkelA;
    public double reibung = 100;     //Realer Wert?
    public double deltaY;
    public double deltaX;
    public double b;
    
     //Für Kollisionserechnung
    public double n_vektor_x;
    public double n_vektor_y;
    public double d;
    public double oldDistanz;
    public double distanz;
    
    //new
    private Point2D n0;
    
    public Bahn(int px1, int py1, int px2, int py2)
    {
        x1 = px1;
        x2 = px2;
        y1 = py1;
        y2 = py2;
        
        super.setStartX(x1);
        super.setStartY(y1);
        super.setEndX(x2);
        super.setEndY(y2);
        
        deltaY = y2 - y1;
        deltaX = x2 - x1;
        
        steigung = deltaY / deltaX;
    
        winkelA = Math.toDegrees(Math.acos(deltaX / Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2))));
        
        System.out.println(winkelA);
        winkel = Math.atan(steigung);           //Rechnet gerade nach Rad muss aber nach Deg
        winkel = Math.toDegrees(winkel);      //Radiant in Degree umrechnen
        
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
        n0 = new Point2D(n_vektor_x,n_vektor_y);
        bahnen.add(this);
    }
    
    public Point2D getN0(){
        return n0;
    }
    
    public double getSteigung()
    {
        return steigung;
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
    
    public double getGroesseresX()
    {
        if(x1 > x2)
        {
            return x1;
        }
        else
        {
            return x2;
        }            
    }
    public double getGroesseresY()
    {
        if(y1 > y2)
        {
            return y1;
        }
        else
        {
            return y2;
        }            
    }
    
    public double getKleineresX()
    {
        if(x1 < x2)
        {
            return x1;
        }
        else
        {
            return x2;
        }
    }
    
    public double getKleineresY()
    {
        if(y1 < y2)
        {
            return y1;
        }
        else
        {
            return y2;
        }
    }
    
    public double getReibung()
    {
        return reibung;
    }
    
    public double getWinkel()
    {
        return winkel;
    }
    
    
    public double getWinkelA()
    {
        return winkelA;
    }
    public static ArrayList<Bahn> getBahnen()
    {
        return bahnen;
    }
    public double getStrecke()
    {
        return Math.sqrt(deltaX * deltaX + deltaY * deltaY);
    }
    
    public double getOldDistanz()
    {
        return oldDistanz;
    }
    
    public void setOldDistanz(double old)
    {
        oldDistanz = old;
    }
    
    public double getDistanz()
    {
        return distanz;
    }
    
    public void setDistanz(double newD)
    {
        distanz = newD;
    }    
    
   
}
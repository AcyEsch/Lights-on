package logic;

import java.util.ArrayList;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class Bahn extends Line
{
    private static ArrayList<Bahn> bahnen = new ArrayList<Bahn>();
//    private static ObservableList<Bahn> watchedBahnen = FXCollections.observableArrayList();
    
    private double x1;
    private double x2;
    private double y1;
    private double y2;
    private double steigung;
    private double winkel;
    private final double reibung = 100;     //Realer Wert?
    private double deltaY;
    private double deltaX;
    private double b;
    private double rot = 0;
    
    private boolean canBeDraged;
//    private boolean isSelected = false;
    private BooleanProperty selectedBahnProp = new SimpleBooleanProperty(false);
    
     //Für Kollisionserechnung
    private double n_vektor_x;
    private double n_vektor_y;
    private double d;
    private double oldDistanz;
    private double distanz;
    
    public Bahn()
    {
    
    }
    
    public Bahn(int px1, int py1, int px2, int py2, boolean draged)
    {
        x1 = px1;
        x2 = px2;
        y1 = py1;
        y2 = py2;
        
        super.setStartX(x1);
        super.setStartY(y1);
        super.setEndX(x2);
        super.setEndY(y2);
        
        this.canBeDraged = draged;
       
       if(canBeDraged)
       {
           this.setStroke(Color.AQUA);
       }
       else
       {
           this.setStroke(Color.CORNFLOWERBLUE);
       }
        
        werteBerechnen();
        
        bahnen.add(this);
//        watchedBahnen.add(this);
    }
    
    public void werteBerechnen()
    {
        
        //Änderung für DragAndDrop Bug
      x1 = this.getStartX();
      x2 = this.getEndX();
      y1 = this.getStartY();
      y2 = this.getEndY();
      //~~~~~~~~~~~~~        
        // deltaY = this.getGroeseresY() - this.getKleineresY();     
       // deltaX = this.getGroeseresY() - this.getKleineresX();
       
        deltaY = y2 - y1;
        deltaX = x2 - x1;
        
        steigung = deltaY / deltaX;
        
        
        if(deltaX != 0)
        {        
            winkel = Math.atan(steigung);           //Rechnet gerade nach Rad muss aber nach Deg
            winkel = winkel * (180 / Math.PI);      //Radiant in Degree umrechnen
            
            if(winkel < 0)
            {
                winkel = 360 + winkel;
            }
            //~~~~~NEU~~~~~22.06.2017~~~~~~
            if(winkel >= 180)
            {
                winkel = winkel - 180;          //Winkel hat werte von 0 - 180;
            }
            //~~~~END~~~~~~
        }
        else
        {
            winkel = 90;                            //Oder 180
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
    }    
    
    public double getFunktionNachX(double x)
    {
        return steigung * x + b;
    }
    
    public double getSteigung()
    {
        return steigung;
    }
    
    public boolean getCanBeDraged()
    {
        return canBeDraged;
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
    
    public double getGroeseresX()
    {
        if(x2 > x1)
        {
            return x2;
        }
        else
        {
            return x1;
        }            
    }
    
    public double getKleineresX()
    {
        if(x2 < x1)
        {
            return x2;
        }
        else
        {
            return x1;
        }
    }
    
     public double getGroeseresY()
    {
        if(y2 > y1)
        {
            return y2;
        
        }else
        {
            return y1;
        }            
    }
    
    public double getKleineresY()
    {
        if(y2 < y1)
        {
            return y2;
        }
        else
        {
            return y1;
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
    public static ArrayList<Bahn> getBahnen()
    {
        return bahnen;
    }
    
    public double getStrecke()
    {
        return Math.sqrt((deltaX * deltaX) + (deltaY * deltaY));
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
    
    public double getNVektorX()
    {
        return n_vektor_x;
    }
    
    public double getNVektorY()
    {
        return n_vektor_y;
    }
    
    public double getD()
    {
        return d;
    }
    
    public double getRot()
    {
        return rot;
    }
    public void setRot(double r)
    {
        rot = r;
    }
    
    public void setIsSelected(boolean b){
        selectedBahnProp.set(b);
    }
    
    public boolean getIsSelected(){
       return selectedBahnProp.get();
    }
    
     public BooleanProperty getSelectedBahnProp() {
         return selectedBahnProp;
     }
    
    public static Bahn getSelectedBahn(){
        for (int i = 0; i < bahnen.size(); i++ ){
            if (bahnen.get(i).getIsSelected()){
                return bahnen.get(i);
            }
            
        }       return null;
    }
}

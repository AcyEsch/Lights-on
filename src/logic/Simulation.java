package logic;

import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;


    public class Simulation 
    {  
        //public int formelTyp = 1;              
    
        //public double masse = 25;      //Realistischer Wert?
        public double g = 9.81;         //Schwerkraft
        //public double a;                //Beschleunigung
        
        public double Fg;            //Gewichtskraft
        public double Fh;              //Hangabtriebskraft
        public double Fn;               // Normalkraft
        public double Fr;               // Reibungskraft
        public double mik = 0.2;              //Reibungszahl      Realistischer Wert?        
         
       // public double Vk = 0;  //Geschwindigkeit
        public double t0;  //Aktuelle Zeit
        public double deltaT;
        public boolean timeMerker = true;
        public double time = 0;
    
        public double Vn;
        public double s;
        public int steig;
        //public double winkel = 90;
        public double winkelBeta;
        public double strecke;
        public double deltaX;
        public double deltaY;
        
        //Größe des Fensters
        public double fieldWidth;
        public double fieldHeight;
       
        ArrayList<Bahn> bahnen = Bahn.getBahnen();
        ArrayList<Kugel> kugeln = Kugel.getKugeln();
        Kugel k;
        
        Bahn kolBahn;
    
    public int merker = -1;
        
    public Simulation() 
    {        
                          
    }    
    
    public void move()
    {
        if(timeMerker)                  //t0 nimmt die Zeit bei der der StartButton gedrückt wurde
        {
            t0 = System.currentTimeMillis();
            timeMerker = false;
        }
        
        time = (System.currentTimeMillis() - t0) / 10000;
  
        
      for (int i = 0; i < kugeln.size(); i++)
        {
            k = kugeln.get(i);  
         
            s = time * k.getVk() + 0.5 * k.getA() * time * time;                                  //Auf der Bahn
            k.setXSpeed(steig * (s / Math.sin((90 * Math.PI) / 180)) * Math.sin(((90 - k.getWinkel()) * Math.PI) / 180));
            k.setYSpeed(steig * (s / Math.sin((90 * Math.PI) / 180)) * Math.sin((k.getWinkel() * Math.PI) / 180));
            
            
            System.out.println("Winkel: " + k.getWinkel()+ " Vk: " + k.getVk() + " A " + k.getA() + " time " + time);
            System.out.println(k.getXSpeed() + "  " + k.getYSpeed());
            /*
            s = g * Math.cos((k.getWinkel() * Math.PI) / 180);
            Vn = k.getVk() + s * time;
          */
            
            k.setXSpeed(k.getVk() + (Math.sin((k.getWinkel() * Math.PI) / 180) * time));
            k.setYSpeed(k.getVk() + (g * time));
            
            
            //checkForEnd(kolBahn); 
            Bahn b = kollisionBahnen(bahnen);
            kolBahn = b;
      
            //System.out.println("xSpeed  " + k.getXSpeed() + "  ySpeed   " + k.getYSpeed() + "  " + s);
            k.setCenterX(k.getCenterX() + k.getXSpeed());
            k.setCenterY(k.getCenterY() + k.getYSpeed());      
        }
    }
    
    //Kollisiondetector
    public Bahn kollisionBahnen(ArrayList<Bahn> bahnen)
    {
        for (int i = 0; i < bahnen.size(); i++)
        {
            Bahn b = bahnen.get(i);
            // normalenvektor * mittelpunkt kreis - d von der bahn - radius
            b.setDistanz((k.getCenterX()* b.getNVektorX() + k.getCenterY() * b.getNVektorY())- b.getD() - k.getRadius());
                       
            if (b.getOldDistanz() > 0 && b.getDistanz() <= 0 && k.getCenterX() < b.getGroeseresX() + k.getRadius() && k.getCenterX() > b.getKleineresX() - k.getRadius())
            {
               // System.out.println("Kollision mit Bahn " + i);

                if(merker != i)//&& formelTyp != 2)
                {
                    strecke = b.getStrecke();
                    deltaX = b.getDeltaX();
                    deltaY = b.getDeltaY();
                    
                    k.setWinkel(b.getWinkel()); //Übernimmt den Winkel der Bahn
                    //formelTyp = 2;          //Schräge runter 
                    formelnBerechnen(k);
                    merker = i;
                }
                
                return b;
            }
            else
            {                
                b.setOldDistanz(b.getDistanz());                
               // return null;              //Hier kein return, denn dan bricht die for-Schleife ab  
            } 
        }
        return null;
    }
    
    public void checkForEnd(Bahn b)
    {
        double ybp = k.getCenterY() + (b.getNVektorY() * k.getRadius() * (-1));
        
        //System.out.println("Check for End");

        if((ybp > b.getGroeseresY() && (k.getCenterX() > b.getGroeseresX() || k.getCenterX() < b.getKleineresX())) || 
                (ybp < b.getKleineresY() && (k.getCenterX() > b.getGroeseresX() || k.getCenterX() < b.getKleineresX())))
        {
            //formelTyp = 3;
            k.setVk(s);
            k.setWinkel(90.0); //Winkel wird wieder zu 90 Grad
            //t0 = System.currentTimeMillis() ;
          //  System.out.println("Runter von der Bahn");
        }
    }
    
    public void formelnBerechnen(Kugel k)
    {
        //winkelBeta = 180 - 90 - winkel;
        
        k.setVk(k.getYSpeed());
       // t0 = System.currentTimeMillis();
        
        
        Fg = k.getMasse() * g;
        Fh = Fg * Math.sin((k.getWinkel() * Math.PI)/180);     
    
        Fn = Fg * Math.cos((k.getWinkel() * Math.PI)/180);
        Fr = mik * Fn;             
        
        k.setA(Fh / k.getMasse());              
        
        
       
        if(k.getWinkel() > 0 )//|| formelTyp == 2)//Wenn Kugel zuerst auf Negative Steigung trifft, rollt sie hoch
        {
            steig = 1;
        }
        else
        {
            steig = -1;
        }   
    }
}
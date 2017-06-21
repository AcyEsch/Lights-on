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
        private double g = 9.81;         //Schwerkraft
        //public double a;                //Beschleunigung
        
        private double Fg;            //Gewichtskraft
        private double Fh;              //Hangabtriebskraft
        private double Fn;               // Normalkraft
        private double Fr;               // Reibungskraft
        private double mik = 0.2;              //Reibungszahl      Realistischer Wert?        
         
       // public double Vk = 0;  //Geschwindigkeit
        private double t0;  //Aktuelle Zeit
        private double deltaT;
        private double talt = 0;
        private boolean timeMerker = true;
        private double time = 0;
    
        private double Vn;
        private double s;
        private double sMerker;
        //public double winkel = 90;
        private double winkelBeta;
        private double winkelMerker;
        private double strecke;
        private double deltaX;
        private double deltaY;
        
        //Größe des Fensters
        public double fieldWidth;
        public double fieldHeight;
       
        ArrayList<Bahn> bahnen = Bahn.getBahnen();
        ArrayList<Kugel> kugeln = Kugel.getKugeln();
        Kugel k;
        
        Bahn kolBahn;
    
    //public int merker = -1;
        
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
        
        deltaT = (time - talt);
        talt = time;
        
        
      for (int i = 0; i < kugeln.size(); i++)
        {
            k = kugeln.get(i);  
                                                      
                        
            
            if(k.getBounce())
            {
               bouncen();
            }
            else
            {                               
                s = time * k.getVk() + 0.5 * k.getA() * time * time;  
                
                k.setXSpeed((s / Math.sin(90 * Math.PI/180)) * Math.sin(((90 - k.getWinkel())* Math.PI/180)));
                k.setYSpeed((s / Math.sin(90 * Math.PI/180)) * Math.sin(k.getWinkel() * Math.PI/180));
            }
           // System.out.println("Winkel: " + k.getWinkel() + "  S: " + s + " Vk: " + k.getVk() + " A " + k.getA() + " time " + time);
           // System.out.println(k.getXSpeed() + "  " + k.getYSpeed());

            
            //checkForEnd(kolBahn); 
            
            k.setVk(s);
            
            Bahn b = kollisionBahnen(bahnen, i);
            kolBahn = b;
            
            
            if(k.getKollision())
            {
               // System.out.println("Kugel " + i);
                checkForEnd(kolBahn);
            }
      
           // System.out.println("xSpeed  " + k.getXSpeed() + "  ySpeed   " + k.getYSpeed() + "  " + s);
            k.setCenterX(k.getCenterX() + k.getXSpeed());
            k.setCenterY(k.getCenterY() + k.getYSpeed());  
        }
    }
    
    //Kollisiondetector
    public Bahn kollisionBahnen(ArrayList<Bahn> bahnen, int j)
    {
        for (int i = 0; i < bahnen.size(); i++)
        {
            Bahn b = bahnen.get(i);
            // normalenvektor * mittelpunkt kreis - d von der bahn - radius
            b.setDistanz((k.getCenterX()* b.getNVektorX() + k.getCenterY() * b.getNVektorY())- b.getD() - k.getRadius());
                       
            if (b.getOldDistanz() > 0 && b.getDistanz() <= 0 && k.getCenterX() < b.getGroeseresX() + k.getRadius() && k.getCenterX() > b.getKleineresX() - k.getRadius())
            {
                if(k.getMerker() != i)//&& formelTyp != 2)
                {
                    /*
                    strecke = b.getStrecke();
                    deltaX = b.getDeltaX();
                    deltaY = b.getDeltaY();
                    */
                    //if(k.getBounce() == false)
                    winkelMerker = k.getWinkel();
                    
                    k.setWinkel(b.getWinkel()); //Übernimmt den Winkel der Bahn
                    
                    k.setVk(k.getVk() * 0.4);   //Die Kugel verliert beim aufprall Energie
                                       
                   
                    //k.setKollision(true);
                    k.setMerker(i);                                        
                    
                    
                    winkelBeta = winkelMerker - k.getWinkel();                
                    winkelBeta = winkelMerker + winkelBeta;
                    k.setBounce(true);
                }
                //Wenn Ball bounct gibt es trotzdem kollisionen
                if(k.getBounce() == false)
                {
                    k.setCenterY(b.getFunktionNachX(k.getCenterX()) - k.getRadius());           //Kugel liegt auf Bahn auf
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
        /*
        double ybp = k.getCenterY() + (b.getNVektorY() * k.getRadius() * (-1));
        
       // System.out.println("Check for End");
       
        if((ybp > b.getGroeseresY() && (k.getCenterX() > b.getGroeseresX() || k.getCenterX() < b.getKleineresX())) || 
                (ybp < b.getKleineresY() && (k.getCenterX() > b.getGroeseresX() || k.getCenterX() < b.getKleineresX())))
        {
            //formelTyp = 3;
            k.setVk(s);
            k.setWinkel(90.0); //Winkel wird wieder zu 90 Grad
            k.setKollision(false);
            k.setMerker(-1);
            //t0 = System.currentTimeMillis() ;
          //  System.out.println("Runter von der Bahn");

        }
        */
    }

    public void bouncen()
    {         
        int richtung;                
        
        k.setWinkel(winkelBeta);
        sMerker = s;
            
        if(winkelBeta == 0 || winkelBeta == 180) k.setWinkel(90);
        
        k.setXSpeed(-1 * (sMerker / Math.sin(90 * Math.PI/180)) * Math.sin(((90 - k.getWinkel())* Math.PI/180)));
        k.setYSpeed(-1 * (sMerker / Math.sin(90 * Math.PI/180)) * Math.sin(k.getWinkel() * Math.PI/180));
        
        System.out.println("Before X " + k.getXSpeed() + "  Y " + k.getYSpeed());
        

        
        
        if(winkelBeta != 0 && winkelBeta != 180)k.setXSpeed(k.getXSpeed() * (0.8 - time));//+ (g * time));                 
        k.setYSpeed(k.getYSpeed() + (g * time)); 
        
        
        
        
        
        System.out.println("X " + k.getXSpeed() + "  Y " + k.getYSpeed() + "   " + g + "    " + time);
        
        
        if((0.8 - time) <= 0.1 && k.getYSpeed() > 0)    // Eigentlich (0.8 - time) <= 0.1,  Nur zum testen 0.5
        {
            System.out.println("Fällt wieder normal");
            k.setXSpeed(0);
            k.setWinkel(90);
            k.setBounce(false);
        }
        
    }
    
    public void formelnBerechnen(Kugel k)
    {
        /*
        //winkelBeta = 180 - 90 - winkel;
        
        k.setVk(k.getYSpeed());
       // t0 = System.currentTimeMillis();
        
        
        Fg = k.getMasse() * g;
        Fh = Fg * Math.sin((k.getWinkel() * Math.PI)/180);     
    
        Fn = Fg * Math.cos((k.getWinkel() * Math.PI)/180);
        Fr = mik * Fn;             
        
        k.setA(Fh / k.getMasse());   
*/
    }   

    private void setFill(Color BLACK) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
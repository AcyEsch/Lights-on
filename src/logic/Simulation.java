package logic;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Rectangle;
        
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
        private double talt = 0;
        public boolean timeMerker = true;
        public double time = 0;

        public double Vn;
        public double s;
        private double sMerker;
        //public double winkel = 90;
        public double winkelBeta;
        private double winkelMerker;
        public double strecke;
        public double deltaX;
        public double deltaY;        
               
        private double reibung = 0.1;
        private double maxS = 10;
        
        //Größe des Fensters
        public double fieldWidth;
        public double fieldHeight;
        public boolean an = false;
       
        private ArrayList<Bahn> bahnen = Bahn.getBahnen();
        private ArrayList<Kugel> kugeln = Kugel.getKugeln();
        private ArrayList<Schalter> schalter = Schalter.getSchalter();
         
        
        private Kugel k;
        private Bahn kolBahn;
       
        
    
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

//~~~~~~~~~~~~~~Viel zu viele if´s. Geht bestimmt besser~~~~~~~~~~~~~~~~~~~~~~~~
            if(k.getHRollen())
            {
        
                if(k.getKollision() == false && (k.getWinkel() <= 89 || k.getWinkel() >= 91))
                {
                    s = time * k.getVk() + 0.5 * k.getA() * time * time;
                    if(s > maxS)s = maxS;
                    k.setXDelta(-1 *(s / Math.sin(90 * Math.PI/180)) * Math.sin(((90 - k.getWinkel())* Math.PI/180)));
                    k.setYDelta((s / Math.sin(90 * Math.PI/180)) * Math.sin(k.getWinkel() * Math.PI/180)); 
                }
                else
                {
                s = s / (reibung + g/10) ;// Realer Wert?                       //Kann zu fehler füren mit 2 Kugeln????
                
                if(k.getWinkel() == 0 && winkelMerker < 90) k.setWinkel180(180);

            k.setXDelta(-1 *(s / Math.sin(90 * Math.PI/180)) * Math.sin(((90 - k.getWinkel())* Math.PI/180)));
            k.setYDelta(-1 *(s / Math.sin(90 * Math.PI/180)) * Math.sin(k.getWinkel() * Math.PI/180));           
                }
                
            if(s < 0.01)//(s <= 0)
            {
                k.setHRollen(false);
                k.setVk(0);
                s = 0;
                k.setWinkel(k.getWinkel());                                     //Wenn winkel auf 180 war wird er jetzt zu 0

                t0 = System.currentTimeMillis();
            }
            }
            else
            {
                s = time * k.getVk() + 0.5 * k.getA() * time * time;  
               // s = k.getVk() + k.getA() * deltaT;                            //Wäre es besser mit deltaT zu rechnen ?
               if(s > maxS)s = maxS;
               
               
               if(k.getWinkel() != 0)
               {
            k.setXDelta((s / Math.sin(90 * Math.PI/180)) * Math.sin(((90 - k.getWinkel())* Math.PI/180)));
            k.setYDelta((s / Math.sin(90 * Math.PI/180)) * Math.sin(k.getWinkel() * Math.PI/180));
               }
               else
               {
                   k.setXDelta(0);
                   k.setYDelta(0);
               }
               
               k.setVk(s);
            }
            System.out.println("Geschwindigkeit " + s);
          
           //Kugel fliegt im Bogen           
           if(k.getKollision() == false && (k.getWinkel() <= 89 || k.getWinkel() >= 91))
           {               
               //Muss noch geändert werden für denn fall, dass die Kugel beim hochrollen von der Bahn fällt               
                if(k.getWinkel() < 90) k.setWinkel(k.getWinkel() + g * time);//Math.pow(g * time, 2));//Realer Wert?     
                if(k.getWinkel() > 90) k.setWinkel(k.getWinkel() - g * time);//Math.pow(g * time, 2));                                            
           }
          
          //Test           
           if((k.getWinkel() != 0 && k.getWinkel() != 180)&&(k.getHRollen() && (k.getXDelta() == 0)))
           {
               k.setHRollen(false);
           }
          

            //checkForEnd(kolBahn); 
                        
            
            Bahn b = kollisionBahnen(bahnen, i);
            kolBahn = b; 
            
            if(k.getKollision() && b.getIsSchalter())
                an =true;
          
            
             if(k.getKollision())
            {
               checkForEnd(kolBahn);
            }

      
             
             
            k.setCenterX(k.getCenterX() + k.getXDelta());
            k.setCenterY(k.getCenterY() + k.getYDelta());  
            
            //setzt die Property, damit von Controller überwacht werden kann
            k.xKugelProp().set(k.getCenterX());
            k.yKugelProp().set(k.getCenterY());
        }    
    }
  
    //Kollisiondetector
    public Bahn kollisionBahnen(ArrayList<Bahn> bahnen, int j)
    {
        for (int i = 0; i < bahnen.size(); i++)            
        {
            Bahn b = bahnen.get(i);
            // normalenvektor * mittelpunkt kreis - d von der bahn - radius
            b.setDistanz(((k.getCenterX()* b.getNVektorX()) + (k.getCenterY() * b.getNVektorY()))- b.getD() - k.getRadius());
                      
            if (b.getOldDistanz() > 0 && b.getDistanz() <= 0 && k.getCenterX() < b.getGroeseresX() + k.getRadius() && k.getCenterX() > b.getKleineresX() - k.getRadius())
            {
               
                if(k.getMerker() != i)//&& formelTyp != 2)
                {
                    //Kugel rollt Bahn mit Schwung hoch
                    // //if((k.getWinkel() > 90 && b.getWinkel() < 90) || (k.getWinkel() < 90 && b.getWinkel() > 90))
                    if(b.getWinkel() == 0 ||(k.getWinkel() > 90 && b.getWinkel() < 90) || (k.getWinkel() < 90 && b.getWinkel() > 90))
                    {                        
                        kugelRolltHoch(b);                        
                    }                    

                    winkelMerker = k.getWinkel();
                    
                    k.setWinkel(b.getWinkel()); //Übernimmt den Winkel der Bahn
                    
                    k.setVk(k.getVk() * 0.1);   //Die Kugel verliert beim aufprall Energie

                    k.setKollision(true);
                    k.setMerker(i);                                                            
                    
                    winkelBeta = winkelMerker - k.getWinkel();                
                    winkelBeta = winkelMerker + winkelBeta;
                   // k.setBounce(true);
                }
                //Wenn Ball bounct gibt es trotzdem kollisionen
                if(k.getBounce() == false)
                {
                  // k.setCenterY((b.getFunktionNachX(k.getCenterX()) - k.getRadius()) - k.getRadius() * Math.sin(k.getWinkel() * Math.PI/180));
                    k.setCenterY(b.getFunktionNachX(k.getCenterX()) - k.getRadius());//Kugel liegt auf Bahn auf
                }            
                
                return b;
            }
            else
            {         
                
                
//                if(k.getMerker() == i)
//                {
////~~~~~~Muss in checkForEnd rein~~~~~~~
//                //k.setWinkel(90.0); //Winkel wird wieder zu 90 Grad
//                k.setKollision(false);
//                //k.setHRollen(false);
//                k.setMerker(-1);
//                //k.setFill(Color.OLIVE);
//                }
////~~~~~~~~~~~~~~~~~~~~~~~~~~~                
                
                b.setOldDistanz(b.getDistanz());                
               // return null;              //Hier kein return, denn dan bricht die for-Schleife ab  
            } 
        }
        return null;
    }
    
    public void checkForEnd(Bahn b)
    {
       double ybp = k.getCenterY() + (b.getNVektorY() * k.getRadius() * (-1));
       
        System.out.println("Check for End");
        
        
       

   if((ybp > b.getGroeseresY() && (k.getCenterX() > b.getGroeseresX() || k.getCenterX() < b.getKleineresX())) || 
                (ybp < b.getKleineresY() && (k.getCenterX() > b.getGroeseresX() || k.getCenterX() < b.getKleineresX())))
        {
            k.setKollision(false);
            k.setMerker(-1);
            k.setVk(s);            
            //k.setFill(Color.OLIVE);
           System.out.println("Runter von der Bahn");
        }   
    }
         
   
    public void kugelRolltHoch(Bahn b)
    {            
        System.out.println(b.getWinkel());
        if(b.getWinkel()  < 90 && b.getWinkel() != 0)
        {
            winkelBeta = b.getWinkel() + 90;
                                
                
            if(k.getWinkel() > winkelBeta)                                  //Dann rollt die Kugel hoch
            {
                k.setHRollen(true);
                k.setVk(k.getVk() * 0.1);   //Energieverlust müsste abhängig vom Winkel sein
                System.out.println("HRollen 1");
            }
        }
        if(b.getWinkel() > 90)
        {
            winkelBeta = b.getWinkel() - 90;            
            
            if(k.getWinkel() < winkelBeta)                                  //Dann rollt die Kugel hoch
            {
                k.setHRollen(true);
                k.setVk(k.getVk() * 0.1);   //Die Kugel verliert beim aufprall Energie                
                
                System.out.println("HRollen 2");
            }
        }
        if(b.getWinkel() == 0)
        {
             System.out.println("Winkel Bahn " + b.getWinkel());
            if(k.getWinkel() == 90)             //Kugel belibt auf Bahn stehen
            {
                s=0;
                k.setVk(0);
                k.setA(0);
                 System.out.println("Kugel fällt mit " + k.getWinkel());
            }
            else
            {
                 System.out.println("Kugel fällt mit Winkel " + k.getWinkel());
                k.setHRollen(true);
                k.setVk(k.getVk() * 0.1);
            }  
        }
        if(b.getWinkel() == 0)
        {
             System.out.println("Winkel Bahn " + b.getWinkel());
            if(k.getWinkel() == 90)             //Kugel belibt auf Bahn stehen
            {
                s=0;
                k.setVk(0);
                k.setA(0);
                 System.out.println("Kugel fällt mit " + k.getWinkel());
            }
            else
            {
                 System.out.println("Kugel fÃ¤llt mit Winkel " + k.getWinkel());
                k.setHRollen(true);
                k.setVk(k.getVk() * 0.1);
            }  
        }
        
        System.out.println("Winkel Kugel " + k.getWinkel() + "  WinkelBeta " + winkelBeta);
        
        s = time * k.getVk() + 0.5 * k.getA() * time * time; 
    }

    
    public void bouncen()
    {        
        /*
        int richtung;                
        
        k.setWinkel(winkelBeta);
        sMerker = s;
            
        if(winkelBeta == 0 || winkelBeta == 180) k.setWinkel(90);
        
        k.setXDelta(-1 * (sMerker / Math.sin(90 * Math.PI/180)) * Math.sin(((90 - k.getWinkel())* Math.PI/180)));
        k.setYDelta(-1 * (sMerker / Math.sin(90 * Math.PI/180)) * Math.sin(k.getWinkel() * Math.PI/180));
        
        System.out.println("Before X " + k.getXDelta() + "  Y " + k.getYDelta());
        

        
        
        if(winkelBeta != 0 && winkelBeta != 180)k.setXDelta(k.getXDelta() * (0.8 - time));//+ (g * time));                 
        k.setYDelta(k.getYDelta() + (g * time)); 
        
        System.out.println("X " + k.getXDelta() + "  Y " + k.getYDelta() + "   " + g + "    " + time);
        
        
        if((0.8 - time) <= 0.1 && k.getYDelta() > 0)    // Eigentlich (0.8 - time) <= 0.1,  Nur zum testen 0.5
        {
            System.out.println("Fällt wieder normal");
            k.setXDelta(0);
            k.setWinkel(90);
            k.setBounce(false);
        }     
*/
    }
    
    public void formelnBerechnen(Kugel k)
    {
//       winkelBeta = 180 - 90 - k.getWinkel();
//        
//       // k.setVk(k.getYSpeed());
//       // t0 = System.currentTimeMillis();
//        
//        
//        Fg = k.getMasse() * g;
//        Fh = Fg * Math.sin((k.getWinkel() * Math.PI)/180);     
//    
//        Fn = Fg * Math.cos((k.getWinkel() * Math.PI)/180);
//        Fr = mik * Fn;             
//        
//        k.setA(Fh / k.getMasse());              
    }
    
    private void setFill(Color BLACK) 
    {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

      public boolean getTimeMerker(){
            return timeMerker;
          
      } 
        public void setTimeMerker(boolean tM){
            timeMerker=tM;
          
      } 
         public boolean getAn(){
            return an;
          
      } 
}

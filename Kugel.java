
package testcode;


import java.util.ArrayList;
import javafx.geometry.Point2D;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

/***
 *
 * @author Jasmin, Acy
 */

    public class Kugel extends Circle 
    {  
        public int formelTyp = 1;
        
        public double x_speed;
        public double y_speed;
        
        public double startX = 100;
        public double startY = 100;
    
        public double radius;
        public double masse = 20;      //Realistischer Wert?
                       //Beschleunigung
        
        public double Fg;            //Gewichtskraft
        public double Fh;              //Hangabtriebskraft
        public double Fn;               // Normalkraft
        public double Fr;               // Reibungskraft
        public double mik = 0.2;              //Reibungszahl      Realistischer Wert?
        
        public double Gx;                //Rechnung
        public double Gy;
         
        public double Vk = 0;  //Geschwindigkeit
        public double t0;  //Aktuelle Zeit
        public double time = 0;
        private boolean timeMerker = true;
    
        public double s;
        public int steig;
        public double winkel;
        public double winkelBeta;
        public double strecke;
        public double deltaX;
        public double deltaY;
        public double a;
        
        
        //Größe des Fensters
        public double fieldWidth;
        public double fieldHeight;
     
        //forKollision
        public double distanz;
        ArrayList<Bahn> bahnen = Bahn.getBahnen();
        private double oldDistanz = 0;
        public int merker;
        
        //for Fall mit Schwung
        private Bahn kolBahn = null;
    
        //for CheckForEnd
        double ybp; //y-Koordinate des Berührpunktes der Kugel mit der Bahn
        
        //new
        double v_y = 0;
        double v_x = 0;
        public double g = 9.81; 
        double alpha = 90;       
        Point2D gravitation = new Point2D(0 , g);
        Point2D beschleunigung;
        Point2D geschwindigkeit = new Point2D(0,0);
        double dt = 0;
        double kolBahnSteig = 1;
        int cases = 1;
        Line bewegungsVektor;
        
        
    public Kugel(double radius, double fieldWidth, double fieldHeight) 
    {        
       super();                                                                 //Greift auf Superklasse zu (hier Circle)
       
       this.radius = radius;
       this.fieldWidth = fieldWidth;
       this.fieldHeight = fieldHeight;
       
       super.setRadius(radius);
       
       //Start Position der Kugel
       super.setCenterX(startX);                                            
       super.setCenterY(startY);          
    }    
    
    public void move()
    {
        if (timeMerker)
        {
            t0 = System.currentTimeMillis();
            timeMerker = false;
        }
        dt = (System.currentTimeMillis() - time);
        time = (System.currentTimeMillis() - t0) / 10000000;
        
    //// Neue Master Formel
    ///// Checkt nach Kollisionen
     
    kolBahn = kollisionBahnen(bahnen);
    //System.out.println(kolBahnSteig);
    
    switch(cases){
        case 1: // Fall
        beschleunigung = new Point2D(
                        gravitation.getX(), 
                        gravitation.getY()
        );
        break;
        case 2 : // Fall in abhänikeit des Winkels
            beschleunigung = new Point2D(
                        Math.toDegrees(Math.cos(alpha)), 
                        Math.toDegrees(Math.sin(alpha)) //gravitation.getY()*
             );
        break;
    }
    geschwindigkeit = new Point2D(
                        geschwindigkeit.getX()+(beschleunigung.getX()),
                        geschwindigkeit.getY()+(beschleunigung.getY())
    );
    //// Bewegungsvektorvisualisieren
//    int v = 40;    
//    bewegungsVektor = new Line(super.getCenterX(), super.getCenterY(), 
//                super.getCenterX() + geschwindigkeit.getX()*v, super.getCenterY() + geschwindigkeit.getY()*v);
//        bewegungsVektor.setStrokeWidth(10);
//        bewegungsVektor.setStroke(Color.AQUAMARINE);
        
    //// Addiert      
        super.setCenterX(super.getCenterX() + geschwindigkeit.getX()*time);
        super.setCenterY(super.getCenterY() + geschwindigkeit.getY()*time);   //kolBahnSteig*         
    }
    
    //Kollisiondetector
 public Bahn kollisionBahnen(ArrayList<Bahn> bahnen)
    {
        for (int i = 0; i < bahnen.size(); i++)
        {
            Bahn b = bahnen.get(i);
            // normalenvektor * mittelpunkt kreis - d von der bahn - radius
            b.setDistanz((super.getCenterX()* b.n_vektor_x + super.getCenterY()* b.n_vektor_y)- b.d - radius);
            
           
            
            if (b.getOldDistanz() > 0 && b.getDistanz() <= 0  && super.getCenterX() < b.getGroesseresX() + radius && super.getCenterX() > b.getKleineresX() - radius)
            {
                geschwindigkeit =  bounce(b); 
                
                
                winkel = b.getWinkel(); //Übernimmt den Winkel der Bahn
                alpha = winkel;
                kolBahnSteig = b.getSteigung();
                
                
//                if(formelTyp != 2 && merker != i)
//                {
//                    strecke = b.getStrecke();
//                    deltaX = b.getDeltaX();
//                    deltaY = b.getDeltaY();
//                    
//                    winkel = b.getWinkel(); //Übernimmt den Winkel der Bahn
//                    formelTyp = 2;          //Schräge runter 
//                    formelnBerechnen();
//                    merker = i;
//                }
//                
                return b;
            }
            else
            {
//                if(formelTyp == 2 &&i == merker)
//                {
//                   formelTyp = 1;
//                   Vk = s;
//                   //t0 = System.currentTimeMillis() ;
//                   System.out.println("Runter von der Bahn");
//                }
//                
                b.setOldDistanz(b.getDistanz());
               // return null;              //Hier kein return, denn dan bricht die for-Schleife ab  
            } 
        }
        return null;
    }
    
   
   public void formelnBerechnen()
    {
        winkelBeta = 180 - 90 - winkel;
        
        Vk = y_speed;
        
        Fg = masse * g;
        Fh = Fg * Math.sin((winkel * Math.PI)/180);     
    
        Fn = Fg * Math.cos((winkel * Math.PI)/180);
        Fr = mik * Fn;             
        
        a = Fh / masse ;              
        
        if(winkel > 0 || formelTyp == 2)
        {
            steig = 1;
        }
        else
        {
           steig = -1;
        }
    }

    public void checkForEnd(Bahn b){
        //Noch nicht 100% genau 
        System.out.println("Check for end");
        ybp = super.getCenterY() + (b.n_vektor_y*radius*(-1));
        if ((ybp > b.getGroesseresY() && (super.getCenterX() > b.getGroesseresX() || super.getCenterX() < b.getKleineresX()))||
                (ybp < b.getKleineresY() && (super.getCenterX() > b.getGroesseresX() || super.getCenterX() < b.getKleineresX())) ) {
            formelTyp = 3;
            Vk = Math.sqrt(Math.pow(y_speed, 2)+Math.pow(x_speed, 2)) ;
            
        }
    }
    public Point2D bounce (Bahn b){
        //get nvektor von Bahn, Aufprallwinkel bestimmen, Spiegeln am Nvektor, 
        //Magnitude methode funktioniert!
        // dotProduct() ist nicht das Skalarprodukt!!
        // acos muss in degree gewandelt werden!!!!
        //multiply methode ebenso shitty!
        Point2D n = b.getN0();
        // Winkel zwischen nVektor Bahn und geschwindikeitsvektor
        double beta = 180-geschwindigkeit.angle(n);
        //berechnet die neue Richtung
        double z = Math.toDegrees(Math.cos(-beta))* n.magnitude() * (geschwindigkeit.magnitude() - (geschwindigkeit.magnitude()/2));
        Point2D neueRichtung = new Point2D(z/n.getX(), z/n.getY());
        //double v = (2/neueRichtung.getX());
        //neueRichtung = new Point2D(neueRichtung.getX()*v,neueRichtung.getY()*v );

        System.out.println(neueRichtung);
        System.out.println(n.magnitude());
        return neueRichtung;
        
    } 
    
    public double skalarProduct (Point2D a, Point2D b){
        return a.getX()*b.getX() + a.getY()*b.getY();
    }
}



//beschleunigung = new Point2D(
//                        Math.toDegrees(Math.cos(alpha)), 
//                        gravitation.getY()*Math.toDegrees(Math.sin(alpha))
//             );
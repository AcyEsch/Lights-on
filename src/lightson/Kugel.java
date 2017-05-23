/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basiseffekt;


import java.util.ArrayList;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.RadialGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
/***
 *
 * @author Jasmin
 */

    public class Kugel extends Circle 
    {

    
        
    public double x_speed;
    public double y_speed;
        
    public double radius;
    
    public double startX = 100;
    public double startY = 100;
    
    public double a = 9.81; //Beschleunigung
    public double Vk = 0;  //Geschwindigkeit
    public double t0 = System.currentTimeMillis();  //Aktuelle Zeit
    public double time = 0;
    
    //Größe des Fensters
    public double fieldWidth;
    public double fieldHeight;
   
    
    
    
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
       
       
       
       /*
       RadialGradient g;
        g = new RadialGradient(0,0,
                0.35,0.35,
                0.5,
                true,
                CycleMethod.NO_CYCLE,
                new Stop(0.0,Color.WHITE),
                new Stop(1.0, Color.RED));
       */
    }
    
    
    public void move()
    {
        time = (System.currentTimeMillis() - t0) / 1000;
       
        
        y_speed = time * Vk + 0.5 * a * time * time;               

        
        super.setCenterX(super.getCenterX() + this.x_speed);
        super.setCenterY(super.getCenterY() + this.y_speed);
       
           
    }
    
    public void kollisionBahn(Bahn bahn)
    {
         //Abfrage ob die Kugel über der Bahn ist (X Position)
        if(super.getCenterX() > bahn.getX1() - radius && super.getCenterX() < bahn.getX2() + radius)
        {
           // System.out.println("Die Kugel ist über der Bahn");
            
            //Abfrage ob die Kugel im Bereich der Bahn ist (Y Position)
            if(super.getCenterY() > bahn.getY1() - radius && super.getCenterY() < bahn.getY2() + radius)
            {
                //System.out.println("XXXXXXXXXXX");
                
                System.out.println((int)super.getCenterY() + "  "  + (int)bahn.getFunktionNachX(super.getCenterX())); // Y Wert der Kugel macht zu große sprünge (148, 154, 160 ...)
                                                                                                                        //Puffer einbauen
                //Berührt die Kugel die Bahn?
                if((int)(super.getCenterY() + radius) >= (int)bahn.getFunktionNachX(super.getCenterX()))
                {
                    super.setCenterY(bahn.getFunktionNachX(super.getCenterX()) - radius);                   //Kugel bleibt auf der Bahn
                    //System.out.println("Berührt die Bahn");
                }
                
            }
        }
        
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
        
        
        //Kollision Links
        if(super.getCenterX()<=this.radius)
        {
        
            super.setCenterX(this.radius);
            this.x_speed= -this.x_speed;
        }
        
        //Kollison Rechts
        if(super.getCenterX() >=this.fieldWidth - this.radius)
        {
            super.setCenterX(this.fieldWidth - this.radius);
            this.x_speed= -this.x_speed;
            
        }        
        
        //Kollision mit Boden
        if(super.getCenterY() >=this.fieldHeight-this.radius)
        {
            /*
            super.setCenterY(this.fieldHeight-this.radius);
            this.y_speed= -this.y_speed;
            */
            
            super.setCenterY(this.fieldHeight - this.radius);                   //Kugel bleibt auf dem Boden
            this.y_speed = 0;
        }  
    }
}

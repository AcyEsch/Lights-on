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
        /*
        //Kollision Oben
        if(super.getCenterY()<= this.radius)
        {
            super.setCenterY(this.radius);
            this.y_speed= -this.y_speed;
        }
        */
        
        
        //Kollision Unten
        if(super.getCenterY() >=this.fieldHeight-this.radius)
        {
            /*
            super.setCenterY(this.fieldHeight-this.radius);
            this.y_speed= -this.y_speed;
            */
            
            super.setCenterY(this.fieldHeight - this.radius);
            this.y_speed = 0;
        }
        
        
        //detect collision with other balls
        /*
        for (Ball b : balls){
            if(b != this && b.intersects(super.getLayoutBounds()))
            {
                
                double tempx=this.x_speed;
                double tempy=this.y_speed;
                this.x_speed=b.x_speed;
                this.y_speed=b.y_speed;
                b.x_speed=tempx;
                b.y_speed=tempy;
                break;
            }
                
        }
        */
        
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package basisefekt;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author Jasmin
 */
public class Basisefekt extends Application 
{
    
    @Override
    public void start(Stage primaryStage) 
    {
       Group root=new Group();
       
       Button btnStart = new Button("Start");
       
      
        

      
       
       
        double radius = 20;
        double height = 1000;                                                    // Größe des Fensters           
        double width = 1000;                                                     //Und Bereich in der sich die Kugel bewegen kann
        
       Kugel kugel = new Kugel(radius, height, width);
       
       btnStart.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
              
           KeyFrame k = new KeyFrame(Duration.millis(10),
            e->
            {
                
                    kugel.move();
                
            });
       
       Timeline t= new Timeline(k);
            t.setCycleCount(Timeline.INDEFINITE);
            t.play();
            }
        });
       
       
       
       
       
       
       
       
        root.getChildren().add(kugel);
        root.getChildren().add(btnStart);
        Scene scene = new Scene(root, height, width);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }
    
}

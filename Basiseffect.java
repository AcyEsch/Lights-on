/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package testcode;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import javafx.scene.shape.CircleBuilder;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;


/**
 *
 * @author Jasmin
 */
public class Basiseffect extends Application 
{
    
    @Override
    public void start(Stage primaryStage) 
    {
       Group root = new Group();
       
       Button btnStart = new Button("Start");
       
       
        double radius = 20;
        double height = 600;
        double width = 600;              
        
        

        //Bahn bahn = new Bahn(50, 300, 300, 200);//negativ Steigung   x1 y1 x2 y2
        Bahn bahn1 = new Bahn(400, 300, 50, 400);//negativ Steigung   x1 y1 x2 y2
       //Bahn bahn = new Bahn(50, 150, 250, 350);//pos Steigung   x1 y1 x2 y2
       //Bahn bahn2 = new Bahn(250, 250, 250, 750);//pos Steigung   x1 y1 x2 y2
      Bahn bahn = new Bahn(50, 150, 250, 550);//pos Steigung   x1 y1 x2 y2
       //Bahn bahn = new Bahn(350, 150, 100, 50);//Negative Steigung   x1 y1 x2 y2  
       //Bahn bahn = new Bahn(500, 150, 400, 200);
               
       Kugel kugel = new Kugel(radius, height, width);
       
       
       
       btnStart.setOnAction(new EventHandler<ActionEvent>()
        {
            public void handle(ActionEvent ae)
            {
              
           KeyFrame k = new KeyFrame(Duration.millis(5),
            e->
            {
                    kugel.move();                                    
            });
       
       Timeline t= new Timeline(k);
            t.setCycleCount(Timeline.INDEFINITE);
            t.play();
            }
        });

        root.getChildren().addAll( bahn, bahn1);
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
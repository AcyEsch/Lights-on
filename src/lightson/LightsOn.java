/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package lightson;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/**
 *
 * @author Acy  Jasmin
 */
public class LightsOn extends Application 
{
    
    @Override
    public void start(Stage stage) throws Exception 
    {
        Parent root = FXMLLoader.load(getClass().getResource("FXMLightsOn.fxml"));
        
        Scene scene = new Scene(root);
        
        
        double radius = 20;
        double height = 800;                                                    // Größe des Fensters           
        double width = 500;                                                     //Und Bereich in der sich die Kugel bewegen kann
        
       Kugel kugel = new Kugel(radius, height, width);
       
       KeyFrame k=new KeyFrame(Duration.millis(10),
            e->
            {
                
                    kugel.move();
                
            });
       
       Timeline t= new Timeline(k);
            t.setCycleCount(Timeline.INDEFINITE);
            t.play();
       

        
        
        
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) 
    {
        launch(args);
    }   
}

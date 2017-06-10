package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import logic.*;



public class FXMLGameController implements Initializable {
    @FXML   
    private GridPane mainPane;
    private Button simButton;
    private Label timer;
     
    @FXML private Pane simPane;
    private VBox elementsBox, controllsBox;
    private final Integer startTime=60;
    private Integer seconds=startTime;
    @FXML 
    private AnchorPane rootPane;
    @FXML
    public Button level1;



    private ArrayList<Object> dragList = new ArrayList<Object>();
    private DragDrop drag = new DragDrop();

    private final double radius = 20;
    
    private Simulation sim;
        
    
    @FXML
    private void handleSimButtonAction(ActionEvent event) {
        System.out.println("You clicked me!");
        //
        KeyFrame k = new KeyFrame(Duration.millis(10),
            e->
            {
                sim.move();                                   
            });
       
       Timeline t= new Timeline(k);
            t.setCycleCount(Timeline.INDEFINITE);
            t.play();
 
    }
   
    private void load () {
    Bahn bahn = new Bahn(50, 150, 250, 250, true);//Negative Steigung   x1 y1 x2 y2   
    Bahn bahn1 = new Bahn(300, 50, 450, 250, false);//Negative Steigung   x1 y1 x2 y2  
    Kugel kugel = new Kugel(radius, 100, 100, false);
    Kugel kugel1 = new Kugel(radius, 300, 100, true);
    sim = new Simulation();   
       
  
    ArrayList<Bahn> bahnen = Bahn.getBahnen();
    ArrayList<Kugel> kugeln = Kugel.getKugeln();
        
        for(int i = 0; i < bahnen.size(); i++)
                {
                    simPane.getChildren().add(bahnen.get(i));
                }
                
                for(int i = 0; i < kugeln.size(); i++)
                {
                    simPane.getChildren().add(kugeln.get(i));
                }
        //DragAndDrop Aufrufen       
       //Objekte die ROT sind können nicht bewegt werden!!
       for(int i = 0; i < kugeln.size(); i++)
       {          
           Kugel k = kugeln.get(i);
           
           if(k.getCanBeDraged())
           {
                drag.dragKugel(k);
           }
       }
       
       for(int i = 0; i < bahnen.size(); i++)
       {
           Bahn b = bahnen.get(i);
           
           if(b.getCanBeDraged())
           {
            drag.dragBahn(b);
           }
       }     
    //Drag And Drop END
    }
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
       //timer();
       load();
       
    }    
    
}

        
//  @FXML           
//    public void timer() {
//        Timeline time = new Timeline();
//        time.setCycleCount(Timeline.INDEFINITE);
//      
//       
//       KeyFrame frame=new KeyFrame(Duration.seconds(1), (ActionEvent event) -> {
//           seconds--;
//           timer.setText(seconds.toString());
//           if(seconds<=0){
//               time.stop();
//           }
//        });
//        
//    time.playFromStart();
//    
//}
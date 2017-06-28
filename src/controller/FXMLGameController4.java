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
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.AQUAMARINE;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.*;
import static logic.Main.root;
import static logic.Main.scene;
import static logic.Main.stage;



public class FXMLGameController4 implements Initializable 
{
    @FXML   
    private GridPane mainPane;
    private GridPane gridPane;
    public Button simButton;
    public Label timer;
    public Line line;
   private Button btn_home;
    Stage thisStage; 
    @FXML
    private Pane simPane;
    private VBox elementsBox, controllsBox;
    private HBox hBox;
    private final Integer startTime=60;
    public Integer seconds=startTime;
    @FXML 
   private AnchorPane rootPane;
 
    @FXML
    public Button level1;
 



    public ArrayList<Object> dragList = new ArrayList<Object>();
    public DragDrop drag = new DragDrop();

    private final double radius = 20;
    
    private Simulation sim;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

   
        
    
    @FXML
    private void handleSimButtonAction(ActionEvent event) 
    {      
        KeyFrame k = new KeyFrame(Duration.millis(10),
            e->
            {
                sim.move();                                   
            });
       
       Timeline t= new Timeline(k);
            t.setCycleCount(Timeline.INDEFINITE);
            t.play();
 
    }
    
    
    
    
    
       
   @FXML
   private void schalter(ActionEvent event) throws IOException{
       
      simPane.getStyleClass().add("light");
       simPane.setId("light");
   }
    
    
    
    
 @FXML 
    
  private void home(ActionEvent event) throws IOException, Exception{

     
       Main main=new Main();
       main.start(stage);
//        stage.setScene(scene);
//        stage.show();
 //  System.out.println("gogogogogoogogogg" );
    }
    
    
    
    
    
    
    private void load () 
    { 
   //Bahn bahn = new Bahn(50, 150, 250, 250, true);//Negative Steigung   x1 y1 x2 y2   
    Bahn bahn3= new Bahn(700, 50, 1100, 250, false,false);//Negative Steigung   x1 y1 x2 y2  
    //Unterschied?
    Bahn bahn = new Bahn(250, 250, 50, 150, true,false);//Negative Steigung   x1 y1 x2 y2   
    Bahn bahn1 = new Bahn(450, 250, 300, 50, false,false);//Negative Steigung   x1 y1 x2 y2 
    Bahn bahn2 = new Bahn(400, 250, 150, 350, true,false);
    bahn.setStrokeWidth(5.0);
    bahn1.setStrokeWidth(5.0);
    bahn2.setStrokeWidth(5.0);
    bahn3.setStrokeWidth(5.0);
            
   
            
            
            
            
    Kugel kugel = new Kugel(radius, 100, 100, false);
    Kugel kugel1 = new Kugel(radius, 300, 100, true);
    
    
    sim = new Simulation();   
       line.setOnMousePressed(circleOnMousePressedEventHandler);
       line.setOnMouseDragged(circleOnMouseDraggedEventHandler);
  
    ArrayList<Bahn> bahnen = Bahn.getBahnen();
    ArrayList<Kugel> kugeln = Kugel.getKugeln();
      
    
    
//    
//    hBox.setOnDragDetected(e -> {
//            Dragboard db = bahn.startDragAndDrop(TransferMode.ANY);
//            db.setDragView(new Bahn().snapshot(null, null), e.getX(), e.getY());
//            ClipboardContent cc = new ClipboardContent();
//           cc.clone();
//            db.setContent(cc);
//            e.consume();
//        });
//    
//    
//        hBox.setOnDragDetected(e -> {
//            Dragboard dbk = kugel.startDragAndDrop(TransferMode.ANY);
//            dbk.setDragView(new Kugel().snapshot(null, null), e.getX(), e.getY());
//            ClipboardContent cck = new ClipboardContent();
//            dbk.setContent(cck);
//            e.consume();
//        });
//   
//         
//        hBox.setOnDragEntered(new EventHandler<DragEvent>() {
//    @Override
//    public void handle(DragEvent event) {
//    /* the drag-and-drop gesture entered the target */
//         System.out.println("onDragEntered");
//    /* show to the user that it is an actual gesture target */
//         if (event.getGestureSource() != hBox &&
//                 event.getDragboard().hasString()) {
//           
//         }
//                
//         event.consume();
//    }
//});
//    
//    
    
    
    
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
           
           
           
           
//         hBox.setOnDragOver(e -> {
//            e.acceptTransferModes(TransferMode.COPY);
//        });
//           
         
           
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
    
    //~~~~~~~~~NEW~~~~~~~~
    /*
        Änderungen in
        FMXLGameController.java
        DragDrop.java
        Bahn.java
        FXMLGame.fxml
        */
    
    
    
    
     EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            orgSceneX = t.getSceneX();
            orgSceneY = t.getSceneY();
            orgTranslateX = ((Line)(t.getSource())).getTranslateX();
            orgTranslateY = ((Line)(t.getSource())).getTranslateY();
        }
    };
     
    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
        new EventHandler<MouseEvent>() {
 
        @Override
        public void handle(MouseEvent t) {
            double offsetX = t.getSceneX() - orgSceneX;
            double offsetY = t.getSceneY() - orgSceneY;
            double newTranslateX = orgTranslateX + offsetX;
            double newTranslateY = orgTranslateY + offsetY;
             
            ((Line)(t.getSource())).setTranslateX(newTranslateX);
            ((Line)(t.getSource())).setTranslateY(newTranslateY);
        }
    };
    
    
    
   
    
    
    
    
    
    
    
    @FXML
    private void handlePlus(ActionEvent event)
    {
        Bahn b = drag.getBahn();
        
        if(b.getDeltaX() < 500 && b.getDeltaX() > -500)
        {
        if(b.getKleineresX() == b.getStartX())
        {
            b.setStartX(b.getStartX() - 5);            
            b.setEndX(b.getEndX() + 5);            
        }
        else
        {
            b.setStartX(b.getStartX() + 5);
            b.setEndX(b.getEndX() - 5);
        }
        
        b.setStartY(b.getFunktionNachX(b.getStartX()));
        b.setEndY(b.getFunktionNachX(b.getEndX()));
        
        b.werteBerechnen();       
        }
    }
    
    @FXML
    private void handleMinus(ActionEvent event)
    {
        Bahn b = drag.getBahn();
        
        if(b.getDeltaX() > 50 || b.getDeltaX() < -50)
        {
        if(b.getKleineresX() == b.getStartX())
        {
            b.setStartX(b.getStartX() + 5);            
            b.setEndX(b.getEndX() - 5);            
        }
        else
        {
            b.setStartX(b.getStartX() - 5);
            b.setEndX(b.getEndX() + 5);
        }
        
        b.setStartY(b.getFunktionNachX(b.getStartX()));
        b.setEndY(b.getFunktionNachX(b.getEndX()));
        
        b.werteBerechnen();
        }        
    }
    
    @FXML
    private void handleLeft(ActionEvent event) 
    {                   
        Bahn b = drag.getBahn();
       
       //Routieren
       b.setRot(b.getRot() - 5);
       b.setRotate(b.getRot());
            

       b.werteBerechnen();       
    }
    
    @FXML
    private void handleRight(ActionEvent event)
    {
        Bahn b = drag.getBahn();
       
       //Routieren
       b.setRot(b.getRot() + 5);
       b.setRotate(b.getRot());
            

       b.werteBerechnen();  
    }
    //~~~~~~~~~~~END~~~~~~~~~~~~~
    
    public void setStage (Stage stage){
    thisStage = stage;
}

    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
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

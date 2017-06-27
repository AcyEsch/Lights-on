package controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Bounds;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.AQUAMARINE;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import javafx.util.Duration;
import logic.*;



public class FXMLGameController implements Initializable 
{ 
    @FXML private GridPane gridPane;                //Lyu
    public Label timer;                         //Lyu
    public Line line;
    @FXML private Button home;
   
    private Kugel kugel;
   @FXML private ToggleButton simButton; 
 
    
   @FXML private HBox hBox;
   
    @FXML private Pane simPane;
    @FXML private VBox elementsBox, controllsBox;
    private HBox drehen;                         
    private final Integer startTime=60;
    public Integer seconds=startTime;
    @FXML private ScrollPane controllPane;
 
    @FXML 
    public Button level1;
 
    public ArrayList<Object> dragList = new ArrayList<Object>();
    public DragDrop drag = new DragDrop();

    private final double radius = 20;
    
    private Simulation sim;
    double orgSceneX, orgSceneY;
    double orgTranslateX, orgTranslateY;

    private Timeline tl;
    
    private Group simGroup = new Group();
    private ArrayList<Bahn> bahnen ;
    private ArrayList<Kugel> kugeln ;
    private ArrayList<Schalter> schalter;
    //Layout
    private Bounds gridB, simB;
    private final double PERCENT_WIDTH_SIM = 0.69;
    private final double PERCENT_WIDTH_CON = 0.29;
    private final double PERCENT_HEIGHT = 0.89;
 
    ////////////////////////////Buttons/////////////////////////////////////////
   
    
    
@FXML
 protected ToggleButton handleSimButtonAction(ActionEvent event) throws IOException, Exception{
      simButton.selectedProperty().addListener(new ChangeListener<Boolean>() {
          @Override
          public void changed(ObservableValue<?extends Boolean> oValue ,Boolean selected, Boolean wasSelected) {
              
              if (selected) {
                  
                 drag.setCanDrag(false);             // Jasmin
                 
                  KeyFrame kf = new KeyFrame(Duration.millis(10),
                    e->
                      { 
                          sim.move();
                          if(sim.getAn()==true ){
                        System.out.println("k.getKollision()k.getKollision()k.getKollision()k.getKollision()" + sim.getAn()); 
                       simPane.getStyleClass().add("light");
                       simPane.setId("light");
                   
         }
                });
                  
                  Timeline t= new Timeline(kf);
                  t.getCurrentTime();
                  t.setAutoReverse(true);
                  t.setCycleCount(Timeline.INDEFINITE);
                  
                  tl=t;
                  t.playFromStart();
                  
                  simButton.setText("Stop");
        } else {
                  tl.stop();
                  sim.setTimeMerker(true);
                  drag.setCanDrag(true);
                  simButton.setText("Start");
              }
          }
      });
    return simButton;
 }


//   @FXML
//   private void schalter(ActionEvent event) throws IOException{
//      
//      simPane.getStyleClass().add("light");
//      simPane.setId("light");
//   }
//    
   
    @FXML  
    private void home(ActionEvent event) throws IOException, Exception{
        Scene scene = simPane.getScene();
        scene.setRoot(FXMLLoader.load(Main.class.getResource("/gui/LevelsFXML.fxml")));
        deleteContent();
        tl.stop();
        
    }
    
    
//////////////////////////////LÄDT//////////////////////////////////////////////    
    
    private void load () 
    {   
        
    controllsBox.setVisible(false);
    
    //!!!!!!!!!!!!!!!!!Spezifische Eigenschaften des Levels!!!!!!!!!!!!!!!!!!
    
   Bahn bahn4 = new Bahn(300, 450, 450, 650, true, false);//Negative Steigung   x1 y1 x2 y2 
    Bahn bahn = new Bahn(250, 250, 50, 150, true, false);//Negative Steigung   x1 y1 x2 y2 
    Bahn bahn2 = new Bahn(400, 250, 150, 350, true, false);
    bahn.setStrokeWidth(5.0);
    bahn2.setStrokeWidth(5.0);
    bahn4.setStrokeWidth(5.0);
     Bahn bahn3 = new Bahn(750,920,830,920, false, true);    
     bahn3.setStrokeWidth(20.0);
     bahn3.setStroke(AQUAMARINE);
      Kugel kugel = new Kugel(radius, 100, 100, true);
   

  
    sim = new Simulation();   

    bahnen = Bahn.getBahnen();
    kugeln = Kugel.getKugeln();
    schalter = Schalter.getSchalter();
    
   
//    Schleifen um oben entstandene Elemente hinzuzufügen

        for(int i = 0; i < bahnen.size(); i++)
                {
                    Bahn b = bahnen.get(i);
            simGroup.getChildren().add(b);
            b.getSelectedBahnProp().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            if (Bahn.getSelectedBahn() != null){
                                System.out.println("Bahn ausgewählt");
                                controllsBox.setVisible(true);
                            } else{ 
                                System.out.println("Bahn abgewählt");
                                controllsBox.setVisible(false); 
                             }                            
                        }                       
                    }
            });
        }

                
      
        for(int i = 0; i < kugeln.size(); i++)
        {
            Kugel k = kugeln.get(i);
            simGroup.getChildren().add(k);
            k.getSelectedKugelProp().addListener(new ChangeListener<Boolean>() {
                    @Override
                    public void changed(ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) {
                        if (newValue) {
                            if (Kugel.getSelectedKugel() != null){
                                System.out.println("Kugel ausgewählt");
                                controllsBox.setVisible(false);
                            } else{ 
                                System.out.println("Kugel abgewählt");
                                controllsBox.setVisible(false); 
                             }
                            
                        }
                       
                    }
            });
        }

        
                
                

 
 //~~~~~~~~~~~~~~~ NEW 27.06.17 Drag&Drop~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
    Bahn bahnT = new Bahn(200, 250, 150, 350, true, false);
    bahnT.setStrokeWidth(5.0);
             
    hBox.getChildren().add(bahnT);
    
    
    hBox.setOnDragDetected(e -> {
        System.out.println("Drag&Drop");
            Dragboard db = bahn.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new Line().snapshot(null, null), e.getX(), e.getY());
            ClipboardContent cc = new ClipboardContent();
            cc.putString("Line");
            db.setContent(cc);
        });
    
    
    simPane.setOnDragOver(e -> {
            e.acceptTransferModes(TransferMode.COPY);
        });
    
    
        
        simPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasString()) {
                
            Bahn b = bahnT;
            
            b.setStartX(b.getStartX() + e.getX());
            b.setStartY(b.getStartY() + e.getY());
            
            b.setEndX(b.getEndX() + e.getX());
            b.setEndY(b.getEndY() + e.getY());
                
            bahnen.add(b);
            simPane.getChildren().add(bahnT);
                
                e.setDropCompleted(true);
            } else {
                e.setDropCompleted(false);
            }
        });
    
    
    //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
    
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
           for(int j = 0; j < bahnen.size(); j++)
       {                 
            Bahn b = bahnen.get(j);

           if(b.getCanBeDraged())
           {
            drag.dragBahn(b);
           }
       }  
        //Drag And Drop END
       
 
    simPane.getChildren().add(simGroup);
    
    
    }    
    
//     EventHandler<MouseEvent> circleOnMousePressedEventHandler = 
//        new EventHandler<MouseEvent>() {
// 
//        @Override
//        public void handle(MouseEvent t) {
//            orgSceneX = t.getSceneX();
//            orgSceneY = t.getSceneY();
//            orgTranslateX = ((Line)(t.getSource())).getTranslateX();
//            orgTranslateY = ((Line)(t.getSource())).getTranslateY();
//        }
//    };
//     
//    EventHandler<MouseEvent> circleOnMouseDraggedEventHandler = 
//        new EventHandler<MouseEvent>() {
// 
//        @Override
//        public void handle(MouseEvent t) {
//            double offsetX = t.getSceneX() - orgSceneX;
//            double offsetY = t.getSceneY() - orgSceneY;
//            double newTranslateX = orgTranslateX + offsetX;
//            double newTranslateY = orgTranslateY + offsetY;
//             
//            ((Line)(t.getSource())).setTranslateX(newTranslateX);
//            ((Line)(t.getSource())).setTranslateY(newTranslateY);
//        }
//    };
//    
    
    
   
    
   
    
    
    
 
    
    
//    
//  @FXML
//   private void lichtAn(Kugel k, Label l){
//       System.out.println("lichtAnlichtAnlichtAnlichtAnlichtAnlichtAn");
//      
//              k.getCenterX();
//              k.getCenterY();
//
//    
//    
//      l.getLayoutX();
//      l.getLayoutY();
//      if (k.getCenterY()<=l.getLayoutY() || k.getCenterX()>=l.getLayoutX()){
//          System.out.println("yes yes yes yes   lichtAnlichtAnlichtAnlichtAnlichtAn");
//          simPane.getStyleClass().add("light");
//           simPane.setId("light");
//      }else{
//          System.out.println("kein kein kein kein lichtAnlichtAnlichtAnlichtAnlichtAn");
//      }
//       
//       
//   }
  
   
    
    
    
           
    @FXML
    private void handlePlus(ActionEvent event)
    {
        Bahn b = drag.getBahn();
        
        if(b.getStrecke() < 500)  //Jas
        {
        if(b.getKleineresX() == b.getStartX())
        {
            b.setStartX(b.getStartX() - 10);            
            b.setEndX(b.getEndX() + 10);            
        }
        else
        {
            b.setStartX(b.getStartX() + 10);
            b.setEndX(b.getEndX() - 10);
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
        
        if(b.getStrecke() > 100)   //Jas
        {
        if(b.getKleineresX() == b.getStartX())
        {
            b.setStartX(b.getStartX() + 10);            
            b.setEndX(b.getEndX() - 10);            
        }
        else
        {
            b.setStartX(b.getStartX() - 10);
            b.setEndX(b.getEndX() + 10);
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
       
        this.rotateLeft(true);       
    }
    
    @FXML
    private void handleRight(ActionEvent event)
    {
        Bahn b = drag.getBahn();
        this.rotateLeft(false); 
    }
    
    //JAS
    public void rotateLeft(boolean rotLeft)
    {
        Bahn b = drag.getBahn();
        double xM, yM, r = b.getStrecke() / 2;
        int lr, yStart, yEnd;
        
        if(rotLeft)                          //True = linksrum
        {
            lr = 8;
        }
        else
        {
            lr = -8;
        }
        
       /*
       //Rotieren
       
       b.setRot(b.getRot() - 5);
       b.setRotate(b.getRot());            
       ///Berechnet die Punkte nicht!!!!
*/
       
//Bahn in den Ursprung verschieben
       xM = (Math.abs(b.getDeltaX()) / 2) + b.getKleineresX();
       yM = (Math.abs(b.getDeltaY()) / 2) + b.getKleineresY();
       
       b.setStartX(b.getStartX() - xM);
       b.setStartY(b.getStartY() - yM);
       b.setEndX(b.getEndX() - xM);
       b.setEndY(b.getEndY() - yM);
       
       b.werteBerechnen();
       
//Rotieren       
      
        if(b.getWinkel() == 0)
        {
            if(b.getStartX() == b.getKleineresX())
            {
                b.setStartX(b.getStartX() + 1);
                b.setEndX(b.getEndX() - 1);
            }
            else
            {
                b.setStartX(b.getStartX() - 1);
                b.setEndX(b.getEndX() + 1);
            }
        }
        else
        {
            if(b.getWinkel() <= 90)
            {
                if(b.getStartY() == b.getKleineresY())
                {
                    b.setStartX(b.getStartX() - lr);
                    b.setEndX(b.getEndX() + lr);
                }
                else
                {
                    b.setStartX(b.getStartX() + lr);
                    b.setEndX(b.getEndX() - lr);
                }
            }
            else
            {
                if(b.getStartY() == b.getKleineresY())
                {
                    b.setStartX(b.getStartX() - lr);
                    b.setEndX(b.getEndX() + lr);
                }
                else
                {
                    b.setStartX(b.getStartX() + lr);
                    b.setEndX(b.getEndX() - lr);
                }
            }
        }
        
      
    if(rotLeft)   
    {
        if(b.getKleineresY() == b.getStartY())
        {
            yStart = -1; 
            yEnd = 1;
        }
        else
        {
            yStart = 1;
            yEnd = -1;
        }
    }
    else
    {
        if(b.getKleineresY() == b.getEndY())
        {
            yStart = 1; 
            yEnd = -1;
        }
        else
        {
            yStart = -1;
            yEnd = 1;
        }
    }
    
    if(b.getStartX() > r)
    {
        b.setStartX(r);
        b.setEndX(-r);
    }
    if(b.getStartX() < -r)
    {
        b.setStartX(-r);
        b.setEndX(r);
    }            
                
      b.setStartY(yStart * Math.sqrt(Math.pow(r,2) - Math.pow(b.getStartX(), 2)));
      b.setEndY(yEnd * Math.sqrt(Math.pow(r,2) - Math.pow(b.getEndX(), 2)));
      
    //Zurücksetzen
       b.setStartX(b.getStartX() + xM);
       b.setStartY(b.getStartY() + yM);
       b.setEndX(b.getEndX() + xM);
       b.setEndY(b.getEndY() + yM);
       
        
       b.werteBerechnen();      
    }
    
  
    //~~~~~~~~~~~END~~~~~~~~~~~~~
    
    public void deleteContent(){
        bahnen.clear();
        kugeln.clear();
        schalter.clear();
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {
       //timer();
   
        /////////////////////////////////////Layout/////////////////////////////////////

        //Sorgt dafür, dass die SimulationPane sich der Größe des Grid anpasst
        //Doesnt work correctly
        gridPane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>(){
          @Override
          public void changed(ObservableValue<? extends Bounds> observable,
              Bounds oldValue, Bounds newValue) {
              //
                gridB = newValue;
                simGroup.layoutBoundsProperty().addListener(new ChangeListener<Bounds>(){
            @Override
            public void changed(ObservableValue<? extends Bounds> obser,
                Bounds oldV , Bounds newV) {
                //
                simB = newV;
                try{
                    setTheSizes();
                }catch(Exception e){
                    System.out.println("Preferierte Größen können nicht gesetzt werden in der SimPane");
                }
          }
        });
          }
        });
        
        //Layout: Observer, der schaut ob eine Bahn ausgewählt ist
//        Bahn.selectedBahnProp().addListener(new ChangeListener<Boolean>(){
//        @Override 
//        public void changed(ObservableValue<? extends Boolean> o,Object oldVal, 
//                 Object newVal){
//              if (newVal) {
//                            System.out.println("Bahn ausgewählt");
//                            controllsBox.setVisible(true);
//                        }
//        }
//      });

        
        
     
        
       load();
       
    }      
    
    public void setTheSizes() throws Exception{
        if (gridB.getWidth()*PERCENT_WIDTH_SIM >= simB.getWidth() && gridB.getHeight()*PERCENT_HEIGHT >= simB.getHeight())
             simPane.setPrefSize(gridB.getWidth()*PERCENT_WIDTH_SIM, gridB.getHeight()*PERCENT_HEIGHT);
        else if (gridB.getWidth()*PERCENT_WIDTH_SIM < simB.getWidth() && gridB.getHeight()*PERCENT_HEIGHT >= simB.getHeight())
             simPane.setPrefSize(simB.getWidth(), gridB.getHeight()*PERCENT_HEIGHT);
        else if (gridB.getWidth()*PERCENT_WIDTH_SIM >= simB.getWidth() && gridB.getHeight()*PERCENT_HEIGHT < simB.getHeight())
             simPane.setPrefSize(gridB.getWidth()*PERCENT_WIDTH_SIM, simB.getHeight());
        else if (gridB.getWidth()*PERCENT_WIDTH_SIM < simB.getWidth() && gridB.getHeight()*PERCENT_HEIGHT < simB.getHeight())
             simPane.setPrefSize(simB.getWidth(), simB.getHeight());
        else
            throw new Exception();
        controllPane.setPrefSize(gridB.getWidth()*PERCENT_WIDTH_CON, gridB.getHeight()*PERCENT_HEIGHT);
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

package controller;

import java.awt.MouseInfo;
import java.awt.Point;
import java.awt.Window;
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
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.image.Image;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import static javafx.scene.paint.Color.AQUAMARINE;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
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
    public Integer seconds = startTime;
    @FXML private BorderPane controllPane;
 
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

    
    int merkerT = 0;
    int maxT = 2;
    
    private String strLabel;                   
   private Label lBahn = new Label();
    
    //Layout
    private Bounds gridB, simB;
    private final double PERCENT_WIDTH_SIM = 0.693;
    private final double PERCENT_WIDTH_CON = 0.29;
    private final double PERCENT_HEIGHT = 0.893;
 
    ////////////////////////////Buttons/////////////////////////////////////////
   boolean mSim = true;
    
    
@FXML
 protected ToggleButton handleSimButtonAction(ActionEvent event) throws IOException, Exception{
      if(mSim)
     {
         System.out.println("Start");
         simButton =  simStart();
         
        mSim = false;
     }
      else
      {
          System.out.println("Reset");
          simButton = simReset();
          mSim = true;
      }
    return simButton;
 }

 public ToggleButton simReset()
 {
                  tl.stop();
                  sim.setTimeMerker(true);
                  drag.setCanDrag(true);
                    gameOver();
         simButton.setText("Start");
    
     return simButton;
 }
 
 public ToggleButton simStart()
 {

     drag.setCanDrag(false);             
                    KeyFrame kf = new KeyFrame(Duration.millis(10),
                    e->
                    { 
                        sim.move();
                        if(sim.getAn()==true ){            
                        gridPane.getStylesheets().addAll(this.getClass().getResource("/gui/style_light.css").toExternalForm());
                       simPane.setId("scene");
                       gridPane.setId("mainPaneLight");
                       controllPane.setId("controllPane");
                        for(int j = 0; j < bahnen.size(); j++){      
                          Bahn b = bahnen.get(j);
                         b.setStroke(Color.DARKOLIVEGREEN);
                        }
                        for(int j = 0; j < kugeln.size(); j++){      
                          Kugel b = kugeln.get(j);
                         b.setFill(Color.DARKOLIVEGREEN);
                        }
                         tl.setDelay(Duration.seconds(1.0));
                          tl.stop();  
                           try {
                               next();
                           } catch (IOException ex) {
                               Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
                           }
                                     
                                                             
         }
                });
                  
                  Timeline t= new Timeline(kf);
                  t.getCurrentTime();
                  t.setAutoReverse(true);
                  t.setCycleCount(Timeline.INDEFINITE);
                  
                  tl=t;
                  t.playFromStart();
                  simButton.setText("Reset");

                  return simButton;
 }
   
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
      
      Light.Distant light = new Light.Distant() ;
        light.setAzimuth(271.05) ; 
        light.setElevation(53.37) ; 
        light.setColor(Color.web("#ffffff"));
        Lighting lighting = new Lighting() ; 
        lighting.setLight(light) ;  
   

  
    sim = new Simulation();   

    bahnen = Bahn.getBahnen();
    kugeln = Kugel.getKugeln();
    
   
//    Schleifen um oben entstandene Elemente hinzuzufügen

        for(int i = 0; i < bahnen.size(); i++)
                {
                    Bahn b = bahnen.get(i);
                    b.setEffect(lighting);
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
            k.setEffect(lighting);
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
        //Checkt, ob die Kugeln immer noch in der simPane sind
        
            k.xKugelProp().addListener(new ChangeListener(){
                @Override 
                public void changed(ObservableValue o,Object oldVal, Object newVal){
                     if((Double)newVal > simPane.getWidth()){
                          gameOverMessage();
                     }
                     if((Double)newVal < 0){
                          gameOverMessage();
                     }
                }
            });
            
            k.yKugelProp().addListener(new ChangeListener(){
                @Override 
                public void changed(ObservableValue o,Object oldVal, Object newVal){
                     if((Double)newVal > simPane.getHeight()){
                        gameOverMessage();
                     }
                     if((Double)newVal < 0){
                          gameOverMessage();
                     }
                }
            });
     
        }

//~~~~~~~~~~~~~~~Drag&Drop~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~  
        
    int x1 = 50, x2 = 150, y1 = 150, y2 = 50;    
                    
    merkerT = bahnen.size();
    Bahn bahnT = new Bahn(x1, y1, x2, y2, true, false);
    bahnT.setStrokeWidth(5.0);
    

    strLabel = "" + maxT;
    lBahn.setText(strLabel);
    
    
    elementsBox.getChildren().addAll(lBahn, bahnT);
     
            
    if(maxT > 0)
    {
    elementsBox.setOnDragDetected(e -> {
            Dragboard db = bahnen.get(merkerT).startDragAndDrop(TransferMode.COPY);
            db.setDragView(bahnen.get(merkerT).snapshot(null, null), e.getX(), e.getY());
            ClipboardContent cc = new ClipboardContent();
            cc.putString("Line");
            db.setContent(cc);
        });
    
    
    simPane.setOnDragOver(e -> {
            e.acceptTransferModes(TransferMode.COPY);
        });
    
        simPane.setOnDragDropped(e -> {
            Dragboard db = e.getDragboard();
            if (db.hasString()) 
            {                
                int endX = (int)e.getX() + (int)bahnen.get(merkerT).getDeltaX();
                int endY = (int)e.getY() + (int)bahnen.get(merkerT).getDeltaY();
                
                Bahn b1 = new Bahn((int)e.getX(),(int)e.getY(), endX,endY,true,false);
                b1.setStrokeWidth(5.0);
                
            b1.werteBerechnen();
                
            simGroup.getChildren().add(b1);
            simPane.getChildren().add(b1); 
                        
            
            maxT--;
            if(maxT == 0)elementsBox.getChildren().remove(bahnen.get(merkerT));
            strLabel = "" + maxT;
            lBahn.setText(strLabel);
            
for(int j = 0; j < bahnen.size(); j++)
       {                 
            Bahn b = bahnen.get(j);

           if(b.getCanBeDraged())
           {
            drag.dragBahn(b);
           }
          //Licht An Effekt 
         
          if(sim.getAn()==true ){
             System.out.println("k.getKollision()k.getKollision()k.getKollision()k.getKollision()" + sim.getAn()); 
         
             simPane.getStyleClass().add("light");
             simPane.setId("sceneLight");
             gridPane.setId("mainPaneLight");
                   
       }  
       } 
            
             b1.getSelectedBahnProp().addListener(new ChangeListener<Boolean>() {
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
          //Licht An Effekt 
         
          if(sim.getAn()==true ){
             System.out.println("k.getKollision()k.getKollision()k.getKollision()k.getKollision()" + sim.getAn()); 
         
             simPane.getStyleClass().add("light");
             simPane.setId("sceneLight");
             gridPane.setId("mainPaneLight");
                   
       }  
       }  
        //Drag And Drop END
       
 
    simPane.getChildren().add(simGroup);
    
    
    }
 }          
           
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
     
    
    public void deleteContent(){
        bahnen.clear();
        kugeln.clear();
      
    }


    @Override
    public void initialize(URL url, ResourceBundle rb) 
    {       
        gridPane.layoutBoundsProperty().addListener(new ChangeListener<Bounds>(){
          @Override
          public void changed(ObservableValue<? extends Bounds> observable,
              Bounds oldValue, Bounds newValue) {
                gridB = newValue;
                try{
                    setTheSizes();
                }catch(Exception e){
                    System.out.println("Preferierte Größen können nicht gesetzt werden in der SimPane");
                }        
       }
        });  
        
         simGroup.layoutBoundsProperty().addListener(new ChangeListener<Bounds>(){
            @Override
            public void changed(ObservableValue<? extends Bounds> obser,
                Bounds oldV , Bounds newV) {
                simB = newV;
                try{
                    setTheSizes();
                }catch(Exception e){
                    System.out.println("Preferierte Größen können nicht gesetzt werden in der SimPane");
                }
          }
        });
             
       load();
       
    }      
    
    public void setTheSizes() throws Exception{
        
        simPane.setPrefWidth(gridB.getWidth()*PERCENT_WIDTH_SIM);
        simPane.setPrefHeight(gridB.getHeight()*PERCENT_HEIGHT);
        
        controllPane.setPrefSize(gridB.getWidth()*PERCENT_WIDTH_CON, gridB.getHeight()*PERCENT_HEIGHT);
    }
    public void next()  throws IOException{
  
      Stage dialog=new Stage();
//     Pane root = FXMLLoader.load(getClass().getResource("/gui/FXMLMessage.fxml"));
//      
//        
//    
//                                                                                                                                      //Entweder das hier (798-809) oder (813-844) beides funktioniert 
//        
//        Scene scene = new Scene(root, 500, 300);
//        scene.getStylesheets().addAll(this.getClass().getResource("/gui/style.css").toExternalForm());
//        dialog.setTitle("Lights On");
//        dialog.getIcons().add(new Image("/gui/Pics/Favicon.png"));
//        dialog.setScene(scene);
//        dialog.show();


                dialog.initStyle(StageStyle.UTILITY);
                dialog.initModality(Modality.WINDOW_MODAL);
                dialog.initOwner(Main.stage);
                dialog.getIcons().add(new Image("/gui/Pics/Favicon.png"));
                dialog.setTitle("Level 1 completed");
                
                
                VBox dialogVbox = new VBox(20);
                dialogVbox.setAlignment(Pos.CENTER);
                
               Label text = new Label(" LEVEL  COMPLETED");
               text.setScaleX(2);
               text.setScaleY(1.5);
               
               
                Button button=new Button("Next Level ");
                button.setOnAction((new EventHandler<ActionEvent>()  {
                    
                @Override 
                public void handle(ActionEvent e) {
                 deleteContent();
                 Scene scene = gridPane.getScene();
                    try {
                        scene.setRoot(FXMLLoader.load(getClass().getResource("/gui/FXMLGameLevel2.fxml")));
                    } catch (IOException ex) {
                        Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                   dialog.close();
                   }
                }));
                
                Button button1=new Button("Repeat");
                 button1.setOnAction((new EventHandler<ActionEvent>()  {
                @Override 
                public void handle(ActionEvent e) {
                    gameOver();
                    dialog.close();
                }
                }));
                 
                dialogVbox.getChildren().add(text);
                dialogVbox.getChildren().addAll(button,button1);
                
                Scene dialogScene = new Scene(dialogVbox, 500, 300);
                dialogScene.getStylesheets().addAll(this.getClass().getResource("/gui/style_light.css").toExternalForm());
                dialogVbox.getStyleClass().add("konfetti");
                dialogVbox.setId("konfetti");
                dialog.setScene(dialogScene);
                dialog.isAlwaysOnTop();
                
                dialog.showingProperty().asObject();
                dialog.show();
    }
    
    public void gameOver(){
        tl.stop();
        deleteContent();
        Scene scene = gridPane.getScene();
         try {
             scene.setRoot(FXMLLoader.load(getClass().getResource("/gui/FXMLGame.fxml")));
         } catch (IOException ex) {
             Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
         }
    }
    
    public void gameOverMessage(){
        tl.stop();
         Stage gameOver=new Stage();
         gameOver.initStyle(StageStyle.UTILITY);
                gameOver.initModality(Modality.WINDOW_MODAL);
                gameOver.initOwner(Main.stage);
                gameOver.getIcons().add(new Image("/gui/Pics/Favicon.png"));
                gameOver.setTitle("Game over...");
                
                Label lb = new Label(" GAME  OVER ");
               lb.setScaleX(2);
               lb.setScaleY(1.5);
               
               Button repeat_btn=new Button(" Repeat ");
                 repeat_btn.setOnAction((new EventHandler<ActionEvent>()  {
                @Override 
                public void handle(ActionEvent e) {
                      System.out.println("Game Over");
        deleteContent();
        Scene scene = gridPane.getScene();
         try {
             scene.setRoot(FXMLLoader.load(getClass().getResource("/gui/FXMLGame.fxml")));
         } catch (IOException ex) {
             Logger.getLogger(FXMLGameController.class.getName()).log(Level.SEVERE, null, ex);
         }
                    gameOver.close();
                }
                }));
                 
                VBox dialogVbox = new VBox(20);
                dialogVbox.setAlignment(Pos.CENTER);
                dialogVbox.getChildren().add(lb);
                dialogVbox.getChildren().addAll(repeat_btn);
                
                Scene dialogScene = new Scene(dialogVbox, 500, 300);
                dialogScene.getStylesheets().addAll(this.getClass().getResource("/gui/style_light.css").toExternalForm());
                gameOver.setScene(dialogScene);
                gameOver.show();
        }
    
}

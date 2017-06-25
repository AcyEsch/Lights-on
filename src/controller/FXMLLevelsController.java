package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.SplitPane;
import javafx.scene.effect.Light;
import javafx.scene.effect.Lighting;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import logic.Main;

/**
 * FXML Controller class
 *
 * 
 */
public class FXMLLevelsController implements Initializable {

    @FXML
    private GridPane rootPane;
    @FXML
    private Button level1, level2, level3, level4, level5, level6, newLevel;
    @FXML
    private Label uberschrift;
   
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        //Effekt der Überschrift und Buttons  
        Light.Distant light = new Light.Distant() ;
        light.setAzimuth(271.05) ; 
        light.setElevation(53.37) ; 
        light.setColor(Color.web("#ffffff"));
        Lighting lighting = new Lighting() ; 
        lighting.setLight(light) ;  
        uberschrift.setEffect(lighting);  
        level1.setEffect(lighting);
        level2.setEffect(lighting);
        level3.setEffect(lighting);
        level4.setEffect(lighting);
        level5.setEffect(lighting);
        level6.setEffect(lighting);
        newLevel.setEffect(lighting);
    }    
    
    @FXML
    private void level1(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLGame.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void level2(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLGameLevel2.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void level3(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLGameLevel3.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    private void level4(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLGameLevel4.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    private void newLevel(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLNewLevel.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}

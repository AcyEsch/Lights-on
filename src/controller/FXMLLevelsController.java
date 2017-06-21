package controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.SplitPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

/**
 * FXML Controller class
 *
 * 
 */
public class FXMLLevelsController implements Initializable {

    @FXML
    private AnchorPane rootPane;
   
    
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
    @FXML
    private void level1(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLGame.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    
    @FXML
    private void level2(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLGame.fxml"));
        rootPane.getChildren().setAll(pane);
        
      
    }
    
    @FXML
    private void level3(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLGame.fxml"));
        rootPane.getChildren().setAll(pane);
    }
    @FXML
    private void newLevel(ActionEvent event) throws IOException{
        GridPane pane = FXMLLoader.load(getClass().getResource("/gui/FXMLNewLevel.fxml"));
        rootPane.getChildren().setAll(pane);
    }
}

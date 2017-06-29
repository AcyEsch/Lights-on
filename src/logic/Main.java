package logic;

import static com.sun.org.apache.xalan.internal.xsltc.compiler.util.Type.Root;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import static javafx.application.Application.launch;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import controller.*;
import javafx.scene.image.Image;


public class Main  extends Application {
    
   public static Stage  stage;
   public static Scene scene;
   public static Parent root;
   
   
    @Override
    
    public void start(Stage stage) throws Exception {
        
        
       Parent root = FXMLLoader.load(getClass().getResource("/gui/LevelsFXML.fxml"));
      
        this.stage=stage;
    
        
        
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/gui/style.css").toExternalForm());
         
        stage.setTitle("Lights On");
        stage.getIcons().add(new Image("/gui/Pics/Favicon.png"));
        stage.setFullScreen(true);
        stage.setResizable(true);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getStage(){
        return stage;
    }

  
    
}
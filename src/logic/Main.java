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


public class Main  extends Application {
    
   public static Stage  stage;
   public static Scene scene;
   public static Parent root;
   
   
    @Override
    
    public void start(Stage stage) throws Exception {
        
        
       Parent root = FXMLLoader.load(getClass().getResource("/gui/LevelsFXML.fxml"));
      
        
    
        
        
        Scene scene = new Scene(root);
        scene.getStylesheets().addAll(this.getClass().getResource("/gui/style.css").toExternalForm());
         
       
         
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }


  
    
}
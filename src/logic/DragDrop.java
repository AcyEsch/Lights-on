package logic;
import javafx.scene.Cursor;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.paint.Color;

public class DragDrop 
{
    private double orgSceneX, orgSceneY;
    private Bahn lastBahn = new Bahn();
    private boolean canDrag = true;
    
    public void dragKugel(Kugel kugel)
    {
        kugel.setCursor(Cursor.HAND);

    kugel.setOnMousePressed((t) -> {
      if(canDrag)
        {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Kugel k = (Kugel) (t.getSource());
      
      k.setCenterX(k.getCenterX() + offsetX);
      k.setCenterY(k.getCenterY() + offsetY);
      
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
        }
    });      
    }
    
    
    
    
    
    
    
    public void dragBahn(Bahn bahn)
    {
      bahn.setCursor(Cursor.HAND);

     
      bahn.setOnMousePressed((t) -> {
      if(canDrag)
        {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

      Bahn b = (Bahn) (t.getSource());
      
      if(lastBahn != b)
      {
        lastBahn.setStroke(Color.BLACK);      
        lastBahn = b;
      }
      
      
      b.toFront();
      
      b.setStroke(Color.GREEN);   
        }         
    });
    
    bahn.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Bahn b = (Bahn) (t.getSource());

     b.setStartX(b.getStartX() + offsetX);
     b.setStartY(b.getStartY() + offsetY);
     b.setEndX(b.getEndX() + offsetX);
     b.setEndY(b.getEndY() + offsetY);
     
     b.werteBerechnen();
    
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();              
    });           
    }
    public Bahn getBahn()
    {
        return lastBahn;
    }   
    
    public void setCanDrag(boolean cd)
    {
        canDrag = cd;
    }
}


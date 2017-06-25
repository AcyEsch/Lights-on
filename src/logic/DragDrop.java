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

   
        //~~~~New~~~~22.06.2017~~~~~

    kugel.setOnMousePressed((t) -> {
        if(canDrag)
        {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

      Kugel k = (Kugel) (t.getSource());
      k.toFront();
        }
    });
        
        kugel.setOnMouseDragged((t) -> {
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
       //~~~~~End~~~~~
    }
    
    
    
    
    
    
    
    public void dragBahn(Bahn bahn)
    {
      bahn.setCursor(Cursor.HAND);

     bahn.setOnDragDetected((e -> {
      // bahn.setOnMousePressed((t) -> {
      if(canDrag)
        {
      orgSceneX = e.getSceneX();
      orgSceneY = e.getSceneY();

      Bahn b = (Bahn) (e.getSource());
      
      if(lastBahn != b)
      {
        lastBahn.setStroke(Color.BLACK);      
        lastBahn = b;
      }
      
      
      b.toFront();
      
      b.setStroke(Color.GREEN);   
        } 
      
      
      Dragboard db = bahn.startDragAndDrop(TransferMode.COPY);
            db.setDragView(new Bahn().snapshot(null, null), e.getX(), e.getY());
            ClipboardContent cc = new ClipboardContent();
            cc.getImage();
            db.setContent(cc);
           
     }));
    
     
    bahn.setOnDragDone(e -> {
        Dragboard db = e.getDragboard();
      double offsetX = e.getSceneX() - orgSceneX;
      double offsetY = e.getSceneY() - orgSceneY;

      Bahn b = (Bahn) (e.getSource());

     b.setStartX(b.getStartX() + offsetX);
     b.setStartY(b.getStartY() + offsetY);
     b.setEndX(b.getEndX() + offsetX);
     b.setEndY(b.getEndY() + offsetY);
     
     b.werteBerechnen();
    
      orgSceneX = e.getSceneX();
      orgSceneY = e.getSceneY();              
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


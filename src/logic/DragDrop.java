package logic;
import javafx.scene.Cursor;

public class DragDrop 
{
    private double orgSceneX, orgSceneY;
       /*
       //DragAndDrop
     kugel.setCursor(Cursor.HAND);
    kugel.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
      Kugel k = (Kugel) (t.getSource());
      k.toFront();
    });
    kugel.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;
      Kugel k = (Kugel) (t.getSource());
      k.setCenterX(k.getCenterX() + offsetX);
      k.setCenterY(k.getCenterY() + offsetY);
      
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();      
    });
       
    bahn.setCursor(Cursor.HAND);
    bahn.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();
      Bahn b = (Bahn) (t.getSource());
      b.toFront();
    });
    bahn.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;
      Bahn b = (Bahn) (t.getSource());
      //b.setCenterX(b.getCenterX() + offsetX);
     // b.setCenterY(b.getCenterY() + offsetY);
     b.setStartX(b.getStartX() + offsetX);
     b.setStartY(b.getStartY() + offsetY);
     b.setEndX(b.getEndX() + offsetX);
     b.setEndY(b.getEndY() + offsetY);
     
     b.werteBerechnen();
     
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();      
    });   
      //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~ 
    */
    
    public void dragKugel(Kugel kugel)
    {
        kugel.setCursor(Cursor.HAND);

    kugel.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

      Kugel k = (Kugel) (t.getSource());
      k.toFront();
    });
    kugel.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Kugel k = (Kugel) (t.getSource());

      k.setCenterX(k.getCenterX() + offsetX);
      k.setCenterY(k.getCenterY() + offsetY);

      
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();      
    });
    }
    
    public void dragBahn(Bahn bahn)
    {
        bahn.setCursor(Cursor.HAND);

    bahn.setOnMousePressed((t) -> {
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();

      Bahn b = (Bahn) (t.getSource());
      b.toFront();
    });
    bahn.setOnMouseDragged((t) -> {
      double offsetX = t.getSceneX() - orgSceneX;
      double offsetY = t.getSceneY() - orgSceneY;

      Bahn b = (Bahn) (t.getSource());

      //b.setCenterX(b.getCenterX() + offsetX);
     // b.setCenterY(b.getCenterY() + offsetY);

     b.setStartX(b.getStartX() + offsetX);
     b.setStartY(b.getStartY() + offsetY);
     b.setEndX(b.getEndX() + offsetX);
     b.setEndY(b.getEndY() + offsetY);
     
     b.werteBerechnen();
     
      orgSceneX = t.getSceneX();
      orgSceneY = t.getSceneY();      
    });   
    }
    
}

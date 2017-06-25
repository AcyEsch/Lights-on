/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logic;

import java.util.ArrayList;
import javafx.scene.paint.Color;


/**
 *
 * @author Lerraxie
 */
public class Schalter extends Bahn {
    
private static  ArrayList<Schalter> schalter = new ArrayList<Schalter>();




    public static ArrayList<Schalter> getSchalter() {
       return schalter;
    }

    private  double X1Position;
    private  double Y1Position;
    private  double X2Position;
    private  double Y2Position;
    
  public Schalter(double X1Position, double Y1Position, double  X2Position, double  Y2Position){
      super();
      super.setStartX(X1Position);
        super.setStartY(Y1Position);
        super.setEndX(X2Position);
        super.setEndY(Y2Position);
      super.setStrokeWidth(26.0);
  //   super.setFill(Color.YELLOWGREEN);
  this.setFill(Color.CADETBLUE);
 schalter.add(this);
  }

   
  
  

}

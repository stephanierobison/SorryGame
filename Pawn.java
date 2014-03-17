import java.awt.*;


/**
   A Pawn is a simple token which contains
   a reference to a Space
*/

public class Pawn extends Token{

   public Pawn(){
      iAmOn = null;
   }
   
   public Pawn(Space s){
      iAmOn = s;
   }


   void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh, Color c, double scale){
      double xModifier = ((xHigh - xLow) - scale * (xHigh - xLow)) / 2;
      double yModifier = ((yHigh - yLow) - scale * (yHigh - yLow)) / 2;
      drawMe(g, (int)Math.floor(xLow + xModifier), (int)Math.floor(yLow + yModifier), 
                (int)Math.ceil(xHigh - xModifier), (int)Math.ceil(yHigh - yModifier), c);
            
   }
   
   void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh, Color c){
      g.setColor(c);
      g.fillArc(xLow, yLow,
               xHigh - xLow, yHigh - yLow,
               0, 360);
   }

}// end of Pawn
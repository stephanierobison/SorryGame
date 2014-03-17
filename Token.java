import java.awt.*;

/**
   The token interface is intended to be a root level interface 
   which encompasses all of the little fiddly bits that can be
   placed somewhere on a gameboard: pawns, counters, markers, etc.
   
   At this highest level of abstraction no actual attributes or
   methods are assumed to be common to all tokens. Token is
   essentially a data type.
*/
public abstract class Token{
   protected Space iAmOn;
   
   public Token(){
      iAmOn = null;
   }
   
   public Token(Space s){
      iAmOn = s;
   }
   
   public void moveTo(Space s){
      iAmOn = s;
   }
   
   public void decouple(){
      iAmOn = null;
   }
   
   public Space whereAmI(){
      return iAmOn;
   }
   
   abstract void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh, Color c, double scale);
   abstract void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh, Color c);

}
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
//-------------------------------------------------------------------------
   
   public Token(){
      iAmOn = null;
   }
//*************************************************************************
// BASIC SETTERS/GETTERS
//************************************************************************* 
   public Token(Space s){
      iAmOn = s;
   }
//-------------------------------------------------------------------------
   public void moveTo(Space s){
      iAmOn = s;
   }
//-------------------------------------------------------------------------
   /** Removes the Token from it's Space if necessary */
   public void decouple(){
      iAmOn = null;
   }
//-------------------------------------------------------------------------
   /** @return  The Space the Token is on */
   public Space whereAmI(){
      return iAmOn;
   }
//*************************************************************************
// ABSTRACT METHODS
//*************************************************************************
/** Draw the token in the indicated region scaled down by the indicated amount */
   abstract void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh, double scale);
//-------------------------------------------------------------------------
   /** Overloaded drawMe assuming no scaling */
   abstract void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh);
//-------------------------------------------------------------------------

}// end of Token
import java.awt.*;

/**
   A Pawn is a simple extension of Token which contains
   a reference to a home Space and a Color.
   This gives Pawns a Starting place and a
   potential owning Player.
*/

public class Pawn extends Token{
//*************************************************************************
// INSTANCE VARIABLES
//*************************************************************************
   /** The Pawn's color - used to indicate ownership and safe zones */
   private Color color;
   /** Where the Pawn starts out and where it returns when bumped */
   private Space startSpace;

//*************************************************************************
// CONSTRUCTORS
//*************************************************************************
   /** A simple constructor which simply assigns the pawn's attributes */
   public Pawn(Space s, Color c){
      color = c;
      iAmOn = null;
      startSpace = s;
      moveTo(s);
   }
//*************************************************************************
// METHODS
//*************************************************************************   
   /** 
      Uses the overloaded draw method to draw the scaled Pawn by scaling the 
      draw limits prior to the call
   */
   public void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh, double scale){
      double xModifier = ((xHigh - xLow) - scale * (xHigh - xLow)) / 2;
      double yModifier = ((yHigh - yLow) - scale * (yHigh - yLow)) / 2;
      drawMe(g, (int)Math.floor(xLow + xModifier), (int)Math.floor(yLow + yModifier), 
                (int)Math.ceil(xHigh - xModifier), (int)Math.ceil(yHigh - yModifier));
            
   }
//-------------------------------------------------------------------------
   /**
      Draws the Pawn as a circle in the indicated range.
   */
   public void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh){
      g.setColor(color);
      g.fillArc(xLow, yLow,
               xHigh - xLow, yHigh - yLow,
               0, 360);
      Graphics2D g2 = (Graphics2D) g;
      g2.setStroke(new BasicStroke(1));
      g2.setColor(color.darker().darker());
      g2.drawArc(xLow, yLow,
               xHigh - xLow, yHigh - yLow,
               0, 360);
   }
//-------------------------------------------------------------------------
   /** Moves the Pawn to its starting place */
   public void goHome(){
      moveTo(startSpace);
   }
   
//-------------------------------------------------------------------------
   /** Moves the Pawn to the indicated space */
   public void moveTo(Space s){
      Space whereIWas = iAmOn;
      if (whereIWas != null)     // A post three man puzzle
         whereIWas.clearToken();
      s.setToken(this);
      iAmOn = s;
   }
//-------------------------------------------------------------------------
   /**
      A simple 3 step swap of references to swap the posiions of two pawns.
   */
   public void swapWith(Pawn p){
      Space tempHolder = whereAmI();
      moveTo(p.whereAmI());
      p.moveTo(tempHolder);
   }
//-------------------------------------------------------------------------
  
   /** @return the String representation of the Pawn's Color */
   public String toString(){
      return color.toString();
   }

//*************************************************************************
// BASIC GETTERS
//************************************************************************* 
   public Color getColor(){
      return color;
   }
   
//-------------------------------------------------------------------------
   public Space getHomeSpace(){
      return startSpace;
   }

}// end of Pawn
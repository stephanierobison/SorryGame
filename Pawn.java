import java.awt.*;


/**
   A Pawn is a simple token which contains
   a reference to a Space
*/

public class Pawn extends Token{

   private Color color;
   private Space startSpace;

   /*public Pawn(Color c){
      iAmOn = null;
      color = c;
   }*/
   
   public Pawn(Space s, Color c){
      color = c;
      iAmOn = null;
      startSpace = s;
      moveTo(s);
   }

   public void drawMe(Graphics g, int xLow, int yLow, int xHigh, int yHigh, double scale){
      double xModifier = ((xHigh - xLow) - scale * (xHigh - xLow)) / 2;
      double yModifier = ((yHigh - yLow) - scale * (yHigh - yLow)) / 2;
      drawMe(g, (int)Math.floor(xLow + xModifier), (int)Math.floor(yLow + yModifier), 
                (int)Math.ceil(xHigh - xModifier), (int)Math.ceil(yHigh - yModifier));
            
   }
   
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
   
   public Color getColor(){
      return color;
   }
   
   public Space getHomeSpace(){
      return startSpace;
   }
   
   public void goHome(){
      moveTo(startSpace);
   }
   
  
   public void moveTo(Space s){
      Space whereIWas = iAmOn;
      if (whereIWas != null)
         whereIWas.clearToken();
      s.setToken(this);
      iAmOn = s;
   }
   
   
   public void swapWith(Pawn p){
      Space tempHolder = whereAmI();
      moveTo(p.whereAmI());
      p.moveTo(tempHolder);
   }
  
   public String toString(){
      return color.toString();
   }

}// end of Pawn
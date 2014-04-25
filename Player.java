// Matt hally
import java.awt.*;
/**
   A Player is described via a Color, a name, a portrait, and an AI flag
   indicating computer control.
*/
public class Player{
//*************************************************************************
// INSTANCE VARIABLES
//*************************************************************************   
   /** Player's unique color */
   private Color color;
   /** Player's name - for fun only */
   private String name;
   /** Player's portrait - for fun only */
   private String portrait;
   /** Player AIType - Human is always 0 */
   private int AIType;
//*************************************************************************
// CONSTRUCTORS
//*************************************************************************   
   public Player(Color c, String name, String portrait, int AIType){
      
      color = c;
      this.name = name;
      this.portrait = portrait;
      this.AIType = AIType;
   
   }
//*************************************************************************
// BASIC SETTER AND GETTERS
//*************************************************************************   
   public boolean isHuman(){
      boolean result = true;
      if (AIType != AIRules.HUMAN)
         result = false;
         
      return result;
   }
//-------------------------------------------------------------------------
   public int getAIType(){
      return AIType;
   }
//-------------------------------------------------------------------------
   public Color getColor(){
      return color;   
   }
//-------------------------------------------------------------------------
   
   public String getName(){
      return name;
   }  
//-------------------------------------------------------------------------
   public String getPortrait(){
      return portrait;
   }
//-------------------------------------------------------------------------

}// end of class
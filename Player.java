import java.awt.*;

public class Player{

   private Color color;
   private String name;
   private String portrait;
   private int AIType;
   
   public Player(Color c, String name, String portrait, int AIType){
      
      color = c;
      this.name = name;
      this.portrait = portrait;
      this.AIType = AIType;
   
   }
   
   public boolean isHuman(){
      boolean result = true;
      if (AIType != AIRules.HUMAN)
         result = false;
         
      return result;
   }
   
   public int getAIType(){
      return AIType;
   }
   
   public Color getColor(){
      return color;   
   }
   
   public String getName(){
      return name;
   }  
   
   public String getPortrait(){
      return portrait;
   }

}// end of class
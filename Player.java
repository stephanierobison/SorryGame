import java.awt.*;

public class Player{

   private Color color;
   private String name;
   private String portrait;
   private boolean isHuman;
   
   public Player(Color c, String name, String portrait, boolean human){
      
      color = c;
      this.name = name;
      this.portrait = portrait;
      this.isHuman = human;
   
   }
   
   public boolean isHuman(){
      return isHuman;
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
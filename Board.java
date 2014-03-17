import java.util.*; 

/**
   The board class is a multiply linked list of Space
   objects. The actual objects are stored in an 
   ArrayList to permit immediate references. (Not sure
   how that will work out..)
   
   For now each different configuration will be done 
   manually using a make() method. I hope to later
   change this to:
   1. Read from file 
   2. Use GUI editor
*/

public abstract class Board{
   
   //private ArrayList<Space> spaces;
   protected ArrayList<Space> spaces; // want subclasses to inherit, but no public access
   
   public abstract void make();
   
   
   //recursivly determines all possible forwards moves
   // should probably make iterative somehow for speed..
   public ArrayList<Space> getTargetsForward(Space s, int n){
      ArrayList<Space> result = new ArrayList<Space>();
      if (n == 0){
         if (s.isEmpty()) // can only land on empty spaces
            result.add(s);
      }
      else {
         ArrayList<Space> fn = s.getForwardsNeighbors();
         for (int i = 0; i < fn.size(); i++){
            ArrayList<Space> fn2 = getTargetsForward(fn.get(i), (n - 1) );
            result.addAll(fn2);
         }
      }
         
         
      return result;
   
   }// end of getTargetsF
   
   //recursivly determines all possible BACKWARDS moves
   // should probably make iterative somehow for speed..
   public ArrayList<Space> getTargetsBack(Space s, int n){
      ArrayList<Space> result = new ArrayList<Space>();
      if (n == 0){
         if (s.isEmpty()) // can only land on empty spaces
            result.add(s);
      }
      else {
         ArrayList<Space> fn = s.getBackwardsNeighbors();
         for (int i = 0; i < fn.size(); i++){
            ArrayList<Space> fn2 = getTargetsBack(fn.get(i), (n - 1) );
            result.addAll(fn2);
         }
      }
         
         
      return result;
   
   }// end of getTargetsF
   
   public Space getByIndex(int n){
      return spaces.get(n);
   }
   
   // finds a space by it's XY coordinates
   public Space getSpace(int x, int y){
      Space result = null;
      Space currentSpace;
      // just linear scan spaces for last instance of x, y
      for (int i = 0; i < spaces.size(); i++){
         currentSpace = spaces.get(i);
         if ((currentSpace.getX() == x) && (currentSpace.getY() == y))
            result = currentSpace;   
      }
      
      return result;
   }// end getSpace(int, int)
  
   // find all tokens
   public ArrayList<Token> getTokens(){
      ArrayList<Token> result = new ArrayList<Token>();
      Token currentToken;
      for (int i = 0; i < spaces.size(); i++){
         currentToken = spaces.get(i).getToken();
           if (currentToken != null)
              result.add(currentToken);
      }
      return result;
   }
   
   public String toString(){
      //System.out.println(spaces);
      StringBuilder s = new StringBuilder();
      for (int i = 0; i < spaces.size(); i++){
         s.append(spaces.get(i).toString() + "\n");
      }
      return s.toString();   
   }


}
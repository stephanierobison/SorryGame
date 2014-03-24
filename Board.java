import java.util.*; 

/**
   The board class is a contains a graph of Space
   objects. The actual objects are stored in an 
   ArrayList Adjacency List.
   
   Methods are provided to allow:
   +  Searching the Board for a particular Space
      (by x,y index or adjacency list location)
   +  Get all Token objects "on" Board Spaces
   +  Represent the board as a String
   
   Subclasses must provide provisions to:
   +  Actually build the graph 
   
   Note that the board is intended to be relatively
   static: made once, used, and then disposed of.
   As such no methods have been provided to aid in
   on the fly mutation of the graph.
   
   @see Space

*/

public abstract class Board{

//*************************************************************************
// INSTANCE VARIABLES
//*************************************************************************   
   /**
      The adjacency list describing the graph. Note that the individual
      Space objects own their own neighbor lists.
   */
   protected ArrayList<Space> spaces; // want subclasses to inherit, but no public access
   
//*************************************************************************
// PUBLIC METHODS
//*************************************************************************   
//-------------------------------------------------------------------------

/*   
   For now each different configuration will be hard 
   coded using a make() method. I hope to later
   change this to:
   1. Read from file 
   2. Use GUI editor
*/
   protected abstract void make();

//-------------------------------------------------------------------------
   
   /**
      @param   int   An integer value n
      @return  Space The Space object at index
                     n in the adjacency list.
   */
   public Space getByIndex(int n){
      Space result = null;
      if (n >= spaces.size())
           throw new IndexOutOfBoundsException();
      else
         result = spaces.get(n);
      return result;
   }
 
//-------------------------------------------------------------------------
  
   /**
      @param   int   The x coordinate of the desired Space
      @param   int   The y coordinate of the desired Space
      @return  Space The last Space in the adjacency list
                     with the desired x,y coordinates, NULL
                     otherwise.
      
      Note that this method implicitly assumes that Space
      objects will e given unique x,y coordinates although
      it will function for non-unique coordinates.
   */   
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

//-------------------------------------------------------------------------
  
   /**
      @return  ArrayList<Token>  All of the Token objects "on" the
                                 Space objects in this Board.
   */
   public ArrayList<Token> getTokens(){
      ArrayList<Token> result = new ArrayList<Token>();
      Token currentToken;
      for (int i = 0; i < spaces.size(); i++){     // Step through adjacency list
         currentToken = spaces.get(i).getToken();
           if (currentToken != null)
              result.add(currentToken);
      }
      return result;
   }// end getTokens()

//-------------------------------------------------------------------------
   
   /**   
      @return  String   The String representation of this
                        Board: the String representation of
                        each Space in the adjacency list by
                        index order each on it's own line.
      @see     Space
   */
   public String toString(){
      StringBuilder s = new StringBuilder();
      for (int i = 0; i < spaces.size(); i++){
         s.append(spaces.get(i).toString() + "\n");
      }
      return s.toString();   
   }// end of toString()

//-------------------------------------------------------------------------
}// end of Board
import java.util.*; 
import java.awt.*; 

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
// CONSTANTS
//************************************************************************* 
   public static final int DEFAULT_ROWS = 16;
   public static final int DEFAULT_COLUMNS = 16;
   
   public final int ROWS;// = 16;
   public final int COLUMNS;// = 16;
   public final int N;// = (2 * ROWS) + (2 * COLUMNS) - 4;

//*************************************************************************
// INSTANCE VARIABLES
//*************************************************************************   
   /**
      The adjacency list describing the graph. Note that the individual
      Space objects own their own neighbor lists.
   */
   protected ArrayList<Space> spaces; // want subclasses to inherit, but no public access
   protected Ruleset rules;

//*************************************************************************
// CONSTRUCTORS
//*************************************************************************   
   public Board(){
      this(DEFAULT_ROWS,DEFAULT_COLUMNS);
    }

   public Board(int r, int c){
      ROWS = r;
      COLUMNS = c;
      N = (2 * ROWS) + (2 * COLUMNS) - 4;
      spaces = new ArrayList<Space>();
      make();
    }
   
//*************************************************************************
// METHODS
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
      @param   String   The Tag of a desired Space
      @return  Space    The last Space in the adjacency list
                        with the desired tag, NULL
                        otherwise.
      
      Note that this method implicitly assumes that Space
      objects will e given unique tags.
   */      
      public Space getSpace(String s){
      Space result = null;
      Space currentSpace;
      // just linear scan spaces for last instance of s
      for (int i = 0; i < spaces.size(); i++){
         currentSpace = spaces.get(i);
         if ((currentSpace.getTag().equals(s)))
            result = currentSpace;   
      }
      
      return result;
   }// end getSpace(String)
   
//-------------------------------------------------------------------------   
   /**
      @param   int                A Space attribute
      @return  ArrayList<Space>   An array containing all of the Map Spaces
                                  possesing  the given trait
      

   */     
   public ArrayList<Space> getTypeOfSpaces(int trait){
      ArrayList<Space> result = new ArrayList<Space>();
      Space currentSpace;
      for (int i = 0; i < spaces.size(); i++){  // simple linear scan
        currentSpace = spaces.get(i);
        if (currentSpace.getTrait() == trait)
          result.add(currentSpace);   
        }
      
        return result;
   }
//-------------------------------------------------------------------------   
   /**
      @param   int                A Space attribute
      @param   color                A Space trait color
      @return  ArrayList<Space>   An array containing all of the Map Spaces
                                  possesing  the given trait AND trait color
      

   */     
   public ArrayList<Space> getTypeOfSpaces(int trait, Color c){
      ArrayList<Space> result = new ArrayList<Space>();
      ArrayList<Space> x = getTypeOfSpaces(trait);
      Space currentSpace;
      for (int i = 0; i < x.size(); i++){ /// linear scan again
        currentSpace = x.get(i);
        if (currentSpace.getTraitColor().toString().equals(c.toString()))
          result.add(currentSpace);   
        }
      
        return result; 
      
   }

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
      @return  ArrayList<Pawn>  All of the Pawn objects "on" the
                                 Space objects in this Board.
   */   
   public ArrayList<Pawn> getPawns(){
      ArrayList<Pawn> result = new ArrayList<Pawn>();
      Pawn p;
      ArrayList<Token> t = getTokens();
      for (int i = 0; i < t.size(); i++){
         if (t.get(i) instanceof Pawn){
            p = (Pawn)t.get(i);
            result.add(p);
         }
      }
      return result;
   }
//-------------------------------------------------------------------------   
      /**
         @param   Color             The color of a given player's pawns   
         @return  ArrayList<Pawn>   All of the Pawn objects "on" the
                                    Space objects in this Board who also
                                    have lavender eyes
      */  
      public ArrayList<Pawn> getPawns(Color c){
      ArrayList<Pawn> result = new ArrayList<Pawn>();
      ArrayList<Pawn> ps = getPawns();
      for (int i = 0; i < ps.size(); i++){   // Linear scan
         if (ps.get(i).getColor().toString().equals(c.toString())){
            result.add(ps.get(i));
         }
      }
      return result;
   }

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
   /**
      @return ALL Spaces in the that bold.
   */
   public ArrayList<Space> getAllSpaces(){
      return spaces;
   }
//-------------------------------------------------------------------------   
   /**
      @return All pawns on "unsafe" areas ie Pawns who see their own 
              Color
   */
   public ArrayList<Pawn> getUnsafePawns(){
      ArrayList<Token> x = getTokens();
      ArrayList<Pawn> result = new ArrayList<Pawn>();
      Token currentToken;
      Pawn p;
      for (int i = 0; i < x.size(); i++){ // Still more linear scans
         currentToken = x.get(i);
         if (currentToken instanceof Pawn){
            p = (Pawn)currentToken;
            if (!p.whereAmI().getColor().equals(p.getColor())){
               result.add(p);    
            }
         }
      }
      return result;
   }
//-------------------------------------------------------------------------   
}// end of Board
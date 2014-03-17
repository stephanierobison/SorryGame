import java.util.*; 

/**
   A Space is a single location on a game board.
   It contains references to its neighbor spaces 
   which can either be reached via backwards or 
   forwards moves. It also contains references
   to the Tokens placed on it.
   
   It is intended to be used as the nodes in the
   manually constructed multiply linked list class
   Board. However, it has not been made a private
   class to permit other objects to directly 
   reference individual spaces (such as a Token
   containing a reference to the Space it is on).
   Also, since Board objects are anticipated to
   be fairly static in nature Space's methods
   do not emphasize post hoc mutation.  
   
   
   ???? SPECIAL RULES FOR LANDING ON?????
*/


public class Space{

   public static final String DEFAULT_TAG = "NO TAG";
   public static final int DEFAULT_X = -1;
   public static final int DEFAULT_Y = -2;

//*************************************************************************
//*************************************************************************
// INSTANCE VARIABLES
//*************************************************************************
//*************************************************************************   

   /** An identifying label. Is not checked for
   uniqueness. */
   private String tag;
   
   
   /** Space X coordinate. Optional. */
   private int x;
   /** Space Y coordinate. Optional. */
   private int y;
   
   
   

   /** A list of refences to all spaces which
   can be reached from this one via a single
   forwards move. */
   private ArrayList<Space> forwardsNeighbors;

   /** A list of refences to all spaces which
   can be reached from this one via a single
   backwards move. */
   private ArrayList<Space> backwardsNeighbors;

   /** A list of references to all Tokens 
   which are CURRENTLY occupying this space.
   Note that this set is unconstrained at this
   level of abstraction. If an owner wants to add
   constraints (for example, only one token per
   space) it must do the error checking itself.*/
   //private ArrayList<Token> tokens;
   private Token token;

//*************************************************************************
//*************************************************************************
// CONSTRUCTORS
//*************************************************************************
//*************************************************************************

   /** The default constructor simply creates an empty,
   unconnectd, unlabeled space. */
   public Space(){
      tag = DEFAULT_TAG;   
      x = DEFAULT_X;
      y = DEFAULT_Y;   
      forwardsNeighbors = new ArrayList<Space> ();
      backwardsNeighbors = new ArrayList<Space> ();
//      tokens = new ArrayList<Token> ();
      token = null;
   }
   
   /** A Space can also be initialized with tag; forwards
   and backwards neighbors; x, y cooridinates; and/or tokens defined. */
   public Space(String tag){
      this();
      this.tag = tag;    
   }
   

   public Space(int x, int y){
      this();
      this.x = x;    
      this.y = y;
   }
   

//*************************************************************************
//*************************************************************************
// PUBLIC METHODS
//*************************************************************************
//*************************************************************************
   public void addForwardsNeighbor(Space fn){
      forwardsNeighbors.add(fn);
   }// end addForwardsNeighbor

   public void addBackwardsNeighbor(Space bn){
      backwardsNeighbors.add(bn);
   }// end addBackwardsNeighbor
   
   
   public String getTag(){
      return tag;
   }
//-------------------------------------------------------------------------
// VANILLA GETTERS
//-------------------------------------------------------------------------
   public int getX(){
      return x;
   }

   public int getY(){
      return y;
   }
   
   public Token getToken(){
      return token;
   }
//-------------------------------------------------------------------------
// VANILLA SETTERS
//-------------------------------------------------------------------------
   public void setTag(String tag){
      this.tag = tag;
   }// end setTag
   
   public void setXY(int x, int y){
      this.x = x;    
      this.y = y;
   }// end setXY   
   
   public void setForwardsNeighbors(ArrayList<Space> fn){
      this.forwardsNeighbors = fn;
   }// end setForwardsNeighbors

   public void setBackwardsNeighbors(ArrayList<Space> bn){
      this.backwardsNeighbors = bn;
   }// end setBackwardsNeighbors
   
   public void setToken(Token t){
      token = t;
   }// end addToken
   
   public void clearToken(){
      token = null;
   }
   
   public boolean isEmpty(){
      boolean result = false;
      if (token == null)
         result = true;
      return result;
   }


   public ArrayList<Space> getForwardsNeighbors(){
      return forwardsNeighbors;
   }// end getForwardsNeighbors


   public ArrayList<Space> getBackwardsNeighbors(){
      return backwardsNeighbors;
   }// end getBackwardsNeighbors



   public String toString(){
      StringBuilder s = new StringBuilder();
      s.append("Tag: " + tag);
      s.append("\tXY: " + x + ", " + y);
      s.append("\tFN's: ");
      for (int i = 0; i < forwardsNeighbors.size(); i++){
         s.append(forwardsNeighbors.get(i).getTag() + " ");
      }
      s.append("\tBN's: ");
      for (int i = 0; i < backwardsNeighbors.size(); i++){
         s.append(backwardsNeighbors.get(i).getTag() + " ");
      }
      //s.append("\n");
      return s.toString();      
   }// end toString


}

   /*
   public Space(ArrayList<Space> forwardsNeighbors,
                ArrayList<Space> forwardsNeighbors){
      this();
      this.forwardsNeighbors = forwardsNeighbors();
      this.backwardsNeighbors = backwardsNeighbors();
   }


   public Space(ArrayList<Token> tokens){
      this();
      this.tokens = tokens;
   }
   
   
      public Space(String tokens,
                ArrayList<Space> forwardsNeighbors,
                ArrayList<Space> forwardsNeighbors){
      this();
      this.forwardsNeighbors = forwardsNeighbors();
      this.backwardsNeighbors = backwardsNeighbors();
   }*/
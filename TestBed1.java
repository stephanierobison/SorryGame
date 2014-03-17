
public class TestBed1{

   public static void main(String [ ] args){
      SimpleBoard b = new SimpleBoard();
      Pawn p = new Pawn(b.getByIndex(0));
      
      System.out.println("The Board:");
      System.out.println(b.toString());
      System.out.println("The Pawn is on");
      System.out.println(p.whereAmI().toString());
      System.out.println("TARGET TEST");
      System.out.println(b.getTargets(p.whereAmI(), 2));
   }

}
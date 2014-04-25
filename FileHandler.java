// Matt Hally
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;


//Class FileHandler creates an object which will intereact with the text file
//which contains statistics of the game.  Each instance of FileHandler has the
//ability to interact with the text file by reading from it and writing to it.
//New FileHandler object is created for each file.
//In the text file, statistics are stored in the following manner:
//First line contains the number of turns
//Second line contains the number of bumps
//Third line contains the number of swaps
public class FileHandler{

	static ReadFile reader;
	static WriteFile writer;
	
	
	//Constructor - including the file name
	public FileHandler(String fileName)throws IOException{
	
		reader = new ReadFile(fileName);
		writer = new WriteFile(fileName);
		//writer.makeNewFile();
	
	}
	
	//Increase the number of turns taken by 1
	public static void incTurns()throws IOException{
	
		String[] stats = new String[3];
		stats = reader.getData();
		int turns = Integer.parseInt(stats[0]);
		turns = turns + 1;
		String str = String.valueOf(turns);
		stats[0] = str;
		writer.writeToFile(stats);
	
	}
	
	
	//Increase the number of bumps by 1
	public static void incBumps() throws IOException{
		String[] stats = new String[3];
		stats = reader.getData();
		int bumps = Integer.parseInt(stats[1]);
		bumps = bumps + 1;
		String str = String.valueOf(bumps);
		stats[1] = str;
		writer.writeToFile(stats);
	}
	
	
	//Increase the number of swaps by 1
	public static void incSwaps() throws IOException{
		String[] stats = new String[3];
		stats = reader.getData();
		int swaps = Integer.parseInt(stats[2]);
		swaps = swaps + 1;
		String str = String.valueOf(swaps);
		stats[2] = str;
		writer.writeToFile(stats);
	}
	
	
	//Set each value to 0 in the text file
	public static void clear()throws IOException{
	
		writer.makeNewFile();
	
	}
	
	
	//Return a string stating the number of turns taken, the number of pawns
	//bumped back to start, and the number of swaps.
	public String getStats()throws IOException{
	
		String[] stats = new String[3];
		stats = reader.getData();
		String output;
		output = "There were "+stats[0]+" turns taken, "+stats[1]+" pawns were bumped"+
					" back to start, and "+stats[2]+" swaps made.";
		return output;
		
	}

}


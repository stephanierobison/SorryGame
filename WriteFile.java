
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;


//Object WriteFile creates a text file where game statistics will be stored
public class WriteFile{

	private String path;//the location of the text file
	private boolean appendToPath = false;//if true, text fill will be appended,
													 //if false, file will be replaced with
													 //new statistics

//Constructor - Overwrites the existing file and replaces it will desired content
	public WriteFile(String filePath){
		path = filePath;
	}


	
//Method writeToFile() allows a String to be written in the file,
//which contains statistics
	public void writeToFile(String[] stats) throws IOException{
		
		FileWriter fw = new FileWriter(path, appendToPath);
		PrintWriter pw = new PrintWriter(fw);
		for(int i=0; i<3; i++){
			pw.println(stats[i]);
		}
		pw.close();
		
	}
	
	
//Method makeNewFile sets the String on each line (each representing
//a statistic) to 0
	public void makeNewFile() throws IOException{
		FileWriter fw = new FileWriter(path, appendToPath);
		PrintWriter pw = new PrintWriter(fw);
		for(int i=0; i<3; i++){
			pw.println("0");
		}
		pw.close();
	
	}
	
	
	
}


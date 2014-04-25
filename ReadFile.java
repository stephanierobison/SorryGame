// Matt Hally
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

//Object ReadFile allows the contents of a text file to be read
public class ReadFile{
	
	private String path;//the location of the text file
	
	//Constructor
	public ReadFile(String filePath){
		path = filePath;
	}
	
	//Method getData() returns the contents of the file as a String array, with each
	//index of the array containing a String with the stats.  Each new line of the text
	//file is in a different index of the array.
	//Line 1 contains the number of turns
	//Line 2 contains the number of bumps
	//Line 3 contains the number of swaps
	public String[] getData() throws IOException{
		FileReader reader = new FileReader(path);
		BufferedReader textReader = new BufferedReader(reader);
		int numOfLines = getNumLines();
		String[] data = new String[numOfLines];
		for(int i = 0; i < numOfLines; i++){
			data[i] = textReader.readLine();
		}
		textReader.close();
		return data;
	
	}
	
	//Method getNumLines() counts the number of lines in the text file, and is called 
	//by method getData() above.
	private int getNumLines() throws IOException{
		FileReader file = new FileReader(path);
		BufferedReader bf = new BufferedReader(file);
		String line;
		int numLines = 0;
		while((line = bf.readLine()) != null){
			numLines++;
		}
		bf.close();
		return numLines;
	}

}
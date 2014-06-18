package processing;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

/**
 * 
 * @author vikas
 *This class builds a list of words that
 *will be popped up in  auto-suggest
 */

public class BuildDictionary {
	
	private Vector<String > dictionary = new Vector<String>();

	public  Vector<String > parseFile(File file){
		
		FileReader fr=null;
		BufferedReader br=null;
		try {
			fr = new FileReader(file);
			 br= new BufferedReader(fr);
			String line="";
			while(line!=null){
				 try {
					line= br.readLine();
					dictionary.add(line);
					System.out.println("word added is "+line);
				} catch (IOException e) {
					// TODO Auto-generated catch block
					System.out.println("error reading the file");
					e.printStackTrace();
				}
				
			}
			
		} catch (FileNotFoundException e) {
			System.out.println("File  not found ");
			e.printStackTrace();
		}
		finally{
			if(br!=null){
				try {
					br.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
		
	
		
		
		return dictionary;
		
	}
	
}

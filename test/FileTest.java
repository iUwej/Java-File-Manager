package test;

import java.io.File;
import java.io.IOException;

import com.iUwej.filemanager.FileCopy;
import com.iUwej.filemanager.FileInfo;

public class FileTest {
	
	public static void main(String[] args) throws IOException{
		File source= new File("C:\\Users\\iUwej\\Desktop\\work");
		//File dest= new File("C:\\Users\\iUwej\\Desktop\\work");
		
		//C:\Users\iUwej\Desktop\Push Sample
		
		
		try{
		
		FileInfo.deleteDir(source);
		}
		catch(IOException io){
			io.printStackTrace();
		}
		
	}

}

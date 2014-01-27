package com.iUwej.filemanager;

import java.io.File;
import java.io.IOException;

public class FileInfo {
	
	/**
	 * A static helper method to help you delete a single file. It first check your write permissions before
	 * deleting the file
	 * */
	
	public static void deleteFile(File file) throws IOException{
		if(!file.canWrite())
			throw new IOException("You do not have persmission to modify this file!");
		if(!file.delete())
			throw new IOException("Error while deleting file");
	}
	
	
	/**
	 * A static recursive method to delete a directory and its content
	 * */	
	public static void deleteDir(File file) throws IOException{
		System.out.println("Deleting "+file.getPath()+"...");
		if(!file.isDirectory() || file.listFiles().length==0){
			deleteFile(file);
			return;
		}
		
			
		
		for(File f :file.listFiles()){
			
			
				deleteDir(f);
			
			
		}
		deleteFile(file);
	}

}

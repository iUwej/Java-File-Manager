package com.iUwej.filemanager;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author iUwej
 * 
 * <p>
 * <b>Class Summary-</b>
 * A  class responsible for copying of a file(s) from one specified path to another.
 * It is also responsible for the deleting of files
 * The methods are all static</p>
 * */
public class FileCopy {
	
	/**
	 * Static method for copying a single file from one location to another
	 * To copy files in a directory or nested directories, Use the {@link #copyDir(File, File, int)
	 * } static method 
	 * instead
	 */
	
	public static void copyFile(String sourcePath,String destinationPath,int bufferSize) throws IOException{
		File sourceFile=new File(sourcePath);
		
		//ensure that the source path exists
		if(!sourceFile.exists())
			throw new IOException("File does not exists");
		
		
		//check users permission and ensure can read the file
		if(!sourceFile.canRead())
			throw new IOException("You do not have the read permission for the specified file");
		
		//create a new destination file if the destination file is a directory
		File destFile= new File(destinationPath);
		
		if(destFile.isDirectory())
			destFile= new File(destFile, sourceFile.getName());
		//verify the permissions for the destination file
		if(destFile.exists() && destFile.canWrite())
			throw new IOException("Destination file cannot be written");
		
		FileInputStream fromStream=null;
		FileOutputStream toStream=null;
		
		try{
			fromStream= new FileInputStream(sourceFile);
			toStream= new FileOutputStream(destFile);
			byte[] buffer= new byte[bufferSize];
			// read data into the buffer and then write it out to the destination file
			int bytes_read=0;
			while((bytes_read=fromStream.read(buffer))!=-1)
				toStream.write(buffer,0,bytes_read);
			
		}
		
		finally{
			if(fromStream !=null)
				try{
					fromStream.close();//close the FileInputstream
				}

				catch(IOException e){
					e.printStackTrace();
				}

				if(toStream !=null)
				try{
					toStream.close();//closes the FileOutputstream
				}

				catch(IOException e){
					e.printStackTrace();
				}
		}
		
		
		
		
	}
	
	/**
	 * A static method to a file from one location to another taking source  as a file
	 * and the destination as a file too
	 * @throws IOException 
	 * */
	
	public static void copyFile(File source,File destination,int bufferSize) throws IOException{
		
		if(!source.canRead())
			throw new IOException("You do not have permission to read the file");
		if(!destination.canWrite())
			throw new IOException("You do not have write permission to the destination file");
		FileInputStream instream=null;
		FileOutputStream oustream=null;
		if(destination.isDirectory()){
			destination=new File(destination.getAbsolutePath(),source.getName());
		}
		
		try{
			instream= new FileInputStream(source);
			oustream= new FileOutputStream(destination);
			
			byte[] buffer= new byte[bufferSize];
			int readBytes=0;
			while((readBytes=instream.read(buffer))!=-1)
				oustream.write(buffer, 0, readBytes);
				
			
			
		}
		catch(IOException io){
			throw io;
			
		}
		
		finally{
			if(instream !=null)
				instream.close();
			if(oustream !=null)
				oustream.close();
		}
	}
	
	/**
	 * A static method which recursively copies files from a directory(s) to the destination 
	 * directory. To copy a single file use the {@link #copyFile(String, String, int)} method instead as it ensures that the 
	 * source file is a single file*/
	
	public static void copyDir(File sourceDir,File destDir,int bufferSize) throws IOException{
		
		
		
		//base condition for the recursion
		if(!sourceDir.isDirectory()){
			copyFile(sourceDir, destDir, bufferSize);
			return;
		}
		
		File[] listFiles= sourceDir.listFiles();//enumerate all the files from a directory
		for (File file : listFiles) {
			System.out.println("Processing..."+file.getPath());
			if(!file.isDirectory()){
				File destFile= new File(destDir,file.getName());
				copyFile(file, destFile, bufferSize);
				
			}
			else{
				File newDest=new File(destDir, file.getName());
				if(!newDest.exists())
					newDest.mkdirs();
				copyDir(file,newDest, bufferSize);
				
			}
			
		}
		
		
	}
}
package com.iUwej.filemanager;

import java.io.*;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
/**
 * A class which is responsible for creation of gzip files and the extraction of the gzip files
 * */

public class GZipManager {
	
	/**
	 * A public static method to create a .gzip file format from a single file
	 * */
	
	public static void gzipFile(File source,File dest,int bufferSize) throws IOException{
		FileInputStream in= new FileInputStream(source);
		FileOutputStream out= new FileOutputStream(dest);
		
		GZIPOutputStream gOut=new GZIPOutputStream(out);
		
		byte[] buffer= new byte[bufferSize];
		int readBytes=0;
		while((readBytes=in.read(buffer))!=1)
			gOut.write(buffer, 0, readBytes);
		gOut.close();
		in.close();
		
	}
	
	/**
	 * A static method that extracts a gzip file to an ordinary file
	 * */
	public static void gunzipFile(File source,File dest,int bufferSize) throws IOException{
		FileInputStream in= new FileInputStream(source);
		GZIPInputStream input=new GZIPInputStream(in);
		
		FileOutputStream out= new FileOutputStream(dest);
		
		byte[] buffer= new byte[bufferSize];
		int readBytes=0;
		
		while((readBytes=input.read(buffer))!=-1)
			out.write(buffer, 0, readBytes);
		out.close();
		input.close();
		
		
	}

}

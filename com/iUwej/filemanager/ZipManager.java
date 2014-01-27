package com.iUwej.filemanager;

import java.io.*;
import java.util.Enumeration;
import java.util.zip.*;

/**
 * A class to handle the compression of files to zip files and the extraction of zip files to dirs
 * */

public class ZipManager {
	
	
	/**
	 * Private static method which does the actual creation of the zip file by reading from the 
	 * source. For more information please check the class {@link java.util.zip.ZipOutputStream}*/
	
	private static void zip(File dirFile,File base,ZipOutputStream zos,int bufferSize) throws IOException{
		File[] files=dirFile.listFiles();
		byte[] buffer= new byte[bufferSize];
		int readBytes=0;
		
		for(int i=0;i<files.length;i++){
			if(files[i].isDirectory()){
				zip(files[i], base, zos, bufferSize);
			}
			else{
				FileInputStream in= new FileInputStream(files[i]);
				ZipEntry entry=new ZipEntry(files[i].getPath().substring(base.getPath().length()+1));
				zos.putNextEntry(entry);
				
				while((readBytes=in.read(buffer))!=-1)
					zos.write(buffer,0,readBytes);
				in.close();
			}
				
		}
		
		
	}
	
	/**
	 * The static method which creates a zip from a given directroy. It acts as a client for the private
	 * method {@link #zip(File, File, ZipOutputStream, int)}
	 * */
	
	public static void zipDir(File zip,File destDir,int bufferSize) throws IOException{
		ZipOutputStream zos= new ZipOutputStream(new FileOutputStream(zip));
		zip(destDir,destDir,zos,bufferSize);
		zos.close();
	}
	
	/**
	 * A static method to read the content of a directory and extract them to a named location
	 * */
	
	public static void  unzipDir(File zip,File destDir,int bufferSize) throws ZipException, IOException{
		ZipFile archive= new ZipFile(zip);
		Enumeration<? extends ZipEntry> list=archive.entries();
		int size=archive.size();
		ZipEntry[] entries= new ZipEntry[size];
		
		for(int i=0;i<size;i++)
			entries[i]=list.nextElement();
		
		byte[] buffer= new byte[bufferSize];
		int readBytes=0;
		for(int i=0;i< entries.length;i++){
			File file=new File(destDir,entries[i].getName());
			if(entries[i].isDirectory() && !file.exists())
				file.mkdirs();
			if(!file.getParentFile().exists())
				file.getParentFile().mkdirs();
			else{
				InputStream in= archive.getInputStream(entries[i]);
				FileOutputStream out=new FileOutputStream(file);
				while((readBytes=in.read(buffer)) !=-1)
					out.write(buffer, 0, readBytes);
				in.close();
				out.close();
			}
			
		}
		
		archive.close();
		
	}
	
	

}

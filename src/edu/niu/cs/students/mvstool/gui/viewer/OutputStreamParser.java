package edu.niu.cs.students.mvstool.gui.viewer;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import java.util.Scanner;
import java.util.Iterator;

public class OutputStreamParser implements Iterator {
  
  final static String eof = 
    "!! END OF JES SPOOL FILE !!";

  Scanner sc;
  
  public OutputStreamParser(File inFile)
    throws IOException {
    
    sc = new Scanner(inFile);
  
  }
  
  public boolean hasNext(){
    
    return sc.hasNextLine();
    
  }
  
  public File next(){
    
    File temp;
    FileOutputStream fout;
    String line;
      
    try {
      
      
      temp = File.createTempFile("mvstool","SpoolFile");
      fout = new FileOutputStream(temp);
      
      while(sc.hasNextLine()){
        
        line = sc.nextLine();
        fout.write((line + "\r\n").getBytes());
        
        if(line.startsWith(eof)) break;
        
      }
      
      fout.close();
      
    } catch(Exception e){
      
      System.out.println("Error");
      temp = new File("/");
      
    }
      
    return temp;
    
  }
  
  public void remove(){
    
  }  
  
}
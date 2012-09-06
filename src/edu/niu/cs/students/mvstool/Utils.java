package edu.niu.cs.students.mvstool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public final class Utils {
  
  public static void sleep(int seconds){
    
    try{
      
      Thread.sleep(seconds * 1000);
      
    } catch(Exception ie){
    }
    
  }
  
  public static String loadFileToString(File input){
    
    try {
      
      InputStream in = new FileInputStream(input);
      
      // TODO: Fix this, this is a terrible security and stability
      //       issue, and should be addressed asap!
      byte[] data = new byte[(int)input.length()];
      
      in.read(data, 0, data.length);
      
      in.close();
      
      return new String(data);
      
    } catch(IOException e){
      
      return "Could not load file!";
      
    }
    
  }
  
  public static void copyFile(File src, File dst) throws IOException {
    
    byte[] buffer = new byte[4096];
    
    InputStream in = new FileInputStream(src);
    OutputStream out = new FileOutputStream(dst);
    
    int read = in.read(buffer);
    
    while(read > 0){
      
      out.write(buffer, 0, read);
      read = in.read(buffer);
      
    }
    
    in.close();
    out.close();
    
  }
  
}
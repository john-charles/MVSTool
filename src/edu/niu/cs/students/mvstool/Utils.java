package edu.niu.cs.students.mvstool;
/*********************************************************************************
  *                                                                              *
  * Copyright (c) 2012 John-Charles D. Sokolow                                   *
  *                                                                              *
  * Permission is hereby granted, free of charge, to any person obtaining a      *
  *   copy of this software and associated documentation files (the "Software"), *
  *   to deal in the Software without restriction, including without             *
  *   limitation the rights to use, copy, modify, merge, publish, distribute,    *
  *   sublicense, and/or sell copies of the Software, and to permit persons to   *
  *   whom the Software is furnished to do so, subject to the following          *
  *   conditions:                                                                *
  *                                                                              *
  *     The above copyright notice and this permission notice shall be included  *
  *       in all copies or substantial portions of the Software.                 *
  *                                                                              *
  *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS  *
  *       OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF             *
  *       MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. *
  *       IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY   *
  *       CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,   *
  *       TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE      *
  *       SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.                 *
  *                                                                              *
  * ******************************************************************************/
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
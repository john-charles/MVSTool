package edu.niu.cs.students.mvstool;
/***********************************************************************
 * MVSTool                                                             *
 *                                                                     *
 * Copyright (C) 2012 John-Charles D. Sokolow                          *
 *                                                                     *
 * MVSTool is free software; you can redistribute it and/or modify     *
 * it under the terms of the GNU General Public License as published   *
 * by the Free Software Foundation; either version 2 of the License,   *
 * or (at your option) any later version.                              *
 *                                                                     *
 * MVSTool is distributed in the hope that it will be useful, but    *
 * WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU    *
 * General Public License for more details.                            *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with MVSTool; if not, write to the Free Software              *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 *  
 * USA                                                                 *
 ***********************************************************************/
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import edu.niu.cs.students.netio.LineInput;

/* Catch all class for things that are needed but don't fit anywhere
 * else! */
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
    
    InputStream in = new FileInputStream(src);
    OutputStream out = new FileOutputStream(dst);
    
    copyStream(in, out);
    
    in.close();
    out.close();
    
  }
  
  public static void copyStream(InputStream in, OutputStream out)
    throws IOException {
    
    byte[] buffer = new byte[1024];
    
    int read = in.read(buffer, 0, 1024);
    
    while(read > -1){
      
      out.write(buffer, 0, read);
      read = in.read(buffer, 0, 1024);
      
    }
    
  }
  
  public static void copyLines(LineInput in, OutputStream out)
    throws IOException {
    
    String line = in.recv();
    
    while(line != null){
      
      out.write(line.getBytes(), 0, line.length());
      line = in.recv();
      
    }
    
  }
  
}
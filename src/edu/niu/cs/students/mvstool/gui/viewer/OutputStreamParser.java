package edu.niu.cs.students.mvstool.gui.viewer;
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
package edu.niu.cs.students.mvs;
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

import java.io.IOException;

import java.util.Stack;

import edu.niu.cs.students.netio.LineInput;

public class ASAParser implements LineInput {
  
  LineInput input;
  Stack<String> lineStack;
  
  public ASAParser(LineInput input){
    
    this.input = input;
    lineStack = new Stack<String>();
    
  }
  
  public void close()
    throws IOException {
    
    input.close();
    
  }
  
  private String proc(String line){
    
    char ccChar = line.charAt(0);
    
    line = line.substring(1, line.length());
    
    if(line.endsWith("\r\n")){
      line = line.substring(0, line.length() -2);
    }
    
    if(ccChar == '1'){
      
      lineStack.push(line);
      return "\r\n";
      
    }
    
    if(ccChar == ' '){
      
      return "\r\n" + line;
      
    }
    
    if(ccChar == '0'){
      
      lineStack.push(line);
      lineStack.push("\r\n");
      
      return "\r\n";
      
    }
    
    if(ccChar == '-'){
      
      lineStack.push(line);
      lineStack.push("\r\n");
      lineStack.push("\r\n");
      return "\r\n";
      
    }
    
    if(ccChar == '+'){
      
      return "\r" + line;
      
    }
    
    return "\r\n" + line;
    
  }
  
  public String recv()
    throws IOException {
    
    if(!lineStack.empty()){
      
      return lineStack.pop();
      
    } else {
      
      String line = input.recv();
      
      if(line == null){
        return null;
        
      }
      
      return proc(line);
      
    }
    
  }
  
}


package edu.niu.cs.students.netio;
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
import java.io.InputStream;
import java.io.IOException;

import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.ByteInput;

public class LFLineInput extends ByteInput
  implements LineInput {
  
  public LFLineInput(InputStream in){
    super(in);
  }
  
  public String recv() throws IOException { 
       
    throw new IOException("Note Implemented!");
    /*String response = "";
    int curByte; = getByte();
    if(curByte <= 0){
      return null;
      
    }
    
    while(curByte > -1){
      response = response + (char)curByte;
      
      if(curByte == LF){
        break;
        
      } else {
        
        curByte = getByte();
        
      }
      
    }
    
    return response;
  */  
  }
  
}
    
  
  
  
  
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
import java.io.IOException;
import java.io.InputStream;

import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.ByteInput;

public class CRLFLineInput extends ByteInput
  implements LineInput {
  
  public CRLFLineInput(InputStream in){
    super(in);    
  }
  
  /*******************************************************************************
    * String = recv(); recieves one message from the server, a message is        *
    *                  considered data starting with the FTP response number and *
    *                  ending with CRLF (carriage return, line feed, '\r\n')     *
    *                                                                            *
    * Throws: This method may throw java.io.IOException if the exception is      *
    *         thrown by the underlying socket connection.                        *
    *                                                                            *
    ******************************************************************************/  
  public String recv() throws IOException {
    
    
    
    boolean crFound = false;
    String response = new String();
    
    int curByte = getByte();
    
    if(curByte == -1){
      System.out.println("curByte = " + curByte);
      return null;
    }
    
    while(curByte != -1){
      
      response = response + (char)curByte;
      /* The following is logic to determine if we have successfully
       * encountered a CRLF secquence.
       * 
       * if the current byte is a CR it sets a flag,
       * if the current byte is a LF and the flag is set
       *    we break if the flag is set and the current
       *    byte is not LF then we unset the flag. */
      if(curByte == CR)
        crFound = true; 
      else
        if(curByte == LF && crFound)
        break;
      else
        crFound = false;
      
      curByte = getByte();
      /* This works.... well, sort of, sometimes it returns
       * the null string "", I don't know why that is though! */
    }   
    
    
    return response;
    
  }  
}
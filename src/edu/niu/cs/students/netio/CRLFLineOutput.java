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
import java.io.OutputStream;
import java.io.FileOutputStream;

import edu.niu.cs.students.netio.LineOutput;

public class CRLFLineOutput implements LineOutput {
  
  OutputStream out;
  
  public CRLFLineOutput(OutputStream _out){
    out = _out;
  }
  
  /*******************************************************************************
    *                                                                            *
    * send(String); Sends one message to the server, a message is considered all *
    *               text before the CRLF (carriage return, line feed), note that *
    *               if the message does not end with a CRLF, or if the message   *
    *               ends only with an LF this method will append the proper CRLF *
    *               secquence to the end of the message.                         *
    *                                                                            *
    * Throws: java.io.IOException                                                *
    *                                                                            *
    * ****************************************************************************/  
  public void send(String cmd) throws IOException {
    
    int i;
      
    for(i = 0; i < cmd.length(); i++){
      
      out.write((int)cmd.charAt(i));
      
    }
    
    if(!cmd.endsWith("\r\n")){
      
      out.write((int)'\r');
      out.write((int)'\n');
      
    }
      
  }
  
  public void close() throws IOException {
    
    out.close();
    
  }
  

  
}
  
  
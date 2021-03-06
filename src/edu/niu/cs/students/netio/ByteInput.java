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
 * MVSTool is distributed in the hope that it will be useful, but      *
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

import edu.niu.cs.students.netio.EOFException;

class ByteInput {
  
  /* This is needed later to get around the fact that InputStream.read() returns
   * and int instead of a char, or byte, this is easier than using a typecast!*/
  protected static final byte CR = 13;
  protected static final byte LF = 10;
  
  InputStream input;
  
  public ByteInput(InputStream _input){
    input = _input;
  }
  
  /*******************************************************************************
    * byte getByte(); recieves one byte from the server, may block indefinitely! *
    *                                                                            *
    * Throws: This method may throw java.io.IOException if the the socket throws *
    *         an IOException.                                                    *
    ******************************************************************************/
  protected int getByte() throws IOException, EOFException {
    /* This looks odd, but it is needed. Instead of calling read() and getting
     * and int and supposedly checking for -1 we must do this instead. Because
     * the -1 thing is not working, and fails al the time. Technically this is
     * not the correct way to do it either, but it seems to work. Basically if
     * the read does not read 1 byte, throw an EOFException which is caught and
     * interepreted by the caller as signaling end of file, and handled properly
     * there. */
    byte[] buff = new byte[1];
    
    if(input.read(buff) != 1){
      throw new EOFException();
    }
            
    return buff[0];   
    
  }
  
  public void close() throws IOException {
    input.close();
  }
  
}
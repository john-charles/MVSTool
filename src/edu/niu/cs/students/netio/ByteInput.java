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

class ByteInput {
  
  /* This is needed later to get around the fact that InputStream.read() returns
   * and int instead of a char, or byte, this is easier than using a typecast!*/
  protected static final byte CR = 13;
  protected static final byte LF = 10;
  
  InputStream input;
  
  public ByteInput(InputStream _input){
    input = _input;
  }
  
  private int lastByte = 1;
  
//  protected boolean hasMore(){
//    
//    try {
//      
//      if(input.available() > 0){
//        
//        if(lastByte < 0 || lastByte > 255){
//          return (boolean)false;
//        } else {
//          return (boolean)true;
//        }
//      }
//    } catch(IOException e){
//      e.printStackTrace();
//      //return false;
//    }
//    
//  }
    
    
  
  /*******************************************************************************
    * byte getByte(); recieves one byte from the server, may block indefinitely! *
    *                                                                            *
    * Throws: This method may throw java.io.IOException if the the socket throws *
    *         an IOException.                                                    *
    ******************************************************************************/
  protected int getByte() throws IOException {
    /* WHYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYYY?
     * Why would read possibly return an integer....
     * according to the documentation when the end of
     * a file/end of a socket stream is reached -1 is returned
     * but guess what, read returnes an int, what happens when
     * you add 00000000 00000000 00000000 to the front of 11111111?
     * you get positive 256, not -1.... WTF?
     * I can't believe this is really part of the java standard 
     * library... */
    int read = input.read();
    //System.out.println("available: " + input.available());
    
    if(read < 0 || read > 255){
      System.out.println("EOF allegedly reached, read = " + read);
      System.out.println("input.available() = " + input.available());
      return -1;
    }
    
    return (byte)read;
    
  }
  
  public void close() throws IOException {
    input.close();
  }
  
}
  
  
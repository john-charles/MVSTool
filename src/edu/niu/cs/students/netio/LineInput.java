package edu.niu.cs.students.netio;

import java.io.IOException;
import java.io.InputStream;

import edu.niu.cs.students.netio.LineEnding;

public class LineInput {
  
  /* This is needed later to get around the fact that InputStream.read() returns
   * and int instead of a char, or byte, this is easier than using a typecast!*/
  private static final byte CR = 13;
  private static final byte LF = 10;
  
  InputStream input;
  
  public LineInput(InputStream in, LineEnding ending){
    
    input = in;
  }
  
  /*******************************************************************************
    * byte getByte(); recieves one byte from the server, may block indefinitely! *
    *                                                                            *
    * Throws: This method may throw java.io.IOException if the the socket throws *
    *         an IOException.                                                    *
    ******************************************************************************/
  private byte getByte() throws IOException {
    /* Note this should be re-implemented to use the int InputStream.read() 
     * method, I wrote this before I looked at the InputStream documentation! */
    byte[] bytes = new byte[1];
    
    int read = input.read(bytes, 0, 1);
    
    if(read == 0){
      bytes[0] = -1;
    }
    
    return bytes[0];
    
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
    
    byte curByte = getByte();
    
    while(curByte > -1){
      
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
      
    }   
    
    return response;
    
  }
  
  public void close() throws IOException {
    
    input.close();
    
  }
  
}
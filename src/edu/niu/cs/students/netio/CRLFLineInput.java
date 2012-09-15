package edu.niu.cs.students.netio;

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
    
    byte curByte = getByte();
    
    if(curByte <= 0){
      return null;
    }
    
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
}
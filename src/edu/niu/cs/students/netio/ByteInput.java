package edu.niu.cs.students.netio;

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
  
  /*******************************************************************************
    * byte getByte(); recieves one byte from the server, may block indefinitely! *
    *                                                                            *
    * Throws: This method may throw java.io.IOException if the the socket throws *
    *         an IOException.                                                    *
    ******************************************************************************/
  protected byte getByte() throws IOException {
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
    
    if(read < 0 || read > 255){
      return -1;
    }
    
    return (byte)read;
    
  }
  
  public void close() throws IOException {
    input.close();
  }
  
}
  
  
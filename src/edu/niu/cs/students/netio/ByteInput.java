package edu.niu.cs.students.netio;

import java.io.InputStream;
import java.io.IOException;

class ByteInput {
  
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
    /* Note this should be re-implemented to use the int InputStream.read() 
     * method, I wrote this before I looked at the InputStream documentation! */
    byte[] bytes = new byte[1];
    System.out.println("in ByteInput.getByte()...");
    int read = input.read(bytes, 0, 1);
    
    if(read == 0){
      bytes[0] = -1;
    }
    
    return bytes[0];
    
  }
  
  public void close() throws IOException {
    input.close();
  }
  
}
  
  
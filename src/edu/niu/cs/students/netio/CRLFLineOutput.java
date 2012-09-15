package edu.niu.cs.students.netio;

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
  
  
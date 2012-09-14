package edu.niu.cs.students.netio;

import java.io.IOException;
import java.io.OutputStream;

import edu.niu.cs.students.netio.LineEnding;

public class LineOutput {
  
  OutputStream out;
  
  public LineOutput(OutputStream out, LineEnding ending){
    this.out = out;
  }
  
  public void send(String cmd) throws IOException {
    /* NOTE: Both send and recv shoud really be merged with the version in FTPPasvSock
     * as it stands there are 3 seperate implementations of the same logic, note 
     * FTPPasvSocket is the latest and best implementation, this code should be
     * refactored to use that implemntation*/
    
    
    
      int i;
      
      for(i = 0; i < cmd.length(); i++){
        
        out.write((int)cmd.charAt(i));
        
      }
      
      if(!cmd.endsWith("\r\n")){
        
        this.serversend.write((int)'\r');
        this.serversend.write((int)'\n');
        
      }
      
  }
  
}
  
  
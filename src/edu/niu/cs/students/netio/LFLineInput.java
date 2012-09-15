package edu.niu.cs.students.netio;

import java.io.InputStream;
import java.io.IOException;

import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.ByteInput;

public class LFLineInput extends ByteInput
  implements LineInput {
  
  public LFLineInput(InputStream in){
    super(in);
  }
  
  public String recv() throws IOException {
    
    System.out.println("IN LFLineInput.recv()");
    
    String response = "";
    byte curByte = getByte();
    
    if(curByte == -1){
      return null;
      
    }
    
    while(curByte > -1){
      System.out.println("while.response = " + response);
      response = response + (char)curByte;
      
      if(curByte == (char)'\n'){
        break;
        
      } else {
        
        curByte = getByte();
        
      }
      
    }
    
    
    
    return response;
    
  }
  
}
    
  
  
  
  
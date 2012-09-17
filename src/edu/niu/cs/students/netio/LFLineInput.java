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
       
    String response = "";
    int curByte = getByte();
    if(curByte <= 0){
      return null;
      
    }
    
    while(curByte > -1){
      response = response + (char)curByte;
      
      if(curByte == LF){
        break;
        
      } else {
        
        curByte = getByte();
        
      }
      
    }
    
    return response;
    
  }
  
}
    
  
  
  
  
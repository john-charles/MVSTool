package edu.niu.cs.students.mvs;

import java.io.IOException;

import java.util.Stack;

import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.CRLFLineInput;

public class ASAParser implements LineInput {
  
  LineInput input;
  Stack<String> lineStack;
  
  public ASAParser(LineInput input){
    
    this.input = input;
    lineStack = new Stack<String>();
    
  }
  
  public void close()
    throws IOException {
    
    input.close();
    
  }
  
  private String proc(String line){
    
    char ccChar = line.charAt(0);
    
    line = line.substring(1, line.length());
    
    if(line.endsWith("\r\n")){
      line = line.substring(0, line.length() -2);
    }
    
    if(ccChar == '1'){
      
      lineStack.push(line);
      return "\r\n";
      
    }
    
    if(ccChar == ' '){
      
      return "\r\n" + line;
      
    }
    
    if(ccChar == '0'){
      
      lineStack.push(line);
      lineStack.push("\r\n");
      
      return "\r\n";
      
    }
    
    if(ccChar == '-'){
      
      lineStack.push(line);
      lineStack.push("\r\n");
      lineStack.push("\r\n");
      return "\r\n";
      
    }
    
    if(ccChar == '+'){
      
      return "\r" + line;
      
    }
    
    return "\r\n" + line;
    
  }
  
  public String recv()
    throws IOException {
    
    if(!lineStack.empty()){
      
      return lineStack.pop();
      
    } else {
      
      String line = input.recv();
      
      if(line == null){
        return null;
        
      }
      
      return proc(line);
      
    }
    
  }
  
}


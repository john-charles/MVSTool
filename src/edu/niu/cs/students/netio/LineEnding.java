package edu.niu.cs.students.netio;

public enum LineEnding {
  
  CR("\r"),
  LF("\n"),
  CRLF("\r\n");
  
  private String chars;
  
  private LineEnding(String chars){
    this.chars = chars;
  }
 
    
}
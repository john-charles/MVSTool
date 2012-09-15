package edu.niu.cs.students.netio;

import java.io.IOException;

public interface LineOutput {
  
  public void send(String cmd) throws IOException;  
  public void close() throws IOException;
  
}
  
  
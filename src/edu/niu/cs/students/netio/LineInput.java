package edu.niu.cs.students.netio;

import java.io.IOException;

public interface LineInput {
  
  public String recv() throws IOException;
  public void   close() throws IOException;
  
}
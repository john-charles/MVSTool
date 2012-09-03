package edu.niu.cs.students.mvstool.ftp;

import java.io.OutputStream;

public abstract class FTPOutputStream extends OutputStream {
  
  public abstract void success();
  public abstract void failure(Exception e);
  
}
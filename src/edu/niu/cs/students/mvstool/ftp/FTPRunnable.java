package edu.niu.cs.students.mvstool.ftp;

public abstract class FTPRunnable implements Runnable {
  
  public abstract void success();
  public abstract void failure(Exception e);
  
}
  
  
  
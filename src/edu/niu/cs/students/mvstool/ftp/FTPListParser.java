package edu.niu.cs.students.mvstool.ftp;

public interface FTPListParser {
  
  public void parseLine(String line);
  public void succeeded();
  public void failed(Exception e);
  
}
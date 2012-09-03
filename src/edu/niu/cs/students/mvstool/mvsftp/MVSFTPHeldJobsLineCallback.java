package edu.niu.cs.students.mvstool.mvsftp;

import edu.niu.cs.students.mvstool.ftp.FTPListParser;

public class MVSFTPHeldJobsLineCallback implements FTPListParser {
  
  public void parseLine(String line){
    
    System.out.println(line.trim());
    
  }
  
  public void succeeded(){}
  public void failed(Exception e){}
  
}
package edu.niu.cs.students.mvstool.ftp;

import edu.niu.cs.students.mvstool.ftp.FTPListParser;

class FTPDefaultListParser implements FTPListParser {
  
  public void parseLine(String line){
    
    System.out.println(line.trim());
    
  }
  
  public void succeeded(){
    
    System.out.println("Success!");
    
  }
  
  public void failed(Exception e){
    
    System.out.println("Failed!");
    e.printStackTrace();
    
  }
  
}
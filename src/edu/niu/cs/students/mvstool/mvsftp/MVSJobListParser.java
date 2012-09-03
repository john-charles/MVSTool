package edu.niu.cs.students.mvstool.mvsftp;

import edu.niu.cs.students.mvstool.ftp.FTPListParser;

public abstract class MVSJobListParser implements FTPListParser {
  
  public MVSJobListParser(){
    
  }
    
  public void parseLine(String line){
    
    System.out.println(line);
    
  }
  
  
}
  
  
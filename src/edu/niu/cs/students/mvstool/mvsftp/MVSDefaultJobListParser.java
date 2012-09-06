package edu.niu.cs.students.mvstool.mvsftp;

import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser;

class MVSDefaultJobListParser extends MVSJobListParser {
  
  public void failed(Exception e){
    
    e.printStackTrace();
    
  }
  
  public void succeeded(){
    
    System.out.println("MVS Job listing completed successfully!");
    
  }
  
}
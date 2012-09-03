package edu.niu.cs.students.mvstool.ftp;

import edu.niu.cs.students.mvstool.ftp.FTPOutputStream;

class FTPDefaultOutputStream extends FTPOutputStream {
  
  public void write(int b){
    
    System.out.print((char)b);
    
  }
  
  public void success(){
    
    System.out.println("Download complete!");
    
  }
  
  public void failure(Exception e){
    
    e.printStackTrace();
    
  }
  
}
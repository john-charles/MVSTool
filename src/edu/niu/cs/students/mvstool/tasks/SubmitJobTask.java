package edu.niu.cs.students.mvstool.tasks;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;

import edu.niu.cs.students.ftp.FTPClient;
import edu.niu.cs.students.ftp.FTPException;

import edu.niu.cs.students.task.Task;


public class SubmitJobTask extends Task {
  
  private File file;
  private FTPClient ftp;
  
  public SubmitJobTask(FTPClient ftp, File file){
    
    this.ftp = ftp;
    this.file = file;
    
  }
  
  public void run() throws Exception {
    
    //OutputStream jobStream = ftp.putFile();
    
    
    
  }
  
  public void failure(Exception e){
    
  }
  
  public void success(){
    
  }
  
}
    
    
    
    
  
  
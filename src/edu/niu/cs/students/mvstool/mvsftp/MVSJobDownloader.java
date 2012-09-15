package edu.niu.cs.students.mvstool.mvsftp;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;

import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.LFLineInput;

import edu.niu.cs.students.mvstool.ftp.FTPClient;
import edu.niu.cs.students.mvstool.ftp.FTPRunnable;
  
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

public abstract class MVSJobDownloader extends FTPRunnable {
  
  Job job;
  File tempFile;
  FTPClient client;
  OutputStream out;
  
  public MVSJobDownloader(Job _ob, FTPClient _lient) throws IOException {
    
    job = _ob;
    client = _lient;
    tempFile = File.createTempFile("ftpGet",".dat");
    out = new FileOutputStream(tempFile);
    
  }
  
  public File getTempFile(){
    
    return tempFile;
    
  }
  
  private void write(String data)
    throws IOException {
    
    for(int i = 0; i < data.length(); i++){
      
      out.write((int)data.charAt(i));
      
    }
    
  }
  
  private String processLine(String line){
    
    System.out.println("Processing line: " + line);
    
    return line;
    
  }
  
  public void run(){
    
    System.out.println("IN MVSJobDownloader...");
    
    synchronized(client){
      
      try {
        
        
        LineInput in = new LFLineInput(client.getFile(job.getName(), 'A'));
        String line = in.recv();
        
        while(line != null){
          
          write(processLine(line));
          line = in.recv();
          
        }
        
        in.close();
        out.close();
        
        System.out.println("MVSJobDownloader, succeeding...");
        
        success();
        
      } catch(Exception e){
        
        failure(e);
        
      }
      
    }
    
  }
    
}
        
          
          
        
        
        
      
      
  
  
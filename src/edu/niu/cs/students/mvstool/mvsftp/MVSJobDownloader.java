package edu.niu.cs.students.mvstool.mvsftp;

import java.io.File;
import java.io.InputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;

import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.CRLFLineInput;

import edu.niu.cs.students.mvstool.mvsftp.MVSFTPClient;
import edu.niu.cs.students.mvstool.ftp.FTPRunnable;
  
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

public abstract class MVSJobDownloader extends FTPRunnable {
  
  Job job;
  int page;
  File tempFile;
  MVSFTPClient client;
  OutputStream out;
  
  private static String CRLF = "\r\n";
  
  public MVSJobDownloader(Job _ob, MVSFTPClient _lient) throws IOException {
    
    job = _ob;
    page = 0;
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
  
  private String newPage(){
    
    return CRLF + "------------------------ Page " + (page++) + " -----------------------" + CRLF;
    
  }
  
  private String processLine(String line){
    
    char ccChar = line.charAt(0);
    line = line.substring(1, line.length()).trim();
    
    if(ccChar == '0'){
      line = line + CRLF;
    } else if(ccChar == '-'){
      line = line + CRLF + CRLF;
    } else if(ccChar == '1'){
      line = newPage() + line;
    }
    
    /* This is a little hack to make all lines of jcl lign up correctly... */
    if(line.startsWith("//")){
      line = "  " + line;
    }
    
    /* Hack to deal with XX* lines which seem to come down from ISPF jobs... */
    if(line.startsWith("XX")){
      line = "  " + line;
    }
                
    return line + CRLF;
    
  }
  
  public void run(){
    
    synchronized(client){
      
      try {
        
               
        LineInput in = new CRLFLineInput(client.getJob(job));
        
        String line = in.recv();
                
        while(line != null){
          
          write(processLine(line));
          line = in.recv();
        }
        
        in.close();
        out.close();
        
        success();
        
      } catch(Exception e){
        e.printStackTrace();
        failure(e);
        
      }
      
    }
    
  }
    
}
        
          
          
        
        
        
      
      
  
  
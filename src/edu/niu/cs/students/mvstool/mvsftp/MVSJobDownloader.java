package edu.niu.cs.students.mvstool.mvsftp;
/***********************************************************************
 * MVSTool                                                             *
 *                                                                     *
 * Copyright (C) 2012 John-Charles D. Sokolow                          *
 *                                                                     *
 * MVSTool is free software; you can redistribute it and/or modify     *
 * it under the terms of the GNU General Public License as published   *
 * by the Free Software Foundation; either version 2 of the License,   *
 * or (at your option) any later version.                              *
 *                                                                     *
 * MVSTool is distributed in the hope that it will be useful, but      *
 * WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU    *
 * General Public License for more details.                            *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with MVSTool; if not, write to the Free Software              *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 *  
 * USA                                                                 *
 ***********************************************************************/
import java.io.File;
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
    return CRLF + CRLF  + CRLF;
    //return CRLF + "------------------------ Page " + (page++) + " -----------------------" + CRLF;
    // + "\f"
  }
  
  private String processLine(String line){
    
    if(line.equals("")){
      return line;
    }
        
    line = line.replace("\r", "");
    line = line.replace("\n", "");
    
    
    
    char ccChar = line.charAt(0);
    line = line.substring(1, line.length());
    
    if(ccChar == '0'){
      line = CRLF + CRLF + line;
    } else if(ccChar == '-'){
      line = CRLF + CRLF + CRLF + line;
    } else if(ccChar == '1'){
      line = newPage() + line;
    } else if(ccChar == ' '){
      line = CRLF + line;
    }
    
    /* This is a little hack to make all lines of jcl lign up correctly... */
    if(line.startsWith("//")){
      line = "  " + line;
    }
    
    /* Hack to deal with XX* lines which seem to come down from ISPF jobs... */
    if(line.startsWith("XX")){
      line = "  " + line;
    }
                
    return line;
    
  }
  
  public void run(){
   
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
  
  public static void main(String[] args) throws Exception {
    edu.niu.cs.students.mvstool.gui.MainFrame.main(args);   
  }
    
}
        
          
          
        
        
        
      
      
  
  
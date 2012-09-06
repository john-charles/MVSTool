package edu.niu.cs.students.mvstool.gui;

import java.io.IOException;

import edu.niu.cs.students.mvstool.gui.OutputViewerFrame;
import edu.niu.cs.students.mvstool.ftp.FTPTempFileOutputStream;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

class GUITempFileOutputStream extends FTPTempFileOutputStream {
  
  private Job job;
  
  public GUITempFileOutputStream(Job job) throws IOException {
    super();
    
    this.job = job;
    
  }
  
  public void success(){
    
    OutputViewerFrame.createOutputViewer(getTempFile(), job);
    
  }
  
}
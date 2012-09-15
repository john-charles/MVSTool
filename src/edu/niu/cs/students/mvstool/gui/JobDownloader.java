package edu.niu.cs.students.mvstool.gui;

import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.io.IOException;

import edu.niu.cs.students.mvstool.ftp.FTPClient;
import edu.niu.cs.students.mvstool.gui.OutputViewerFrame;
import edu.niu.cs.students.mvstool.mvsftp.MVSFTPClient;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobDownloader;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

class JobDownloader extends MVSJobDownloader {
  
  Job job;
  JPanel frame;
  
  public JobDownloader(Job _ob, MVSFTPClient _lient, JPanel _rame) throws IOException {
    super(_ob, _lient);
    job = _ob;
    frame = _rame;
  }
  
  
  public void success(){
    
    OutputViewerFrame.createOutputViewer(getTempFile(), job);
    
  }
  
  public void failure(final Exception e){
    
    SwingUtilities.invokeLater(new Runnable(){
      
      public void run(){
        
        JOptionPane.showMessageDialog(frame, e.toString());
        
      }
      
    });
    
  }
  
}
  
  
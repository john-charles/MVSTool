package edu.niu.cs.students.mvstool.gui;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.niu.cs.students.mvstool.gui.GUIJobListPanel;
import edu.niu.cs.students.mvstool.gui.GUIJobListPanel.Updater;

import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser;

/* A swing enabled job list parser */
class GUIJobLister extends MVSJobListParser {
  
  private GUIJobListPanel panel;
  
  public GUIJobLister(GUIJobListPanel panel){
    this.panel = panel;
  }
  
  public void failed(Exception e){
    
    JOptionPane.showMessageDialog(panel, e.getMessage());
    
    e.printStackTrace();
    
  }
  
  /* If the download succeeds this fires an updater into
   * the swing event queue such that swing can happily
   * update the ui with all the new jobs that have been
   * downloaded */
  public void succeeded(){
    
    System.out.println("MVSFTPClient.getJobs() succeeded!");
    
    Updater updater = panel.getUpdater(getJobs());    
    SwingUtilities.invokeLater(updater);
    
  }
  
}
    
    
  
  
  
  
  
  

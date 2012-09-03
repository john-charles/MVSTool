package edu.niu.cs.students.mvstool.gui;

import java.util.Vector;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JScrollPane;
import javax.swing.SwingUtilities;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.border.EmptyBorder;

import javax.swing.table.DefaultTableModel;

import edu.niu.cs.students.mvstool.ConnectionProfile;
import edu.niu.cs.students.mvstool.mvsftp.MVSFTPClient;


class MVSJobList extends JPanel {
  
  private JTable  table;
  private JScrollPane scrollPane;
  
  private static final  Vector<String> colNames = new Vector<String>();
  private Vector<Vector<String>> data;
  
  private DefaultTableModel tableModel;
  
  BlockingQueue<String[]> jobEntries;
   
  public MVSJobList(){
    
    jobEntries = new LinkedBlockingQueue<String[]>();
    
    if(colNames.isEmpty()){
      
      colNames.add("Job ID");
      colNames.add("User ID");
      
    }
    
    data = new Vector<Vector<String>>();
    
    
    
    table = new JTable(data, colNames);
    scrollPane = table.createScrollPaneForTable(table);
    
    tableModel = (DefaultTableModel)table.getModel();
    
    add(scrollPane);
  } 
  
  
  
  private Runnable updateUI = new Runnable(){
    
    public void run(){
      
      String[] job;
      
      if(jobEntries.size() > 0){
        
        job = jobEntries.poll();
        tableModel.addRow(job);
        
      }
      
    }
    
  };
  
  private class OnDoConnect extends Thread {
    
    public void putline(String line){
    
      String[] job = new String[]{ line };   
    
      jobEntries.add(job);
    
      SwingUtilities.invokeLater(updateUI);    
    
    
    }
    
    public void done(){}
    
    public void run(){
      
      ConnectionProfile profile = ConnectionProfile.getConnectionProfile();
      MVSFTPClient ftpClient;
    
      try {
      
        ftpClient = profile.getFTPClient();
      
      } catch(Exception e){
      
        e.printStackTrace();
        return;
      
      }
    
      try {
      
        //ftpClient.getJobs();
      
      } catch(Exception e){
      
        e.printStackTrace();
      
      }
      
    }
    
  };
    
    
        
        
  
  public void doConnect(){
        
    new OnDoConnect().start();  
    
  } 
}
package edu.niu.cs.students.mvstool.gui;

import java.io.File;

import java.util.List;
import java.util.Vector;

import java.awt.BorderLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


import edu.niu.cs.students.mvstool.ConnectionProfile;

import edu.niu.cs.students.mvstool.gui.GUITempFileOutputStream;

import edu.niu.cs.students.mvstool.gui.GUIJobLister;
import edu.niu.cs.students.mvstool.gui.OutputViewerFrame;
import edu.niu.cs.students.mvstool.gui.ImmutableTableModel;

import edu.niu.cs.students.mvstool.mvsftp.MVSFTPClient;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

class GUIJobListPanel extends JPanel {
  
  private JTable  table;
  private JScrollPane scrollPane;
  private ImmutableTableModel tableModel;
  
  public GUIJobListPanel(){
    
    table = new JTable();
    
    scrollPane = new JScrollPane(table); 
    tableModel = new ImmutableTableModel();
    
    table.setModel(tableModel);
    
    table.addMouseListener(new MouseAdapter() {
      
      public void mouseClicked(MouseEvent e) {
      
        if (e.getClickCount() == 2){
            
          int row = ((JTable)e.getSource()).getSelectedRow();
          Job job = tableModel.getJobAt(row);
          
          onRowClicked(job);
          
        }
        
      }
    });

    
    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);
    
  }  
  
  private void onRowClicked(Job job){
    
    try {
      
      MVSFTPClient client = ConnectionProfile.getConnectionProfile().getFTPClient();
      GUITempFileOutputStream output = new GUITempFileOutputStream(job);
      
      client.getJobOutput(job, output);
           
    } catch(Exception e){
      
      JOptionPane.showMessageDialog(this, e.getMessage());
      e.printStackTrace();
      
    }
    
  }
    
  
    
  public class Updater implements Runnable {
    
    private List<Job> jobs;
    
    public Updater(List<Job> jobs){
      this.jobs = jobs;
    }
    
    public void run(){
      
      tableModel.setJobs(jobs);        
      
    }
    
  }
  
  
  public Updater getUpdater(List<Job> jobs){
    
    return new Updater(jobs);
    
  }
  
  public void doConnect(){
    
    try {
      
      MVSFTPClient client = ConnectionProfile.getConnectionProfile().getFTPClient();
      GUIJobLister lister = new GUIJobLister(this);
      
      client.getJobs(lister);      
           
    } catch(Exception e){
      
      JOptionPane.showMessageDialog(this, e.getMessage());
      e.printStackTrace();
      
    }
    
    
    
  }
  
  
}
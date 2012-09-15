package edu.niu.cs.students.mvstool.gui;
/*********************************************************************************
  *                                                                              *
  * Copyright (c) 2012 John-Charles D. Sokolow                                   *
  *                                                                              *
  * Permission is hereby granted, free of charge, to any person obtaining a      *
  *   copy of this software and associated documentation files (the "Software"), *
  *   to deal in the Software without restriction, including without             *
  *   limitation the rights to use, copy, modify, merge, publish, distribute,    *
  *   sublicense, and/or sell copies of the Software, and to permit persons to   *
  *   whom the Software is furnished to do so, subject to the following          *
  *   conditions:                                                                *
  *                                                                              *
  *     The above copyright notice and this permission notice shall be included  *
  *       in all copies or substantial portions of the Software.                 *
  *                                                                              *
  *     THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS  *
  *       OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF             *
  *       MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. *
  *       IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY   *
  *       CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,   *
  *       TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE      *
  *       SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.                 *
  *                                                                              *
  * ******************************************************************************/
import java.util.List;

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
import edu.niu.cs.students.mvstool.gui.ImmutableTableModel;

import edu.niu.cs.students.mvstool.ftp.FTPClient;

import edu.niu.cs.students.mvstool.mvsftp.MVSFTPClient;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobDownloader;
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
      new Thread(new JobDownloader(job, (FTPClient)client, this)).start();
           
    } catch(Exception e){
      
      JOptionPane.showMessageDialog(this, e.getMessage());
      e.printStackTrace();
      
    }
    
  }
    
  
  /* When an mvs held job listing succeeds this 
   * is fired into the event queue and is used to 
   * update the user interface */
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
      
      /* WARNING: This creates a new thread! */
      client.getJobs(lister);      
           
    } catch(Exception e){
      JOptionPane.showMessageDialog(this, e.toString());
      e.printStackTrace();
      
    }
    
    
    
  }
  
  
}
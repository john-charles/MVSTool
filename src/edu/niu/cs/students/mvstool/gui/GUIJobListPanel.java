package edu.niu.cs.students.mvstool.gui;
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
 * MVSTool is distributed in the hope that it will be useful, but    *
 * WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU    *
 * General Public License for more details.                            *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with MVSTool; if not, write to the Free Software              *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 *  
 * USA                                                                 *
 ***********************************************************************/
import java.util.List;

import java.awt.BorderLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;


import edu.niu.cs.students.mvstool.ConnectionProfile;

import edu.niu.cs.students.mvstool.gui.GUIJobLister;
import edu.niu.cs.students.mvstool.gui.JobListPopupMenu;
import edu.niu.cs.students.mvstool.gui.ImmutableTableModel;

//import edu.niu.cs.students.mvstool.ftp.FTPClient;

import edu.niu.cs.students.mvstool.mvsftp.MVSFTPClient;
//import edu.niu.cs.students.mvstool.mvsftp.MVSJobDownloader;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

/****************************************************
  * This class displays the list of downloaded jobs *
  * *************************************************/
class GUIJobListPanel extends JPanel {
  
  private JTable  table;
  private GUIJobListPanel  self;
  private JScrollPane scrollPane;
  private ImmutableTableModel tableModel;
  
  public GUIJobListPanel(){
    
    self = this;
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
      
      public void mousePressed(MouseEvent e){
        
        if(e.isPopupTrigger()){
          rightClick(e);
        }
      }
      
      public void mouseReleased(MouseEvent e){
        
        if(e.isPopupTrigger()){
          rightClick(e);
        }
        
      }
      
      
      private void rightClick(MouseEvent e){
        
        int r = table.rowAtPoint(e.getPoint());        
        table.setRowSelectionInterval(r, r);

        
        JobListPopupMenu menu = new JobListPopupMenu(e, tableModel, self);
        
        menu.show(e.getComponent(), e.getX(), e.getY());
        
      }     
    });
    
    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);
    
  }
  
  public void onRowClicked(Job job){
    
    try {
      
      MVSFTPClient client = ConnectionProfile.getConnectionProfile().getFTPClient();
      new Thread(new JobDownloader(job, client, this)).start();
           
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
  
  public static void main(String[] args){
    edu.niu.cs.students.mvstool.gui.MainFrame.main(args);
  }
  
  
}
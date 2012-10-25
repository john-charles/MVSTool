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
package edu.niu.cs.students.mvstool.gui.main;

import java.awt.BorderLayout;

import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import edu.niu.cs.students.task.Task;



import edu.niu.cs.students.mvstool.gui.main.JobListPopupMenu;
import edu.niu.cs.students.mvstool.gui.main.JobListTableModel;

import edu.niu.cs.students.mvs.MVSJob;

import edu.niu.cs.students.mvstool.tasks.GetJobOutputTask;

/****************************************************
  * This class displays the list of downloaded jobs *
  * *************************************************/
class JobListPanel extends JPanel {
  
  private JTable  table;
  private JobListPanel  self;
  private JScrollPane scrollPane;
  private JobListTableModel tableModel;
  
  public JobListPanel(JobListTableModel model){
    
    this.self = this;
    this.table = new JTable();
    this.tableModel = model;
    
    
    scrollPane = new JScrollPane(table); 
    
    
    table.setModel(tableModel);
    
    table.addMouseListener(new MouseAdapter() {
      
      public void mouseClicked(MouseEvent e) {
      
        if (e.getClickCount() == 2){
            
          int row = ((JTable)e.getSource()).getSelectedRow();
          MVSJob job = tableModel.getJobAt(row);
          
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
    });
    
    setLayout(new BorderLayout());
    add(scrollPane, BorderLayout.CENTER);
    
  }
  
  private void rightClick(MouseEvent e){
        
    int r = table.rowAtPoint(e.getPoint());        
    table.setRowSelectionInterval(r, r);
    
    
    JobListPopupMenu menu = new JobListPopupMenu(e, tableModel, self);    
    menu.show(e.getComponent(), e.getX(), e.getY());
    
  } 
  
  public void onRowClicked(MVSJob job){
    
    try {
      
      Task.fire(new GetJobOutputTask(job));      
           
    } catch(Exception e){
      
      JOptionPane.showMessageDialog(this, e.getMessage());
      e.printStackTrace();
      
    }
    
  }
    
  
  public static void main(String[] args){
    //edu.niu.cs.students.mvstool.gui.MainFrame.main(args);
  }
  
  
}
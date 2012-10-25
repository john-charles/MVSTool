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

import java.util.List;
import java.util.LinkedList;

import javax.swing.JTable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.niu.cs.students.task.Task;

import edu.niu.cs.students.mvs.MVSJob;

import edu.niu.cs.students.mvstool.tasks.PurgeJobTask;
import edu.niu.cs.students.mvstool.tasks.GetJobOutputTask;

import edu.niu.cs.students.mvstool.gui.main.JobListPanel;
import edu.niu.cs.students.mvstool.gui.main.JobListTableModel;

class JobListPopupMenu extends JPopupMenu {
  
  MouseEvent cause;
  JobListPanel p;
  JobListTableModel tableModel;
  
  public JobListPopupMenu(MouseEvent _ause, JobListTableModel _ableModel, JobListPanel p){
    this.p = p;
    cause = _ause;  
    tableModel = _ableModel;
    buildUI();
   
  }
  
  private JMenuItem buildDownload(){
    
    JMenuItem download = new JMenuItem("Download Job");
    
    download.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent e){
        
        int row = ((JTable)cause.getSource()).getSelectedRow();
        MVSJob job = tableModel.getJobAt(row);
          
        Task.fire(new GetJobOutputTask(job));
        
      }
      
    });
    
    
    return download;
    
  }
  
  private JMenuItem buildPurger(){
    
    JMenuItem purge = new JMenuItem("Purge Job Output");
    
    purge.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent e){
        
        int[] rows = ((JTable)cause.getSource()).getSelectedRows();
        List<MVSJob> jobs = new LinkedList<MVSJob>();
        
        for(int i = 0; i < rows.length; i++){
          
          jobs.add(tableModel.getJobAt(rows[i]));
        
        }
        
        Task.fire(new PurgeJobTask(jobs, tableModel));
        
      }
      
    });
    
    return purge;
    
  }
  
  private void buildUI(){
    
    add(buildDownload());
    
    add(buildPurger());
    
  }
  
}
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

import javax.swing.SwingUtilities;

import javax.swing.table.AbstractTableModel;

//import edu.niu.cs.students.mvs.MVSJobListParser;
import edu.niu.cs.students.mvs.MVSJob;

/* This is horriby named, as I didn't fully understand what
 * it was going to be used for, or how it would function
 * when I first created it, contrary to being 
 * immutable it is quite mutable, and can be changed
 * by calling the setJobs method */
public class JobListTableModel extends AbstractTableModel {
  
  private List<MVSJob> jobs;
  
  @Override
  public String getColumnName(int column){
    return MVSJob.names[column];
  }
  
  @Override
  public int getColumnCount(){
    return MVSJob.names.length;
  }
  
  @Override
  public int getRowCount(){
    if(jobs == null) return 0;
    return jobs.size();
  }
  
  @Override
  public Object getValueAt(int row, int col){
    return jobs.get(row).toArray()[col];
  }
  
  /* call this to update the ui when a new listing
   * is recieved from the server */
  private void setJobs(List<MVSJob> jobs){
    
    this.jobs = jobs;
    fireTableDataChanged();
    
  }
  
  public MVSJob getJobAt(int idx){
    return jobs.get(idx);
  }
  
  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
  
  public void update(final List<MVSJob> jobs){
    
    SwingUtilities.invokeLater(new Runnable(){
      
      public void run(){
        
        setJobs(jobs);
        
      }
      
    });
    
  }
    
  
}
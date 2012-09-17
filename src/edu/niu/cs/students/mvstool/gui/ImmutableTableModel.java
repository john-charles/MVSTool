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

import javax.swing.table.AbstractTableModel;

import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

/* This is horriby named, as I didn't fully understand what
 * it was going to be used for, or how it would function
 * when I first created it, contrary to being 
 * immutable it is quite mutable, and can be changed
 * by calling the setJobs method */
class ImmutableTableModel extends AbstractTableModel {
  
  private List<Job> jobs;
  
  @Override
  public String getColumnName(int column){
    return MVSJobListParser.JobNames[column];
  }
  
  @Override
  public int getColumnCount(){
    return MVSJobListParser.JobNames.length;
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
  public void setJobs(List<Job> jobs){
    
    this.jobs = jobs;
    fireTableDataChanged();
    
  }
  
  public Job getJobAt(int idx){
    return jobs.get(idx);
  }
  
  @Override
  public boolean isCellEditable(int row, int column) {
    return false;
  }
  
}
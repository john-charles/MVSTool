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
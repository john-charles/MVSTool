package edu.niu.cs.students.mvstool.gui;

import java.util.List;

import javax.swing.table.AbstractTableModel;

import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

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
package edu.niu.cs.students.mvstool.gui;

import javax.swing.JTable;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

import java.awt.event.MouseEvent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.niu.cs.students.mvstool.gui.GUIJobListPanel;
import edu.niu.cs.students.mvstool.gui.ImmutableTableModel;
//import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

class JobListPopupMenu extends JPopupMenu {
  
  MouseEvent cause;
  GUIJobListPanel p;
  ImmutableTableModel tableModel;
  
  public JobListPopupMenu(MouseEvent _ause, ImmutableTableModel _ableModel, GUIJobListPanel p){
    this.p = p;
    cause = _ause;  
    tableModel = _ableModel;
    buildUI();
    
    /*
    int row = ((JTable)e.getSource()).getSelectedRow();
    Job job = tableModel.getJobAt(row);
    */
  }
  
  private JMenuItem buildDownload(){
    
    JMenuItem download = new JMenuItem("Download Job");
    
    download.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent e){
        
        int row = ((JTable)cause.getSource()).getSelectedRow();
        //MVSJob job = tableModel.getJobAt(row);
          
        //p.onRowClicked(job);
        
      }
      
    });
    
    
    return download;
    
  }
  
  private JMenuItem buildPurger(){
    
    JMenuItem purge = new JMenuItem("Purge Job Output");
    
    return purge;
    
  }
  
  private void buildUI(){
    
    add(buildDownload());
    
    add(buildPurger());
    
  }
  
}
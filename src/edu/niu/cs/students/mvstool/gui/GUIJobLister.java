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
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.niu.cs.students.mvstool.gui.GUIJobListPanel;
import edu.niu.cs.students.mvstool.gui.GUIJobListPanel.Updater;

import edu.niu.cs.students.mvs.MVSJob;

/* A swing enabled job list parser */
//class GUIJobLister extends MVSJobListParser {
//  
//  private GUIJobListPanel panel;
//  
//  public GUIJobLister(GUIJobListPanel panel){
//    this.panel = panel;
//  }
//  
//  public void failed(Exception e){
//    
//    JOptionPane.showMessageDialog(panel, e.getMessage());
//    
//    e.printStackTrace();
//    
//  }
//  
//  /* If the download succeeds this fires an updater into
//   * the swing event queue such that swing can happily
//   * update the ui with all the new jobs that have been
//   * downloaded */
//  public void succeeded(){
//    
//    System.out.println("MVSFTPClient.getJobs() succeeded!");
//    
//    Updater updater = panel.getUpdater(getJobs());    
//    SwingUtilities.invokeLater(updater);
//    
//  }
//  
//}
//    
//    
//  
//  
//  
//  
//  
  

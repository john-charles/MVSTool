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
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import edu.niu.cs.students.mvstool.gui.GUIJobListPanel;
import edu.niu.cs.students.mvstool.gui.GUIJobListPanel.Updater;

import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser;

/* A swing enabled job list parser */
class GUIJobLister extends MVSJobListParser {
  
  private GUIJobListPanel panel;
  
  public GUIJobLister(GUIJobListPanel panel){
    this.panel = panel;
  }
  
  public void failed(Exception e){
    
    JOptionPane.showMessageDialog(panel, e.getMessage());
    
    e.printStackTrace();
    
  }
  
  /* If the download succeeds this fires an updater into
   * the swing event queue such that swing can happily
   * update the ui with all the new jobs that have been
   * downloaded */
  public void succeeded(){
    
    System.out.println("MVSFTPClient.getJobs() succeeded!");
    
    Updater updater = panel.getUpdater(getJobs());    
    SwingUtilities.invokeLater(updater);
    
  }
  
}
    
    
  
  
  
  
  
  

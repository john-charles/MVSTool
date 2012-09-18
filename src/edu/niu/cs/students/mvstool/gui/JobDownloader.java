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
import javax.swing.JPanel;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import java.io.IOException;

import edu.niu.cs.students.mvstool.gui.OutputViewerFrame;
import edu.niu.cs.students.mvstool.mvsftp.MVSFTPClient;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobDownloader;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

class JobDownloader extends MVSJobDownloader {
  
  Job job;
  JPanel frame;
  
  public JobDownloader(Job _ob, MVSFTPClient _lient, JPanel _rame) throws IOException {
    super(_ob, _lient);
    job = _ob;
    frame = _rame;
  }
  
  
  public void success(){
    
    OutputViewerFrame.createOutputViewer(getTempFile(), job);
    
  }
  
  public void failure(final Exception e){
    
    SwingUtilities.invokeLater(new Runnable(){
      
      public void run(){
        
        JOptionPane.showMessageDialog(frame, e.toString());
        
      }
      
    });
    
  }
  
}
  
  
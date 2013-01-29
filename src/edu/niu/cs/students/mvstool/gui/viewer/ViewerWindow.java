package edu.niu.cs.students.mvstool.gui.viewer;
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

import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.LinkedList;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.SwingUtilities;


import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;



import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;

import edu.niu.cs.students.mvstool.gui.GUIUtils;

import edu.niu.cs.students.mvstool.gui.viewer.OutputViewerPane;
import edu.niu.cs.students.mvstool.gui.viewer.OutputStreamParser;

public class ViewerWindow extends JFrame {
  
  File summationFile;
  List<File> files;
  JTabbedPane tabs;
  
  public ViewerWindow(File infile)
    throws IOException {
    
    super("Job Output Viewer");
    setSize(900, 400);
    
    summationFile = infile;
    
    tabs = new JTabbedPane();
    OutputStreamParser p = new OutputStreamParser(infile);
    
    int tabno = 0;
    
    files = new LinkedList<File>();
    
    while(p.hasNext()){
      
      File file = p.next();
      
      files.add(file);
      
      tabs.add(new OutputViewerPane(file), "Spool " + ++tabno);
      
    }
    
    add(tabs);
       
    
  }
  
  
  public static void showViewerWindow(final File infile){
    
    SwingUtilities.invokeLater(new Runnable(){
      
      public void run(){
        
        try {
          
          ViewerWindow window = new ViewerWindow(infile);
      
          window.setVisible(true);
          
        } catch(Exception e){
          
          GUIUtils.postMessage("Could not show viewere frame...");
          
        }
          
        
      }
      
    });
    
  }
  
  
  private void addClosingAction(){
    
    addWindowListener(new WindowAdapter(){
      
      public void windowClosing(WindowEvent e){
        
        summationFile.delete();
        
        for(File file: files) file.delete();
        
      }
      
    });
    
  }
    
  
  
  
  
  
  
}
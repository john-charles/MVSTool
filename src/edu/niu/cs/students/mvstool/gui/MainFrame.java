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

import java.awt.BorderLayout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;


import edu.niu.cs.students.mvstool.gui.AboutBox;
import edu.niu.cs.students.mvstool.gui.SetLookAndFeel;
import edu.niu.cs.students.mvstool.gui.ConnectionPanel;
import edu.niu.cs.students.mvstool.gui.GUIJobListPanel;

/* I created this class a week ago, I don't rember how it works, but
 * I do know I have not really needed to mess with it, but I code in
 * a verbose manor, shouldn't be too hard to figure out */
public class MainFrame extends JFrame {
  
  MainFrame mainFrameHook;
  ConnectionPanel connect;
  GUIJobListPanel jobList;
  
  public MainFrame(){
    
    super.setTitle("MVS Tool");
    super.setSize(700,400);
    super.setLocationRelativeTo(null);
    super.setJMenuBar(this.buildMenuBar());
    
    getContentPane().setLayout(new BorderLayout());
    
    jobList = new GUIJobListPanel();
    connect = new ConnectionPanel(jobList);
    
    getContentPane().add(connect, BorderLayout.NORTH);
    getContentPane().add(jobList, BorderLayout.CENTER);
    
        
    this.mainFrameHook = this;
    
  }
  
  
  private JMenu buildFileMenu(){
    
    JMenu filemenu = new JMenu("File");
    
    JMenuItem exit;
        
    exit = new JMenuItem("Exit"); //this.getIcon("exit.png"));
    exit.setToolTipText("Exit");
    
    exit.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent event){
        System.exit(0);
      }
      
    });
    
    filemenu.add(exit);
    return filemenu;
    
  }
    
  
  private JMenu buildAboutMenu(){
    
    JMenu aboutmenu = new JMenu("Help");
    
    JMenuItem help = new JMenuItem("Help");
    JMenuItem about = new JMenuItem("About");
    
    help.setToolTipText("Get directions on connecting to the mainframe!");
    
    about.setToolTipText("Get Information about this application");
    about.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent event){
        
        AboutBox aboutBox = new AboutBox();
        aboutBox.setLocationRelativeTo(mainFrameHook);
        aboutBox.setVisible(true);
        
      }
      
    });
    
    
    aboutmenu.add(help);
    aboutmenu.addSeparator();
    aboutmenu.add(about);
    
    return aboutmenu;
    
  }
    
  
  private JMenuBar buildMenuBar(){
    
    JMenuBar menubar = new JMenuBar();
    
    menubar.add(this.buildFileMenu());
    menubar.add(this.buildAboutMenu());
    
    return menubar;  
    
  }
    
    
  /* This is the main invocation for the entire application */  
  public static void main(String[] args){
    
    final String messageText = 
      "MVSTool v1.0 testing/developer preview\n" +
      "Please address comments questions to: \n" +
      "    John-Charles D. Sokolow <z1552897@students.niu.edu>";
    
    SetLookAndFeel.set();
    
    SwingUtilities.invokeLater(new Runnable() {
      public void run(){
        
        
        
        MainFrame frame;
        
        frame = new MainFrame();
        frame.setVisible(true);
        
        JOptionPane.showMessageDialog(frame, messageText);
        
      }   
    
    });
    
  }
}
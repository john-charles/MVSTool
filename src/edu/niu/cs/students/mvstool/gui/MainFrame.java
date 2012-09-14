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
import java.awt.BorderLayout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.SwingUtilities;


import edu.niu.cs.students.mvstool.gui.AboutBox;
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
    
    SwingUtilities.invokeLater(new Runnable() {
      public void run(){
    
        MainFrame frame;
        
        frame = new MainFrame();
        frame.setVisible(true);
        
      }   
    
    });
    
  }
}
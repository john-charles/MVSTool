package edu.niu.cs.students.mvstool.gui;

import java.awt.BorderLayout;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.UIManager;
import javax.swing.SwingUtilities;


import edu.niu.cs.students.mvstool.gui.AboutBox;
import edu.niu.cs.students.mvstool.gui.ConnectionPanel;
import edu.niu.cs.students.mvstool.gui.GUIJobListPanel;


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
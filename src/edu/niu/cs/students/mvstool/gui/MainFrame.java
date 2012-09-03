package edu.niu.cs.students.mvstool.gui;

import javax.swing.BoxLayout;
import javax.swing.Box.Filler;

import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JMenu;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
//import javax.swing.ImageIcon;
import javax.swing.SwingUtilities;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import edu.niu.cs.students.mvstool.gui.AboutBox;
import edu.niu.cs.students.mvstool.gui.MVSJobList;
import edu.niu.cs.students.mvstool.gui.ConnectionPanel;


public class MainFrame extends JFrame {
  
  MainFrame mainFrameHook;
  ConnectionPanel connect;
  MVSJobList jobList;
  
  public MainFrame(){
    
    super.setTitle("MVS Tool");
    super.setSize(700,500);
    super.setLocationRelativeTo(null);
    super.setJMenuBar(this.buildMenuBar());
    
    BoxLayout layout = new BoxLayout(super.getContentPane(), BoxLayout.Y_AXIS);
    
    super.setLayout(layout);
    
    jobList = new MVSJobList();
    connect = new ConnectionPanel(jobList);
    
    super.add(connect);
    super.add(jobList);
    
    JPanel red = new JPanel();
    
    Dimension dimm = new Dimension(100,100);
    
    red.add(new Filler(dimm, dimm, dimm));
    red.setBackground(Color.RED);
    add(red);
    
    this.mainFrameHook = this;
    
  }
  
//  private ImageIcon getIcon(String iconName){
//    
//    /* FIXME: This code throws java.lang.NullPointerException */    
//    return new ImageIcon(this.getClass().getResource(iconName));
//    
//  }
  
  private JMenu buildFileMenu(){
    
    JMenu filemenu = new JMenu("Connection");
    
    JMenuItem exit;
    JMenuItem connectTo;
    
    exit = new JMenuItem("Exit"); //this.getIcon("exit.png"));
    exit.setToolTipText("Exit");
    
    exit.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent event){
        System.exit(0);
      }
    });
    
    
    
    connectTo = new JMenuItem("Connect To");
    connectTo.setToolTipText("Open a new connection to a mainframe!");
    
    filemenu.add(connectTo);
    filemenu.addSeparator();
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
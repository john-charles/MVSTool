package edu.niu.cs.students.mvstool.gui;

import java.awt.Insets;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import javax.swing.border.EmptyBorder;

import edu.niu.cs.students.mvstool.gui.MVSJobList;
import edu.niu.cs.students.mvstool.ConnectionProfile;

class ConnectionPanel extends JPanel {
  
  JTextField hostname;
  JTextField hostport;
  JTextField username;
  JPasswordField password;
  
  MVSJobList jobList;
  
  public ConnectionPanel(MVSJobList jobList){
    
    Dimension size = new Dimension(650, 60);
    ConnectionProfile profile = ConnectionProfile.getConnectionProfile();
    
    this.jobList = jobList;
    
    hostname = new JTextField(profile.getHostname());
    hostport = new JTextField(Integer.toString(profile.getHostport()));
           
    username = new JTextField(profile.getUsername());
    password = new JPasswordField(profile.getPassword());
    
    setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    
    
    add(buildConnectionArea());    
    add(buildConnectionTrigger());
    
    setLayout(new BoxLayout(this, BoxLayout.X_AXIS));
    setPreferredSize(size);
    setMaximumSize(size);
    
  }
  
  
  private JPanel buildConnectionArea(){
    
    JPanel entryArea = new JPanel();
    JPanel entryHUArea = new JPanel();
    JPanel entryPPArea = new JPanel();
    
    JPanel usernameArea = new JPanel(new GridLayout(1,2));
    JPanel hostnameArea = new JPanel(new GridLayout(1,2));
    JPanel passwordArea = new JPanel(new GridLayout(1,2));
    JPanel hostportArea = new JPanel(new GridLayout(1,2));
    
    
    
    
    entryArea.setLayout(new BoxLayout(entryArea, BoxLayout.X_AXIS));
    
    entryHUArea.setLayout(new BoxLayout(entryHUArea, BoxLayout.Y_AXIS));
    entryPPArea.setLayout(new BoxLayout(entryPPArea, BoxLayout.Y_AXIS));
    
    //entryArea.setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    
    hostnameArea.add(new JLabel("Hostname:"));
    hostnameArea.add(hostname);
    
    hostportArea.add(new JLabel("Port:"));
    hostportArea.add(hostport);
    
    usernameArea.add(new JLabel("Username:"));
    usernameArea.add(username);
    
    passwordArea.add(new JLabel("Password:"));
    passwordArea.add(password);
    
    entryHUArea.add(hostnameArea);
    entryHUArea.add(usernameArea);
    
    entryPPArea.add(hostportArea);
    entryPPArea.add(passwordArea);
    
    entryArea.add(entryHUArea);
    entryArea.add(entryPPArea);
    
    return entryArea;
    
  }
  
  private JButton buildConnectionTrigger(){
    
    JButton connect = new JButton("Connect");
    
    connect.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent event){
        
        ConnectionProfile profile = ConnectionProfile.getConnectionProfile();
        
        profile.setHostname(hostname.getText());
        profile.setHostport(hostport.getText());
        profile.setUsername(username.getText());
        profile.setPassword(password.getText());
        
        jobList.doConnect();
        
      }
      
    });
    
    return connect;
    
  }       
  
}
  
  

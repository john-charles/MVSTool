package edu.niu.cs.students.mvstool.gui;

import java.awt.GridLayout;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import edu.niu.cs.students.mvstool.gui.GUIJobListPanel;
import edu.niu.cs.students.mvstool.ConnectionProfile;

class ConnectionPanel extends JPanel {
  
  JTextField hostname;
  JTextField hostport;
  JTextField username;
  JPasswordField password;
  
  GUIJobListPanel jobList;
  
  public ConnectionPanel(GUIJobListPanel jobList){
    
    //Dimension size = new Dimension(650, 60);
    ConnectionProfile profile = ConnectionProfile.getConnectionProfile();
    
    this.jobList = jobList;
    
    hostname = new JTextField(profile.getHostname());
    hostport = new JTextField(Integer.toString(profile.getHostport()));
           
    username = new JTextField(profile.getUsername());
    password = new JPasswordField(profile.getPassword());
    
    //setBorder(new EmptyBorder(new Insets(10, 10, 10, 10)));
    setLayout(new BorderLayout());
    
    add(buildLeftInputArea(), BorderLayout.WEST);
    add(buildRightInputArea(), BorderLayout.CENTER);
    add(buildConnectionTrigger(), BorderLayout.EAST);
    
    
    //setPreferredSize(size);
    //setMaximumSize(size);
    
  }
  
  private JPanel buildLeftInputArea(){
    
    JPanel area = new JPanel(new GridLayout(2, 4));
   
    area.add(new JLabel("Hostname"));
    area.add(hostname);
    
    area.add(new JLabel("Username"));
    area.add(username);
    
    return area;
    
  }
  
  private JPanel buildRightInputArea(){
    
    JPanel area = new JPanel(new GridLayout(2, 4));
    
    area.add(new JLabel("Port"));
    area.add(hostport);
    
    area.add(new JLabel("Password"));
    area.add(password);
    
    return area;
    
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
  
  

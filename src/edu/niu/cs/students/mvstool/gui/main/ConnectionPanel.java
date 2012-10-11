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
package edu.niu.cs.students.mvstool.gui.main;

import java.awt.GridLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.GridBagConstraints;
import java.awt.event.ActionListener;


import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JPasswordField;

import edu.niu.cs.students.task.Task;
import edu.niu.cs.students.mvstool.Profile;

import edu.niu.cs.students.mvstool.gui.main.JobListTableModel;

import edu.niu.cs.students.mvstool.tasks.GetJobListTask;

class ConnectionPanel extends JPanel {
  
  JButton connect;
  
  JTextField hostname;
  JTextField hostport;
  JTextField username;
  JPasswordField password;
  
  JobListTableModel model;
  
  public ConnectionPanel(JobListTableModel model){
    
    Profile profile = Profile.getCurrentProfile();
    
    this.model = model;
    
    hostname = new JTextField(profile.getHostname());
    hostport = new JTextField(Integer.toString(profile.getHostport()));
           
    username = new JTextField(profile.getUsername());
    password = new JPasswordField(profile.getPassword());
    
    setLayout(new GridBagLayout());
    
    GridBagConstraints c = new GridBagConstraints();
    
    c.fill = GridBagConstraints.BOTH;
    c.weightx = 0.5;    
    
    add(buildFirstInputArea(), c);
    
    c.weightx = 2.0;
    
    add(buildSecondInputArea(), c);
    
    c.weightx = 0.5;
    
    add(buildThirdInputArea(), c);
    
    c.weightx = 2.0;
    
    add(buildFourthInputArea(), c);
    
    c.weightx = 1.0;
    c.gridwidth = GridBagConstraints.REMAINDER;
    
    add(buildConnectionTrigger(), c);
    
  }
  
  private JPanel buildFirstInputArea(){
    
    JPanel panel = new JPanel(new GridLayout(2, 1));
    
    panel.add(new JLabel("Hostname"));
    panel.add(new JLabel("Username"));
    
    return panel;
    
  }
  
  private JPanel buildSecondInputArea(){
    
    JPanel area = new JPanel(new GridLayout(2, 1));
    
    area.add(hostname);
    area.add(username);
    
    return area;
    
  }
  
  private JPanel buildThirdInputArea(){
    
    JPanel area = new JPanel(new GridLayout(2, 1));
    
    area.add(new JLabel("Port"));
    area.add(new JLabel("Password"));
    
    return area;
    
  }
  
  private JPanel buildFourthInputArea(){
    
    JPanel area = new JPanel(new GridLayout(2, 1));
    
    area.add(hostport);
    area.add(password);
    
    return area;
    
  }
  
  /* When the user clicks the "connect" button this action is
   * fired, it takes the values currently in the connection
   * panel and adds them to the connection profile object.
   * it then calls the code which actually connects to the
   * server... I don't know about this architecture, but
   * it works! so I'm keeping it! */
  private JButton buildConnectionTrigger(){
    
    connect = new JButton("Connect");
    
    connect.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent event){
        
        Profile profile = Profile.getCurrentProfile();
        
        connect.setText("Refresh");
        
        profile.setHostname(hostname.getText());
        profile.setHostport(hostport.getText());
        profile.setUsername(username.getText());
        /* Note that the compiler warns about the
         * next line, for now I'm choosing to 
         * ignore it, as the "non-depricated" 
         * method is stupid and would get the
         * same result with twice the amount of code */
        profile.setPassword(password.getText());
        
        Task.fire(new GetJobListTask(model));
        
      }
      
    });
    
    return connect;
    
  }       
  
}
  
  

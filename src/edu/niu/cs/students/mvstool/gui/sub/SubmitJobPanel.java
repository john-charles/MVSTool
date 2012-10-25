package edu.niu.cs.students.mvstool.gui.sub;

import java.io.File;

import java.awt.BorderLayout;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JTextField;
import javax.swing.JFileChooser;

import edu.niu.cs.students.mvstool.Profile;

public class SubmitJobPanel extends JPanel {
  
  File jobPath;
  Profile profile;
  JTextField pathField;
  
  public SubmitJobPanel(){
    super(new BorderLayout());
    
    profile = Profile.getCurrentProfile();
    jobPath = profile.getLastJob();
    
    add(buildBrowseButton(), BorderLayout.WEST);
    add(buildJobPathField(), BorderLayout.CENTER);
    add(buildSubmitButton(), BorderLayout.EAST);
    
    
  }
  
  private JButton buildBrowseButton(){
    
    JButton browse = new JButton("Browse");
    
    browse.addActionListener( new ActionListener(){
      
      public void actionPerformed(ActionEvent event){
        
        JFileChooser chooser;
        
        if(jobPath == null)
          chooser = new JFileChooser();
        else
          chooser = new JFileChooser(jobPath);
          
        
        int returnVal = chooser.showOpenDialog(null);
        if(returnVal == JFileChooser.APPROVE_OPTION){
          
          jobPath = chooser.getSelectedFile();
          pathField.setText(jobPath.toString());
          profile.setLastJob(jobPath);
          
        }
                
      }
      
    });
    
    return browse;
    
  }
  
  private JTextField buildJobPathField(){
    
    if(jobPath == null)
      pathField = new JTextField("Select job file. example : C:\\jobs\\myjob.txt");
    else
      pathField = new JTextField(jobPath.toString());
    
    return pathField;
    
  }
  
  private JButton buildSubmitButton(){
    
    JButton sub = new JButton("Submit");
    return sub;
    
  }
  
}
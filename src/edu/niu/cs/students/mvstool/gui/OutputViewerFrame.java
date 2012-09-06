package edu.niu.cs.students.mvstool.gui;

import java.io.File;
import java.io.IOException;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;


import edu.niu.cs.students.mvstool.Utils;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

public class OutputViewerFrame extends JFrame {
  
  private Job  job;
  private File tempFile;
  
  public static void createOutputViewer(File tempFile, Job job){
    
    final OutputViewerFrame frame = new OutputViewerFrame(tempFile, job);
    
    SwingUtilities.invokeLater(new Runnable(){
      
      public void run(){
        
        frame.activateFrame();
        
      }
      
    });
    
  }
  
  private OutputViewerFrame(File tempFile, Job job){
    super("JCL Output Viewer");
    this.job = job;
    this.tempFile = tempFile;
    setLayout(new BorderLayout());
    
  }
  
  private void activateFrame(){
    setSize(700, 400);
    
    System.out.println("job saved to: " + tempFile);
    
    addClosingAction();
    
    add(buildToolBar(), BorderLayout.NORTH);
    add(buildTextArea(), BorderLayout.CENTER);
    
    setVisible(true);
    
  }
  
  private void addClosingAction(){
    
    addWindowListener(new WindowAdapter(){
      
      public void windowClosing(WindowEvent e){
        
        tempFile.delete();
        
      }
      
    });
    
  }
    
  
  private JPanel buildToolBar(){
    
    JPanel toolbar = new JPanel(new BorderLayout());
    JButton saveButton = new JButton("Save output to file");
    
    saveButton.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent e){
        
        saveToFile();
        
      }
      
    });
    
    toolbar.add(new JLabel("Output for job id: " + job.getID()), BorderLayout.WEST);
    toolbar.add(saveButton, BorderLayout.EAST);
    
    
    return toolbar;
    
  }
  
  private JScrollPane buildTextArea(){
    
    String text = Utils.loadFileToString(tempFile);    
    JTextArea textArea = new JTextArea(text);    
    return new JScrollPane(textArea);
    
  }
  
  public void saveToFile(){
    
    /* TODO: make this remember the last directory, or even
     * allow the user to set a default directory and then
     * pass that directory to the JFileChooser() constructor!
     */
    JFileChooser chooser = new JFileChooser();
    
    int ret = chooser.showSaveDialog(this);
    
    if(ret == JFileChooser.APPROVE_OPTION){
      
      File saveFile = chooser.getSelectedFile();
      
      try {
        
        Utils.copyFile(tempFile, saveFile);
        
      } catch(IOException e){
        
        JOptionPane.showMessageDialog(this, e.getMessage());
        
      }
      
    }
    
  }
  
}
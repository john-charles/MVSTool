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

import java.awt.Font;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.ActionListener;


import edu.niu.cs.students.mvstool.Utils;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;

/* This is self explanatory, this window is opened when job output
 * has successfully been downloaded, it is simple it shows the job
 * id in one corner and let's the user save the output by clicking
 * a button in the other corner */
public class OutputViewerFrame extends JFrame {
  
  private Job  job;
  private File tempFile;
  
  /* Creates the new frame but doesn't show it until it's invoked
   * by the swing event queue, I'm not sure if this is technically
   * correct as I am uncertain if swing just requires any ui updates
   * to ocurr in the AWT thread, or all object manipulation, but 
   * at the moment this works and does not cause any awful errors
   * so I'm keeping it. */
  public static void createOutputViewer(File tempFile, Job job){
    
    final OutputViewerFrame frame = new OutputViewerFrame(tempFile, job);
    
    SwingUtilities.invokeLater(new Runnable(){
      
      public void run(){
        
        frame.activateFrame();
        
      }
      
    });
    
  }
  
  /* This  creates the new frame, but does not initialize the GUI
   * in any way, see activeateFrame for gui initialization */
  private OutputViewerFrame(File tempFile, Job job){
    super("JCL Output Viewer");
    this.job = job;
    this.tempFile = tempFile;
    setLayout(new BorderLayout());
    
  }
  
  /* This updates, and shows the ui in the main AWT thread */
  private void activateFrame(){
    setSize(700, 400);
    
    System.out.println("job saved to: " + tempFile);
    
    addClosingAction();
    
    add(buildToolBar(), BorderLayout.NORTH);
    add(buildTextArea(), BorderLayout.CENTER);
    
    setVisible(true);
    
  }
  
  /* This ensures that the temp file get's deleted when 
   * the user closes the window, don't want to be using
   * all the temp space now do we? */
  private void addClosingAction(){
    
    addWindowListener(new WindowAdapter(){
      
      public void windowClosing(WindowEvent e){
        
        tempFile.delete();
        
      }
      
    });
    
  }
    
  /* Pretty self explanatory, it builds the toolbar with the 
   * job name, and save button. */
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
    textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
    return new JScrollPane(textArea);
    
  }
  
  /* When the user clicks save file this code is executed and 
   * saves the file.
   * 
   *        Note that I did not implement this in a background
   * thread, maybe I should have but for now, I'm not noticing
   * any lag or freezes, the output file would need to be very 
   * big for that to happen. */
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
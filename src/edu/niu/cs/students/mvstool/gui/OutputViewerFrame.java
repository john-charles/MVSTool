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
import java.io.File;
import java.io.IOException;

import java.util.List;
import java.util.ArrayList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import javax.swing.JScrollPane;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.SwingUtilities;

import javax.swing.text.Highlighter.HighlightPainter;
import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import java.awt.Font;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.BorderLayout;
import java.awt.GridBagLayout;
import java.awt.event.ActionEvent;
import java.awt.event.WindowEvent;
import java.awt.GridBagConstraints;


import java.awt.event.ActionEvent;
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
  
  private int currError;
  private int errorCount;
  
  
  private List<Integer> errorAreas;
  
  private JTextArea textArea;
  private JLabel errorCountLabel;
  
  private static final String _p = "(\\=\\=[0-9]+\\=\\=.*\\s\\s.*)$";
  private static final Pattern cobolErrorPattern = Pattern.compile(_p,Pattern.MULTILINE); 
    
  
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
    super("Job Output Viewer");
    this.job = job;
    this.tempFile = tempFile;
    setLayout(new BorderLayout());
    
    currError = 0;
    
  }
  
  /* This updates, and shows the ui in the main AWT thread */
  private void activateFrame(){
    setSize(700, 400);
    
    errorAreas = new ArrayList<Integer>();
    
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
  
  private void blinkNoErrors(){
    
    for(int i = 0; i < 5; i++){
      
      errorCountLabel.setBackground(Color.YELLOW);
      errorCountLabel.updateUI();      
      
    }    
    
  }
  
  private void moveToError(int moveAmt){
    
    if(errorAreas.size() == 0){
      
      blinkNoErrors();
      
    } else {
      
      currError = (currError + moveAmt) % errorAreas.size();
      
      int start = errorAreas.get(currError).intValue();
      
      try {
        
        textArea.scrollRectToVisible(textArea.modelToView(start));
        
      } catch(Exception ex){
        
        ex.printStackTrace();
        
      }
      
    }
  }
    
    
  
  private JButton buildPrevErrorButton(){
    
    JButton button = new JButton("<<");
    
    button.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent e){
        
        moveToError(-1);
        
      }     
      
    });
    
    
    return button;
    
  }
  
  private JButton buildNextErrorButton(){
    
    JButton button = new JButton(">>");
    
    button.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent e){
        
        moveToError(1);
        
      }
      
    });
    
    return button;
    
  }
  
  private JPanel buildErrorBar(){
    
    errorCountLabel = new JLabel("0 Errors found!");
    
    errorCountLabel.setOpaque(true);
    errorCountLabel.setBackground(Color.GREEN);
    
    JPanel toolbar = new JPanel(new BorderLayout());
        
    toolbar.add(buildPrevErrorButton(), BorderLayout.WEST);
    
    toolbar.add(errorCountLabel, BorderLayout.CENTER);
        
    toolbar.add(buildNextErrorButton(), BorderLayout.EAST);
    
    return toolbar;
    
  }
    
  /* Pretty self explanatory, it builds the toolbar with the 
   * job name, and save button. */
  private JPanel buildToolBar(){
    
    JPanel toolbar = new JPanel(new BorderLayout());
    
    JPanel infoBar = new JPanel(new BorderLayout());
    
    JButton saveButton = new JButton("Save output to file");
    
       
    saveButton.addActionListener(new ActionListener(){
      
      public void actionPerformed(ActionEvent e){
        
        saveToFile();
        
      }
      
    });
    
    infoBar.add(new JLabel("Output for job id: " + job.getID()), BorderLayout.WEST);
    infoBar.add(saveButton, BorderLayout.EAST);
    
    toolbar.add(infoBar, BorderLayout.NORTH);
    toolbar.add(buildErrorBar(), BorderLayout.SOUTH);
    
    return toolbar;
    
  }
  
  private void highlightArea(int start, int end){
    
    System.out.println("Start: " + start + " End: " + end);
    
    HighlightPainter painter = new DefaultHighlightPainter(Color.RED);    
    
    errorAreas.add(new Integer(end));
    
    try {
      
      textArea.getHighlighter().addHighlight(start, end, painter);
      
    } catch(Exception e){
      /* */
    }
    
    errorCount = errorCount + 1;
    
    errorCountLabel.setOpaque(true);
    errorCountLabel.setBackground(Color.RED);
    errorCountLabel.setText(errorCount + " Errors found!");
    
  }
  
  private void highlightCobolErrors(String text){
    
    System.out.println("highlightCobolErrors!");
    
    Matcher m = cobolErrorPattern.matcher(text);
    
    while(m.find()){
      
      highlightArea(m.start(), m.end());
      
    }
    
  } 
  
  private void findAreasToHighlight(String text){
    
    highlightCobolErrors(text);
    
  }
  
  private JScrollPane buildTextArea(){
    
    String text = Utils.loadFileToString(tempFile); 
    
    textArea = new JTextArea(text);  
    
    findAreasToHighlight(text);
    
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
  
  public static void main(String[] args){
    edu.niu.cs.students.mvstool.gui.MainFrame.main(args);
  }
  
}
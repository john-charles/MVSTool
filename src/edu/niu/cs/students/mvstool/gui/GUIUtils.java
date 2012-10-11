package edu.niu.cs.students.mvstool.gui;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

public final class GUIUtils {
  
  public static void postMessage(final String mesg){
    
    SwingUtilities.invokeLater(new Runnable(){
      
      public void run(){
        
        JOptionPane.showMessageDialog(null, mesg);
        
      }
      
    });
    
  }
  
}
    
    
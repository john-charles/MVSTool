package edu.niu.cs.students.mvstool.gui;

/* NOTE: I don't usually use *, but in this case, 
 * I can't easily find the fully qualified imports
 * and I don't have time to find them, a tut on the
 * web show's this set of imports...
 * The contents of the set method actually comes from
 * the sun/oracle/java trail map tutorials... */
import javax.swing.*;
import java.awt.*;

import edu.niu.cs.students.mvstool.ConnectionProfile;

class SetLookAndFeel {
  
  private static void setLNF(String name){
    
    try {
      
      UIManager.setLookAndFeel(name);
    } 
    catch (Exception e) {
      /* Swing has a sane default... so if we can't use
       * this lnf then we really don't care... the user
       * might be disapointed, but this is not a substantial
       * error... so ignore it here!*/
    }       
  }
  
  public static void set(){
    
    UIManager.LookAndFeelInfo plaf[] = UIManager.getInstalledLookAndFeels();

    
    ConnectionProfile profile = ConnectionProfile.getConnectionProfile();
    
    /* The gtk look and feel looks aweful on kde based systems...
     * this lets the user specify in their configuration file
     * that they don't want the gtk look and feel...*/    
    if(profile.useGTKLookAndFeel()){
      setLNF("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
    }
    
    setLNF("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    
  }
  
}
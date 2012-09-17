package edu.niu.cs.students.mvstool.gui;

/* NOTE: I don't usually use *, but in this case, 
 * I can't easily find the fully qualified imports
 * and I don't have time to find them, a tut on the
 * web show's this set of imports...
 * The contents of the set method actually comes from
 * the sun/oracle/java trail map tutorials... */
import javax.swing.*;
import java.awt.*;

class SetLookAndFeel {
  
  private static void setLNF(String name){
    
    try {
      
      UIManager.setLookAndFeel(name);
    } 
    catch (UnsupportedLookAndFeelException e) {
      e.printStackTrace();
    }
    catch (ClassNotFoundException e) {
      // handle exception
    }
    catch (InstantiationException e) {
      // handle exception
    }
    catch (IllegalAccessException e) {
      // handle exception
    }
    
  }
  
  private static void tryGTK(){
    
    String gtkLNF = "com.sun.java.swing.plaf.gtk.GTKLookAndFeel";
    setLNF(gtkLNF);
    
  } 
  
  public static void set(){
    
    UIManager.LookAndFeelInfo plaf[] = UIManager.getInstalledLookAndFeels();
//    for (int i = 0, n = plaf.length; i < n; i++) {
//      System.out.println("Name: " + plaf[i].getName());
//      System.out.println("  Class name: " + plaf[i].getClassName());
//    }
    
    tryGTK(); 
    setLNF("joxy.JoxyLookAndFeel");
    
  }
  
}
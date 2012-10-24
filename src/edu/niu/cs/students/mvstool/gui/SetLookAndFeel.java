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

import javax.swing.UIManager;


import edu.niu.cs.students.mvstool.Profile;

public class SetLookAndFeel {
  
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
    
    Profile profile = Profile.getCurrentProfile();
    
    /* The gtk look and feel looks aweful on kde based systems...
     * this lets the user specify in their configuration file
     * that they don't want the gtk look and feel...*/    
    if(profile.useGTKLookAndFeel()){
      setLNF("com.sun.java.swing.plaf.gtk.GTKLookAndFeel");
    }
    
    setLNF("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    
  }
  
}
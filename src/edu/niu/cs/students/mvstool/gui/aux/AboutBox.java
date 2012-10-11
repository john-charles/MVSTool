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
package edu.niu.cs.students.mvstool.gui.aux;

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

public class AboutBox extends JFrame {
  
  public AboutBox(){
    
    super.setTitle("About MVS Tool");
    
    String aboutDetails = "<html>" + 
      "MVS Tool 1.0 (c) 2012<br><br><br>" +
      "License information:<br><br>" +
      "Copyright (c) 2012 John-Charles D. Sokolow<br>" +
      "Redistribute under terms of GTPv2... " +
      "</html>";     
    
    JPanel panel = new JPanel(); 
    
    
    panel.setLayout(new BorderLayout(10, 10));
    
    JLabel lAboutDetails = new JLabel(aboutDetails);
    
    panel.add(lAboutDetails, BorderLayout.CENTER);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    super.add(panel);
    super.pack();
    
  }
  
}
  
  
  
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

import javax.swing.JPanel;
import javax.swing.JFrame;
import javax.swing.JLabel;
import java.awt.BorderLayout;
import javax.swing.BorderFactory;

class AboutBox extends JFrame {
  
  public AboutBox(){
    
    super.setTitle("About MVS Tool");
    
    String aboutDetails = "<html>" + 
      "MVS Tool 1.0 (c) 2012<br><br><br>" +
      "License information:<br><br>" +
      "Copyright (c) 2012 John-Charles D. Sokolow<br>" +
      "<br>" +
      "    Permission is hereby granted, free of charge, to any person obtaining a<br>" +
      " copy of this software and associated documentation files (the \"Software\"),<br>" +
      " to deal in the Software without restriction, including without<br>" +
      " limitation the rights to use, copy, modify, merge, publish, distribute,<br>" +
      " sublicense, and/or sell copies of the Software, and to permit persons to<br>" +
      " whom the Software is furnished to do so, subject to the following<br>" +
      " conditions:<br><br>" +
      "    The above copyright notice and this permission notice shall be included<br>" +
      "       in all copies or substantial portions of the Software.<br><br>" +
      "    THE SOFTWARE IS PROVIDED \"AS IS\", WITHOUT WARRANTY OF ANY KIND, EXPRESS<br>" +
      "    OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF<br>" +
      "    MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT.<br>" +
      "    IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY<br>" +
      "    CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT,<br>" +
      "    TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE<br>" + 
      "    SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.<br>" + "</html>";     
    
    JPanel panel = new JPanel(); 
    
    
    panel.setLayout(new BorderLayout(10, 10));
    
    JLabel lAboutDetails = new JLabel(aboutDetails);
    
    panel.add(lAboutDetails, BorderLayout.CENTER);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    super.add(panel);
    super.pack();
    
  }
  
}
  
  
  
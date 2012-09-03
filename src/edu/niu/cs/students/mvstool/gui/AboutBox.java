package edu.niu.cs.students.mvstool.gui;

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
      "THE SOFTWARE IS PROVIDED \"AS IS\", <br>" +
      "WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED,<br>" + 
      "INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF <br>" +
      "MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE <br>" +
      "AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS <br>" +
      "OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES <br>" +
      "OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, <br>" +
      "TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION <br>" + 
      "WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE <br>" +
      "SOFTWARE.</html>";     
    
    JPanel panel = new JPanel(); 
    
    
    panel.setLayout(new BorderLayout(10, 10));
    
    JLabel lAboutDetails = new JLabel(aboutDetails);
    
    panel.add(lAboutDetails, BorderLayout.CENTER);
    panel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
    super.add(panel);
    super.pack();
    
  }
  
}
  
  
  
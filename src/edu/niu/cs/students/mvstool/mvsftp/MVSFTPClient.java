package edu.niu.cs.students.mvstool.mvsftp;

import java.io.IOException;

/* Resources for understanding this code!
 * 
 * http://publib.boulder.ibm.com/infocenter/zos/v1r11/index.jsp?topic=/com.ibm.zos.r11.halu001/autosubmit.htm
 * http://publib.boulder.ibm.com/infocenter/zos/basics/index.jsp?topic=/com.ibm.zos.znetwork/znetwork_110.htm
 * http://www-01.ibm.com/support/docview.wss?uid=nas1418dba0e644f915086256bdc005f684f
 * */

import edu.niu.cs.students.mvstool.ftp.FTPClient;
import edu.niu.cs.students.mvstool.ftp.FTPException;
import edu.niu.cs.students.mvstool.mvsftp.MVSException;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser;
import edu.niu.cs.students.mvstool.mvsftp.MVSDefaultJobListParser;

public class MVSFTPClient extends FTPClient {
  
  public MVSFTPClient(String hostname, int port) throws IOException {
    super(hostname, port);
       
  }
  /* ************************************************************************************
   * Documentation for this site command is provided at                                 *
   * http://www-01.ibm.com/support/docview.wss?uid=nas1418dba0e644f915086256bdc005f684f *
   **************************************************************************************/
  public void getJobs(MVSJobListParser parser){
    
    
    String resp = exec("SITE FILEtype=jes");
    
  
  
  
  public static void main(String[] args) throws Exception {
    
    MVSFTPClient client = new MVSFTPClient("zos.kctr.marist.edu", 21);
    
    client.login("kc03m62", "jkmg8840");
    
    
  }
    
  
}
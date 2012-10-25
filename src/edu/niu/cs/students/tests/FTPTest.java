package edu.niu.cs.students.tests;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import java.net.Socket;



public class FTPTest {
  
  InputStream srvin;
  OutputStream srvot;
  
  private FTPTest() throws IOException {
    
    Socket s = new Socket("zos.kctr.marist.edu", 21);
    
    srvin = s.getInputStream();
    srvot = s.getOutputStream();
    
  }
  
//  private String exec(String cmd) throws IOException {
//    
//    send(cmd);
//    return recv().trim();
//    
//  }
  
  private String recv()
    throws IOException {
    
    byte[] buff = new byte[1];
    String resp = new String();
    
    while(srvin.read(buff) > 0){
      
      if(buff[0] == '\n')
        return resp;
      resp = resp + (char)buff[0];
      
    }
    
    return resp;
    
  }
  
  private void send(String cmd)
    throws IOException {
    
    srvot.write(cmd.getBytes());
    
  } 
    
    
  
  public static void main(String[] args)
    throws IOException {
    
//    FTPTest ftp = new FTPTest();
//    
//    System.out.println(ftp.recv().trim());
//    System.out.println(ftp.recv().trim());
//    
//    String resp = ftp.exec("USER kc03m62\r\n");
//    
//    System.out.println(resp.trim());
//    
//    
//    
//    String str = ftp.exec("EPSV\r\n");
//    
//    String portStr = str.replace("229 Entering Extended Passive Mode (|||","");
//    portStr = portStr.replace("|)","");
//    
//    System.out.println("portStr = " + portStr);
    
    
  }
  
}

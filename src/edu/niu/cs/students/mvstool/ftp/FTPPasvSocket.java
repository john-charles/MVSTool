package edu.niu.cs.students.mvstool.ftp;

import java.io.IOException;

import java.net.Socket;
import java.net.InetSocketAddress;

public class FTPPasvSocket extends Socket {
  
  public FTPPasvSocket(String pasvResponse) throws IOException {
    super();
    
    connect(parsePASVAddress(pasvResponse));
    
  }
  
  private InetSocketAddress parsePASVAddress(String resp){
    
    int port1;
    int port2;
    
    String[] info;
       
    int port;
    String hostname = new String();
    
    
    while(!resp.startsWith("(")){
      
      resp = resp.substring(1, resp.length());
      
    }
    
    
    resp = resp.replace("(","");
    resp = resp.replace(")","");
    resp = resp.replace(".","");
      
    info = resp.split(",");
    
    for(int i = 0; i < 4; i++){
      
      hostname = hostname + info[i];
      
      if(i < 3){
        
        hostname = hostname + ".";
        
      }
      
    }
    
      
    port1 = Integer.parseInt(info[4].trim());
    port2 = Integer.parseInt(info[5].trim());
    
    port = (port1 * 256) + port2;
    
    return new InetSocketAddress(hostname, port);
    
  }
  
}
  
  
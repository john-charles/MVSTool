package edu.niu.cs.students.mvstool.ftp;
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
  
  
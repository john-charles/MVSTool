package edu.niu.cs.students.ftp;
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
  
  
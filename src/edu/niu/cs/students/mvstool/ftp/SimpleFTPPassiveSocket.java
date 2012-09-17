package edu.niu.cs.students.mvstool.ftp;
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
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import edu.niu.cs.students.mvstool.Utils;

/* DEPRICATED!!!!!!!!!!!!! I decided to get rid of the Simple* prefix
 * on the ftp package early on, as it became too much typing... this 
 * module is simply a remenant of having not yet deleted it, it is
 * used by the list method of FTPClient... TODO: Update the list function
 * to use the replacement to this class, FTPPasvSocket... */
class SimpleFTPPassiveSocket {
  
  private InputStream  recvstream;
  private OutputStream sendstream;
  
  private class Address {
    
    public String hostname;
    public int    port;
    
    public Address(){
      
      this.hostname = new String();
      
    }
    
  }
  
  private Socket conn;
  
  public SimpleFTPPassiveSocket(String pasvAddress) throws IOException {
    
    Address address = parsePASVAddress(pasvAddress);
    
    conn = new Socket(address.hostname, address.port);
    
    this.recvstream = conn.getInputStream();
    this.sendstream = conn.getOutputStream();
    
  }
  
  public void close(){
    try {
      
      conn.close();
      
    } catch(Exception e){
      
    }
  }
  
  private Address parsePASVAddress(String resp){
    
    String[] info;
    Address  address = new Address();
    
    int port1;
    int port2;
    
    while(!resp.startsWith("(")){
      
      resp = resp.substring(1, resp.length());
      
    }
    
    
    resp = resp.replace("(","");
    resp = resp.replace(")","");
    resp = resp.replace(".","");
      
    info = resp.split(",");
    
    for(int i = 0; i < 4; i++){
      
      address.hostname = address.hostname + info[i];
      
      if(i < 3){
        
        address.hostname = address.hostname + ".";
        
      }
      
    }
    
      
    port1 = Integer.parseInt(info[4].trim());
    port2 = Integer.parseInt(info[5].trim());
    
    address.port = (port1 * 256) + port2;
    
    
    
    return address;
    
    
  }
  
  public String recv() throws IOException {
    
    char    curByte = 0;
    String  message = new String();
    
    curByte = (char)this.recvstream.read();
    
    if((int)curByte == -1 || (int)curByte > 255){
      return null;
    }
    
    while(curByte != -1){
      
      message = message + curByte;
      
      if(message.length() > 300){
        break;
      }
      
      if(curByte == '\n'){
        
        return message;
        
      }
      
      curByte = (char)this.recvstream.read();
      
    }
    
    
    Utils.sleep(2);
    
    return null;
    
  }
  
  
  public void send(String message) throws IOException {
    
    if(message.endsWith("\n")){
      
      message = message.substring(0, message.length() -1);
      
    }
    
    for(int i = 0; i < message.length(); i++){
      
      this.sendstream.write((int)message.charAt(i));
      
    }
    
    if(!message.endsWith("\r\n")){
      
      this.sendstream.write((int)'\r');
      this.sendstream.write((int)'\n');
      
    }
    
  }
  
  
  private int read() throws IOException {
    
    int data = this.recvstream.read();
    
    if(data > 255){
      return -1;
    } else if(data < 0){
      return -1;
    }
    
    return data;
    
  }
  
  
  public String read(int size) throws IOException {
    
    int    ibyte;
    String block = new String();
    
    for(int i = 0; i < size; i++){
      
      ibyte = this.read();
      
      if(ibyte != -1){
        block = block + (char)ibyte;
      } else {
        break;
      }
    }
    
    if(block.equals("")){
      return null;
    } else {
      return block;      
    }
  }
}
    
    
        
        
    
    
    
    
    
    
    
  
  
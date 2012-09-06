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
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import edu.niu.cs.students.mvstool.Utils;

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
    
    
        
        
    
    
    
    
    
    
    
  
  
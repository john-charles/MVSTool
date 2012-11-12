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

import java.util.LinkedList;

import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;


import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.LineOutput;
import edu.niu.cs.students.netio.CRLFLineInput;
import edu.niu.cs.students.netio.CRLFLineOutput;

import edu.niu.cs.students.ftp.FTPException;
import edu.niu.cs.students.ftp.FTPPasvSocket;


/** SimpleFTP is a limited implementation of the ftp protocal! Supporting only   
  *     the most essential ftp commands needed by the marist mainframe!          
  * */
public class FTPClient {
  
  private String hostname;
  private int    hostport;
  
  private Socket serverconn;
  
  private LineInput  serverrecv;  
  private LineOutput serversend;
  
  /* Constructs a new ftp client instance, connecting it to the 
   * target host and port, throws IOException if the socket cannot
   * be created! */
  public FTPClient(String hostname, int port) throws IOException {
    
    this.hostname = hostname;
    this.hostport = port;
    
    serverconn = new Socket(this.hostname, this.hostport);
    
    this.serverrecv = new CRLFLineInput(serverconn.getInputStream());
    this.serversend = new CRLFLineOutput(serverconn.getOutputStream());
    
    /* Some servers like to send random stuff after a connection is made
     * this wades through the muck until that stuff if cleared out */
    flush();
    
  }
  
   /*********************************************************************
    *                                                                  *
    * closes the ftp connection first executing a proper quit command. *
    *                                                                  *
    ********************************************************************/
  public void close() throws IOException {
    
    exec("QUIT");
    serverconn.close();
    
  }
  
  public void delete(String path)
    throws IOException, FTPException {
    
    String resp = exec(String.format("DELE %s", path));
    
    if(!resp.startsWith("250")){
      throw new FTPException(resp.trim());
    }
  }
  
  /*******************************************************************************
    *                                                                            *
    * exec(String); Executes an ftp command on the server and returns the        *
    *               server's response, this method calls send and recv each      *
    *               once!                                                        *
    *                                                                            *
    * Throws: java.io.IOException                                                *
    *                                                                            *
    * ****************************************************************************/
  protected String exec(String cmd) throws IOException {
    
    synchronized(this) {
          
      this.send(cmd);
      return recv();
      
    }
    
  }
  
  
  /* Some servers like to send random stuff after a connection is made
   * this wades through the muck until that stuff if cleared out 
   * Basically this code calls noop until the server responds with
   * a proper 200 response! */
  private void flush() throws IOException {
    
    synchronized(this){
      
      this.send("NOOP");
      
      String resp = recv();
      
      while(!resp.startsWith("200 OK")){
        
        if(resp.startsWith("530")){
          
          break;
          
         }
        
        resp = this.recv();
        
      }
      
    }
    
  }
  
  public String[] finishTransfer()
    throws IOException, FTPException {
    
    synchronized(this){
    
      LinkedList<String> resps = new LinkedList<String>();
      
      String resp = recv();
      
      while(resp.startsWith("250-")){
        
        resps.add(resp);
        resp = recv();
        
      }
      
      resps.add(resp);
      
      /* This seems like a type cast should work, but whateves
       * I'm just going to make it work here! */
      String[] r = new String[resps.size()];
      int i = 0;
      for(String s: resps)
        r[i++] = s;
      return r;
    
    }
    
  }     
    
  
  /* According to the ftp rfc, it is thread safe to 
   * read this socket asychronously from the control thread
   * only operations on the control thread need be synchronous. */
  public InputStream getFile(String path, char type)
    throws FTPException, IOException {
    
    setMode(type);
      
    synchronized(this){
      
      InputStream in = getPasvSocket().getInputStream();
      
      String resp = exec(String.format("RETR %s", path));
      
      if(!resp.startsWith("5")){
        return in;
      } else {
        in.close();
        throw new FTPException(resp.trim());
      }
    }
       
  } 
  
  public LineInput getList(String path)
    throws IOException, FTPException {
    
    synchronized(this){
      
      InputStream in = getPasvSocket().getInputStream();
      
      String resp = exec(String.format("LIST %s", path));
      
      if(resp.startsWith("125")){
        
        return new CRLFLineInput(in);
        
      } else {
        
        throw new FTPException(resp.trim());
        
      }
      
    }
    
  } 
  
  /* Returns a new FTPPasvSocket object based on the 227 response of the 
   * "PASV" ftp command. This is the preferred method for getting a
   * passive socket! */
  private FTPPasvSocket getPasvSocket() throws IOException, FTPException {
    
    synchronized(this){
      
      String resp = exec("EPSV");
      
      if(!resp.startsWith("229")){
                
        resp = exec("PASV");
        
      }
         
      if(resp.startsWith("227") || resp.startsWith("229")){
        
        return new FTPPasvSocket(resp);
        
      } else {
        
        throw new FTPException(resp.trim());
        
      }
      
    }
        
  }
  
  
  /* This method logs in the user, using the user's username and password strings */
  public void login(String username, String password) throws IOException, FTPException {
    
    String resp;
    
    synchronized(this) {
    
      this.send(String.format("USER %s", username));
      resp = this.recv();
      
      if(resp.startsWith("331")){
        
        this.send(String.format("PASS %s", password));
        resp = this.recv();
        
        if(resp.startsWith("230")){
          
          System.out.println(resp.trim());
          
        } else {
          
          throw new FTPException(resp);
          
        }
        
        
      } else {
        
        throw new FTPException(resp);
        
      }   
    
    }
    
  }
  
  public void noop() throws IOException, FTPException {
    
    flush();
    
  }
  
  
  public OutputStream putFile(String path, char mode)
    throws IOException, FTPException {
    
    setMode(mode);
    
    FTPPasvSocket sock = getPasvSocket();    
    OutputStream out = sock.getOutputStream();

    
    String resp = exec(String.format("STOR %s", path));
    
    if(resp.startsWith("125")){
      
      return out;
      
    } else {
      
      throw new FTPException(resp.trim());
      
    }
    
  }
    
  
  public String quote(String cmd) throws IOException {
    
    return exec(String.format("SITE %s", cmd));
    
  }
  
  protected String recv() throws IOException {
    
    String resp = serverrecv.recv();
    
    if(resp == null){
      throw new IOException("Recieved a null!");
    }
    
    System.out.println("=> " + resp.trim());
    
    return resp;
    
  }
  
  /*******************************************************************************
    *                                                                            *
    * send(String); Sends one message to the server, a message is considered all *
    *               text before the CRLF (carriage return, line feed), note that *
    *               if the message does not end with a CRLF, or if the message   *
    *               ends only with an LF this method will append the proper CRLF *
    *               secquence to the end of the message.                         *
    *                                                                            *
    * Throws: java.io.IOException                                                *
    *                                                                            *
    * ****************************************************************************/
  protected void send(String cmd) throws IOException {
    
    System.out.print("Executing command: ");
    
    if(cmd.startsWith("PASS")){
      System.out.println("PASS ********");
    } else {
      System.out.println(cmd);
    }
    
    serversend.send(cmd);
  }  
  
  
  /* set's the ftp mode to ascii */
  protected void setAscii() throws IOException, FTPException {
    
    setMode('A');
    
  }
  
  protected void setMode(char mode)
    throws IOException, FTPException {
    
    String resp = this.exec(String.format("TYPE %c", mode));
    
    if(!resp.startsWith("2")){
      throw new FTPException(resp.trim());
    }
  }
  
  /* Test method, to test this class while it's in development! */
  public static void main(String[] args) throws Exception {
    edu.niu.cs.students.mvstool.gui.main.MainWindow.main(args);   
  }


}


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
import java.net.Socket;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;
//import java.io.OutputStream;

import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.LineOutput;
import edu.niu.cs.students.netio.LFLineInput;
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
    
    /* I had a problem where the app usually worked, but some transfers would 
     * not, so I decided to ignore the second response from RETR requests, and
     * just flush the connection with noops before all commands. */
    flush();
    
    synchronized(this) {
          
      this.send(cmd);
      return recv();
      
    }
    
  }
  
  public String quote(String cmd) throws IOException {
    
    return exec(String.format("SITE %s", cmd));
    
  }
    
  /* Older name for exec, used in some of the older code */
  /* by older i mean several days our hours older */
  protected String doCMD(String cmd) throws IOException {
    
    return exec(cmd);
    
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
  
  public void noop() throws IOException, FTPException {
    
    flush();
    
  }
  
  /* Returns a new FTPPasvSocket object based on the 227 response of the 
   * "PASV" ftp command. This is the preferred method for getting a
   * passive socket! */
  private FTPPasvSocket getPasvSocket() throws IOException, FTPException {
    
    String resp = exec("PASV");
    
    if(resp.startsWith("227")){
      
      return new FTPPasvSocket(resp);
      
    } else {
      
      throw new FTPException(resp.trim());
      
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
  
  /* set's the ftp mode to ascii */
  protected void setAscii() throws IOException, FTPException {
    
    String resp = this.exec("TYPE A");    
    
    if(!resp.startsWith("2")){
      throw new FTPException(resp.trim());
    }
    
  }
  
  
  public LineInput getList(String path)
    throws IOException, FTPException {
    
    synchronized(this){
      
      InputStream in = getPasvSocket().getInputStream();
      
      String resp = exec(String.format("LIST %s", path));
      
      if(resp.startsWith("200")){
        
        return new LFLineInput(in);
        
      } else {
        
        throw new FTPException(resp.trim());
        
      }
      
    }
    
  }     
  

  
  /* According to the ftp rfc, it is thread safe to 
   * read this socket asychronously from the control thread
   * only operations on the control thread need be synchronous. */
  public InputStream getFile(String path, char type)
    throws FTPException, IOException {
    
    String resp = exec(String.format("TYPE %s", type));
    
    if(resp.startsWith("200")){
      
      synchronized(this){
      
        InputStream in = getPasvSocket().getInputStream();
        PushbackInputStream pin = new PushbackInputStream(in);
        
        resp = exec(String.format("RETR %s", path));
        
        System.out.println("Trying to read first byte!");
        
        byte[] buff = new byte[1];
        
        if(pin.read(buff) != 1){
          throw new IOException("Could not read first byte!");
        }
        
        System.out.println("Read first byte, un-reading!");
        
        pin.unread(buff);
        
        System.out.println("Getting the 150 resp!");
        
        /* Don't read the 250 response, just let the library
         * flush with noops */        
        if(!resp.startsWith("5")){
          return in;
        } else {
          in.close();
          throw new FTPException(resp.trim());
        }
      }
    } else {
      throw new FTPException(resp.trim());
    }    
  }  
      
      
  /*********************************************************************
    *                                                                  *
    * closes the ftp connection first executing a proper quit command. *
    *                                                                  *
    ********************************************************************/
  public void close() throws IOException {
    
    System.out.println(exec("QUIT"));
    serverconn.close();
    
  }


  /* Test method, to test this class while it's in development! */
  public static void main(String[] args) throws Exception {
    edu.niu.cs.students.mvstool.gui.MainFrame.main(args);   
  }


}


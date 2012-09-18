package edu.niu.cs.students.mvstool.mvsftp;
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
import java.io.InputStream;

/* Resources for understanding this code!
 * 
 * http://publib.boulder.ibm.com/infocenter/zos/v1r11/index.jsp?topic=/com.ibm.zos.r11.halu001/autosubmit.htm
 * http://publib.boulder.ibm.com/infocenter/zos/basics/index.jsp?topic=/com.ibm.zos.znetwork/znetwork_110.htm
 * http://www-01.ibm.com/support/docview.wss?uid=nas1418dba0e644f915086256bdc005f684f
 * */

import edu.niu.cs.students.mvstool.ftp.FTPClient;
import edu.niu.cs.students.mvstool.ftp.FTPException;


import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser;
import edu.niu.cs.students.mvstool.mvsftp.MVSJobListParser.Job;
import edu.niu.cs.students.mvstool.mvsftp.MVSDefaultJobListParser;

/* This extends the standard ftp client to enable the use of various
 * mvs ftp server specific site commands */
public class MVSFTPClient extends FTPClient {
  
  public MVSFTPClient(String hostname, int port) throws IOException {
    super(hostname, port);
       
  }
  
  /* ************************************************************************************
   * Documentation for this site command is provided at                                 *
   * http://www-01.ibm.com/support/docview.wss?uid=nas1418dba0e644f915086256bdc005f684f *
   **************************************************************************************/
  private void setMode(String mode) throws IOException, FTPException {
    
    String resp = exec(String.format("SITE FILEtype=%s", mode));
    /* Weird case convention copied from IBM documentation */
    /* I don't think it actually matters at all, seeing as the
     * mainframe is case insensative, but I'll follow the docs */
    
    if(!resp.startsWith("2")){
      throw new FTPException(resp.trim());
    } else {
      System.out.println(resp.trim());
    }
    
  }
  
  /* Changes the ftp server list mode to show the held jobs output
   * queue */
  private void setModeJES() throws IOException, FTPException {    
    setMode("jes");    
  }
  
  /* I implemented this for the future, for the moment it is not
   * used, the compiler loves to remind me of this fact ad-infinitum
   * I suppose I should find a way of suppressing those warnings! */
//  private void setModeSEQ() throws IOException, FTPException {
//    setMode("seq");
//  } 
  

  /* Pretty basic, get's the jobs from the server...
   * 
   *     NOTE: This fires off a thread, please make sure your parser 
   * is thread safe! */
  public void getJobs(MVSJobListParser parser) throws IOException, FTPException {
    setModeJES();    
    list("", parser);    
  }
  
  /* same as above */
  public void getJobs() throws IOException, FTPException {
    
    getJobs(new MVSDefaultJobListParser());
    
  }
  
  /* Determines if a job can be downloaded, if the user tried
   * to download an active job, the server will block, and thanks
   * to concurrency means the application will in general lock up
   * completely! */
  private void goodJob(Job job) throws FTPException {
    
    if(job.getStatus().equals("ACTIVE")){
      throw new FTPException("Cannot get active jobs, please wait for the job to terminate!");
    }
    
  }
  
    
  public InputStream getJob(Job job)
    throws IOException, FTPException {
    
    goodJob(job);
    setModeJES();
    return getFile(job.getID(), 'A');
    
  }
  
    
  
}
package edu.niu.cs.students.mvstool.mvsftp;
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

/* Resources for understanding this code!
 * 
 * http://publib.boulder.ibm.com/infocenter/zos/v1r11/index.jsp?topic=/com.ibm.zos.r11.halu001/autosubmit.htm
 * http://publib.boulder.ibm.com/infocenter/zos/basics/index.jsp?topic=/com.ibm.zos.znetwork/znetwork_110.htm
 * http://www-01.ibm.com/support/docview.wss?uid=nas1418dba0e644f915086256bdc005f684f
 * */

import edu.niu.cs.students.mvstool.ftp.FTPClient;
import edu.niu.cs.students.mvstool.ftp.FTPException;
import edu.niu.cs.students.mvstool.ftp.FTPOutputStream;

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
  private void setModeSEQ() throws IOException, FTPException {
    setMode("seq");
  } 
  

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
   * to concurrency needs the application will in general lock up
   * completely! */
  private void goodJob(Job job) throws FTPException {
    
    if(job.getStatus().equals("ACTIVE")){
      throw new FTPException("Cannot get active jobs, please wait for the job to terminate!");
    }
    
  }
  
  /* The FTPClient implementation has a default FTPOutputStream 
   * that is uses, so we have to re-implement the whole function twice!
   * what a bother */
  public void getJobOutput(Job job) throws IOException, FTPException {
    
    goodJob(job);    
    setModeJES();    
    get(job.getID(), 'A');
    
  }
  
  /* Any call to get will fire off a new thread, this is to make swing coding 
   * easier, and to improve the overall design of the application! */
  public void getJobOutput(Job job, FTPOutputStream out) throws IOException, FTPException {
    
    goodJob(job);
    setModeJES();
    get(job.getID(), 'A', out);
  }
  
  
  
    
  
}
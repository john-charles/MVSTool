package edu.niu.cs.students.mvs;
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
import java.io.OutputStream;

import edu.niu.cs.students.netio.LineInput;
import edu.niu.cs.students.netio.CRLFLineInput;

import edu.niu.cs.students.ftp.FTPClient;
import edu.niu.cs.students.ftp.FTPException;

import edu.niu.cs.students.mvs.ASAParser;
import edu.niu.cs.students.mvs.MVSJobListInput;

public class MVSClient extends FTPClient {
  
  public MVSClient(String hostname, int port) throws IOException, FTPException {
    super(hostname, port);
  }
  
  
  public String recv() throws IOException {
    return super.recv();
  }
  
  public void setModeJES()
    throws FTPException, MVSException, IOException {
    
    String resp = quote("FILEtype=jes");
    
    if(!resp.startsWith("200")){
      
      throw new MVSException(resp.trim());
      
    }
    
  }
  
  public MVSJobListInput getJobList()
    throws IOException, FTPException, MVSException {
    
    setModeJES();
    
    return new MVSJobListInput(getList(""));  
    
  }
  
  /* Determines if a job can be downloaded, if the user tried
   * to download an active job, the server will block, and thanks
   * to concurrency means the application will in general lock up
   * completely! */
  private void goodJob(MVSJob job) throws MVSException {
    
    if(job.getStatus().equals("ACTIVE")){
      throw new MVSException("Cannot get active jobs, please wait for the job to terminate!");
    }
    
  }
  
  public LineInput getJob(MVSJob job)
    throws IOException, FTPException, MVSException {
    
    goodJob(job);    
    setModeJES();
    
    return new ASAParser(new CRLFLineInput(getFile(job.getID(), 'A')));
    
  }
  
  public void purgeJob(MVSJob job)
    throws IOException, FTPException, MVSException {
    
    goodJob(job);
    setModeJES();
    
    delete(job.getID());
    
  }
  
  public OutputStream subJob(String name)
    throws IOException, FTPException, MVSException {
    
    setModeJES();
    
    return putFile(name,'A');
    
  }
    
  
}
  
  
  
  
package edu.niu.cs.students.mvs;

import java.io.IOException;
import java.io.InputStream;
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
  
  public OutputStream subJob()
    throws IOException, FTPException, MVSException {
    
    setModeJES();
    
    return putFile("",'A');
    
  }
    
  
}
  
  
  
  
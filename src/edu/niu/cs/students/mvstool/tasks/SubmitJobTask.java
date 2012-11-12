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
package edu.niu.cs.students.mvstool.tasks;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;

import edu.niu.cs.students.task.Task;

import edu.niu.cs.students.mvs.MVSClient;
import edu.niu.cs.students.ftp.FTPException;

import edu.niu.cs.students.mvstool.Utils;
import edu.niu.cs.students.mvstool.Profile;

import edu.niu.cs.students.mvstool.gui.GUIUtils;




public class SubmitJobTask extends Task {
  
  private File file;
  private String jobID;
  
  public SubmitJobTask(File file){
    
    this.file = file;
    
  }
  
  public void run() throws Exception {
    
    MVSClient mvs = Profile.getCurrentProfile().getMVSClient();
    InputStream jobInput = new FileInputStream(file);
        
    synchronized(mvs){
      
      /* This is very hacky, but let's just make it work... */
      byte[] job = new byte[(int)file.length()];
      String jobT;
      
      OutputStream jobStream = mvs.subJob(file.getName());
      
      jobInput.read(job);
      jobT = new String(job);
      jobT.replace("\n","\r\n");
      jobT.replace("\t", "    ");
      
      
      jobStream.write(jobT.getBytes());
      
      jobInput.close();
      jobStream.close();
      
      String[] resps = mvs.finishTransfer();      
      /* Pase a response that looks like:
       * "250-It is known to JES as JOB02192"
       */
      String base = "250-It is known to JES as ";
      for(int i = 0; i < resps.length; i++){
        
        if(resps[i].startsWith(base)){
          jobID = resps[i].replace(base, "");
          jobID = jobID.trim();
        }
        
      }      
      
    }   
    
  }
  
  public void failure(Exception e){
    
    e.printStackTrace();
    
  }
  
  public void success(){
    
    String mesg = "Successfully submitted " + file.getName() + 
      "\nas jes job '" + jobID + "' please click refresh!";
    
    GUIUtils.postMessage(mesg);
    
  }
  
}
    
    
    
    
  
  
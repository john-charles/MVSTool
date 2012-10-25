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




public class SubmitJobTask extends Task {
  
  private File file;
  
  public SubmitJobTask(File file){
    
    this.file = file;
    
  }
  
  public void run() throws Exception {
    
    MVSClient mvs = Profile.getCurrentProfile().getMVSClient();
    InputStream jobInput = new FileInputStream(file);
        
    synchronized(mvs){
      
      OutputStream jobStream = mvs.subJob();      
      Utils.copyStream(jobInput, jobStream);
      
      jobInput.close();
      jobStream.close();
      
      String[] resps = mvs.finishTransfer();
      
      for(int i = 0; i < resps.length; i++){
        
        System.out.println(resps[i]);
        
      }      
      
    }   
    
  }
  
  public void failure(Exception e){
    
    e.printStackTrace();
    
  }
  
  public void success(){
    
  }
  
}
    
    
    
    
  
  
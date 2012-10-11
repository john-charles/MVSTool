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

import java.util.List;
import java.util.ArrayList;
import java.util.LinkedList;

import edu.niu.cs.students.task.Task;

import edu.niu.cs.students.mvstool.Profile;

import edu.niu.cs.students.mvs.MVSJob;
import edu.niu.cs.students.mvs.MVSClient;
import edu.niu.cs.students.mvs.MVSJobListInput;
import edu.niu.cs.students.mvstool.gui.GUIUtils;
import edu.niu.cs.students.mvstool.gui.main.JobListTableModel;


public class GetJobListTask extends Task {
  
  List<MVSJob> jobs;
  JobListTableModel model;
  boolean suppressDialog;
  
  public GetJobListTask(JobListTableModel model){
    this(model, false);
  }
  
  public GetJobListTask(JobListTableModel model, boolean suppressDialog){
    
    this.model = model;
    this.suppressDialog = suppressDialog;
    this.jobs = new ArrayList<MVSJob>();
    
  }
  
  public void run() throws Exception {
    
    Profile profile = Profile.getCurrentProfile();
    MVSClient client = profile.getMVSClient();
    MVSJobListInput input = client.getJobList();
    
    MVSJob job = input.next();
    
    while(job != null){
      
      jobs.add(job);
      job = input.next();
      
    }   
    
    input.close();    
    client.recv();
    
    profile.putMVSClient(client);
    
  }
  
  public void failure(Exception e){
    
    if(e.getMessage().startsWith("550")){
      /* No messages found on server, so update the ui with 
       * an empty/blank list */
      model.update(new LinkedList<MVSJob>());
    }     
    
    if(!suppressDialog){
      GUIUtils.postMessage(e.getMessage());
    }
    
  }
  
  public void success(){
    
    model.update(jobs);
    
  }
  
  public static void main(String[] args){
    edu.niu.cs.students.mvstool.gui.main.MainWindow.main(args);
  }
  
}
  
  
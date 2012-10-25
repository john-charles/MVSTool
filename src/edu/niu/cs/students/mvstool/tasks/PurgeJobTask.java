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
 * MVSTool is distributed in the hope that it will be useful, but      *
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

import edu.niu.cs.students.task.Task;

import edu.niu.cs.students.mvs.MVSJob;
import edu.niu.cs.students.mvs.MVSClient;

import edu.niu.cs.students.mvstool.Profile;
import edu.niu.cs.students.mvstool.gui.GUIUtils;
import edu.niu.cs.students.mvstool.tasks.GetJobListTask;
import edu.niu.cs.students.mvstool.gui.main.JobListTableModel;



public class PurgeJobTask extends Task {
  
  List<MVSJob> jobs;
  JobListTableModel tableModel;
  
  public PurgeJobTask(List<MVSJob> jobs, JobListTableModel tableModel){
    
    this.jobs = jobs;
    this.tableModel = tableModel;
    
  }
  
  public void run() throws Exception {
    
    MVSClient client = Profile.getCurrentProfile().getMVSClient();
    
    for(MVSJob job: jobs){
      client.purgeJob(job);
    } 
    
  }
  
  public void failure(Exception e){
    
    GUIUtils.postMessage(e.getMessage());
    
  }
  
  public void success(){
    
    Task.fire(new GetJobListTask(tableModel, true));
    
  }
  
  
}
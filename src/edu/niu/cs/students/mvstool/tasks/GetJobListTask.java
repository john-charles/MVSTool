package edu.niu.cs.students.mvstool.tasks;

import java.util.List;
import java.util.ArrayList;

import edu.niu.cs.students.task.Task;

import edu.niu.cs.students.mvs.MVSJob;
import edu.niu.cs.students.mvs.MVSClient;
import edu.niu.cs.students.mvs.MVSJobListInput;

import edu.niu.cs.students.mvstool.Profile;

public class GetJobListTask extends Task {
  
  List<MVSJob> jobs;
    
  public GetJobListTask(){
    
    jobs = new ArrayList<MVSJob>();
    
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
    
  }
  
}
  
  
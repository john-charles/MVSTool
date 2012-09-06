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
import java.util.List;
import java.util.LinkedList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import edu.niu.cs.students.mvstool.ftp.FTPListParser;

public abstract class MVSJobListParser implements FTPListParser {
  
  public static final String[] JobNames = {
    "Name", "ID", "Owner", "Status", "Class", "Return Code", "Details"
  };
  
  public final class Job {
    
    /* I can't figure out how to use a java enum as an array index */
    /* So I am using a public final class instead  */    
    static final int Name   = 0;
    static final int ID     = 1;
    static final int Owner  = 2;
    static final int Status = 3;
    static final int Class  = 4;
    static final int RC     = 5;
    static final int Dets   = 6;
    
    
    
    private String[] details;
    
    private Job(String[] details){
      this.details = details;
    }
    
    public String getName(){
      return details[Name];
    }
    
    public String getID(){
      return details[ID];
    }
    
    public String getOwner(){
      return details[Owner];
    }
    
    public String getStatus(){
      return details[Status];
    }
    
    public String getclass(){
      return details[Class];
    }
    
    public String getReturnCode(){
      return details[RC];
    }
    
    public String toString(){
      
      String format = 
        "<MVSJob(Name: %s, ID: %s, Owner: %s, Status: %s, Class: %s, ReturnCode: %s)>";
      return String.format(format, getName(), getID(), getOwner(), getStatus(), 
                           getclass(), getReturnCode());
      
    }
    
    public String[] toArray(){
      return details;
    }
    
  }
  
  private static final String _p =
    "([A-Z0-9]+) +([A-Z0-9]+) +([A-Z0-9]+) +([A-Z]+) +([A-Z0-9]+) +RC=([0-9]+) +(.*)$";
  
  private static final Pattern jobPattern = Pattern.compile(_p);
  
  private List<Job> jobs;
    
  public MVSJobListParser(){
    
    jobs = new LinkedList<Job>();
    
  }
    
  /*******************************************************************************
    *                                                                            *
    * This method parses lines returned from the MVS ftp server, in the form of  *
    *                                                                            *
    * JOBNAME  JOBID    OWNER    STATUS CLASS                                    *
    * KC03M62  TSU02305 KC03M62  OUTPUT TSU      RC=0000 3 spool files           *
    *                                                                            *
    *  The first line being a header, and the next several lines being job       *
    *      descriptions.                                                         *
    *                                                                            *
    ******************************************************************************/
  public void parseLine(String line){
    
    String[] jobInfo = new String[7];
    
    Matcher matcher = jobPattern.matcher(line.trim());
    
    System.out.println(line.trim());
    
    if(matcher.find()){
      
      System.out.println("match found!");
      
      for(int i = 1; i < matcher.groupCount(); i++){
          
        //System.out.println("group(" + i + "): '" + matcher.group(i) + "'");
        jobInfo[i -1] = matcher.group(i);
          
      }
      
      
      jobInfo[6] = line.substring(matcher.end(6), line.length()).trim();
      
      Job job = new Job(jobInfo);
      
      System.out.println(job);
      
      jobs.add(job);
        
    }
    
  }
  
  public List<Job> getJobs(){
    
    return jobs;
    
  }
  
}
  
  
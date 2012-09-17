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
 * Cobertura is distributed in the hope that it will be useful, but    *
 * WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU    *
 * General Public License for more details.                            *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with MVSTool; if not, write to the Free Software              *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 *  
 * USA                                                                 *
 ***********************************************************************/
import java.util.List;
import java.util.LinkedList;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import edu.niu.cs.students.mvstool.ftp.FTPListParser;

public abstract class MVSJobListParser implements FTPListParser {
  
  public static final String[] JobNames = {
    "Name", "ID", "Owner", "Status", "Class", "Details"
  };
  
  /* This was originall supposed to just be an enum type thing, but
   * it sort of blew up in function, it is in fact one of the core
   * data structures of the whole application! */
  public final class Job {
    
    /* I can't figure out how to use a java enum as an array index */
    /* So I am using a public final class instead  */    
    static final int Name   = 0;
    static final int ID     = 1;
    static final int Owner  = 2;
    static final int Status = 3;
    static final int Dets   = 4;
    
    
    
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
    

    public String toString(){
      
      String format = 
        "<MVSJob(Name: %s, ID: %s, Owner: %s, Status: %s)>";
      return String.format(format, getName(), getID(), getOwner(), getStatus());
      
    }
    
    public String[] toArray(){
      return details;
    }
    
  }
  
  /* This is the job output format regular expression matching
   * KC03M62  TSU02305 KC03M62  OUTPUT TSU      RC=0000 3 spool files
   * more details are provided below */
  private static final String _p =
    "([A-Z0-9]+) +([A-Z0-9]+) +([A-Z0-9]+) +([A-Z]+) +([A-Z0-9]+).*$"; // +RC=([0-9]+) +(.*)$";
  
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
    
    if(line.trim().equals("JOBNAME  JOBID    OWNER    STATUS CLASS")){
      return;
    }
    
    String[] jobInfo = new String[7];
    
    Matcher matcher = jobPattern.matcher(line.trim());
    
    System.out.println(line.trim());
    
    if(matcher.find()){
      
      System.out.println("match found!");
      
      int i;
      
      for(i = 1; i <= matcher.groupCount(); i++){
          
        System.out.println("group(" + i + "): '" + matcher.group(i) + "'");
        jobInfo[i -1] = matcher.group(i);
          
      }
      
      i--;
      
      jobInfo[i] = line.substring(matcher.end(i), line.length()).trim();
      
      Job job = new Job(jobInfo);
      
      System.out.println(job);
      
      jobs.add(job);
        
    }
    
  }
  
  public List<Job> getJobs(){
    
    return jobs;
    
  }
  
}
  
  
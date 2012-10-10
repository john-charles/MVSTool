package edu.niu.cs.students.mvs;

import java.io.IOException;

import java.util.regex.Pattern;
import java.util.regex.Matcher;

import edu.niu.cs.students.mvs.MVSJob;

import edu.niu.cs.students.netio.LineInput;

public class MVSJobListInput {
  
  /* This is the job output format regular expression matching
   * KC03M62  TSU02305 KC03M62  OUTPUT TSU      RC=0000 3 spool files
   * more details are provided below */
  private static final String _p =
    "([A-Z0-9]+) +([A-Z0-9]+) +([A-Z0-9]+) +([A-Z]+) +([A-Z0-9]+).*$"; // +RC=([0-9]+) +(.*)$";
  
  private static final Pattern jobPattern = Pattern.compile(_p);
  
  private LineInput input;
    
  public MVSJobListInput(LineInput input){
    
    this.input = input;
    
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
  private MVSJob parseLine(String line){
    
    if(line.trim().equals("JOBNAME  JOBID    OWNER    STATUS CLASS")){
      return null;
    }
    
    String[] jobInfo = new String[7];
    
    Matcher matcher = jobPattern.matcher(line.trim());
    
    if(matcher.find()){
      
      //System.out.println("match found!");
      
      int i;
      
      for(i = 1; i <= matcher.groupCount(); i++){
          
        //System.out.println("group(" + i + "): '" + matcher.group(i) + "'");
        jobInfo[i -1] = matcher.group(i);
          
      }
      
      i--;
      
      jobInfo[i] = line.substring(matcher.end(i), line.length()).trim();
      
      return new MVSJob(jobInfo);
        
    }
    
    return null;
    
  }
  
  
  public MVSJob next() throws IOException {
    
    while(true){
      
      String line = input.recv();
      
      if(line == null){
        
        return null;
        
      } else {
        
        MVSJob job = parseLine(line);
        
        if(job != null){
          
          return job;
        }
      }
      
    }
    
  }
  
  public void close() throws IOException {
    
    input.close();
    
  }
  
}
  
  
  
  
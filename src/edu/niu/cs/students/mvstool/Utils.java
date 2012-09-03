package edu.niu.cs.students.mvstool;

public final class Utils {
  
  public static void sleep(int seconds){
    
    try{
      
      Thread.sleep(seconds * 1000);
      
    } catch(Exception ie){
    }
    
  }
  
}
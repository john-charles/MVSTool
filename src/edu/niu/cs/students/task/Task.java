package edu.niu.cs.students.task;

public abstract class Task {
  
  public static void fire(final Task task){
    
    new Thread(new Runnable(){
      
      public void run(){
        
        try {
          
          task.run();
          task.success();
          
        } catch(Exception e){
          
          task.failure(e);
        }
        
      }
      
    }).start();
    
  }
  
  public abstract void run()
    throws Exception;
  
  public abstract void success();
  public abstract void failure(Exception e);
  
}
  
  
  
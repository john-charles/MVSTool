package edu.niu.cs.students.mvstool.ftp;

public class FTPException extends Exception {
  
  FTPException(){
    super();
  }
  
  FTPException(String error){
    super(error);
  }
  
}
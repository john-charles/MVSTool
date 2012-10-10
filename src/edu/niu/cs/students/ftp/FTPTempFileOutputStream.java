package edu.niu.cs.students.ftp;

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

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.FileOutputStream;



public class FTPTempFileOutputStream {
  
  private File tempFile;
  private OutputStream out;
  
  public FTPTempFileOutputStream() throws IOException {
    
    tempFile = File.createTempFile("ftpGet",".dat");
    out = new FileOutputStream(tempFile);
    
  }
  
  public File getTempFile(){
    
    return tempFile;
    
  }
  
  public String toString(){
    return String.format("<FTPTempFileOutputStream(%s)>", tempFile.toString());
  }
  
  public void write(int b) throws IOException {
    out.write(b);    
  }
  
  public void success(){
    
    System.out.println("Download complete!");
    
  }
  
  public void failure(Exception e){
    
    e.printStackTrace();
    
  }
  
  public void close() throws IOException {
    out.close();
  }
  
  public static void main(String[] args) throws Exception {
    
    FTPTempFileOutputStream outs = new FTPTempFileOutputStream();
    
    System.out.println(outs);
    
  }
  
}
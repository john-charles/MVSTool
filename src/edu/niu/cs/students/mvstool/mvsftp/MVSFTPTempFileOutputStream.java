package edu.niu.cs.students.mvstool.mvsftp;
import java.io.IOException;
import edu.niu.cs.students.mvstool.ftp.FTPTempFileOutputStream;

public class MVSFTPTempFileOutputStream extends FTPTempFileOutputStream {
  
  private static final byte CR = 13;
  private static final byte LF = 10;
  
  public MVSFTPTempFileOutputStream() throws IOException {
    
    super();
    
  }
  
  public void write(int b) throws IOException {
    
    
    
    super.write(b);
    
        
  
  }
  
}
  
package edu.niu.cs.students.mvstool;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import java.util.Properties;

import edu.niu.cs.students.mvstool.ftp.FTPException;
import edu.niu.cs.students.mvstool.mvsftp.MVSFTPClient;

public class ConnectionProfile {
  
  private static final String UserShellFolders = 
    "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders";
  
  private static final int ftpTimeOut = 100;
  
  private String password;
  private Properties profile;
  
  long ftpLastUsed;
  boolean ftpIsConnected;
  MVSFTPClient ftpClient;
  
  static ConnectionProfile gProfile;
  
  static public ConnectionProfile getConnectionProfile(){
    
    if(gProfile == null){
      
      gProfile = new ConnectionProfile();
      
    }
    
    return gProfile;
    
  }
  
  private static long getTime(){
    
    return (long) (System.currentTimeMillis() / 1000L);
  
  }
  
  private ConnectionProfile(){
    this("Default");    
  }
  
  private ConnectionProfile(String profileName){
    
    ftpLastUsed = 0;
    loadProfile();    
    
  }
  
  /* This method finds the appropriate directory to save
   * configureation files in on linux hosts it complies with 
   * http://standards.freedesktop.org/basedir-spec/basedir-spec-latest.html
   * */  
  private File findConfigFileLinux(){
    
    File xdg_config_home;
    
    try {
      
      xdg_config_home = new File(System.getenv("XDG_CONFIG_HOME"));
      
    } catch(NullPointerException e){
      
      xdg_config_home = new File(System.getProperty("user.home"), ".config");
      
    }
    
    return new File(xdg_config_home, "mvstool");
    
  }
  
  private int skipBlank(String line, int idx){
    
    while(line.charAt(idx) == ' ') idx++;
    
    return idx;
    
  }    
  
  private String parseLine(String line){
    
    int idx = 0;
    String[] parsed = new String[3];
    
    while(line.contains("  ")){
      line = line.replace("  "," ");
    }
    
    if(line.equals("")) return null;
    
    parsed[0] = new String();
    
    while(line.charAt(idx) != ' '){
      parsed[0] = parsed[0] + line.charAt(idx++);
    }
    
    idx = skipBlank(line, idx);    
    parsed[1] = new String();
    
    while(line.charAt(idx) != ' '){
      parsed[1] = parsed[1] + line.charAt(idx++);
    }
    
    parsed[2] = new String();
    
    while(true){
      try {
        parsed[2] = parsed[2] + line.charAt(idx++);
      } catch(StringIndexOutOfBoundsException e){
        break;
      }
    }
    
    if(parsed[0].equals("Personal")){
      return parsed[2];
    } else {
      return null;
    }
  }
    
  
  private String findPersonal(){
    
    String[] args = new String[]
      {"REG", "QUERY", UserShellFolders };
    
    try {
      
      Runtime run = Runtime.getRuntime();
      Process reg = run.exec(args);
      
      InputStream in = reg.getInputStream();
      //InputStream er = reg.getErrorStream();
      
      String input = new String();
      byte[] bytes = new byte[1];
                  
      while(in.read(bytes, 0, bytes.length) > 0){
        input = input + new String(bytes);                
      }
      
      for(String line: input.split("\n")){
        String path = parseLine(line.trim());
        if(path != null){
          return path.trim();
        }
      }
      
    } catch(Exception e){
      
      e.printStackTrace();
      
      
    }
    return null;
  }
  
  private String resolveWindowsEnv(String string){
    
    String userprofile = System.getenv("USERPROFILE");
    
    string = string.replace("%USERPROFILE%", userprofile);
    
    return string;
  }
  
  private boolean winProfileRoaming(){
    return false;
  }

  private File findConfigFileWindows(){
    
    if(winProfileRoaming()){
      
      String personal = findPersonal();
      
      File appdata = new File(resolveWindowsEnv(personal));
      
      return new File(appdata, "MVSTool");
      
      
    } else {
      
      File appdata = new File(System.getenv("APPDATA"));
      
      return new File(appdata, "mvstool");
      
    }
    
  }
    
  
  private File findConfigFile(){
    
    File configFile;
    
    System.out.println(System.getProperty("os.name"));
    
    if(System.getProperty("os.name").equals("Linux")){
      configFile = findConfigFileLinux();
    } else if(System.getProperty("os.name").startsWith("Windows")){
      //* Place holder for windows!
      configFile = findConfigFileWindows();
    } else {
      configFile = new File(".").getAbsoluteFile();
    }
    
    return new File(configFile, "profiles.lst");
    
  }
  
  private void loadProfile(){
    
    File configFile = findConfigFile();
    
    try {
      
      InputStream in = new FileInputStream(configFile);
      profile = new Properties();
      profile.load(in);
      
      in.close();
      
    } catch(IOException ie){
      
      profile = new Properties();
      
      
    }
    
  }
  
  private void saveProfile(){
    
    File configFile = findConfigFile();
    File configDir = configFile.getParentFile();
    
    if(!configDir.exists()){
      configDir.mkdirs();
    }
    
    try {
      
      OutputStream out = new FileOutputStream(configFile);
      this.profile.store(out, "");
      out.close();
      
    } catch(IOException ie){
      
    }
    
  }   
  
  private boolean isConnected(){
    
    if(getTime() - ftpLastUsed > ftpTimeOut){
      if(ftpClient != null){
        ftpClient.close();
      }
      return false;
    } else if(ftpClient == null){
      return false;
    } else {
      return true;
    }
    
  }
  
  
  public MVSFTPClient getFTPClient() throws IOException, FTPException {
    
    System.out.println(String.format("Using username: %s, password: %s", getUsername(), getPassword()));
    
    if(!isConnected()){
      
      System.out.println("Connection is stale, creating new ftp connection!");
      
      ftpClient = new MVSFTPClient(getHostname(), getHostport());
      //ftpClient.login(getUsername(), getPassword());
    } 
    
    ftpLastUsed = getTime();
    
    return ftpClient;
    
  } 
    
  
  public String getHostname(){
    
    return profile.getProperty("hostname");
    
  }
  
  public void setHostname(String hostname){
    profile.setProperty("hostname", hostname);
    saveProfile();
  }
  
  public int getHostport(){
    String hostport = profile.getProperty("hostport");
    
    try {
      
      return Integer.parseInt(hostport);
      
    } catch(Exception e){
      
      return 21;
      
    }
    
  }
  
  public void setHostport(int port){
    
    profile.setProperty("hostport", Integer.toString(port));
    
    saveProfile();
    
  }
  
  public void setHostport(String port){
    
    profile.setProperty("hostport", port);
    
    saveProfile();
    
  }
  
  public String getUsername(){
    
    return profile.getProperty("username");
    
  }
  
  public void setUsername(String username){
    
    profile.setProperty("username", username);
    
    saveProfile();
    
  }
  
  public String getPassword(){
    
    return password;
    
  }
  
  public void setPassword(String password){
    
    this.password = password;
    
  }
    
    
  
  public static void main(String[] args){
    
    getConnectionProfile();
    
    
    
  }
  
  
}
  
  
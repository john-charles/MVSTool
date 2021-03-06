package edu.niu.cs.students.mvstool;
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
 * MVSTool is distributed in the hope that it will be useful, but    *
 * WITHOUT ANY WARRANTY; without even the implied warranty of          *
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU    *
 * General Public License for more details.                            *
 *                                                                     *
 * You should have received a copy of the GNU General Public License   *
 * along with MVSTool; if not, write to the Free Software              *
 * Foundation, Inc., 59 Temple Place, Suite 330, Boston, MA 02111-1307 *  
 * USA                                                                 *
 ***********************************************************************/
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;


import java.util.Properties;

import edu.niu.cs.students.ftp.FTPException;
import edu.niu.cs.students.mvs.MVSClient;

public class Profile {
  /* Scary, a registry key... this is not used, but I was attempting to make the
   * application "H:" drive aware, in that it would store it's temporary files there
   * instead of %APPDATA%/mvstool this was a disaster, but I may work on it in the
   * future, so I left the string with the registry key in... */
  //private static final String UserShellFolders = 
  //  "HKCU\\Software\\Microsoft\\Windows\\CurrentVersion\\Explorer\\User Shell Folders";
  
  
  private String password;
  private Properties profile;
  
  long ftpLastUsed;
  boolean ftpIsConnected;
  MVSClient ftpClient;
  
  static Profile gProfile;
  
  /* Returns the singleton connection profile instance... */
  static public Profile getCurrentProfile(){
    
    if(gProfile == null){
      
      gProfile = new Profile();
      
    }
    
    return gProfile;
    
  }
  
  private static long getTime(){
    
    return (long) (System.currentTimeMillis() / 1000L);
  
  }
  
  private Profile(){
    this("Default");    
  }
  
  /* I originally wanted to have a method whereby different connection
   * profiles could be specified by the user, I may still do that... 
   * for now profileName is ignored... */
  private Profile(String profileName){
    
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
  
  
  
  
  

  private File findConfigFileWindows(){
    
    {
      
      /* If this returns null, there are bigger problems with your
       * computer than I can possibly solve from here... */
      File appdata = new File(System.getenv("APPDATA"));
      
      return new File(appdata, "mvstool");
      
    }
    
  }
    
  
  /* Gets the directory to use for configuration data, note
   * that this is the XDG_* dir on linux/open desktops and the
   * 
   * on windows it gets the "%APPDATA%" env variable...
   * http://blogs.msdn.com/b/patricka/archive/2010/03/18/where-should-i-store-my-data-and-configuration-files-if-i-target-multiple-os-versions.aspx
   * */
  private File findConfigFile(){
    
    File configFile;
           
    if(System.getProperty("os.name").equals("Linux")){
      configFile = findConfigFileLinux();
    } else if(System.getProperty("os.name").startsWith("Windows")){
      configFile = findConfigFileWindows();
    } else {
      /* Place holder for everything else, anyone want to lend me a mac? */
      /* I guess I should change this to $HOME/.mvstool ? */
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
      /* NOTE: if this information isn't saved, the user will
       * need to re-enter it next time, although this is 
       * inconvenient, this is not really an error, and so I
       * will not treat it as such! */
    }
    
  }   
  
  
  public MVSClient getMVSClient() throws IOException, FTPException {
    
    if(ftpClient == null){
      ftpClient = new MVSClient(getHostname(), getHostport());
      ftpClient.login(getUsername(), getPassword());
    }
    
    return ftpClient;
   
  } 
  
  public void putMVSClient(MVSClient client){
    
    
    
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
  
  private static final String lastSaveKey = "LastSaveLocation";
  
  public File getLastOutputSaveFile(){
    
    String path = profile.getProperty(lastSaveKey);
    
    if(path != null){
      
      return new File(path);
      
    } else {
      
      return null;
      
    }
    
  }
  
  public void setLastOutputSaveFile(File location){
    
    profile.setProperty(lastSaveKey, location.toString());    
    saveProfile();
    
  }
  
  private String lastJobKey = "LastJobPath";
  
  public void setLastJob(File lastJob){
    
    profile.setProperty(lastJobKey, lastJob.toString());
    saveProfile();
    
  }
  
  public File getLastJob(){
    
    String fileString = profile.getProperty(lastJobKey);
    if(fileString == null){
      return null;
    } else {
      return new File(fileString);
    }
    
  }
    
  
  private static final String dontUseGTKLNF = "UseGTKLookAndFeel";
  /* This is all about allowing the user to disable the GTK Look
   * and Feel if it doesn't fit with their desktop...
   * 
   * TODO: Add a ui element so that this is click configurable...*/
  public boolean useGTKLookAndFeel(){
    
    String useGTKLookAndFeel = profile.getProperty(dontUseGTKLNF);
    
    if(useGTKLookAndFeel != null){
      return useGTKLookAndFeel.toLowerCase().equals("true");
    }
    
    return true;
    
  }
    
  public void setDontUseGTKLookAndFeel(boolean val){
    
    profile.setProperty(dontUseGTKLNF, Boolean.toString(!val));
    saveProfile();
    
  }
  
  
  public static void main(String[] args){
    edu.niu.cs.students.mvstool.gui.main.MainWindow.main(args);
  }
  
  
}
  
  
ABOUT THIS PROGRAM

    The purpouse of this program is to manage, and view jobs on an IBM mainframe via
    the ftp api.
    
    Functions:
        
        List all mvs jobs. (Functional)
        
        Download mvs job output. (Functional)
        
        Delete "purge" mvs job output. (Functional, but only one at a time.)
        
        Upload "submit" an mvs job. (This code is implemented, but does needs debugging as it fails to upload the entire file with an ftp too few bytes received error. This was a low priority for me when I originally wrote the application. I no longer access to the mainframe to do further debugging, but I am available to answer questions of anyone who wishes complete this work.)
        
    This code is released under the terms of the GNU GPLv2.
                
MY VISION FOR THE FUTURE:
    
    I initially wrote this program while taking a course on mainframe programing at NIU. It is and was my hope that future students at northern could continue maintaining and updating this project into the future, perhaps of their own initutive perhaps with the aid of faculty. This software is a donation to the community and for various reasons I can no longer mantaine it. But if others wish to work on it/improove it and submit patches I am willing to function as a project manager and curate further patches if desired. To that end I am also willing to answer any questions any future contributor might have. My hope is that this project does not die, I am proud of the work herein and I put in substancial effort to make the design as modular and extensable as possible. This could even become a COBOL and IBM 360 Assembler IDE with future contributions.             
             
        
HOW TO COMPILE THIS PROGRAM...
  
  download a copy of the application dr. java... 
  from http://drjava.sourceforge.net/ run it. 
  
  click "Project" then "open", navigate to the directory 
  in which you unpacked these sources.
  
  and click on the file mvsjobtool.drjava NOTE: that's my original name
  for this application.
  
  once the project loads click on the button "Compile Project" in the toolbar
  then click on [ Source Files ] src/edu/niu/cs/students/mvstool/gui/MainFrame.java 
  
  and click run. 
  
  this should run the application....
  
HOW TO CREATE A JAR...
  
  after you have completed the previous step:
    from the project menu 
    select "Create Jar File From Project"
  
    then check mark the radio button "jar classes"  and "make executable"
    under make executable
       enter "edu.niu.cs.students.mvstool.gui.MainFrame" select where you would
    like the output jar to be stored and click  ok. 
  
  If your operating system is unix like, go to the output jar and set its executable bit to on.
  with something like chmod +x mvstool.jar on windows you can skip this step.
  
  
  your done!

Address comments/questions/suggestions to z1552897@students.niu.edu

Notes about version numbers:
    I intend to follow a Google Chrome/Firefox 4+ numbering convention
    with A second label of "testing" or "stable" to denote project
    project stability, for exampe 1.0 testing is the 1.0 application
    in a testing form. 
    1.0 stable is the 1.0 version which has been determined to be stable
    enough to release to the general user base.


package edu.niu.cs.students.mvs;
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

/* This was originall supposed to just be an enum type thing, but
 * it sort of blew up in function, it is in fact one of the core
 * data structures of the whole application! */
public class MVSJob {
  
  public static final String[] names =
  {"Name", "Job ID", "Owner", "Status", "Details"};
    
  /* I can't figure out how to use a java enum as an array index */
  /* So I am using a public final class instead  */    
  static final int Name   = 0;
  static final int ID     = 1;
  static final int Owner  = 2;
  static final int Status = 3;
  static final int Dets   = 4;
  
  
  
  private String[] details;
  
  public MVSJob(String[] details){
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
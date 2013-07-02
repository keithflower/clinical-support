/*
 *  Units for Android
 *  
 *  Copyright (C) 2011, Keith Flower
 *  
 *  Based on GNU units, a program for units conversion
 *  Copyright (C) 1996, 1997, 1999, 2000, 2001, 2002, 2003, 2004, 2005, 2006,
 *                2007, 2009
 *  Free Software Foundation, Inc
 *
 *  This program is free software; you can redistribute it and/or modify
 *  it under the terms of the GNU General Public License as published by
 *  the Free Software Foundation; either version 3 of the License, or
 *  (at your option) any later version.
 * 
 *  This program is distributed in the hope that it will be useful,
 *  but WITHOUT ANY WARRANTY; without even the implied warranty of
 *  MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *  GNU General Public License for more details.
 *
 *  You should have received a copy of the GNU General Public License
 *  along with this program; if not, write to the Free Software
 *  Foundation, Inc., 51 Franklin Street, Fifth Floor, 
 *  Boston, MA 02110-1301 USA
 *     
 *  GNU Units was written by Adrian Mariano (adrian@cam.cornell.edu)
 */

package org.quexotic.gnuunits;

public class CircularBuffer<T> {

  private T[] 	buf;

  private int 	nextout;
  private int 	nextin;
  private int 	max;
  private int 	size;
  private int	filled;

  @SuppressWarnings("unchecked")
  public CircularBuffer(int n) {
    buf = (T [] ) new Object[n];
    nextin 	= 0;					// next free slot where an add will place an item
    nextout = 0;					// next slot that will be returned by a get
    max 		= n - 1;				// max possible index in this circular buffer
    filled	= 0;					// number of indices used in this circular buffer
    size    = n;
  }

  public void add (T o) {
	  buf[nextin] = o;
	  nextout = nextin;
	  if (nextin + 1 > max) {
		  nextin = 0;
	  }
	  else {
		  nextin = nextin + 1;
		  filled = filled + 1 > size ? size : filled + 1;
	  }
  }
  
  public int len () {
	  return filled;
  }
  
  public boolean isempty() {
	  return filled == 0;
  }
  
  public T get (int i) {
	  return buf[i];
  }
  
  public T get ( ) {
	if (isempty()) return null;
	T o = buf[nextout];
	if (nextout == 0) {
		nextout = filled - 1;
	}
	else {
		nextout = nextout - 1;
	}
    return o;
  }

  public String toString() {
	  StringBuffer s = new StringBuffer();
	  for (int i = 0; i < buf.length; i++) {
		  s.append(buf[i] + "\n");
	  }
	  return s.toString();
  }
}
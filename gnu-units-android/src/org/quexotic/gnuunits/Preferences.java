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
import android.os.Bundle;
import android.preference.PreferenceActivity;

public class Preferences extends PreferenceActivity {

	@Override
	public void onCreate(Bundle savedInstanceState) {
	    super.onCreate(savedInstanceState);
	    addPreferencesFromResource(R.xml.preferences);
	}
}

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
 *  The original GNU Units was written by Adrian Mariano (adrian@cam.cornell.edu)
 */

package org.quexotic.gnuunits;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.quexotic.gnuunits.R;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.text.format.DateUtils;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.MultiAutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;
import android.os.Bundle;
import android.os.Environment;
import android.os.SystemClock;
import android.preference.PreferenceManager;

public class GnuUnits extends Activity
{
	
	private TextView mYouHaveText 						= (TextView) null;
	private TextView mYouWantText 						= (TextView) null;
	private TextView mResultsText 						= (TextView) null;
	private boolean history 								= false;
	private String uom[] 								= {};
	private String defaultUnitsFileName					= null;
	private MultiAutoCompleteTextView actvh 				= (MultiAutoCompleteTextView) null;
	private MultiAutoCompleteTextView actvw 				= (MultiAutoCompleteTextView) null;
	private static String dataDir						= "/Android/data/org.quexotic.gnuunits/files";
	private static String youHaveFileName 				= "/gnuunits_you_have";
	private static String youWantFileName 				= "/gnuunits_you_want";
	private static String resultsFileName 				= "/gnuunits_results";
    private long mLastPress 								= -1;
    private static final long BACK_THRESHOLD 			= DateUtils.SECOND_IN_MILLIS / 2;
    private static CircularBuffer<String> youHaveHistory = new CircularBuffer<String>(100);
    private static CircularBuffer<String> youWantHistory = new CircularBuffer<String>(100);
    private static CircularBuffer<String> resultsHistory = new CircularBuffer<String>(100);
	private String flags									= null;
	private String endTag								= "\n;;;\n";
	SharedPreferences preferences;
	  
	private void outputToFile(String fileName, CircularBuffer<String> vs, String encoding) {
		File newFile = new File(Environment.getExternalStorageDirectory() + dataDir + fileName);
		try {
			newFile.createNewFile();
			try {
				FileOutputStream of = new FileOutputStream(newFile);
				for (int i = 0; i < vs.len(); i++) {
					of.write(vs.get(i).getBytes(encoding));
					of.write(endTag.getBytes(encoding));
				}
				of.close();
			} catch(FileNotFoundException e) {
				//
			}
		} catch(IOException e) {
			//
		}
	}
	
	private void readHistory (String fileName, CircularBuffer<String> cb) throws IOException {
		BufferedReader br = (BufferedReader) null;
		try {
			br = new BufferedReader(new FileReader(Environment.getExternalStorageDirectory() + dataDir + fileName));
			String l;
			StringBuffer sb = new StringBuffer();
			while ((l = br.readLine()) != null) {
				// "History" items can be multiline, so read history item 
				// until a history entry terminator: endTag.
				if (l.equals(";;;")) {
					cb.add(sb.toString());
					sb = new StringBuffer();
				}
				else {
					sb.append(l + "\n");
				}
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
			if (br != null) br.close();
		}
	}
	
	public void onPause() {
		if (history) {
			outputToFile(youHaveFileName, youHaveHistory, "ISO-8859-1");
			outputToFile(youWantFileName, youWantHistory, "ISO-8859-1");
			outputToFile(resultsFileName, resultsHistory, "ISO-8859-1");
		}
		super.onPause();
	}
	
	public void onResume () {
		// Adjust the main activity based on preferences. This function is called at
		// startup, on reentry, and after preferences are changed.
		super.onResume();
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
		int threshold = Integer.parseInt(preferences.getString ("threshold", "0"));
		flags = preferences.getString ("flags", "");
		
		// For convenience, if any of the flag tokens are the names of the 
		// two sample units files, we prepend the default path (so that the
		// user doesn't have to specify the whole path in Preferences.
		flags = flags.replaceAll("fun.dat", Environment.getExternalStorageDirectory() + dataDir + "/fun.dat");
		flags = flags.replaceAll("med.dat", Environment.getExternalStorageDirectory() + dataDir + "/med.dat");
		
		history = preferences.getBoolean ("history", false);
		if (threshold > 0) {
			if ((uom != null) && (uom.length > 0)) {
				Intent targetIntent = new Intent(getBaseContext(), GnuUnits.class);
				startActivity(targetIntent);
				this.finish();
				android.os.Process.killProcess(android.os.Process.myPid());
			}
			uom=null;
			
			// Initialize autocomplete - to get a list of unit names for autocomplete. 
			// We pass in a dummy conversion in order to effectively initialize the native 
			// code, eg read in the units file, set up prefixes, etc. This
			// inefficiently reinitializes the unit names regardless of whether they have
			// actually changed (they actually should change only with specification of a 
			// new units file via the -f option).
			String youhave = "\'" + "liter" + "\'";
			String youwant = "\'" + "" + "\'";
			String input = flags + youhave + youwant;
			String tokens[] = tokenize(input);
			String argv[] = new String[tokens.length + 1];
			// The first argument to a native Unix app is the name of the app.
			argv[0] = "units";
			for (int i = 0; i < tokens.length; i++) {
				argv[i+1] = tokens[i];
			} 
			int argc = argv.length;
			String s = unitsJNI(defaultUnitsFileName, input, argc, argv);
			if (s.contains("Definition")) {
				// TODO:
				// 'Definition' returned implies a successful conversion was done - 
				// if this was not returned, then something went wrong, e.g. a bad
				// units file, or a flag/option was messed up somehow. Note that at
				// present, errors taking place in the native code are not all accompanied
				// by a printed error string. For example, a bad units file *will* return
				// an error string because we changed that 'fprintf (stderr...)' call to a 
				// 'printf(...)' statement - this should be done for all other errors as well.
				String unames = unitnamesJNI("");
				uom = unames.split(" ");
				Arrays.sort(uom);
			}
			else {
				// Some kind of error occurred probably related to flags/options set improperly.
    			Toast.makeText(this, s, Toast.LENGTH_LONG).show();
			}
		}
		if ((actvh) == null && (uom != null)) {
			// Autocompletion was off, turn it on.
			ArrayAdapter<String> ad = new ArrayAdapter<String> (this, android.R.layout.simple_dropdown_item_1line, uom);
			actvh = (MultiAutoCompleteTextView)findViewById(R.id.youhave);
			actvw = (MultiAutoCompleteTextView)findViewById(R.id.youwant);
			actvh.setThreshold(threshold);
			actvh.setAdapter(ad);
			actvh.setTokenizer(new SpaceTokenizer());
			actvw.setThreshold(threshold);
			actvw.setAdapter(ad);
			actvw.setTokenizer(new SpaceTokenizer());
    		}
		if (threshold == 0 && (actvh != null && (actvw != null))) {
			// Autocompletion was on, turn it off
			actvh.setAdapter((ArrayAdapter<String>) null);
			actvw.setAdapter((ArrayAdapter<String>) null);
			actvh = (MultiAutoCompleteTextView) null;
			actvw = (MultiAutoCompleteTextView) null;
		}
		if (history && (youHaveHistory.len() == 0)) {
			try {
				readHistory(youHaveFileName, youHaveHistory);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (history && (youWantHistory.len() == 0)) {
			try {
				readHistory(youWantFileName, youWantHistory);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (history && (resultsHistory.len() == 0)) {
			try {
				readHistory(resultsFileName, resultsHistory);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
    	// If user keeps track of history and we have some history, cycle through history
    	// when back key is pressed.
    	if ((keyCode == KeyEvent.KEYCODE_BACK) && history) {
    		// Compare against last pressed time, and if user hit "back" multiple times
    		// in quick succession, we just do regular "back" processing
    		long currentPress = SystemClock.uptimeMillis();
    		if (currentPress - mLastPress < BACK_THRESHOLD) {
    			return super.onKeyDown(keyCode, event);
    		}
    		mLastPress = currentPress;
    		mYouHaveText = (TextView)findViewById(R.id.youhave);
    		mYouWantText = (TextView)findViewById(R.id.youwant);
    		mResultsText = (TextView)findViewById(R.id.output);
    		if (mYouHaveText.hasFocus()) {
    			if (!youHaveHistory.isempty()) {
    				mYouHaveText.setText(youHaveHistory.get().trim());
    			}
    		}
    		if (mYouWantText.hasFocus()) {
    			if (!youWantHistory.isempty()) {
    				mYouWantText.setText(youWantHistory.get().trim());
    			}
    		}
    		if (mResultsText.hasFocus()) {
    			if (!resultsHistory.isempty()) {
    				mResultsText.setText(resultsHistory.get().trim());
    			}
    		}
    		return true;
    	}
    	// Otherwise, just do regular keydown processing
    	return super.onKeyDown(keyCode, event);
    }

    private void units_file_uncompress (String filename, int resource) {
    		// Note: assumes the presence of an SD card!
	    	try {
	    		// Make a directory for data files (this is done only for 2.1 compatibility)
	    		File odir = new File (Environment.getExternalStorageDirectory() + dataDir);
	    		odir.mkdirs();
	    		File of = new File(Environment.getExternalStorageDirectory() + dataDir, filename);
	    		if (! of.exists()) {
	    			InputStream ins = getResources().openRawResource(resource);
	    			byte[] buffer = new byte[ins.available()];
	    			ins.read(buffer);
	    			ins.close();
	    			FileOutputStream fos = new FileOutputStream(of);
	    			fos.write(buffer);
	    			fos.close();
	    		}
	    	}
	    	catch (IOException e) {
	    		// TODO - this message won't actually pop up if error occurs early
	    		Toast.makeText(this, "Couldn't initialize units data file: " + filename, Toast.LENGTH_LONG);
	    	}
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
    		super.onCreate(savedInstanceState);
    		
        	// The application is packaged with units file(s) compressed 
    		// in res/raw. If the uncompressed file(s) don't already exist (ie this is
    		// the first time this application is run), we open here and write (uncompressed) 
    		// to the external files directory. 
    		units_file_uncompress("units.dat", R.raw.units_file);
	    File of = new File(Environment.getExternalStorageDirectory() + dataDir, "units.dat");
	    defaultUnitsFileName = of.getAbsolutePath();
		units_file_uncompress("med.dat", R.raw.med_file);
    		units_file_uncompress("fun.dat", R.raw.fun_file);

    		setContentView(R.layout.main);          	

        	mYouHaveText = (TextView)findViewById(R.id.youhave);
        	mYouWantText = (TextView)findViewById(R.id.youwant);
        	mResultsText = (TextView)findViewById(R.id.output);
        	
        System.loadLibrary("gnu-units");

        	final Button calcButton = (Button) findViewById(R.id.calcbutton);
        	calcButton.setOnClickListener(new OnClickListener() {
        		public void onClick(View v) {
        			String youhave = mYouHaveText.getText().toString();
        			String youwant = mYouWantText.getText().toString();
        			if (youhave.length() > 0) {
        				String input = flags + "\'" + youhave + "\'" + "\'" + youwant + "\'";

        				// From the user's input, construct as valid a legacy (native) argv as possible. 
        				String tokens[] = tokenize(input);
        				String argv[] = new String[tokens.length + 1];
        				// The first argument to a native unix app is the name of the app.
        				argv[0] = "units";
        				for (int i = 0; i < tokens.length; i++) {
        					argv[i+1] = tokens[i];
        				} 
        				int argc = argv.length;
        				// Call the units app
        				String results = unitsJNI(defaultUnitsFileName, input, argc, argv);

        				// Set the output view to the results obtained. 
    					mResultsText.setText(results.trim());
    					
    					// Save history of input, if desired (set via preferences).
    					if (history) {
    						youHaveHistory.add(youhave);
    						youWantHistory.add(youwant);
    						resultsHistory.add(results);
    					}
        			}
        			else {
        				mResultsText.setText("");
        			}
        		}  
        	});
    }

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.menu, menu);
		return true;
	}
	
	// This method is called once the menu is selected
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		Intent i; 
		switch (item.getItemId()) {
		case R.id.share:
	    		final Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
	    		shareIntent.setType("text/plain");
	        	shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Units conversion");
	        	shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, 
	        			mYouHaveText.getText().toString() + " " +
	        			mYouWantText.getText().toString() + " " +
	        			mResultsText.getText().toString());
	        	startActivity(Intent.createChooser(shareIntent, "Send via..."));
	        	break;
		case R.id.preferences:
			i = new Intent(GnuUnits.this, Preferences.class);
			startActivity(i);
			break;
		case R.id.help:
			i = new Intent(GnuUnits.this, Help.class);
			startActivity(i);
			break;
		case R.id.about:
			i = new Intent(GnuUnits.this, About.class);
			startActivity(i);
			break;

		}
		return true;
	}

	// Tokenize a string as start to constructing a native-style command line list of arguments
	// ie, argv[]. We keep sequences of characters that aren't spaces or quotes, and 
	// sequences of characters that begin and end with a quote, with no quotes 
	// in between, for two kinds of quotes ("" and '').    
	private String[] tokenize (String s) {
		List<String> lMatch = new ArrayList<String>();
		Pattern regex = Pattern.compile("[^\\s\"']+|\"([^\"]*)\"|'([^']*)'");
		Matcher m = regex.matcher(s);
		while (m.find()) {
		    if (m.group(1) != null) {
		        // Keep double-quoted string, sans quotes
		        lMatch.add(m.group(1));
		    } else if (m.group(2) != null) {
		        // Keep single-quoted string, sans quotes
		        lMatch.add(m.group(2));
		    } else {
		        // Keep unquoted words
		        lMatch.add(m.group());
		    }
		}
		String[] sa = (String[]) lMatch.toArray(new String[lMatch.size()]);
		return (sa);
	}
	

    // Prototype for units native method
    public native String unitsJNI(String name, String input, int argc, String[] argv);
    public native String unitnamesJNI(String pat);
    public native String resetJNI(String pat);
    
}

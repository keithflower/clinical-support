package quexotic.org;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SeekBar;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import com.google.android.c2dm.C2DMessaging;

import android.text.Html;
import android.util.Log;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;
import quexotic.org.HttpComm;

public class TrackMyE extends Activity implements SeekBar.OnSeekBarChangeListener{
	
    TextView mResultsText;
    SharedPreferences preferences;
    SeekBar mSeekBar;
    TextView mProgressText;
    TextView mTrackingText;
    TextView mTitleText;	
    String measurable;

    public static final Handler mHandler = new Handler();
    public static Context context;
    public static final Runnable mUpdateResults = new Runnable() {
            public void run() {
            }
        };
	
    private static void showToast() {
        Toast.makeText(context, "msg", Toast.LENGTH_LONG).show();
    }
	
    public void registerAccount(View v) {
        C2DMessaging.register(this, "quexotic.tracker@gmail.com");
    }
	
    @Override
    public void onResume() {
        super.onResume();
        measurable = C2DMReceiver.payload;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
	
		
        super.onCreate(savedInstanceState);
   
        // See if we're registered with C2DM. If not, register...
        String reg_id = C2DMessaging.getRegistrationId(this);
        if (reg_id == "") {
            C2DMessaging.register(this, "quexotic.tracker@gmail.com");
            reg_id = C2DMessaging.getRegistrationId(this);
        }

   
        setContentView(R.layout.main);  
        context = this;
        measurable = C2DMReceiver.payload;
        mResultsText = (TextView)findViewById(R.id.results);
   
        mSeekBar = (SeekBar)findViewById(R.id.seek);
        mSeekBar.setOnSeekBarChangeListener(this);
        mProgressText = (TextView)findViewById(R.id.progress);
        mTrackingText = (TextView)findViewById(R.id.tracking);
        mTitleText = (TextView)findViewById(R.id.title);
        mTitleText.setText(Html.fromHtml ("<big><font size=\"24\" color=\"red\"><b> How is your " + measurable + " on a scale of 1 (least) to 100 (most)?</b></big>"));
	
        Button storeButton = (Button) findViewById(R.id.storebutton);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
	
        storeButton.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    Boolean success = false;
                    // Get user name and password
                    String uname = preferences.getString ("username", "No_username");
                    String pword = preferences.getString ("password", "No_password");
                    Toast.makeText(TrackMyE.this, "Storing...", Toast.LENGTH_LONG).show();
                    Log.v("keith", "before postData");
                    success = HttpComm.postData(uname, pword, getDate(), getTime(), measurable,
                                                mProgressText.getText().toString(), mResultsText.getText().toString());
                    // If the server was up but authentication failed, then either
                    // the wrong password was entered, or there is not yet
                    // that username. We simple-mindedly just add a new user
                    // if that user doesn't exist yet.
                    if (!success) {
                        if (HttpComm.doesUserExist (uname)) {
                            Toast.makeText(TrackMyE.this, "User " + uname + 
                                           "exists - bad password?", Toast.LENGTH_LONG).show();
                        }
                        else {
                            success = HttpComm.createUser(uname, pword);
                            if (success) {
                                success = HttpComm.postData(uname, pword, getDate(), getTime(), measurable, 
                                                            mProgressText.getText().toString(), mResultsText.getText().toString());
                            }
                        }
                    }
                }
            });
    }  
   
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        Date date = new Date();
        return dateFormat.format(date);
    }
    private String getTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);
    }
   
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
        mProgressText.setText(Integer.toString(progress));
    }

    public void onStartTrackingTouch(SeekBar seekBar) {
    }

    public void onStopTrackingTouch(SeekBar seekBar) {
    }
    
    // This method is called once the menu is selected
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            // We have only one menu option
        case R.id.preferences:
            // Launch Preference activity
            Intent i = new Intent(TrackMyE.this, Preferences.class);
            startActivity(i);
            break;
        case R.id.share:
            final Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Summary:");
            shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, "My " + measurable + " was " + mProgressText.getText().toString() + " " + mResultsText.getText().toString());
            startActivity(Intent.createChooser(shareIntent, "Send via..."));
            break;
        case R.id.history:
            String uname = preferences.getString ("username", "No_username");
            String pword = preferences.getString ("password", "No_password");
            //HttpComm.getData (uname, pword);
            Intent h = new Intent(TrackMyE.this, History.class);
            startActivity(h);
            break;
        }
        return true;
    }
}

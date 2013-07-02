package quexotic.org;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceManager;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import android.util.Log;
import android.widget.TextView;
import android.view.Menu;
import android.view.MenuInflater;


public class Abg extends Activity {
	
	TextView mResultsText;
	SharedPreferences preferences;
	StringBuffer sb = new StringBuffer();

	private boolean acidemia (double pH)
	{
	    return (pH < 7.40 ? true : false);
	}

	private boolean low_paCO2 (double paCO2)
	{
	    return ( paCO2 < 40 ? true : false);
	}

	private boolean high_paCO2 (double paCO2)
	{
	    return ( paCO2 > 44 ? true : false);
	}

	private boolean normal_paCO2 (double paCO2)
	{
	    if ( paCO2 > 40.0 && paCO2 < 44.0 )
	        return true;
	    else
	        return false;
	}
	
	private int intround (double val)
	{
	    return ((int) (1000 * val));
	}

	@Override
    public void onCreate(Bundle savedInstanceState) {
		
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);  
        final EditText spH = (EditText) findViewById(R.id.pH);
        final EditText sPCO2 = (EditText) findViewById(R.id.PCO2);
        final EditText sHCO3 = (EditText) findViewById(R.id.HCO3);
        
        mResultsText = (TextView)findViewById(R.id.results);
        final Button interpretButton = (Button) findViewById(R.id.interpret);
        final Button shareButton = (Button) findViewById(R.id.sharebutton);
		Button button1 = (Button) findViewById(R.id.preferences);
		preferences = PreferenceManager.getDefaultSharedPreferences(this);
	
		//button1.setOnClickListener(new OnClickListener() {

		//@Override
		//public void onClick(View v) {
			//String username = preferences.getString("username", "n/a");
			//String password = preferences.getString("password", "n/a");
			//Toast.makeText(Abg.this,
				//	"You maintained user: " + username + " and password: " + password,
					//Toast.LENGTH_LONG).show();

		//}
	//});
		
        shareButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
        		final Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        		shareIntent.setType("text/plain");
            	shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "ABG Summary");
            	shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, mResultsText.getText().toString());
            	startActivity(Intent.createChooser(shareIntent, "Send via..."));
            }
        });   
        
        interpretButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                // Perform action on clicks
            	double pH = Double.parseDouble(spH.getText().toString());
            	double PCO2 = Double.parseDouble(sPCO2.getText().toString());
            	double HCO3 = Double.parseDouble(sHCO3.getText().toString());
            	
                if ( acidemia (pH) ) {
                    if ( low_paCO2 (PCO2) || normal_paCO2 (PCO2) ) {
                        sb.append("Primary metabolic acidemia");
                        /* Is there also a superimposed respiratory disorder? */
                        double expected_paCO2_min = 1.5 * HCO3 + 6;
                        double expected_paCO2_max = 1.5 * HCO3 + 8;
                        
                        if ( PCO2 < expected_paCO2_min ) {
                            sb.append(", with a superimposed respiratory acidosis"); 
                        }
                        if ( PCO2 > expected_paCO2_max ) {
                            sb.append(", with a superimposed respiratory alkalosis"); 
                        }
                    }
                    if ( high_paCO2 (PCO2) ) {
                        sb.append("Primary respiratory acidosis");
                        /* Now determine if this is an acute or a chronic disturbance */
                        double change_in_pH = 7.4 - pH;
                        if ( intround (change_in_pH) > intround (0.008 * (PCO2 - 40.0)) ) {
                            sb.append(": superimposed metabolic acid-base disorder");
                        }
                        if ( intround (change_in_pH) == intround (0.008 * (PCO2 - 40.0)) ) {
                            sb.append(": acute");
                        }
                        if ( intround (change_in_pH) > intround (0.003 * (PCO2 - 40.0)) &&
                             intround (change_in_pH) < intround (0.008 * (PCO2 - 40.0)) ) {
                            sb.append(": partially compensated");
                        }
                        if ( intround (change_in_pH) == intround (0.003 * (PCO2 - 40.0)) ) {
                            sb.append(": chronic");
                        }
                    }
                }
            	String s = String.format("pH %g PCO2 %g HCO3 %g;\n %s", pH, PCO2, HCO3, sb);
            	mResultsText.setText(s);
            	Log.v("keith", "before postData");
            	// postData(heighttext.getText().toString(), weighttext.getText().toString());
             }
        });      
        
    }  
	
	private void postData(String a, String b) {
	    // Create a new HttpClient and Post Header
		Log.v("keith", "enter postData");
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://keithflower.org/bmis/default/create");
	    
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(2);
	        nameValuePairs.add(new BasicNameValuePair("name", a));
	        nameValuePairs.add(new BasicNameValuePair("phone", b));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

	        // Execute HTTP Post Request
	        Log.v("keith", "before response");
	        HttpResponse response = httpclient.execute(httppost);
	        Log.v("keith", "response "  + response);
	    } catch (ClientProtocolException e) {
	    	Log.v("keith", "clientprotocol exception" + e);
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	    	Log.v("keith", "IOException " + e);
	        // TODO Auto-generated catch block
	    }
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
		switch (item.getItemId()) {
		// We have only one menu option
		case R.id.preferences:
			// Launch Preference activity
			Intent i = new Intent(Abg.this, Preferences.class);
			startActivity(i);
			// A toast is a view containing a quick little message for the user.
			Toast.makeText(Abg.this,
					"Here you can maintain your user credentials.",
					Toast.LENGTH_LONG).show();
			break;

		}
		return true;
	}
}
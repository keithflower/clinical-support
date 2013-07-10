package quexotic.org;

import android.app.ListActivity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import android.view.View;
import quexotic.org.HttpComm;

public class History extends ListActivity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
	  super.onCreate(savedInstanceState);
	  //setContentView(R.layout.history);
	  SharedPreferences preferences;
	  preferences = PreferenceManager.getDefaultSharedPreferences(this);
	  String uname = preferences.getString ("username", "No_username");
	  String pword = preferences.getString ("password", "No_password");
	  setListAdapter(new ArrayAdapter<String>(this, R.layout.history, 
			  HttpComm.getData(uname, pword)));

	  ListView lv = getListView();
	  lv.setTextFilterEnabled(true);

	  lv.setOnItemClickListener(new OnItemClickListener() {
	    public void onItemClick(AdapterView<?> parent, View view,
	        int position, long id) {
	      // When clicked, show a toast with the TextView text
	      Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
	          Toast.LENGTH_SHORT).show();
	    }
	  });
	}
	
	
}

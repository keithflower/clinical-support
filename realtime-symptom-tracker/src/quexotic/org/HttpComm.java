package quexotic.org;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.codec.binary.Base64;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.google.android.c2dm.C2DMessaging;

public class HttpComm {

	public static Boolean doesUserExist (String user) {
	    String content = new String("no");
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://keithflower.org/mood/default/userexist");
	    try {
		    List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	        nameValuePairs.add(new BasicNameValuePair("username", user));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
	        HttpResponse response = httpclient.execute(httppost); 
	        HttpEntity entity = response.getEntity();
	        content = EntityUtils.toString (entity);
	    } catch (ClientProtocolException e) {
	    	Log.v("keith", "doesUserExist clientprotocol exception" + e);
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	    	Log.v("keith", "doesUserExist IOException " + e);
	        // TODO Auto-generated catch block
	    }
	    
        if (! content.equals("yes")) {
        		return false;
            } else {
        		return true;
            }
	}

	public static Boolean createUser(String uname, String pword) {
	    // Should consider using a more robust approach like
	    // http://masl.cis.gvsu.edu/2010/04/05/android-code-sample-asynchronous-http-connections/
	    // post data with authentication
	    String content = new String("no");
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://keithflower.org/mood/default/createuser"); 
	    try {
	        // Add your data
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(6);
	        nameValuePairs.add(new BasicNameValuePair("username", uname));
	        nameValuePairs.add(new BasicNameValuePair("password", pword));
	        nameValuePairs.add(new BasicNameValuePair("reg_id", C2DMessaging.getRegistrationId(TrackMyE.context)));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
	        
	        // Execute HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        content = EntityUtils.toString (entity);
	        
	    } catch (ClientProtocolException e) {
	    	Log.v("keith", "clientprotocol exception" + e);
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	    	Log.v("keith", "IOException " + e);
	        // TODO Auto-generated catch block
	    }
	    
	    if (content.equals("success")) {
    			return (true);
	    }
	    else {
	    		return (false);
	    }
	} 
	
	public static Boolean postData(String uname, String pword, String date, String time, String measurable, String level, String comment) {
            String content = new String("no");
            String auth = uname + ":" + pword;
            String up = new String(Base64.encodeBase64(auth.getBytes()));
	    HttpClient httpclient = new DefaultHttpClient();
	    HttpPost httppost = new HttpPost("http://keithflower.org/mood/default/create"); 
	    try {
	        List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
	        nameValuePairs.add(new BasicNameValuePair("identifier", uname));
	        nameValuePairs.add(new BasicNameValuePair("date", date));
	        nameValuePairs.add(new BasicNameValuePair("time", time));
	        nameValuePairs.add(new BasicNameValuePair("mood", level));
	        nameValuePairs.add(new BasicNameValuePair("comments", comment));
	        nameValuePairs.add(new BasicNameValuePair("measurable", measurable));
	        httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
	        httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
	        httppost.setHeader("Authorization", "Basic " + up);
	        
	        // HTTP Post Request
	        HttpResponse response = httpclient.execute(httppost);
	        HttpEntity entity = response.getEntity();
	        content = EntityUtils.toString (entity);
	    } catch (ClientProtocolException e) {
	    	Log.v("keith", "clientprotocol exception" + e);
	        // TODO Auto-generated catch block
	    } catch (IOException e) {
	    	Log.v("keith", "IOException " + e);
	        // TODO Auto-generated catch block
	    }
        if (content.equals("success")) {
        		Log.v("keith", "post data worked");
    			return (true);
	    }
	    else {
    			Log.v("keith", "post data didn't work");
	    		return (false);
	    }
	} 

    public static ArrayList<String> getData(String uname, String pword) {
	ArrayList<String> listItems = new ArrayList<String>();
	String content = new String("no");
	String auth = uname + ":" + pword;
	String up = new String(Base64.encodeBase64(auth.getBytes()));
        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://keithflower.org/mood/default/track.json"); 
        try {
            // Add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(4);
            nameValuePairs.add(new BasicNameValuePair("username", uname));
            httppost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
            httppost.setHeader("Content-type", "application/x-www-form-urlencoded");
            httppost.setHeader("Authorization", "Basic " + up);
        
            // Execute HTTP Post Request
            HttpResponse response = httpclient.execute(httppost);
            HttpEntity entity = response.getEntity();
            content = EntityUtils.toString (entity);
            try {
                JSONObject jsonObject = new JSONObject (content);
                JSONObject recordObject = jsonObject.getJSONObject("records");
                Iterator item = recordObject.keys();
                while (item.hasNext()) {
                    String s       = item.next().toString();
                    JSONObject i   = recordObject.getJSONObject(s);
                    Integer ids    = i.getInt("id");
                    String id      = i.getString("identifier");
                    String date    = i.getString("date");
                    String time    = i.getString("time");
                    String measure = i.getString("measurable");
                    String mood    = i.getString("mood");
                    String comment = i.getString("comments");
                    listItems.add (id + "|" + date + ":" + time + "|" + measure + "|" + mood + "|" + comment);
                }
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                Log.v("keith", "JSON parse failure " + e.toString());
                e.printStackTrace();
            }
        
        } catch (ClientProtocolException e) {
            Log.v("keith", "clientprotocol exception" + e);
            // TODO Auto-generated catch block
        } catch (IOException e) {
            Log.v("keith", "IOException " + e);
            // TODO Auto-generated catch block
        }
        return listItems;
    } 
}	


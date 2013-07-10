/***
	Copyright (c) 2010 CommonsWare, LLC
	
	Licensed under the Apache License, Version 2.0 (the "License"); you may
	not use this file except in compliance with the License. You may obtain
	a copy of the License at
		http://www.apache.org/licenses/LICENSE-2.0
	Unless required by applicable law or agreed to in writing, software
	distributed under the License is distributed on an "AS IS" BASIS,
	WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
	See the License for the specific language governing permissions and
	limitations under the License.
*/

package quexotic.org;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;
import android.widget.Toast;

import com.google.android.c2dm.C2DMBaseReceiver;


public class C2DMReceiver extends C2DMBaseReceiver {
	
	public static String payload = "need for speed";
	
	private void setPayload (String p) {
		payload = p;
		return;
	}
	
	public String getPayload () {
		return payload;
	}
	public C2DMReceiver() {
		super("quexotic.tracker@gmail.com");
	}

	@Override
	public void onUnregistered(Context context) {
		Log.w("C2DMReceiver-onUnregistered", "");
	}

	@Override
	public void onError(Context context, String errorId) {
		Log.w("C2DMReceiver-onError", errorId);
	}
	
	@Override
	protected void onMessage(Context context, Intent intent) {
		Log.w("C2DMReceiver", intent.getStringExtra("payload"));
		setPayload(intent.getStringExtra("payload"));
		
		String ns = Context.NOTIFICATION_SERVICE;
		NotificationManager mNotificationManager = (NotificationManager) getSystemService(ns);
		int icon = R.drawable.notification_icon;        // icon from resources
		CharSequence tickerText = "Time to track your " + payload;     // ticker-text
		long when = System.currentTimeMillis();         // notification time
		Context xcontext = getApplicationContext();     // application Context
		CharSequence contentTitle = "Track!";  // expanded message title
		CharSequence contentText = "Time to track your " + payload;      // expanded message text

		Intent notificationIntent = new Intent(this, TrackMyE.class);
		PendingIntent contentIntent = PendingIntent.getActivity(this, 0, notificationIntent, 0);

		// the next two lines initialize the Notification, using the configurations above
		Notification notification = new Notification(icon, tickerText, when);
		notification.defaults |= Notification.DEFAULT_LIGHTS;
		notification.defaults |= Notification.DEFAULT_SOUND;
		notification.setLatestEventInfo(context, contentTitle, contentText, contentIntent);

		mNotificationManager.notify(1, notification);
	}
}

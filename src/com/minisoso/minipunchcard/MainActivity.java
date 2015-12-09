package com.minisoso.minipunchcard;

import java.util.Calendar;
import java.util.Date;

import com.minisoso.minipunchcard.MiniDigitalClock;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends Activity {

	
	private MiniDigitalClock myTime;
	private TextView myDate;
	private String myDateFormat;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//===
		System.out.println("Debug Debug Debug");
		initViews();
		//setContentView(R.layout.activity_main);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	
	private void initViews() {
        // give up any internal focus before we switch layouts
        //===
		//===
		final View focused = getCurrentFocus();
        if (focused != null) focused.clearFocus();

        setContentView(R.layout.activity_main);

        //===
        //===
        myTime = (MiniDigitalClock) findViewById(R.id.time);
        myDate = (TextView) findViewById(R.id.date);
        //myDate.setText("Hello");
        
        myTime.getRootView().requestFocus();

        //final View.OnClickListener alarmClickListener = new View.OnClickListener() {
        //    public void onClick(View v) {
        //        startActivity(new Intent(DeskClock.this, AlarmClock.class));
        //    }
        //};
    }
	
	private void refreshDate() {
        final Date now = new Date();
        //if (DEBUG) Log.d(LOG_TAG, "refreshing date..." + now);
        //===
        //===
        //myDate.setText(DateFormat.format(myDateFormat, now));
        myDate.setText("Hello");
    }
	
	@Override
    public void onResume() {
        super.onResume();
        //===
		//refreshDate();
	}
	
	private final BroadcastReceiver mIntentReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            final String action = intent.getAction();
            //if (DEBUG) Log.d(LOG_TAG, "mIntentReceiver.onReceive: action=" + action + ", intent=" + intent);
            if (Intent.ACTION_DATE_CHANGED.equals(action)) {
                refreshDate();
            } 
            
//            else if (UiModeManager.ACTION_EXIT_DESK_MODE.equals(action)) {
//                if (mLaunchedFromDock) {
//                    // moveTaskToBack(false);
//                    finish();
//                }
//                mLaunchedFromDock = false;
//            }
        }
    };
    
    @Override
    public void onStart() {
        super.onStart();

        IntentFilter filter = new IntentFilter();
        filter.addAction(Intent.ACTION_DATE_CHANGED);
        //filter.addAction(Intent.ACTION_BATTERY_CHANGED);
//        filter.addAction(UiModeManager.ACTION_EXIT_DESK_MODE);
        //filter.addAction(ACTION_MIDNIGHT);
        registerReceiver(mIntentReceiver, filter);
    }
    
    @Override
    public void onStop() {
        super.onStop();

        unregisterReceiver(mIntentReceiver);
    }
    
	
}

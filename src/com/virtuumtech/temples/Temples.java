package com.virtuumtech.temples;

import java.util.List;

import com.google.android.gms.common.GooglePlayServicesUtil;
import com.virtuumtech.android.googleplaces.GPConstants;
import com.virtuumtech.android.googleplaces.listener.LocationUpdateListener;
import com.virtuumtech.android.googleplaces.MyAddress;
import com.virtuumtech.android.googleplaces.MyLocation;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.location.Address;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class Temples extends Activity implements LocationUpdateListener {
	
	private final String TAG = "Temples";
	public static final String LOCATION = "com.virtuumtech.temples.LOCATION";
	public static final String LOCATION_NAME = "com.virtuumtech.temples.LOCATION_NAME";
	public static final String APPKEY = "com.virtuumtech.temples.APPKEY";

	MyLocation myLocation;
	MyAddress myAddress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
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
	
	//TO get location on click Get Location
	public void onCurrentLocation(View view) {
		myLocation = new MyLocation(this);
		myLocation.getLocationUpdate(this);
	}

	// This is call back procedure to update the Location. Implementation of interface LocationUpdateListener, 
	@Override
	public void onLocationUpdate(Location location) {
		if (location != null) {
			myAddress = new MyAddress(this);
			myAddress.getAddressDetails(this, location);
		}
	}

	// This is call back procedure to update the Location is disabled. Implementation of interface LocationUpdateListener, 
	@Override
	public void onLocationDisabled() {
		// TODO Auto-generated method stub
		
	}

	// This is call back procedure if GoogolePlay service is not installed. Implementation of interface LocationUpdateListener, 
	@Override
	public void onGooglePlayError(int errorCode) {
		Dialog dialog = GooglePlayServicesUtil.getErrorDialog(errorCode, this,-1);
		dialog.show();
	}

	// This is call back procedure in notify geocoder is not exist. Implementation of interface LocationUpdateListener, 
	@Override
	public void onGeocoderDisabled() {
		// TODO Auto-generated method stub
		
	}

	// This is call back procedure to update the address detail of given location. Implementation of interface LocationUpdateListener, 
	@Override
	public void onAddressesUpdate(int status, List<Address> addresses) {
		if (status == GPConstants.SUCCESS) {
			Log.i(TAG,"Address retrieved");
			
			Intent intent = new Intent(this,TemplesList.class);
			intent.putExtra(LOCATION, myLocation.getLocation());
			intent.putExtra(LOCATION_NAME, myAddress.getLocationName());
			intent.putExtra(APPKEY, this.getResources().getString(R.string.appkey));
			startActivity(intent);
		}
	}
}

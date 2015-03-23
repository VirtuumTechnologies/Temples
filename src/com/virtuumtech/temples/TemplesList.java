package com.virtuumtech.temples;


import java.util.ArrayList;

import com.virtuumtech.android.googleplaces.PlaceSummary;
import com.virtuumtech.android.googleplaces.search.NearBySearch;
import com.virtuumtech.android.googleplaces.search.RadarSearch;
import com.virtuumtech.android.googleplaces.search.RequestStatus;
import com.virtuumtech.android.googleplaces.search.TextSearch;
import com.virtuumtech.android.googleplaces.search.SearchUpdateListener;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Adapter;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.TextView;

public class TemplesList extends Activity implements SearchUpdateListener {

	private static final String TAG = "TempleList";
	private Location mLocation;
	private String mLocationName;
	private String APPKEY;
	NearBySearch nearBySearch;
	ArrayList<String> templeNames;
	ListView listView;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		Log.v(TAG,"Inside OnCreate");
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_temples_list);
		
		Intent intent = getIntent();
		
		mLocation = (Location) intent.getParcelableExtra(Temples.LOCATION);
		mLocationName = intent.getStringExtra(Temples.LOCATION_NAME);
		APPKEY = intent.getStringExtra(Temples.APPKEY);
		nearBySearch = new NearBySearch(this, APPKEY);
		templeNames = new ArrayList<String>();
		listView = (ListView) findViewById(R.id.templesListView);
		
		getActionBar().setTitle(mLocationName);
		getSearchResult();
	}
	
	public void getSearchResult() {
		Log.v(TAG,"Inside getSearchResult");
		nearBySearch.setLocation(mLocation);
		nearBySearch.setTypes("hindu_temple");
		nearBySearch.setRankby("distance");
		nearBySearch.getPlaces();
		
	/*	TextSearch textSearch = new TextSearch(this, APPKEY);
		textSearch.setLocation(mLocation);
		textSearch.setQuery("Meenakshi Amman Temple");
		//textSearch.setRadius("5000");
		textSearch.getPlaces();
*/
	/*	RadarSearch radarSearch = new RadarSearch(this,APPKEY);
		radarSearch.setLocation(mLocation);
		radarSearch.setTypes("hospital");
		radarSearch.setRadius("50000");
		radarSearch.getPlaces();*/
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.temples_list, menu);
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

	@Override
	public void onSearchResultsUpdate(int statusCode, ArrayList<PlaceSummary> listPOIs) {
		Log.v(TAG,"Inside onSearchResultsUpdate");
		Log.d(TAG,"Statuscode: "+statusCode+" - "+RequestStatus.getStatusValue(statusCode));
		
		PlaceSummary placeSummary;
		
		if (statusCode == RequestStatus.OK ) {
			for (int i = 0; i < listPOIs.size(); i++) {
				//Log.d(TAG,listPOIs.get(i).toString());
				placeSummary = listPOIs.get(i);
				String displayString = placeSummary.getName();
				Log.d(TAG,displayString);
				templeNames.add(displayString);
			}
			ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.temple_list_view,R.id.lblListItem,templeNames);
			listView.setAdapter(adapter);
		}
	}
	
	public void getNextData (View view) {
		Log.v(TAG,"in getNextData");
		nearBySearch.getNextPlaces();
	}
}

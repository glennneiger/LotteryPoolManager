package com.epichomeservices.powerballpoolmanager;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;


public class ShowPowerballTickets extends ListActivity {

	private ArrayList<PowerballTickets> powerballTickets;
	private TextView powerballTextView;
	private TextView dateTextView;
	private long date;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
				
		super.onCreate(savedInstanceState);
		setContentView(R.layout.show_tickets);
		
		powerballTextView = (TextView) findViewById(R.id.textView7);
		powerballTextView.setText("PB");
		Bundle extras = getIntent().getExtras();
		date = extras.getLong("drawingDate");
		dateTextView = (TextView) findViewById(R.id.dateDisplayTextView);
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		dateTextView.setText(df.format(date));
		
		LotteryPoolManagerApplication application = (LotteryPoolManagerApplication) getApplication();
		application.readAllPBTicketsByDate(date);
		powerballTickets = application.getAllPowerballTickets();
		
		ArrayAdapter<PowerballTickets> adapter = new ShowPBTicketsArrayAdapter(this, powerballTickets);
		setListAdapter(adapter);
//		setListAdapter(new ArrayAdapter<PowerballTickets>(this, android.R.layout.simple_list_item_1, 
//				powerballTickets));
				
	}
	
	public void emailTickets(View view) {
		
		Intent intent = new Intent(this, ShowContactsAndEmailPBTickets.class);
		intent.putExtra("drawingDate", date);
		startActivity(intent);
	}
	
	public void addTickets(View view) {
		
		Intent intent = new Intent(this, SetupTicketsPowerBall.class);
		startActivity(intent);
	}
	

}

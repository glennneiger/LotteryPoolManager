package com.epichomeservices.powerballpoolmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Calendar;

public class SetupTicketsMegaMillions extends Activity {

	private EditText drawDate;
	private EditText ticketAmounts;
	private long dateSelected;
	private TextView gameLabel;
	
	static final int DATE_DIALOG_ID = 0;
	Calendar c = Calendar.getInstance();
			
	@Override
	protected Dialog onCreateDialog(int id) {

		
		int cyear = c.get(Calendar.YEAR);
		int cmonth = c.get(Calendar.MONTH);
		int cday = c.get(Calendar.DAY_OF_MONTH);
		
		switch (id) {

		case DATE_DIALOG_ID:
			return new DatePickerDialog(this, mDateSetListener, cyear, cmonth,
					cday);
		}

		return null;
	}

	private DatePickerDialog.OnDateSetListener mDateSetListener = new DatePickerDialog.OnDateSetListener() {

		@Override
		public void onDateSet(DatePicker view, int year, int monthOfYear,
				int dayOfMonth) {
			String date_selected = String.valueOf(monthOfYear + 1) + " /"
					+ String.valueOf(dayOfMonth) + " /" + String.valueOf(year);
			
			drawDate.setText(date_selected);
			c.set(year, monthOfYear, dayOfMonth);
			c.set(Calendar.HOUR_OF_DAY, 0);
		    c.set(Calendar.MINUTE, 0);
		    c.set(Calendar.SECOND, 0);
		    c.set(Calendar.MILLISECOND, 0);
			dateSelected = c.getTimeInMillis();
			
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.setup_tickets);

		gameLabel = (TextView) findViewById(R.id.textView6);
		gameLabel.setText("Mega Millions");
		drawDate = (EditText) findViewById(R.id.drawDateEditText);
		ticketAmounts = (EditText) findViewById(R.id.TicketAmountsEditText);

		drawDate.setOnTouchListener(new View.OnTouchListener() {

			@SuppressWarnings("deprecation")
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				if (v == drawDate) {

					showDialog(DATE_DIALOG_ID);
					
				}

				return false;
			}
		});

	}

	public void ticketSetupSubmit(View v) {
				
		String tickets;
		String date;
		date = drawDate.getText().toString();
		tickets = ticketAmounts.getText().toString();
		if (tickets.matches("")) {

			Toast.makeText(getApplicationContext(),
					"You must enter the amount of tickets", Toast.LENGTH_SHORT).show();

		} else if (date.matches("")) {

			Toast.makeText(getApplicationContext(),
					"You must enter a drawing date", Toast.LENGTH_SHORT).show();

		} else {

			int amountOfTickets = Integer.parseInt(ticketAmounts.getText()
					.toString());
			Intent intent = new Intent(this, EnterTicketsMegaMillions.class);
			intent.putExtra("ticketAmounts", (int) amountOfTickets);
			intent.putExtra("drawingDate", dateSelected);
			
			startActivity(intent);

		}
	}	

}

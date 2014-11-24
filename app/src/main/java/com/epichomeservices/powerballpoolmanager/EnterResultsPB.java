package com.epichomeservices.powerballpoolmanager;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class EnterResultsPB extends Activity{

	private EditText drawDate;
	private EditText number1;
	private EditText number2;
	private EditText number3;
	private EditText number4;
	private EditText number5;
	private EditText powerballNumber;
	private long dateSelected;
	private TextView gameLabelTextView;
	static final int DATE_DIALOG_ID = 0;
	private String[] lottoFeedStrings = new String[4];
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
		setContentView(R.layout.enter_results);
		
		drawDate = (EditText) findViewById(R.id.dateEditText);
		number1 = (EditText) findViewById(R.id.num1EditText);
		number2 = (EditText) findViewById(R.id.num2EditText);
		number3 = (EditText) findViewById(R.id.num3editText);
		number4 = (EditText) findViewById(R.id.num4EditText);
		number5 = (EditText) findViewById(R.id.num5EditText);
		powerballNumber = (EditText) findViewById(R.id.PbEditText);
		gameLabelTextView = (TextView) findViewById(R.id.gameLabel);
		gameLabelTextView.setText("Powerball");
		powerballNumber.setHint("PB");
		
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
	
	public void checkForWinners(View v) {
		EnterTicketsPowerball validate = new EnterTicketsPowerball();
		int[] winningTicket = new int[6];
		String date;
		date = drawDate.getText().toString();
		boolean resume = true;
				
		if (number1.getText().toString().matches("") || number2.getText().toString().matches("") || number3.getText().toString().matches("")
				|| number4.getText().toString().matches("") || number5.getText().toString().matches("") || powerballNumber.getText().toString().matches("")) {
			Toast.makeText(
					getApplicationContext(),
					"You are missing a number",
					Toast.LENGTH_SHORT).show();
			resume = false;
		}
		else {
			winningTicket[0] = Integer.parseInt(number1.getText().toString());
			winningTicket[1] = Integer.parseInt(number2.getText().toString());
			winningTicket[2] = Integer.parseInt(number3.getText().toString());
			winningTicket[3] = Integer.parseInt(number4.getText().toString());
			winningTicket[4] = Integer.parseInt(number5.getText().toString());
			winningTicket[5] = Integer.parseInt(powerballNumber.getText().toString());
		}
		
		if(date.matches("")) {
			Toast.makeText(
					getApplicationContext(),
					"You must select a date from the top of screen",
					Toast.LENGTH_SHORT).show();
			resume = false;
		}
		
		if (resume) {
			int validateCode = validate.validateNumbers(winningTicket);
			
			if (validateCode == -1) {
				
				Toast.makeText(
					getApplicationContext(),
					"Invalid number, powerball numbers must be between 1-35",
					Toast.LENGTH_SHORT).show();
				resume = false;
			
			} else if (validateCode == -2) {
				Toast.makeText(
						getApplicationContext(),
						"Invalid number, non powerball numbers must be between 1-59",
						Toast.LENGTH_SHORT).show();
				resume = false;
			
			} else if (validateCode == -3) {
				Toast.makeText(
						getApplicationContext(),
						"Ticket cannot contain a duplicate number.",
						Toast.LENGTH_SHORT).show();
				resume = false;
			
			}
			
		}
		
		if (resume) {
			
			Intent intent = new Intent(this, CheckForPBWinners.class);
			intent.putExtra("winningTicket", winningTicket);
			intent.putExtra("drawingDate", dateSelected);
			startActivity(intent);
		}
	}
	
	public void parseLottoFeed(View v) {
		new MyAsyncTask().execute();
		
	}
	
	private class MyAsyncTask extends AsyncTask<String, Void, String>{

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
			EditText[] eTextArray = new EditText[] {number1, number2, number3, number4, number5, powerballNumber};
			drawDate.setText(lottoFeedStrings[0]);
			Pattern p = Pattern.compile("([0-9]+)");
			Matcher m = p.matcher(lottoFeedStrings[1]);
			int i = 0;
			while (m.find()) {
				eTextArray[i].setText(m.group());
				++i;
			}
			SimpleDateFormat df = new SimpleDateFormat("EEEE, MMMM d, yyyy");
			try {
				Date date = df.parse(lottoFeedStrings[0]);
				 dateSelected = date.getTime();
			} catch (ParseException e) {
				e.printStackTrace();
			}
			Log.d(" ", String.valueOf(dateSelected));
			
			
		}

		@Override
		protected String doInBackground(String... params) {
			RSSparser parser = new RSSparser();
			lottoFeedStrings = parser.parse();
			return null;
		}
				
	}
		
}

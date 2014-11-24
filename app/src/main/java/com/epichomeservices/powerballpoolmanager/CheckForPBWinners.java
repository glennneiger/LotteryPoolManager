package com.epichomeservices.powerballpoolmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.HORIZONTAL;

public class CheckForPBWinners extends Activity {

	private int[] winningTicket;
	private long drawingDate;
	private int winningTickets = 0;
	private ArrayList<PowerballTickets> allTickets;
	private TextView dateTextView;
	private TextView num1;
	private TextView num2;
	private TextView num3;
	private TextView num4;
	private TextView num5;
	private TextView numPb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_for_pb_winners);
		Bundle extras = getIntent().getExtras();
		winningTicket = extras.getIntArray("winningTicket");
		drawingDate = extras.getLong("drawingDate");
		dateTextView = (TextView) findViewById(R.id.dateTextView);
		DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
		dateTextView.setText(df.format(drawingDate));

		num1 = (TextView) findViewById(R.id.winning1TextView);
		num2 = (TextView) findViewById(R.id.winning2TextView);
		num3 = (TextView) findViewById(R.id.winning3TextView);
		num4 = (TextView) findViewById(R.id.winning4TextView);
		num5 = (TextView) findViewById(R.id.winning5TextView);
		numPb = (TextView) findViewById(R.id.winningPowerballTextView);

		num1.setText(String.format("%02d", winningTicket[0]));
		num2.setText(String.format("%02d", winningTicket[1]));
		num3.setText(String.format("%02d", winningTicket[2]));
		num4.setText(String.format("%02d", winningTicket[3]));
		num5.setText(String.format("%02d", winningTicket[4]));
		numPb.setText(String.format("%02d", winningTicket[5]));

		checkForWinningTickets();

	}

	public void checkForWinningTickets() {

		LotteryPoolManagerApplication application = (LotteryPoolManagerApplication) getApplication();
		application.readAllPBTicketsByDate(drawingDate);
		allTickets = application.getAllPowerballTickets();

		LinearLayout fiveOf5wPBLinearLayout = (LinearLayout) findViewById(R.id.fiveOfFiveWPBlinearLayout);
		LinearLayout fiveOf5noPBLinearLayout = (LinearLayout) findViewById(R.id.fiveOfFiveNoPBlinearLayout);
		LinearLayout fourOf5wPBLinearLayout = (LinearLayout) findViewById(R.id.fourOfFiveWPBlinearLayout);
		LinearLayout fourOf5noPBLinearLayout = (LinearLayout) findViewById(R.id.fourOfFiveNoPBlinearLayout);
		LinearLayout threeOf5wPBLinearLayout = (LinearLayout) findViewById(R.id.threeOfFiveWPBlinearLayout);
		LinearLayout threeOf5noPBLinearLayout = (LinearLayout) findViewById(R.id.threeOfFiveNoPBlinearLayout);
		LinearLayout twoOf5wPBLinearLayout = (LinearLayout) findViewById(R.id.twoOfFiveWPBlinearLayout);
		LinearLayout oneOf5wPBLinearLayout = (LinearLayout) findViewById(R.id.oneOfFiveWPBlinearLayout);
		LinearLayout zeroOf5wPBLinearLayout = (LinearLayout) findViewById(R.id.zeroOfFiveWPBlinearLayout);

		
		for (PowerballTickets ticket : allTickets) {

			for (int i = 0; i < winningTicket.length - 1; i++) {

				if (winningTicket[i] == ticket.getNumber1()
						|| winningTicket[i] == ticket.getNumber2()
						|| winningTicket[i] == ticket.getNumber3()
						|| winningTicket[i] == ticket.getNumber4()
						|| winningTicket[i] == ticket.getNumber5()) {
					ticket.addMatchingRegularNumbers();
				}
				
			}
			
			if (winningTicket[5] == ticket.getNumberPB()) {
				ticket.setPowerballMatch(true);
			}

		}

		for (PowerballTickets ticket : allTickets) {

			if (ticket.getMatchingRegularNumbers() >= 3
					|| ticket.isPowerballMatch() == true) {
				winningTickets++;
			}
		}

		Collections.sort(allTickets);

		if (winningTickets == 0) {

			TextView noWinners = (TextView) findViewById(R.id.noWinnersTextView);
			noWinners.setText("There were no winning tickets");
			fiveOf5wPBLinearLayout.setVisibility(View.GONE);
			fiveOf5noPBLinearLayout.setVisibility(View.GONE);
			fourOf5wPBLinearLayout.setVisibility(View.GONE);
			fourOf5noPBLinearLayout.setVisibility(View.GONE);
			threeOf5wPBLinearLayout.setVisibility(View.GONE);
			threeOf5noPBLinearLayout.setVisibility(View.GONE);
			twoOf5wPBLinearLayout.setVisibility(View.GONE);
			oneOf5wPBLinearLayout.setVisibility(View.GONE);
			zeroOf5wPBLinearLayout.setVisibility(View.GONE);

		} else {

			int fiveOf5wPB = 0, fiveOf5NoPB = 0, fourOf5wPB = 0, fourOf5NoPB = 0, threeOf5wPB = 0, threeOf5noPB = 0, twoOf5wPB = 0, oneOf5wPB = 0, zeroOf5noPB = 0;

			for (PowerballTickets ticket : allTickets) {

				if (ticket.getMatchingRegularNumbers() == 5
						&& ticket.isPowerballMatch()) {

					fiveOf5wPB = 1;
					fiveOf5wPBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 5
						&& !ticket.isPowerballMatch()) {

					fiveOf5NoPB = 1;
					fiveOf5noPBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 4
						&& ticket.isPowerballMatch()) {

					fourOf5wPB = 1;
					fourOf5wPBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 4
						&& !ticket.isPowerballMatch()) {

					fourOf5NoPB = 1;
					fourOf5noPBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 3
						&& ticket.isPowerballMatch()) {

					threeOf5wPB = 1;
					threeOf5wPBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 3
						&& !ticket.isPowerballMatch()) {

					threeOf5noPB = 1;
					threeOf5noPBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 2
						&& ticket.isPowerballMatch()) {

					twoOf5wPB = 1;
					twoOf5wPBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 1
						&& ticket.isPowerballMatch()) {

					oneOf5wPB = 1;
					oneOf5wPBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 0
						&& ticket.isPowerballMatch()) {

					zeroOf5noPB = 1;
					zeroOf5wPBLinearLayout.addView(addTicketToView(ticket));
				}

			}

			if (fiveOf5wPB == 0) {
				fiveOf5wPBLinearLayout.setVisibility(View.GONE);
			}
			if (fiveOf5NoPB == 0) {
				fiveOf5noPBLinearLayout.setVisibility(View.GONE);
			}
			if (fourOf5wPB == 0) {
				fourOf5wPBLinearLayout.setVisibility(View.GONE);
			}
			if (fourOf5NoPB == 0) {
				fourOf5noPBLinearLayout.setVisibility(View.GONE);
			}
			if (threeOf5wPB == 0) {
				threeOf5wPBLinearLayout.setVisibility(View.GONE);
			}
			if (threeOf5noPB == 0) {
				threeOf5noPBLinearLayout.setVisibility(View.GONE);
			}
			if (twoOf5wPB == 0) {
				twoOf5wPBLinearLayout.setVisibility(View.GONE);
			}
			if (oneOf5wPB == 0) {
				oneOf5wPBLinearLayout.setVisibility(View.GONE);
			}
			if (zeroOf5noPB == 0) {
				zeroOf5wPBLinearLayout.setVisibility(View.GONE);
			}

		}

	}

	public LinearLayout addTicketToView(PowerballTickets pbTicket) {

		LinearLayout linearLayout = new LinearLayout(this);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				MATCH_PARENT, WRAP_CONTENT);
		linearLayout.setLayoutParams(params);
		linearLayout.setOrientation(HORIZONTAL);

		int[] numbers = new int[] { pbTicket.getNumber1(),
				pbTicket.getNumber2(), pbTicket.getNumber3(),
				pbTicket.getNumber4(), pbTicket.getNumber5(),
				pbTicket.getNumberPB() };

		for (int i = 0; i < 6; i++) {
			TextView textView = new TextView(this);
			textView.setId(i);
			textView.setEms(2);
			textView.setText(String.format("%02d", numbers[i]));
			textView.setLayoutParams(new TableLayout.LayoutParams(
					LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1f));

			linearLayout.addView(textView);
		}

		return linearLayout;

	}
	
	public void emailResults(View view) {
		
		Intent intent = new Intent(this, EmailPBResults.class);
		intent.putExtra("drawingDate", drawingDate);
		intent.putExtra("winningTickets", winningTickets);
		startActivity(intent);
	}
	
	public void backToSetup(View view) {
		
		Intent intent = new Intent(this, SetupTicketsPowerBall.class);
		startActivity(intent);
	}

}

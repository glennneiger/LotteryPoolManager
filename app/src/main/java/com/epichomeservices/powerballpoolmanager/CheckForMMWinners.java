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

public class CheckForMMWinners extends Activity {

	private int[] winningTicket;
	private long drawingDate;
	private int winningTickets = 0;
	private ArrayList<MegaMillionsTickets> allTickets;
	private TextView dateTextView;
	private TextView num1;
	private TextView num2;
	private TextView num3;
	private TextView num4;
	private TextView num5;
	private TextView numMb;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.check_for_mm_winners);
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
		numMb = (TextView) findViewById(R.id.winningMegaballTextView);

		num1.setText(String.format("%02d", winningTicket[0]));
		num2.setText(String.format("%02d", winningTicket[1]));
		num3.setText(String.format("%02d", winningTicket[2]));
		num4.setText(String.format("%02d", winningTicket[3]));
		num5.setText(String.format("%02d", winningTicket[4]));
		numMb.setText(String.format("%02d", winningTicket[5]));

		checkForWinningTickets();

	}

	public void checkForWinningTickets() {

		LotteryPoolManagerApplication application = (LotteryPoolManagerApplication) getApplication();
		application.readAllMMTicketsByDate(drawingDate);
		allTickets = application.getAllMegaMillionsTickets();

		LinearLayout fiveOf5wMBLinearLayout = (LinearLayout) findViewById(R.id.fiveOfFiveWMBlinearLayout);
		LinearLayout fiveOf5noMBLinearLayout = (LinearLayout) findViewById(R.id.fiveOfFiveNoMBlinearLayout);
		LinearLayout fourOf5wMBLinearLayout = (LinearLayout) findViewById(R.id.fourOfFiveWMBlinearLayout);
		LinearLayout fourOf5noMBLinearLayout = (LinearLayout) findViewById(R.id.fourOfFiveNoMBlinearLayout);
		LinearLayout threeOf5wMBLinearLayout = (LinearLayout) findViewById(R.id.threeOfFiveWMBlinearLayout);
		LinearLayout threeOf5noMBLinearLayout = (LinearLayout) findViewById(R.id.threeOfFiveNoMBlinearLayout);
		LinearLayout twoOf5wMBLinearLayout = (LinearLayout) findViewById(R.id.twoOfFiveWMBlinearLayout);
		LinearLayout oneOf5wMBLinearLayout = (LinearLayout) findViewById(R.id.oneOfFiveWMBlinearLayout);
		LinearLayout zeroOf5wMBLinearLayout = (LinearLayout) findViewById(R.id.zeroOfFiveWMBlinearLayout);

		
		for (MegaMillionsTickets ticket : allTickets) {

			for (int i = 0; i < winningTicket.length - 1; i++) {

				if (winningTicket[i] == ticket.getNumber1()
						|| winningTicket[i] == ticket.getNumber2()
						|| winningTicket[i] == ticket.getNumber3()
						|| winningTicket[i] == ticket.getNumber4()
						|| winningTicket[i] == ticket.getNumber5()) {
					ticket.addMatchingRegularNumbers();
				}
				
			}
			
			if (winningTicket[5] == ticket.getNumberMB()) {
				ticket.setMegaballMatch(true);
			}

		}

		for (MegaMillionsTickets ticket : allTickets) {

			if (ticket.getMatchingRegularNumbers() >= 3
					|| ticket.isMegaballMatch() == true) {
				winningTickets++;
			}
		}

		Collections.sort(allTickets);

		if (winningTickets == 0) {

			TextView noWinners = (TextView) findViewById(R.id.noWinnersTextView);
			noWinners.setText("There were no winning tickets");
			fiveOf5wMBLinearLayout.setVisibility(View.GONE);
			fiveOf5noMBLinearLayout.setVisibility(View.GONE);
			fourOf5wMBLinearLayout.setVisibility(View.GONE);
			fourOf5noMBLinearLayout.setVisibility(View.GONE);
			threeOf5wMBLinearLayout.setVisibility(View.GONE);
			threeOf5noMBLinearLayout.setVisibility(View.GONE);
			twoOf5wMBLinearLayout.setVisibility(View.GONE);
			oneOf5wMBLinearLayout.setVisibility(View.GONE);
			zeroOf5wMBLinearLayout.setVisibility(View.GONE);

		} else {

			int fiveOf5wMB = 0, fiveOf5NoMB = 0, fourOf5wMB = 0, fourOf5NoMB = 0, threeOf5wMB = 0, threeOf5noMB = 0, twoOf5wMB = 0, oneOf5wMB = 0, zeroOf5noMB = 0;

			for (MegaMillionsTickets ticket : allTickets) {

				if (ticket.getMatchingRegularNumbers() == 5
						&& ticket.isMegaballMatch()) {

					fiveOf5wMB = 1;
					fiveOf5wMBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 5
						&& !ticket.isMegaballMatch()) {

					fiveOf5NoMB = 1;
					fiveOf5noMBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 4
						&& ticket.isMegaballMatch()) {

					fourOf5wMB = 1;
					fourOf5wMBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 4
						&& !ticket.isMegaballMatch()) {

					fourOf5NoMB = 1;
					fourOf5noMBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 3
						&& ticket.isMegaballMatch()) {

					threeOf5wMB = 1;
					threeOf5wMBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 3
						&& !ticket.isMegaballMatch()) {

					threeOf5noMB = 1;
					threeOf5noMBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 2
						&& ticket.isMegaballMatch()) {

					twoOf5wMB = 1;
					twoOf5wMBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 1
						&& ticket.isMegaballMatch()) {

					oneOf5wMB = 1;
					oneOf5wMBLinearLayout.addView(addTicketToView(ticket));
				}

				if (ticket.getMatchingRegularNumbers() == 0
						&& ticket.isMegaballMatch()) {

					zeroOf5noMB = 1;
					zeroOf5wMBLinearLayout.addView(addTicketToView(ticket));
				}

			}

			if (fiveOf5wMB == 0) {
				fiveOf5wMBLinearLayout.setVisibility(View.GONE);
			}
			if (fiveOf5NoMB == 0) {
				fiveOf5noMBLinearLayout.setVisibility(View.GONE);
			}
			if (fourOf5wMB == 0) {
				fourOf5wMBLinearLayout.setVisibility(View.GONE);
			}
			if (fourOf5NoMB == 0) {
				fourOf5noMBLinearLayout.setVisibility(View.GONE);
			}
			if (threeOf5wMB == 0) {
				threeOf5wMBLinearLayout.setVisibility(View.GONE);
			}
			if (threeOf5noMB == 0) {
				threeOf5noMBLinearLayout.setVisibility(View.GONE);
			}
			if (twoOf5wMB == 0) {
				twoOf5wMBLinearLayout.setVisibility(View.GONE);
			}
			if (oneOf5wMB == 0) {
				oneOf5wMBLinearLayout.setVisibility(View.GONE);
			}
			if (zeroOf5noMB == 0) {
				zeroOf5wMBLinearLayout.setVisibility(View.GONE);
			}

		}

	}

	public LinearLayout addTicketToView(MegaMillionsTickets mmTicket) {

		LinearLayout linearLayout = new LinearLayout(this);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				MATCH_PARENT, WRAP_CONTENT);
		linearLayout.setLayoutParams(params);
		linearLayout.setOrientation(HORIZONTAL);

		int[] numbers = new int[] { mmTicket.getNumber1(),
				mmTicket.getNumber2(), mmTicket.getNumber3(),
				mmTicket.getNumber4(), mmTicket.getNumber5(),
				mmTicket.getNumberMB() };

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
		
		Intent intent = new Intent(this, EmailMMResults.class);
		intent.putExtra("drawingDate", drawingDate);
		intent.putExtra("winningTickets", winningTickets);
		startActivity(intent);
	}
	
	public void backToSetup(View view) {
		
		Intent intent = new Intent(this, SetupTicketsMegaMillions.class);
		startActivity(intent);
	}

}
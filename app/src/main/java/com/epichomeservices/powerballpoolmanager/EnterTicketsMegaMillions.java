package com.epichomeservices.powerballpoolmanager;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;
import static android.widget.LinearLayout.VERTICAL;

public class EnterTicketsMegaMillions extends Activity implements TextWatcher {

	private List<EditText> editTextList = new ArrayList<EditText>();
	private MegaMillionsTickets megaMillionsTickets = new MegaMillionsTickets();
	private TextView gameLabelTextView;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.enter_tickets);
		
		gameLabelTextView = (TextView) findViewById(R.id.gameTitleTextView);
		gameLabelTextView.setText("Mega Millions");

		Bundle extras = getIntent().getExtras();
		int amountOfTickets = extras.getInt("ticketAmounts");
		long dateOfDrawing = extras.getLong("drawingDate");
		megaMillionsTickets.setDrawingDate(dateOfDrawing);

		LinearLayout linearLayout = new LinearLayout(this);
		LinearLayout linearLayout1 = (LinearLayout) findViewById(R.id.linearLayout1);
		ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(
				MATCH_PARENT, WRAP_CONTENT);
		linearLayout.setLayoutParams(params);
		linearLayout.setOrientation(VERTICAL);
		linearLayout.addView(tableLayout(amountOfTickets));
		linearLayout1.addView(linearLayout);
		
	}

	private TableLayout tableLayout(int numOfRows) {
		TableLayout tableLayout = new TableLayout(this);
		tableLayout.setStretchAllColumns(true);
		int ticketID = 1;

		for (int i = 0; i < numOfRows; i++) {

			tableLayout.addView(createOneFullRow(ticketID));
			ticketID += 6;
		}

		return tableLayout;
	}

	private TableRow createOneFullRow(int ticketID) {

		TableRow tableRow = new TableRow(this);
		tableRow.setPadding(0, 10, 0, 0);

		for (int i = 0; i <= 5; i++) {

			tableRow.addView(editText(String.valueOf(ticketID)));
			ticketID++;
		}

		return tableRow;
	}

	private EditText editText(String ticketIdentifier) {
		EditText editText = new EditText(this);

		int hintID = Integer.parseInt(ticketIdentifier);

		editText.setId(Integer.parseInt(ticketIdentifier));
		editText.setInputType(InputType.TYPE_CLASS_NUMBER);
		editText.setFilters(new InputFilter[] { new InputFilter.LengthFilter(2) });

		if (hintID < 6) {

			editText.setHint(ticketIdentifier);
			editTextList.add(editText);
			editText.addTextChangedListener(this);

			return editText;

		} else {

			if (hintID % 6 == 0) {
				editText.setHint("MB");
			} else if (hintID % 6 == 1) {
				editText.setHint("1");
			} else if (hintID % 6 == 2) {
				editText.setHint("2");
			} else if (hintID % 6 == 3) {
				editText.setHint("3");
			} else if (hintID % 6 == 4) {
				editText.setHint("4");
			} else {
				editText.setHint("5");
			}

			editTextList.add(editText);

			editText.addTextChangedListener(this);

			return editText;
		}

	}

	public void submitListener(View view) {
		
		
		int[] ticketArray = new int[6];
		int counter = 0;
		int tempCounter = 0;
		int currentNumber = 0;
		boolean resume = true;

		for (EditText editText : editTextList) {

			if (!editText.getText().toString().matches("")) {

				currentNumber = Integer.parseInt(editText.getText().toString());
				
				ticketArray[tempCounter] = (int) currentNumber;
				counter++;
				tempCounter++;

				if (counter % 6 == 0) {

					int numberErrorCode = validateNumbers(ticketArray);
					tempCounter = 0;

					switch (numberErrorCode) {

					case -1:
						// power ball out of range
						Toast.makeText(
								getApplicationContext(),
								"Invalid number in ticket #"
										+ counter
										/ 6
										+ ", megaball numbers must be between 1-15",
								Toast.LENGTH_SHORT).show();
						megaMillionsTickets.clearValuesFromList();
						counter = 0;
						resume = false;
						break;
					case -2:
						// regular number out of range
						Toast.makeText(
								getApplicationContext(),
								"Invalid number in ticket #"
										+ counter
										/ 6
										+ ", non megaball numbers must be between 1-75",
								Toast.LENGTH_SHORT).show();
						megaMillionsTickets.clearValuesFromList();
						counter = 0;
						resume = false;
						break;
					case -3:
						// duplicate number found
						Toast.makeText(
								getApplicationContext(),
								"Ticket # " + counter / 6
										+ " contains a duplicate number.",
								Toast.LENGTH_SHORT).show();
						megaMillionsTickets.clearValuesFromList();
						counter = 0;
						resume = false;
						break;
					default:
						break;
					}

					if (resume) {
						int[] ticketArrayClone = (int[]) ticketArray.clone();
						megaMillionsTickets.addTicketToList(ticketArrayClone);
					}

				}

				if (!resume) {

					break;
				}
			} else {

				if (counter == 0) {
					resume = false;
				}
				break;
				
			}
			
		}

		if (counter % 6 != 0) {

			int invalidTicket = counter / 6;

			Toast.makeText(getApplicationContext(),
					"You are missing a number for ticket #" + ++invalidTicket,
					Toast.LENGTH_SHORT).show();
			counter = 0;
			tempCounter = 0;
			megaMillionsTickets.clearValuesFromList();
			resume = false;

		}

		if (resume) {
			LotteryPoolManagerApplication application = (LotteryPoolManagerApplication) getApplication();
			application.addMMTicketsToDatabase(megaMillionsTickets);
			long date = megaMillionsTickets.getDrawingDate();
			
			Intent intent = new Intent(this, ShowMegaMillionsTickets.class);
			intent.putExtra("drawingDate", date);
			startActivity(intent);
		}

	}
	
	public int validateNumbers(int[] ticketArray) {

		HashSet<Integer> uniqueNumbers = new HashSet<Integer>();

		for (int i = 0; i < ticketArray.length; i++) {

			Integer number = ticketArray[i];

			if (i == 5) {

				if (ticketArray[i] < 1 || ticketArray[i] > 15) {
					return -1;
				}

			} else {

				uniqueNumbers.add(number);

				if (ticketArray[i] < 1 || ticketArray[i] > 75) {

					return -2;
				}
			}

		}

		if (uniqueNumbers.size() != 5) {

			return -3;
		}

		return 0;

	}
	
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.enter_tickets, menu);
		return true;
	}

	@Override
	public void afterTextChanged(Editable s) {
		int id = getCurrentFocus().getId();

		if (s.toString().length() == 2 && id < editTextList.size()) {

			editTextList.get(id).requestFocus();
			
		}

	}

	@Override
	public void beforeTextChanged(CharSequence s, int start, int count,
			int after) {

	}

	@Override
	public void onTextChanged(CharSequence s, int start, int before, int count) {

	}
}

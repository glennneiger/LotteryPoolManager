package com.epichomeservices.powerballpoolmanager;

import android.app.AlertDialog;
import android.app.ListActivity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Toast;

import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Collections;

public class EmailPBResults extends ListActivity{

	/*		email lotto feed
	 * 		http://flalottery.com/video/en/theWinningNumber.xml
	 */
	private Button addContact;
	private Button emailContacts;
	private Button deleteContact;	
	private Button selectAll;
	private ArrayList<Contacts> contacts;
	private ArrayList<PowerballTickets> pbTickets;
	private long date;
	private int winningTickets;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.show_contacts_footer);
		addContact = (Button) findViewById(R.id.addContactsButton);
		emailContacts = (Button) findViewById(R.id.emailButton);
		deleteContact = (Button) findViewById(R.id.deleteContact);
		selectAll = (Button) findViewById(R.id.selectAll);

		Bundle extras = getIntent().getExtras();

		date = extras.getLong("drawingDate");
		winningTickets = extras.getInt("winningTickets");

		LotteryPoolManagerApplication application = (LotteryPoolManagerApplication) getApplication();
		contacts = application.getAllContacts();

		addContact.setOnClickListener(addContactListener);
		emailContacts.setOnClickListener(emailContactsListener);
		deleteContact.setOnClickListener(deleteContactsListener);
		selectAll.setOnClickListener(selectAllListener);

		if (contacts.size() == 0) {

			String message = "There are no contacts to display";

			Intent intent = new Intent(this, EnterContacts.class);
			intent.putExtra("message", message);
			startActivity(intent);

		} else {
			ArrayAdapter<Contacts> adapter = new ContactsArrayAdapter(this,
					contacts);
			setListAdapter(adapter);
		}

	}

	public OnClickListener addContactListener = new OnClickListener() {

		@Override
		public void onClick(View v) {
			Intent intent = new Intent(EmailPBResults.this, EnterContacts.class);
			intent.putExtra("message", "");
			startActivity(intent);

		}
	};

	public OnClickListener emailContactsListener = new OnClickListener() {

		public void onClick(View v) {
			LotteryPoolManagerApplication application = (LotteryPoolManagerApplication) getApplicationContext();
			pbTickets = application.getAllPowerballTickets();
			Collections.sort(pbTickets);
			ArrayList<String> tempContacts = new ArrayList<String>();
			StringBuilder emailTextBuilder = new StringBuilder();
			boolean success = true;

			Intent emailIntent = new Intent(Intent.ACTION_SEND);
			emailIntent.setType("text/html");

			for (Contacts contact : contacts) {
				if (contact.isSelected()) {
					tempContacts.add(contact.getContactEmail());
					
				}
			}

			String[] contactEmails = new String[tempContacts.size()];
			contactEmails = tempContacts.toArray(contactEmails);

			// add date to top of subject
			DateFormat df = DateFormat.getDateInstance(DateFormat.MEDIUM);
			String drawingDate = (df.format(date));

			emailIntent.putExtra(Intent.EXTRA_EMAIL, contactEmails);
			emailIntent.putExtra(Intent.EXTRA_SUBJECT,
					"Powerball results from last drawing");

			emailTextBuilder.append("Results for the drawing on " + drawingDate
					+ "<br>");

			if (winningTickets == 0) {
				
				emailTextBuilder.append("<br><pre>There were no winning tickets for this drawing</pre>");
			
			} else {
				
				emailTextBuilder.append("<br><pre>There was " + winningTickets + " tickets with winning combinations</pre>");
				
				for (PowerballTickets ticket : pbTickets) {
					
					if (ticket.getMatchingRegularNumbers() == 5 && ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br><pre>5 out of 5 numbers - with powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					if (ticket.getMatchingRegularNumbers() == 5 && !ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br/><pre>5 out of 5 numbers - no powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					if (ticket.getMatchingRegularNumbers() == 4 && ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br/><pre>4 out of 5 numbers - with powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					if (ticket.getMatchingRegularNumbers() == 4 && !ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br/><pre>4 out of 5 numbers - no powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					if (ticket.getMatchingRegularNumbers() == 3 && ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br/><pre>3 out of 5 numbers - with powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					if (ticket.getMatchingRegularNumbers() == 3 && !ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br/><pre>3 out of 5 numbers - no powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					if (ticket.getMatchingRegularNumbers() == 2 && ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br/><pre>2 out of 5 numbers - with powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					if (ticket.getMatchingRegularNumbers() == 1 && ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br/><pre>1 out of 5 numbers - with powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					if (ticket.getMatchingRegularNumbers() == 0 && ticket.isPowerballMatch()) {
						emailTextBuilder.append("<br/><pre>0 out of 5 numbers - with powerball" 
							+ " <br><b>" + ticket.toString() + "(PB)</b></pre>");
					}
					
				}
			}

			emailIntent.putExtra(Intent.EXTRA_TEXT, Html.fromHtml(emailTextBuilder.toString()));

			try {
				startActivity(Intent.createChooser(emailIntent, "Send mail..."));

			} catch (android.content.ActivityNotFoundException ex) {
				Toast.makeText(EmailPBResults.this,
						"There are no email clients installed.",
						Toast.LENGTH_SHORT).show();
				success = false;
			}

			if (success) {

				for (Contacts contact : contacts) {
					contact.setSelected(false);
				}
			}
		}
	};
	
	public OnClickListener selectAllListener = new OnClickListener() {
		
		@SuppressWarnings("unchecked")
		@Override
		public void onClick(View v) {
			for (Contacts contact : contacts) {
				contact.setSelected(true);
			}
			
			((ArrayAdapter<Contacts>) getListAdapter()).notifyDataSetChanged();
			
		}
	};

	public OnClickListener deleteContactsListener = new OnClickListener() {

		@Override
		public void onClick(View v) {

			AlertDialog.Builder builder = new AlertDialog.Builder(
					EmailPBResults.this);

			// set title
			builder.setTitle("Delete confirmation");

			// set dialog message
			builder.setMessage(
					"Are you sure you want to delete selected contacts?")
					.setCancelable(false)
					.setPositiveButton("Yes",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									for (Contacts contact : contacts) {
										if (contact.isSelected()) {
											LotteryPoolManagerApplication application = (LotteryPoolManagerApplication) getApplicationContext();
											application.deleteContacts(contact
													.getContactEmail());

										}
									}
									for (Contacts contact : contacts) {
										contact.setSelected(false);
									}
									Intent intent = getIntent();
									finish();
									startActivity(intent);
								}
							})
					.setNegativeButton("No",
							new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog,
										int id) {

									dialog.cancel();
									for (Contacts contact : contacts) {
										contact.setSelected(false);
									}
									Intent intent = getIntent();
									finish();
									startActivity(intent);
								}
							});

			// create alert dialog
			AlertDialog alertDialog = builder.create();

			// show it
			alertDialog.show();

		}
	};
	
	public void goHome(View view) {
		
		Intent intent = new Intent(this, MainGameSelection.class);
		startActivity(intent);
	}
}

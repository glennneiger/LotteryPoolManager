package com.epichomeservices.powerballpoolmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class EnterContacts extends Activity {

	EditText enterName;
	EditText enterEmail;
	private String name;
	private CharSequence email;
	private boolean resume = false;
	private TextView messageTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.enter_contacts);

		enterName = (EditText) findViewById(R.id.enterNameEditText);
		enterEmail = (EditText) findViewById(R.id.enterEmailEditText);

		Bundle extras = getIntent().getExtras();
		
		try {
			String message = (String) extras.get("message");
			messageTextView = (TextView) findViewById(R.id.messageTextView);
			messageTextView.setText(message);
		} catch (NullPointerException npe) {
			Log.d("message was ", "null");
		}
		
//		if (extras.getString("message") != null) {
//
//			String message = (String) extras.get("message");
//			messageTextView = (TextView) findViewById(R.id.messageTextView);
//			messageTextView.setText(message);
//
//		}

	}

	public void addContact(View view) {

		name = enterName.getText().toString();
		email = enterEmail.getText().toString();

		if (!isValidEmail(email)) {

			Toast.makeText(getApplicationContext(),
					"Email address is not valid", Toast.LENGTH_SHORT).show();
			enterEmail.setText("");

		} else {

			resume = true;
		}

		if (resume) {

			LotteryPoolManagerApplication application = (LotteryPoolManagerApplication) getApplication();
			application.addContactToDatabase(name, email);
			messageTextView.setText("");
			enterEmail.setText("");
			enterName.setText("");
			
			Intent intent = new Intent(this, ShowContactsAndEmailPBTickets.class);
			intent.putExtra("drawingDate", 0);
			startActivity(intent);
		}
	}

	public final boolean isValidEmail(CharSequence target) {
		if (target == null) {
			return false;
		} else {
			return android.util.Patterns.EMAIL_ADDRESS.matcher(target)
					.matches();
		}
	}
	
}

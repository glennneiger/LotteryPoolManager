package com.epichomeservices.powerballpoolmanager;

import android.app.Application;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

//import com.google.android.gms.analytics.GoogleAnalytics;
//import com.google.android.gms.analytics.Tracker;

import java.util.ArrayList;
import java.util.HashMap;

import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.CONTACT_EMAIL;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.CONTACT_NAME;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.DRAWING_DATE;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.EMAIL_ADDRESS_TABLE;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.GROUP_ID;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.MEGABALL_NUMBER;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.MEGA_MILLIONS_TICKETS_TABLE;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.NUMBER1;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.NUMBER2;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.NUMBER3;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.NUMBER4;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.NUMBER5;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.POWERBALL_NUMBER;
import static com.epichomeservices.powerballpoolmanager.LotteryManagerDatabaseHelper.POWERBALL_TICKETS_TABLE;

public class LotteryPoolManagerApplication extends Application {
//    // The following line should be changed to include the correct property id.
//    private static final String PROPERTY_ID = "UX-XXXXXXXX-X";
//    //Logging TAG
//    private static final String TAG = "MyApp";
//    public static int GENERAL_TRACKER = 0;

	private ArrayList<Contacts> contacts;
	private ArrayList<int[]> ticketsArrayList = new ArrayList<int[]>();
	private ArrayList<PowerballTickets> powerballTickets;
	private ArrayList<MegaMillionsTickets> megaMillionsTickets;
	private SQLiteDatabase lotteryTicketsDatabase;
	private String powerballWinningNums;
	private String powerballDrawDate;
	private String megamillionsWinningNums;
	private String megamillionsDrawDate;

//    public enum TrackerName {
//        APP_TRACKER, // Tracker used only in this app.
//        GLOBAL_TRACKER, // Tracker used by all the apps from a company. eg: roll-up tracking.
//        ECOMMERCE_TRACKER, // Tracker used by all ecommerce transactions from a company.
//    }
//
//    HashMap<TrackerName, Tracker> mTrackers = new HashMap<TrackerName, Tracker>();
//
//    public LotteryPoolManagerApplication() {
//        super();
//    }
//
//    synchronized Tracker getTracker(TrackerName trackerId) {
//        if (!mTrackers.containsKey(trackerId)) {
//
//            GoogleAnalytics analytics = GoogleAnalytics.getInstance(this);
//            Tracker t = (trackerId == TrackerName.APP_TRACKER) ? analytics.newTracker(R.xml.app_tracker)
//                    : (trackerId == TrackerName.GLOBAL_TRACKER) ? analytics.newTracker(PROPERTY_ID)
//                    : analytics.newTracker(R.xml.ecommerce_tracker);
//            mTrackers.put(trackerId, t);
//
//        }
//        return mTrackers.get(trackerId);
//    }

	@Override
	public void onCreate() {
		super.onCreate();

		LotteryManagerDatabaseHelper lottoDatabaseHelper = new LotteryManagerDatabaseHelper(
				this);
		lotteryTicketsDatabase = lottoDatabaseHelper.getWritableDatabase();
		

	}
	
	public String getPowerballWinningNums() {
		return powerballWinningNums;
	}

	public void setPowerballWinningNums(String powerballWinningNums) {
		this.powerballWinningNums = powerballWinningNums;
	}

	public String getPowerballDrawDate() {
		return powerballDrawDate;
	}

	public void setPowerballDrawDate(String powerballDrawDate) {
		this.powerballDrawDate = powerballDrawDate;
	}

	public String getMegamillionsWinningNums() {
		return megamillionsWinningNums;
	}

	public void setMegamillionsWinningNums(String megamillionsWinningNums) {
		this.megamillionsWinningNums = megamillionsWinningNums;
	}

	public String getMegamillionsDrawDate() {
		return megamillionsDrawDate;
	}

	public void setMegamillionsDrawDate(String megamillionsDrawDate) {
		this.megamillionsDrawDate = megamillionsDrawDate;
	}
	
	public ArrayList<MegaMillionsTickets> getAllMegaMillionsTickets() {
		
		return megaMillionsTickets;
	}

	public ArrayList<PowerballTickets> getAllPowerballTickets() {

		return powerballTickets;
	}
	
	public void readAllMMTicketsByDate(long date) {

		megaMillionsTickets = new ArrayList<MegaMillionsTickets>();

		Cursor cursor;

		cursor = lotteryTicketsDatabase.query(MEGA_MILLIONS_TICKETS_TABLE,
				new String[] { DRAWING_DATE, GROUP_ID, NUMBER1, NUMBER2,
						NUMBER3, NUMBER4, NUMBER5, MEGABALL_NUMBER },
				DRAWING_DATE + "=?", new String[] { String.valueOf(date) },
				null, null, null); // where date = date inquired

		cursor.moveToFirst();

		MegaMillionsTickets tempMMObject;

		if (!cursor.isAfterLast()) {

			// date = cursor.getLong(0);
			do {

				long drawDate = cursor.getLong(0);
				int num1 = cursor.getInt(2);
				int num2 = cursor.getInt(3);
				int num3 = cursor.getInt(4);
				int num4 = cursor.getInt(5);
				int num5 = cursor.getInt(6);
				int numMM = cursor.getInt(7);

				tempMMObject = new MegaMillionsTickets(drawDate, num1, num2, num3,
						num4, num5, numMM);

				megaMillionsTickets.add(tempMMObject);

			} while (cursor.moveToNext());

			cursor.close();

		}
	}

	public void readAllPBTicketsByDate(long date) {

		powerballTickets = new ArrayList<PowerballTickets>();

		Cursor cursor;

		cursor = lotteryTicketsDatabase.query(POWERBALL_TICKETS_TABLE,
				new String[] { DRAWING_DATE, GROUP_ID, NUMBER1, NUMBER2,
						NUMBER3, NUMBER4, NUMBER5, POWERBALL_NUMBER },
				DRAWING_DATE + "=?", new String[] { String.valueOf(date) },
				null, null, null); // where date = date inquired

		cursor.moveToFirst();

		PowerballTickets tempPBObject;

		if (!cursor.isAfterLast()) {

			// date = cursor.getLong(0);
			do {

				long drawDate = cursor.getLong(0);
				int num1 = cursor.getInt(2);
				int num2 = cursor.getInt(3);
				int num3 = cursor.getInt(4);
				int num4 = cursor.getInt(5);
				int num5 = cursor.getInt(6);
				int numPB = cursor.getInt(7);

				tempPBObject = new PowerballTickets(drawDate, num1, num2, num3,
						num4, num5, numPB);

				powerballTickets.add(tempPBObject);

			} while (cursor.moveToNext());

			cursor.close();

		}
	}

	public void addPBTicketsToDatabase(PowerballTickets powerballTickets) {

		assert powerballTickets != null;

		ContentValues cv = new ContentValues();
		ticketsArrayList = powerballTickets.getPbTickets();

		for (int[] ticketArray : ticketsArrayList) {

			cv.put(LotteryManagerDatabaseHelper.DRAWING_DATE,
					powerballTickets.getDrawingDate());
			cv.put(LotteryManagerDatabaseHelper.GROUP_ID, "testGoup");
			cv.put(LotteryManagerDatabaseHelper.NUMBER1, ticketArray[0]);
			cv.put(LotteryManagerDatabaseHelper.NUMBER2, ticketArray[1]);
			cv.put(LotteryManagerDatabaseHelper.NUMBER3, ticketArray[2]);
			cv.put(LotteryManagerDatabaseHelper.NUMBER4, ticketArray[3]);
			cv.put(LotteryManagerDatabaseHelper.NUMBER5, ticketArray[4]);
			cv.put(LotteryManagerDatabaseHelper.POWERBALL_NUMBER,
					ticketArray[5]);

			lotteryTicketsDatabase.insert(
					LotteryManagerDatabaseHelper.POWERBALL_TICKETS_TABLE,
					null, cv);
		}

	}
	
	public void addMMTicketsToDatabase(MegaMillionsTickets megaMillionsTickets) {

		assert powerballTickets != null;

		ContentValues cv = new ContentValues();
		ticketsArrayList = megaMillionsTickets.getmmTickets();

		for (int[] ticketArray : ticketsArrayList) {

			cv.put(LotteryManagerDatabaseHelper.DRAWING_DATE,
					megaMillionsTickets.getDrawingDate());
			cv.put(LotteryManagerDatabaseHelper.GROUP_ID, "testGoup");
			cv.put(LotteryManagerDatabaseHelper.NUMBER1, ticketArray[0]);
			cv.put(LotteryManagerDatabaseHelper.NUMBER2, ticketArray[1]);
			cv.put(LotteryManagerDatabaseHelper.NUMBER3, ticketArray[2]);
			cv.put(LotteryManagerDatabaseHelper.NUMBER4, ticketArray[3]);
			cv.put(LotteryManagerDatabaseHelper.NUMBER5, ticketArray[4]);
			cv.put(LotteryManagerDatabaseHelper.MEGABALL_NUMBER,
					ticketArray[5]);

			lotteryTicketsDatabase.insert(
					LotteryManagerDatabaseHelper.MEGA_MILLIONS_TICKETS_TABLE,
					null, cv);
		}

	}

	public void addContactToDatabase(String name, CharSequence email) {

		ContentValues cv = new ContentValues();

		cv.put(CONTACT_NAME, name);
		cv.put(CONTACT_EMAIL, (String) email);

		lotteryTicketsDatabase.insert(EMAIL_ADDRESS_TABLE, null, cv);
	}

	public ArrayList<Contacts> getAllContacts() {

		contacts = new ArrayList<Contacts>();

		Cursor cursor;

		cursor = lotteryTicketsDatabase.query(EMAIL_ADDRESS_TABLE,
				new String[] { CONTACT_NAME, CONTACT_EMAIL }, null, null, null,
				null, null);

		cursor.moveToFirst();

		Contacts tempContactsObject;

		if (!cursor.isAfterLast()) {

			do {
				String name = cursor.getString(0);
				String emailAddress = cursor.getString(1);

				tempContactsObject = new Contacts(name, emailAddress);

				contacts.add(tempContactsObject);
			} while (cursor.moveToNext());

			cursor.close();
		}

		return contacts;

	}
	
	public void deleteContacts(String email) {
		
		lotteryTicketsDatabase.delete(EMAIL_ADDRESS_TABLE, CONTACT_EMAIL + "=?",
				new String[] { email});
			
	}

}

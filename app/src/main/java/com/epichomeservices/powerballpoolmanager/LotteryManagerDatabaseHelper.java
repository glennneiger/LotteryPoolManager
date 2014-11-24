package com.epichomeservices.powerballpoolmanager;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class LotteryManagerDatabaseHelper extends SQLiteOpenHelper{
	
	public static final String DB_NAME = "LotteryPoolManager.sqlite";
	public static final int DB_VERSION = 1;
	public static final String POWERBALL_TICKETS_TABLE = "PowerballTicketsTable";
	public static final String MEGA_MILLIONS_TICKETS_TABLE = "MegaMillionsTicketsTable";
	public static final String RECORD_ID = "ID";
	public static final String DRAWING_DATE = "DrawingDate";
	public static final String GROUP_ID = "GroupID";
	public static final String NUMBER1 = "Number1";
	public static final String NUMBER2 = "Number2";
	public static final String NUMBER3 = "Number3";
	public static final String NUMBER4 = "Number4";
	public static final String NUMBER5 = "Number5";
	public static final String MEGABALL_NUMBER = "MegaballNumber";
	public static final String POWERBALL_NUMBER = "PowerballNumber";
	public static final String EMAIL_ADDRESS_TABLE = "emailAddressTable";
	public static final String CONTACT_ID = "contactID";
	public static final String CONTACT_NAME = "contactName";
	public static final String CONTACT_EMAIL = "contactEmail";
	

	public LotteryManagerDatabaseHelper(Context context) {
		super(context, DB_NAME, null, DB_VERSION);
		
	}

	@Override
	public void onCreate(SQLiteDatabase db) {
		
		String sqlStatementForPBNumbers = "create table " + POWERBALL_TICKETS_TABLE +
				" (" + RECORD_ID + " integer primary key autoincrement not null,"
				+ DRAWING_DATE + " integer," + GROUP_ID + " text," + NUMBER1 + " integer," 
				+ NUMBER2 + " integer," + NUMBER3 + " integer," + NUMBER4 + " integer,"
				+ NUMBER5 + " integer," + POWERBALL_NUMBER + " integer" + ");";
		
		String sqlStatementForMMNumbers = "create table " + MEGA_MILLIONS_TICKETS_TABLE +
				" (" + RECORD_ID + " integer primary key autoincrement not null,"
				+ DRAWING_DATE + " integer," + GROUP_ID + " text," + NUMBER1 + " integer," 
				+ NUMBER2 + " integer," + NUMBER3 + " integer," + NUMBER4 + " integer,"
				+ NUMBER5 + " integer," + MEGABALL_NUMBER + " integer" + ");";
		
		String sqlStatementForEmail = "create table " + EMAIL_ADDRESS_TABLE +
				" (" + CONTACT_ID + " integer primary Key autoincrement not null,"
				+ CONTACT_NAME + " text," + CONTACT_EMAIL + " text" + ");";
		
		db.execSQL(sqlStatementForPBNumbers);
		db.execSQL(sqlStatementForMMNumbers);
		db.execSQL(sqlStatementForEmail);
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		
		
	}
	
	

}

package com.epichomeservices.powerballpoolmanager;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MainGameSelection extends Activity{


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_game_selection);

	}
	
	@Override
	  public void onResume() {
	    super.onResume();

	  }

	  @Override
	  public void onPause() {
	    super.onPause();
	  }

	  /** Called before the activity is destroyed. */
	  @Override
	  public void onDestroy() {

	    super.onDestroy();
	  }
	  
	  @Override
	  public void onStart() {
	    super.onStart();

	  }

	  @Override
	  public void onStop() {
	    super.onStop();

	  }
	
	public void powerballSetup(View view){
		
		Intent intent = new Intent(this, SetupTicketsPowerBall.class);
		startActivity(intent);
	}
	
	public void megaMillionsSetup(View view) {
		
		Intent intent = new Intent(this, SetupTicketsMegaMillions.class);
		startActivity(intent);
	}
	
	public void checkPBResults(View view){
		Intent intent = new Intent(this, EnterResultsPB.class);
		startActivity(intent);
	}
	
	public void checkMMResults(View view){
		Intent intent = new Intent(this, EnterResultsMM.class);
		startActivity(intent);
	}

}

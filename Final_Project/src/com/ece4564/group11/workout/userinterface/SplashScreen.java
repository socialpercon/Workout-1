package com.ece4564.group11.workout.userinterface;

import com.example.final_project.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class SplashScreen extends Activity {

	public final static String EXTRA_MESSAGE = "message";
	Button startButton_;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);
		
		startButton_ = (Button) findViewById(R.id.button1);
		
		setUpClickListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}
	
	private void setUpClickListeners()
	{
		startButton_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						startApp(1);
					}
				}
		);
	}
	
	@SuppressWarnings("unused")
	private void startApp( int id)
	{
		Intent intent = new Intent(this, ViewpagerNav.class);
		String activityMessage = "";
		
		switch (id) 
		{
			case 1:	
					intent.putExtra(EXTRA_MESSAGE, "default");
					break;
			default:
					break;
		}
		
		startActivity(intent);
	}

}

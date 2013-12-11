package com.ece4564.group11.workout.userinterface;

/**
 * This is the main page when the user starts the application.
 */

import java.util.Random;

import com.ece4564.group11.workout.network.QuoteNetworkTask;
import com.example.final_project.R;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.TextView;

public class SplashScreen extends Activity {

	public final static String EXTRA_MESSAGE = "message";
	// Button startButton_;
	ImageButton heartButton_;
	ImageButton plannerButton_;
	ImageButton buddyButton_;
	ImageButton settingsButton_;
	ImageButton workoutButton_;

	TextView quote_;
	// Used to choose a quote at random
	Random r = new Random();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash_screen);

		// startButton_ = (Button) findViewById(R.id.button1);
		heartButton_ = (ImageButton) findViewById(R.id.imageButton1);
		plannerButton_ = (ImageButton) findViewById(R.id.imageButton2);
		buddyButton_ = (ImageButton) findViewById(R.id.imageButton3);
		settingsButton_ = (ImageButton) findViewById(R.id.imageButton4);
		workoutButton_ = (ImageButton) findViewById(R.id.imageButton5);

		quote_ = (TextView) findViewById(R.id.textView1);
		getQuote();

		setUpClickListeners();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.splash_screen, menu);
		return true;
	}

	private void setUpClickListeners() {

		heartButton_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startApp(1);
			}
		});
		plannerButton_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startApp(2);
			}
		});
		buddyButton_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startApp(3);
			}
		});
		/*
		 * settingButton_.setOnClickListener( new OnClickListener() {
		 * 
		 * @Override public void onClick(View arg0) { startApp(4); } } );
		 */
		workoutButton_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startApp(5);
			}
		});

	}

	@SuppressWarnings("unused")
	private void startApp(int id) {
		Intent intent = new Intent(this, ViewpagerNav.class);
		String activityMessage = "";

		switch (id) {
		case 1:
			intent.putExtra(EXTRA_MESSAGE, "heart");
			break;
		case 2:
			intent.putExtra(EXTRA_MESSAGE, "planner");
			break;
		case 3:
			intent.putExtra(EXTRA_MESSAGE, "buddy");
			break;
		// case 4:
		// intent.putExtra(EXTRA_MESSAGE, "settings");
		// break;
		case 5:
			intent.putExtra(EXTRA_MESSAGE, "workout");
			break;
		default:
			break;
		}

		startActivity(intent);
	}

	private void getQuote() {
		QuoteNetworkTask qnt = new QuoteNetworkTask(
				"http://ec2-54-212-21-86.us-west-2.compute.amazonaws.com/",
				quote_);
		qnt.execute();
	}
}

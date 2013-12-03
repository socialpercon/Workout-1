package com.ece4564.group11.workout.fragments;

import com.ece4564.group11.workout.media.FileExplore;
import com.ece4564.group11.workout.media.MusicPlayer;
import com.ece4564.group11.workout.network.WorkoutProgressBarTask;
import com.example.final_project.R;

import android.content.Intent;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class WorkoutFragment extends Fragment {
	private ProgressBar progressBar_;
	private static String time_;
	
	// MUSIC
	TextView test_;
	Button play_;
	Button stop_;
	Button switch_;
	Button browse_;
	Button browse2_;
	MusicPlayer player_;
	boolean ready1;
	boolean ready2;
	// 
	
	public WorkoutFragment() {
		player_ = new MusicPlayer();
		ready1 = false;
		ready2 = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_workout, container,
				false);
		progressBar_ = (ProgressBar) rootView
				.findViewById(R.id.workout_progressBar);
		progressBar();
		
		// MUSIC
		play_ = (Button) rootView.findViewById(R.id.playbutton);
		play_.setEnabled(false);
		stop_ = (Button) rootView.findViewById(R.id.stopbutton);
		stop_.setEnabled(false);
		switch_ = (Button) rootView.findViewById(R.id.switchbutton);
		browse_ = (Button) rootView.findViewById(R.id.browse1);
		browse2_ = (Button) rootView.findViewById(R.id.browse2);
		switch_.setEnabled(false);
		setUpListen();
		//
		
		return rootView;
	}

	public static void getTimeValue(int time) {
		time_ = Integer.toString(time);
	}

	public void progressBar() {
		final WorkoutProgressBarTask wpbt = new WorkoutProgressBarTask();
		System.out.println("ProgressBarTime" + time_);
		wpbt.execute(time_);

	}
	
	// MUSIC
	@Override
	public void onPause() 
	{
		player_.pause();
		super.onPause();
	}

	@Override
	public void onDestroy() {

		player_.release();
		super.onDestroy();
	}

	private void setUpListen()
	{
		play_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						play_.setEnabled(false);
						stop_.setEnabled(true);
						switch_.setEnabled(true);
						player_.start(0);
					}
				}
		);
		
		stop_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						play_.setEnabled(true);
						stop_.setEnabled(false);
						switch_.setEnabled(false);
						player_.pause();
					}
				}
		);
		
		switch_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						switchMP();
					}
				}
		);
		
		browse_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						startbrowse();
					}
				}
		);
		
		browse2_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						startbrowse2();
					}
				}
		);
		
		OnPreparedListener opl = new OnPreparedListener()
					{
						public void onPrepared(MediaPlayer mp) 
						{
							ready1 = true;
							if (ready1 && ready2)
							{
								ready1 = false;
								ready2 = false;
								play_.setEnabled(true);
							}
						}
					};
		
		OnPreparedListener opl2 = new OnPreparedListener()
					{
						public void onPrepared(MediaPlayer mp) 
						{
							ready2 = true;
							if (ready1 && ready2)
							{
								ready1 = false;
								ready2 = false;
								play_.setEnabled(true);
							}
						}
					};
		
		player_.setOnPreparedListener(opl, opl2);
	}
	
	private void switchMP()
	{
		player_.switchSongs();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		this.getActivity().finishActivity(1);
		this.getActivity().finishActivity(2);
		String result = data.getStringExtra("path");
		test_.setText(result);
		
		if (requestCode == 1 && result.length() > 0)
		{
			player_.setFirstPath(result);
		}
		else if (requestCode == 2 && result.length() > 0)
		{
			player_.setSecondPath(result);
		}
	}

	private void startbrowse()
	{
		Intent intent = new Intent(getActivity(), FileExplore.class);
		this.startActivityForResult(intent, 1);
	}
	
	private void startbrowse2()
	{
		Intent intent = new Intent(getActivity(), FileExplore.class);
		this.startActivityForResult(intent, 2);
	}
	
}

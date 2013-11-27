package com.ece4564.group11.workout.testexample;

import android.media.MediaPlayer;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.ece4564.group11.workout.media.MusicPlayer;
import com.example.final_project.R;

public class testfrag extends Fragment {
	
	TextView test_;
	Button play_;
	Button stop_;
	Button init_;
	Button switch_;
	MusicPlayer mp_;
	MusicPlayer mp2_;
	boolean ready1;
	boolean ready2;
	int state;
	
	public testfrag() 
	{
		mp_ = new MusicPlayer();
		mp2_ = new MusicPlayer();
		ready1 = false;
		ready2 = false;
		state = 0;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
	{
		
		View rootView = inflater.inflate(R.layout.fragment_test, container, false);
		
		test_ = (TextView) rootView.findViewById(R.id.test);
		play_ = (Button) rootView.findViewById(R.id.playbutton);
		play_.setEnabled(false);
		stop_ = (Button) rootView.findViewById(R.id.stopbutton);
		stop_.setEnabled(false);
		init_ = (Button) rootView.findViewById(R.id.init);
		switch_ = (Button) rootView.findViewById(R.id.Switch);
		
		setUpListen();
		
		return rootView;
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
						mp_.start();
						state = 1;
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
						mp_.pause();
						mp2_.pause();
						state = 0;
					}
				}
		);
		
		init_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						init();
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
		mp_.setOnPreparedListener(opl);
		
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
		
		mp2_.setOnPreparedListener(opl2);
	}
	
	private void init()
	{
		mp_.reset();
		mp2_.reset();
		mp_.setData("/storage/emulated/0/test/test.mp3");
		mp2_.setData("/storage/emulated/0/test/test.mp3");
		play_.setEnabled(false);
		stop_.setEnabled(false);
		test_.setText("ready to play");
	}
	
	private void switchMP()
	{
		if (state == 1)
		{
			test_.setText("state = 1");
			state = 2;
			mp_.pause();
			mp2_.start();
		}
		else if (state == 2)
		{
			test_.setText("state = 2");
			state = 1;
			mp2_.pause();
			mp_.start();
		}
	}
	
}
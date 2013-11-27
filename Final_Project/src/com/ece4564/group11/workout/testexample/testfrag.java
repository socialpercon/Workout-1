package com.ece4564.group11.workout.testexample;

import android.content.Intent;
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

import com.ece4564.group11.workout.media.FileExplore;
import com.ece4564.group11.workout.media.MusicPlayer;
import com.example.final_project.R;

public class testfrag extends Fragment {
	
	TextView test_;
	Button play_;
	Button stop_;
	Button switch_;
	Button browse_;
	Button browse2_;
	MusicPlayer mp_;
	MusicPlayer mp2_;
	boolean ready1;
	boolean ready2;
	int state;
	String path1_;
	String path2_;
	boolean pathrdy1_;
	boolean pathrdy2_;
	
	public testfrag() 
	{
		mp_ = new MusicPlayer();
		mp2_ = new MusicPlayer();
		ready1 = false;
		ready2 = false;
		pathrdy1_ = false;
		pathrdy2_ = false;
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
		switch_ = (Button) rootView.findViewById(R.id.Switch);
		browse_ = (Button) rootView.findViewById(R.id.browse1);
		browse2_ = (Button) rootView.findViewById(R.id.browse2);
		switch_.setEnabled(false);
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
						switch_.setEnabled(true);
						if (state == 0 || state == 1)
						{
							mp_.start();
							state = 1;
						}
						else if (state == 2)
						{
							mp2_.start();
						}
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
						mp_.pause();
						mp2_.pause();
						state = 0;
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
		mp_.setData(path1_);
		mp2_.setData(path2_);
		play_.setEnabled(false);
		stop_.setEnabled(false);
		test_.setText("ready to play");
	}
	
	private void switchMP()
	{
		if (state == 1)
		{
			test_.setText("state = 2");
			state = 2;
			mp_.pause();
			mp2_.start();
		}
		else if (state == 2)
		{
			test_.setText("state = 1");
			state = 1;
			mp2_.pause();
			mp_.start();
		}
		else
		{
			test_.setText(state);
		}
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		super.onActivityResult(requestCode, resultCode, data);
		
		if (requestCode == 1)
		{
			pathrdy1_ = true;
			test_.setText(data.getStringExtra("path"));
			path1_ = data.getStringExtra("path");
		}
		else if (requestCode == 2)
		{
			pathrdy2_ = true;
			test_.setText(data.getStringExtra("path"));
			path2_ = data.getStringExtra("path");
		}
		
		if (pathrdy1_ && pathrdy2_)
		{
			init();
			pathrdy1_ = false;
			pathrdy2_ = false;
			play_.setEnabled(true);
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
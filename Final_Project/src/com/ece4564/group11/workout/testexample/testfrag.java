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

	TextView path1_;
	TextView path2_;
	Button play_;
	Button stop_;
	Button switch_;
	Button browse_;
	Button browse2_;
	MusicPlayer player_;
	boolean ready1;
	boolean ready2;

	public testfrag() {
		player_ = new MusicPlayer();
		ready1 = false;
		ready2 = false;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_test, container,
				false);

		path1_ = (TextView) rootView.findViewById(R.id.path1);
		//path2_ = (TextView) rootView.findViewById(R.id.path2);
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

	@Override
	public void onPause() {
		player_.pause();
		super.onPause();
	}

	@Override
	public void onDestroy() {

		player_.release();
		super.onDestroy();
	}

	private void setUpListen() {
		play_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				play_.setEnabled(false);
				stop_.setEnabled(true);
				switch_.setEnabled(true);
				player_.start(0);
			}
		});

		stop_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				play_.setEnabled(true);
				stop_.setEnabled(false);
				switch_.setEnabled(false);
				player_.pause();
			}
		});

		switch_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				switchMP();
			}
		});

		browse_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startbrowse();
			}
		});

		browse2_.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				startbrowse2();
			}
		});

		OnPreparedListener opl = new OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				ready1 = true;
				if (ready1 && ready2) {
					ready1 = false;
					ready2 = false;
					play_.setEnabled(true);
				}
			}
		};

		OnPreparedListener opl2 = new OnPreparedListener() {
			public void onPrepared(MediaPlayer mp) {
				ready2 = true;
				if (ready1 && ready2) {
					ready1 = false;
					ready2 = false;
					play_.setEnabled(true);
				}
			}
		};

		player_.setOnPreparedListener(opl, opl2);
	}

	private void switchMP() {
		player_.switchSongs();
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		this.getActivity().finishActivity(1);
		this.getActivity().finishActivity(2);
		String result = data.getStringExtra("path");
		
		path1_.setText(result);
//		path2_.setText(result);
		
		if (requestCode == 1 && result.length() > 0) {
			player_.setFirstPath(result);
		} else if (requestCode == 2 && result.length() > 0) {
			player_.setSecondPath(result);
		}
	}

	private void startbrowse() {
		Intent intent = new Intent(getActivity(), FileExplore.class);
		this.startActivityForResult(intent, 1);
	}

	private void startbrowse2() {
		Intent intent = new Intent(getActivity(), FileExplore.class);
		this.startActivityForResult(intent, 2);
	}
}
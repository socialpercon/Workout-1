package com.ece4564.group11.workout.fragments;

import com.ece4564.group11.workout.network.WorkoutProgressBarTask;
import com.example.final_project.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class WorkoutFragment extends Fragment {
	private ProgressBar progressBar_;
	private static String time_;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_workout, container,
				false);
		progressBar_ = (ProgressBar) rootView
				.findViewById(R.id.workout_progressBar);

		progressBar();

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
}

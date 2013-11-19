package com.ece4564.group11.workout.fragments;

import com.example.final_project.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class HeartRateFragment extends Fragment {
	private ProgressBar progressBar_;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_heartrate,
				container, false);
		progressBar_ = (ProgressBar) rootView
				.findViewById(R.id.heartrate_progressBar);
		
		return rootView;
	}
}

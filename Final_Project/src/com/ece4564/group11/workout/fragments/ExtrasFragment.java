package com.ece4564.group11.workout.fragments;

import java.util.UUID;

import com.ece4564.group11.workout.sensor.DeviceUuidFactory;
import com.ece4564.group11.workout.sensor.HeartRateMonitor;
import com.example.final_project.R;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class ExtrasFragment extends Fragment{

	Button heart_;
	Button uuidBtn_;
	TextView display_;

	public ExtrasFragment()
	{		
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(R.layout.fragment_extras, container, false);
		heart_ = (Button) rootView.findViewById(R.id.heartOn);
		uuidBtn_ = (Button) rootView.findViewById(R.id.viewID);
		display_ = (TextView) rootView.findViewById(R.id.extraText);
		
		setUpListeners();
		
		return rootView;
	}
	
	private void setUpListeners()
	{
		heart_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						startHRM();
					}
				}
		);
		
		uuidBtn_.setOnClickListener(
				new OnClickListener()
				{
					@Override
					public void onClick(View arg0) 
					{
						viewUUID();
					}
				}
		);
	}
	
	public void startHRM()
	{
		Intent intent = new Intent(getActivity(), HeartRateMonitor.class);
		this.startActivity(intent);
	}
	
	private void viewUUID()
	{
		UUID id = new DeviceUuidFactory(this.getView().getContext()).getDeviceUuid();
		String s = "Your Unique ID is: \n" + id.toString();
		display_.setText(s);
	}
}

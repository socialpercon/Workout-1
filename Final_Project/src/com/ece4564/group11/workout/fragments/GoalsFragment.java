package com.ece4564.group11.workout.fragments;

import java.util.ArrayList;

import com.example.final_project.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class GoalsFragment extends Fragment {
	
	ArrayAdapter<String> goalsAdapter_;
	ArrayList<String> goalsArray_;
	ListView goalsList_;
	
	Button addGoals_;
	Button addCustom_;
	
	public GoalsFragment() {
		goalsAdapter_ = null;
		goalsArray_ = new ArrayList<String>();
		goalsArray_.add("sadsadasd");
		goalsArray_.add("asdsad");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_goals, container,
				false);
		
		addGoals_ = (Button) rootView.findViewById(R.id.goalAdd);
		addCustom_ = (Button) rootView.findViewById(R.id.goalAddCust);
		
		goalsList_ = (ListView) rootView.findViewById(R.id.goalsList);
		goalsAdapter_ = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, goalsArray_);
		goalsList_.setAdapter(goalsAdapter_);
		setUpListeners();

		return rootView;
	}

	private void setUpListeners() {
	}
}

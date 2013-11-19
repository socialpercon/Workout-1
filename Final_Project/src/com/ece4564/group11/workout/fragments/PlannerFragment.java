package com.ece4564.group11.workout.fragments;

import java.util.ArrayList;
import java.util.List;

import com.example.final_project.R;

import android.support.v4.app.Fragment;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;

public class PlannerFragment extends Fragment {

	private Spinner muscleGrpSpinner_;
	private ListView suggestedExercisesList_;
	private ListView plannedWorkoutList_;
	private Button addListButton_;
	private EditText addListWorkoutName_;
	private EditText addListTime_;
	private EditText addListSets_;
	private Button addListCreateButton_;
	private Button addListCancelButton_;
	private String addWorkoutName_;
	private String addWorkoutTime_;
	private String addWorkoutSets_;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_planner, container,
				false);
		muscleGrpSpinner_ = (Spinner) rootView
				.findViewById(R.id.planner_muscleGroupSpinner);
		suggestedExercisesList_ = (ListView) rootView
				.findViewById(R.id.planner_list_suggestedExercises);
		addListButton_ = (Button) rootView
				.findViewById(R.id.planner_ListPopupButton);
		plannedWorkoutList_ = (ListView) rootView
				.findViewById(R.id.planner_list_plannedWorkout);

		createSpinnerList();
		createSuggestedExerciseListView();
		createPlannedWorkoutListView();
		addListPopupWindow();

		return rootView;
	}

	public void addListPopupWindow() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.popup_planner_addlist);
				dialog.setTitle("Add to the list");
				addListCreateButton_ = (Button) dialog
						.findViewById(R.id.addlistpop_CreateButton);
				addListWorkoutName_ = (EditText) dialog
						.findViewById(R.id.addlistpop_workoutNameField);
				addListTime_ = (EditText) dialog
						.findViewById(R.id.addlistpop_timeField);
				addListSets_ = (EditText) dialog
						.findViewById(R.id.addlistpop_setsField);

				addListCancelButton_ = (Button) dialog
						.findViewById(R.id.addlistpop_CancelButton);
				OnClickListener createButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						Log.d("Add List Pop Workout Name", addListWorkoutName_
								.getText().toString());
						setWorkoutPlanName(addListWorkoutName_.getText()
								.toString());

						Log.d("Add List Pop Time", addListTime_.getText()
								.toString());
						setWorkoutPlanTime(addListTime_.getText().toString());

						Log.d("Add List Pop Sets", addListSets_.getText()
								.toString());
						setWorkoutPlanSets(addListSets_.getText().toString());

						dialog.dismiss();
					}

				};

				addListCreateButton_.setOnClickListener(createButtonListener);

				OnClickListener cancelButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}

				};
				addListCancelButton_.setOnClickListener(cancelButtonListener);
				dialog.show();
			}
		};

		addListButton_.setOnClickListener(listener);

	}

	public void setWorkoutPlanName(String name) {
		addWorkoutName_ = name;
	}

	public String getWorkoutPlanName() {
		return addWorkoutName_;
	}

	public void setWorkoutPlanTime(String time) {
		addWorkoutTime_ = time;
	}

	public String getWorkoutPlanTime() {
		return addWorkoutTime_;
	}

	public void setWorkoutPlanSets(String sets) {
		addWorkoutSets_ = sets;
	}

	public String getWorkoutPlanSets() {
		return addWorkoutSets_;
	}

	public void createSpinnerList() {
		List<String> list = new ArrayList<String>();
		// Add the muscle group here
		list.add("list1");
		list.add("list2");
		list.add("list3");

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		muscleGrpSpinner_.setAdapter(spinnerAdapter);
	}

	// Need to improve this function after finishing the pop-up
	public void createSuggestedExerciseListView() {
		List<String> list = new ArrayList<String>();
		int i = 1;
		while (i < 11) {
			list.add("ex" + i);
			i++;
		}
		suggestedExercisesList_.setAdapter(new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_list_item_1, list));
		suggestedExercisesList_
				.setOnItemClickListener(new OnItemClickListener() {

					@Override
					public void onItemClick(AdapterView<?> arg0, View arg1,
							int arg2, long arg3) {

					}
				});
	}

	// Need to improve this function after finishing the pop-up
	public void createPlannedWorkoutListView() {
		List<String> list = new ArrayList<String>();

		int i = 1;
		while (i < 11) {
			list.add("workout plan" + i);
			i++;
		}

		plannedWorkoutList_.setAdapter(new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, list));
		plannedWorkoutList_.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {

			}
		});
	}
}

package com.ece4564.group11.workout.fragments;

/**
 * Planner Fragment class. User can select their workout body parts and create
 * their own workout plan
 */

import java.util.ArrayList;
import java.util.List;

import com.example.final_project.R;

import android.support.v4.app.Fragment;
import android.app.Dialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

public class PlannerFragment extends Fragment {
	// fragment variables
	private Spinner muscleGrpSpinner_;
	private ListView suggestedExercisesList_;
	private ListView plannedWorkoutList_;
	private Button addListButton_;

	// addlist dialog variables
	private EditText addListWorkoutName_;
	private EditText addListTime_;
	private EditText addListSets_;
	private Button addListCreateButton_;
	private Button addListCancelButton_;

	// editlist dialog variables
	private EditText editListWorkoutName_;
	private EditText editListTime_;
	private EditText editListSets_;
	private Button editListEditButton_;
	private Button editListCancelButton_;

	// workout list variables
	private String workoutName_;
	private String workoutTime_;
	private String workoutSets_;
	private ArrayList<String> workoutList_;
	private ArrayAdapter<String> workoutAdapter_;

	public boolean FirstLoad = true;
	private List<String> muscleGrpList_;
	private String selectedMuscleGrp_;

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

		muscleGrpList_ = createSpinnerList();

		/*
		 * Listener links the muscleGroupSpinner and the suggestedExercises
		 * listView
		 */
		muscleGrpSpinner_
				.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
					public void onItemSelected(AdapterView<?> adapterView,
							View view, int i, long l) {
						if (FirstLoad) {
							FirstLoad = false;
							return;
						}
						String selectedMuscleGrp_ = muscleGrpSpinner_
								.getItemAtPosition(i).toString();
						createSuggestedExerciseListView(selectedMuscleGrp_);
						// System.out.println("\nSELECTED MUSCLE : " +
						// SpinnerChoice);
						// Toast.makeText(muscleGrpSpinner_.getContext(),
						// "You selected..." + SpinnerChoice,
						// Toast.LENGTH_LONG).show();
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});
		//

		// createSuggestedExerciseListView();
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

						workoutList_.add(getWorkoutPlanName());
						workoutAdapter_.notifyDataSetChanged();

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
		workoutName_ = name;
	}

	public String getWorkoutPlanName() {
		return workoutName_;
	}

	public void setWorkoutPlanTime(String time) {
		workoutTime_ = time;
	}

	public String getWorkoutPlanTime() {
		return workoutTime_;
	}

	public void setWorkoutPlanSets(String sets) {
		workoutSets_ = sets;
	}

	public String getWorkoutPlanSets() {
		return workoutSets_;
	}

	public List<String> createSpinnerList() {
		List<String> list = new ArrayList<String>();
		// Add the muscle group here
		list.add("Abdominals");
		list.add("Abductors");
		list.add("Adductors");
		list.add("Biceps");
		list.add("Calves");
		list.add("Chest");
		list.add("Forearms");
		list.add("Glutes");
		list.add("Hamstrings");
		list.add("Lats");
		list.add("Lower Back");
		list.add("Middle Back");
		list.add("Neck");
		list.add("Quadriceps");
		list.add("Shoulders");
		list.add("Traps");
		list.add("Triceps");

		ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<String>(
				getActivity(), android.R.layout.simple_spinner_item, list);
		spinnerAdapter
				.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		muscleGrpSpinner_.setAdapter(spinnerAdapter);
		return list;
	}

	// Need to improve this function after finishing the pop-up
	public void createSuggestedExerciseListView(String spinnerSelectedMuscleGrp) {
		List<String> list = new ArrayList<String>();
		// temporary list population
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
		workoutList_ = new ArrayList<String>();
		workoutAdapter_ = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, workoutList_);
		plannedWorkoutList_.setAdapter(workoutAdapter_);

		plannedWorkoutList_.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parentAdapter, View v,
					int position, long id) {
				final Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.popup_planner_editlist);
				dialog.setTitle("Edit");
				editListEditButton_ = (Button) dialog
						.findViewById(R.id.editlistpop_EditButton);
				editListWorkoutName_ = (EditText) dialog
						.findViewById(R.id.editlistpop_workoutNameField);
				editListTime_ = (EditText) dialog
						.findViewById(R.id.editlistpop_timeField);
				editListSets_ = (EditText) dialog
						.findViewById(R.id.editlistpop_setsField);

				editListCancelButton_ = (Button) dialog
						.findViewById(R.id.editlistpop_CancelButton);
				String selectedTitle = plannedWorkoutList_.getItemAtPosition(
						position).toString();
				Log.d("Selected to edit:", selectedTitle);
				OnClickListener editButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {

						workoutList_.add(getWorkoutPlanName());
						workoutAdapter_.notifyDataSetChanged();

						dialog.dismiss();
					}

				};

				editListEditButton_.setOnClickListener(editButtonListener);

				OnClickListener cancelButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}

				};
				editListCancelButton_.setOnClickListener(cancelButtonListener);
				dialog.show();
			}
		});
	}
}

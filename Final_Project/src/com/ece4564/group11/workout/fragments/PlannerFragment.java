package com.ece4564.group11.workout.fragments;

/**
 * Planner Fragment class. User can select their workout body parts and create
 * their own workout plan
 */

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.JSONException;
import org.json.JSONObject;

import com.ece4564.group11.workout.network.DataNetworkTask;
import com.ece4564.group11.workout.network.GetHtml;
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
import android.widget.TextView;
import android.widget.Toast;

public class PlannerFragment extends Fragment {
	// fragment variables
	private Spinner muscleGrpSpinner_;
	private ListView suggestedExercisesList_;
	private ListView plannedWorkoutList_;
	private Button addListButton_;
	private Button saveListButton_;
	private Button retrieveListButton_;

	// addlist dialog variables
	private EditText addListWorkoutName_;
	private EditText addListWorkoutTime_;
	private EditText addListRestTime_;
	private EditText addListSets_;
	private Button addListCreateButton_;
	private Button addListCancelButton_;

	// save workout list dialog variables
	private EditText saveDialogPlanName_;
	private EditText saveDialogWorkoutTag_;
	private Button saveDialogSaveButton_;
	private Button saveDialogCancelButton_;

	// retrieve workout list dialog variables
	private EditText retrieveDialogPlanName_;
	private Button retrieveDialogRetrieveButton_;
	private Button retrieveDialogCancelButton_;

	// plan data dialog variables
	private TextView dataDialogWorkoutName_;
	private TextView dataDialogWorkoutTime_;
	private TextView dataDialogRestTime_;
	private TextView dataDialogSets_;
	private Button dataDialogOKButton_;

	// workout list variables
	private ArrayList<String> workoutList_;
	private ArrayAdapter<String> workoutAdapter_;
	private ArrayList<String> exerciseList_;
	private ArrayAdapter<String> exerciseAdapter_;
	private Map<String, List<String>> planMap_ = new HashMap<String, List<String>>();

	public boolean FirstLoad = true;
	private List<String> muscleGrpList_;
	private String selectedMuscleGrp_;
	private String asyncSelectedMuscleGrp_;
	List<String> selectedMuscleGrpList_ = new ArrayList<String>();

	private String htmlStrings = "";

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
		saveListButton_ = (Button) rootView
				.findViewById(R.id.planner_saveListButton);
		retrieveListButton_ = (Button) rootView
				.findViewById(R.id.planner_retrieveListButton);
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
					}

					public void onNothingSelected(AdapterView<?> adapterView) {
						return;
					}
				});
		//

		// createSuggestedExerciseListView();
		createPlannedWorkoutListView();
		addListPopupWindow();
		savePlannedWorkoutList();
		retrievePlannerWorkoutList();

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
				addListWorkoutTime_ = (EditText) dialog
						.findViewById(R.id.addlistpop_workouttimeField);
				addListRestTime_ = (EditText) dialog
						.findViewById(R.id.addlistpop_resttimeField);
				addListSets_ = (EditText) dialog
						.findViewById(R.id.addlistpop_setsField);

				addListCancelButton_ = (Button) dialog
						.findViewById(R.id.addlistpop_CancelButton);
				OnClickListener createButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						String name = addListWorkoutName_.getText().toString();
						String workoutTime = addListWorkoutTime_.getText()
								.toString();
						String restTime = addListRestTime_.getText().toString();
						String sets = addListSets_.getText().toString();

						Log.d("Add List Pop Workout Name", name);
						Log.d("Add List Workout Time", workoutTime);
						Log.d("Add List Rest Time", restTime);
						Log.d("Add List Pop Sets", sets);

						List<String> values = new ArrayList<String>();
						values.add(workoutTime);
						values.add(restTime);
						values.add(sets);
						planMap_.put(name, values);

						workoutList_.add(name);
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
		list.add("Lower-Back");
		list.add("Middle-Back");
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
		// System.out.println("\nSELECTED MUSCLE: " + spinnerSelectedMuscleGrp);

		asyncSelectedMuscleGrp_ = spinnerSelectedMuscleGrp;
		try {
			new RetrieveHTMLString().execute();
		} catch (Exception e) {
			System.err.println("DEBUG: Exception caught");
			return;
		}

		// exerciseList_ = new ArrayList<String>();
		exerciseAdapter_ = new ArrayAdapter<String>(getActivity(),
				android.R.layout.simple_list_item_1, selectedMuscleGrpList_);
		suggestedExercisesList_.setAdapter(exerciseAdapter_);
		exerciseAdapter_.notifyDataSetChanged();

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
				dialog.setContentView(R.layout.popup_planner_plandata);
				dialog.setTitle("Plan Details");
				dataDialogWorkoutName_ = (TextView) dialog
						.findViewById(R.id.plandata_name);
				dataDialogWorkoutTime_ = (TextView) dialog
						.findViewById(R.id.plandata_workoutTime);
				dataDialogRestTime_ = (TextView) dialog
						.findViewById(R.id.plandata_restTime);
				dataDialogSets_ = (TextView) dialog
						.findViewById(R.id.plandata_sets);
				dataDialogOKButton_ = (Button) dialog
						.findViewById(R.id.plandata_OKButton);
				String selectedTitle = plannedWorkoutList_.getItemAtPosition(
						position).toString();
				Log.d("Selected to view:", selectedTitle);

				List<String> values = planMap_.get(selectedTitle);
				dataDialogWorkoutName_.setText("Plan Name: " + selectedTitle);
				dataDialogWorkoutTime_.setText("Workout Time: "
						+ values.get(0).toString());
				dataDialogRestTime_.setText("Rest Time: "
						+ values.get(1).toString());
				dataDialogSets_.setText("Sets: " + values.get(2).toString());
				OnClickListener okButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {

						dialog.dismiss();
					}

				};
				dataDialogOKButton_.setOnClickListener(okButtonListener);
				dialog.show();
			}
		});
	}

	public void savePlannedWorkoutList() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.popup_planner_save);
				dialog.setTitle("Save");
				saveDialogSaveButton_ = (Button) dialog
						.findViewById(R.id.savepopup_saveButton);
				saveDialogPlanName_ = (EditText) dialog
						.findViewById(R.id.savepopup_nameField);
				saveDialogCancelButton_ = (Button) dialog
						.findViewById(R.id.savepopup_cancelButton);

				OnClickListener saveButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						String planName = saveDialogPlanName_.getText()
								.toString();
						Log.d("Saving plan", planName);
						// JSONSTUFF HERE

						JSONObject j = new JSONObject();
						try {
							j.put(planName, planMap_);
						} catch (JSONException e) {
							e.printStackTrace();
						}
						System.out.println(j.toString());

						DataNetworkTask dnt = new DataNetworkTask(
								j.toString(),
								"http://ec2-54-212-21-86.us-west-2.compute.amazonaws.com/",
								"test");

						dnt.execute();

						dialog.dismiss();
					}

				};

				saveDialogSaveButton_.setOnClickListener(saveButtonListener);

				OnClickListener cancelButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}

				};
				saveDialogCancelButton_
						.setOnClickListener(cancelButtonListener);
				dialog.show();
			}
		};

		saveListButton_.setOnClickListener(listener);
	}

	public void retrievePlannerWorkoutList() {
		OnClickListener listener = new OnClickListener() {

			@Override
			public void onClick(View v) {
				final Dialog dialog = new Dialog(getActivity());
				dialog.setContentView(R.layout.popup_planner_retrieve);
				dialog.setTitle("Retrieve");
				retrieveDialogRetrieveButton_ = (Button) dialog
						.findViewById(R.id.retrievepopup_retrieveButton);
				retrieveDialogPlanName_ = (EditText) dialog
						.findViewById(R.id.retrievepopup_nameField);
				retrieveDialogCancelButton_ = (Button) dialog
						.findViewById(R.id.retrievepopup_cancelButton);

				OnClickListener retrieveButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						String retrievePlan = retrieveDialogPlanName_.getText()
								.toString();
						Log.d("Retrieving plan", retrievePlan);
						// JSON STUFF HERE

						dialog.dismiss();
					}

				};

				retrieveDialogRetrieveButton_
						.setOnClickListener(retrieveButtonListener);

				OnClickListener cancelButtonListener = new OnClickListener() {

					@Override
					public void onClick(View v) {
						dialog.dismiss();
					}

				};
				retrieveDialogCancelButton_
						.setOnClickListener(cancelButtonListener);
				dialog.show();
			}
		};

		retrieveListButton_.setOnClickListener(listener);
	}

	private class RetrieveHTMLString extends AsyncTask<String, Void, String> {
		protected String doInBackground(String... urls) {
			String htmlString;
			GetHtml test = new GetHtml();
			try {
				htmlString = test.getHtmlStrings(asyncSelectedMuscleGrp_);
				return htmlString;
			} catch (Exception e) {
				return null;
			}
		}

		protected void onPostExecute(String response) {
			String tempString = "";
			htmlStrings = response;
			selectedMuscleGrpList_.clear();

			// System.out.println("[DEBUG]" + htmlStrings);

			// Parsing
			// store suggested workout names
			Pattern name_Pattern = Pattern
					.compile("<span class='summary' style='display:none;'>(.*?)</span><span class=");
			Matcher matcher = name_Pattern.matcher(htmlStrings);
			while (matcher.find()) {
				tempString = matcher.group(1);
				selectedMuscleGrpList_.add(tempString);
				System.out.println("\nSUGGESTED LIST: " + tempString);
			}
		}

	}
}

package plannerfragment;

import java.util.ArrayList;
import java.util.List;

import com.example.final_project.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

public class PlannerFragment extends Fragment {
	private Spinner muscleGrpSpinner_;
	private ListView suggestedExercisesList_;
	private ListView plannedWorkoutList_;

	public PlannerFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_planner, container,
				false);
		muscleGrpSpinner_ = (Spinner) rootView
				.findViewById(R.id.planner_muscleGroupSpinner);
		suggestedExercisesList_ = (ListView) rootView
				.findViewById(R.id.planner_list_suggestedExercises);
		plannedWorkoutList_ = (ListView) rootView
				.findViewById(R.id.planner_list_plannedWorkout);

		createSpinnerList();
		createSuggestedExerciseListView();
		createPlannedWorkoutListView();

		return rootView;
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

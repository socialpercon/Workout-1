package plannerfragment;

import java.util.ArrayList;
import java.util.List;

import com.example.final_project.R;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class PlannerFragment extends Fragment {
	private Spinner muscleGrpSpinner_;

	public PlannerFragment() {

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_planner, container,
				false);
		muscleGrpSpinner_ = (Spinner) rootView
				.findViewById(R.id.planner_muscleGroupSpinner);
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

		return rootView;
	}
}

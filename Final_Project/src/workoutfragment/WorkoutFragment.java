package workoutfragment;

import com.example.final_project.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

public class WorkoutFragment extends Fragment {
	private ProgressBar progressBar_;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.fragment_workout, container,
				false);
		progressBar_ = (ProgressBar) rootView
				.findViewById(R.id.workout_progressBar);
		return rootView;
	}
}

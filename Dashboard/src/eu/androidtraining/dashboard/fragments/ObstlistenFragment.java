package eu.androidtraining.dashboard.fragments;

import android.app.Activity;
import android.app.ListFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import eu.androidtraining.dashboard.R;

public class ObstlistenFragment extends ListFragment {

	private OnObstSelectedListener mListener;
	
	public interface OnObstSelectedListener {
		public void onObstSelected(int index);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		return inflater.inflate(R.layout.fragments_obstlistenfragment, container, false);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		ArrayAdapter<String> obstlistenAdapter = 
				new ArrayAdapter<String>(
						getActivity(), 
						android.R.layout.simple_list_item_1, 
						getResources().getStringArray(R.array.fruechte)
				);
		setListAdapter(obstlistenAdapter);
	}

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		try {
			mListener = (OnObstSelectedListener) activity;
		} catch (ClassCastException e) {
			throw new ClassCastException(
					activity.toString() 
					+ " muss OnObstSelectedListener implementieren");
		}
	}

	@Override
	public void onListItemClick(ListView l, View v, int position, long id) {
		mListener.onObstSelected(position);
	}
}

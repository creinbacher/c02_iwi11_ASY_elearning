package eu.androidtraining.dashboard.fragments;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import eu.androidtraining.dashboard.R;

public class ObstFragment extends Fragment {

	public static final int[] BILDQUELLEN = new int[] {
		R.drawable.banane,
		R.drawable.apfel,
		R.drawable.birne,
		R.drawable.orange,
		R.drawable.zitrone,
		R.drawable.kiwi,
		R.drawable.weintraube
	};
	
	private int mBildIndex = -1;

	@Override
	public void onAttach(Activity activity) {
		super.onAttach(activity);
		
		Bundle extras = getActivity().getIntent().getExtras();
		
		if (extras != null) {
			mBildIndex = extras.getInt("selectedIndex");
		}
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragments_obstfragment, container, false);
		
		if (mBildIndex >= 0) {
			ImageView bild = (ImageView) view.findViewById(R.id.img_obst);
			bild.setImageResource(BILDQUELLEN[mBildIndex]);
		}
		
		return view;
	}
}

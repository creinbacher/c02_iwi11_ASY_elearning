package eu.androidtraining.dashboard.menues;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;
import eu.androidtraining.dashboard.R;

public class MenueIcons extends Activity {

	private int[] mIconIds = {
			android.R.drawable.ic_menu_add,
			android.R.drawable.ic_menu_agenda,
			android.R.drawable.ic_menu_always_landscape_portrait,
			android.R.drawable.ic_menu_call,
			android.R.drawable.ic_menu_camera,
			android.R.drawable.ic_menu_close_clear_cancel,
			android.R.drawable.ic_menu_compass,
			android.R.drawable.ic_menu_crop,
			android.R.drawable.ic_menu_day,
			android.R.drawable.ic_menu_delete,
			android.R.drawable.ic_menu_directions,
			android.R.drawable.ic_menu_edit,
			android.R.drawable.ic_menu_gallery,
			android.R.drawable.ic_menu_help,
			android.R.drawable.ic_menu_info_details,
			android.R.drawable.ic_menu_manage,
			android.R.drawable.ic_menu_mapmode,
			android.R.drawable.ic_menu_month,
			android.R.drawable.ic_menu_more,
			android.R.drawable.ic_menu_my_calendar,
			android.R.drawable.ic_menu_mylocation,
			android.R.drawable.ic_menu_myplaces,
			android.R.drawable.ic_menu_preferences,
			android.R.drawable.ic_menu_recent_history,
			android.R.drawable.ic_menu_report_image,
			android.R.drawable.ic_menu_revert,
			android.R.drawable.ic_menu_rotate,
			android.R.drawable.ic_menu_save,
			android.R.drawable.ic_menu_search,
			android.R.drawable.ic_menu_send,
			android.R.drawable.ic_menu_set_as,
			android.R.drawable.ic_menu_share,
			android.R.drawable.ic_menu_slideshow,
			android.R.drawable.ic_menu_sort_alphabetically,
			android.R.drawable.ic_menu_sort_by_size,
			android.R.drawable.ic_menu_today,
			android.R.drawable.ic_menu_upload,
			android.R.drawable.ic_menu_upload_you_tube,
			android.R.drawable.ic_menu_view,
			android.R.drawable.ic_menu_week,
			android.R.drawable.ic_menu_zoom
	};
	
	private GridView mIconGrid;
	
	private BaseAdapter mIconAdapter = new BaseAdapter() {
		
		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			
			ImageView icon;
			
			if (convertView == null) {
				icon = (ImageView) getLayoutInflater().inflate(
						R.layout.menues_icon_image_view,
						(ViewGroup) mIconGrid,
						false
				);
			} else {
				icon = (ImageView) convertView;
			}
			icon.setImageResource(mIconIds[position]);
			return icon;
		}
		
		@Override
		public long getItemId(int position) {
			return 0;
		}
		
		@Override
		public Object getItem(int position) {
			return null;
		}
		
		@Override
		public int getCount() {
			return mIconIds.length;
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.menues_icons);
		
		mIconGrid = (GridView) findViewById(R.id.gv_menues_icons);
		mIconGrid.setAdapter(mIconAdapter);
		
		mIconGrid.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position,
					long id) {
				Toast.makeText(MenueIcons.this, 
						getResources().getStringArray(R.array.menue_icon_namen)[position], 
						Toast.LENGTH_SHORT).show();
			}
		});
	}
}

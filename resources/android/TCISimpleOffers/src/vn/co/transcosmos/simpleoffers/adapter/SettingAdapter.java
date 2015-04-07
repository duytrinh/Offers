package vn.co.transcosmos.simpleoffers.adapter;

import java.util.ArrayList;

import vn.co.transcosmos.simpleoffers.R;
import vn.co.transcosmos.simpleoffers.model.Setting;
import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Switch;
import android.widget.TextView;

public class SettingAdapter extends BaseAdapter {

	private Activity activity;
	private ArrayList<Setting> data;
	private static LayoutInflater inflater = null;
	
	private TextView title;
	private Switch mySwitch;

	public SettingAdapter(Activity a, ArrayList<Setting> d) {
		activity = a;
		data = d;
		inflater = (LayoutInflater) activity
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return data.size();
	}

	public Object getItem(int position) {
		return position;
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(int position, View convertView, ViewGroup parent) {
		View vi = convertView;
		if (convertView == null)
			vi = inflater.inflate(R.layout.setting_row, null);

		title = (TextView) vi.findViewById(R.id.title);
		mySwitch = (Switch) vi.findViewById(R.id.mySwitch);
		
		Setting setting = (Setting) data
				.get(position);
		
		title.setText(setting.getTitle());
		mySwitch.setChecked(setting.isChecked());
		
		return vi;
	}

}

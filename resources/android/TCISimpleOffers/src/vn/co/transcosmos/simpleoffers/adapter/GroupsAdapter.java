package vn.co.transcosmos.simpleoffers.adapter;

import vn.co.transcosmos.simpleoffers.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import co.leonisand.libraries.Image.ImageListener;
import co.leonisand.offers.OffersGroup;

public class GroupsAdapter extends ArrayAdapter<OffersGroup> {

	public GroupsAdapter(Context context) {
		super(context, R.layout.news_row);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		OffersGroup offersGroup = getItem(position);
		ViewHolder viewHolder;

		if (convertView == null) {
			viewHolder = new ViewHolder();
			LayoutInflater inflater = LayoutInflater.from(getContext());
			convertView = inflater.inflate(R.layout.news_row, parent, false);
			viewHolder.textView1 = (TextView) convertView
					.findViewById(R.id.textView1);
			viewHolder.textView2 = (TextView) convertView
					.findViewById(R.id.textView2);
			viewHolder.imageView = (ImageView) convertView
					.findViewById(R.id.imageView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}

		viewHolder.textView1.setText(String.valueOf(offersGroup.getId()));

		viewHolder.textView2.setText(offersGroup.getName());

		offersGroup.groupImage1Bitmap(viewHolder.imageView,
				new ImageListener() {
					public void onDone(View view, Bitmap bitmap) {
						((ImageView) view).setImageBitmap(bitmap);
					}
				});

		return convertView;
	}

	private static class ViewHolder {
		public TextView textView1, textView2;
		public ImageView imageView;
	}

}

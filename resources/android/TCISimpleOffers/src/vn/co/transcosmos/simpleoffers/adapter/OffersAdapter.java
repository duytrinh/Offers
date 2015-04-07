package vn.co.transcosmos.simpleoffers.adapter;

import vn.co.transcosmos.simpleoffers.R;
import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import co.leonisand.libraries.Image.ImageListener;
import co.leonisand.offers.OffersCoupon;
import co.leonisand.offers.OffersOffer;
import co.leonisand.offers.OffersRecommendation;

@SuppressLint("InflateParams")
public class OffersAdapter extends ArrayAdapter<OffersOffer> {

	public OffersAdapter(Context context) {
		super(context, R.layout.news_row);
	}

	public View getView(int position, View convertView, ViewGroup parent) {

		OffersOffer offersOffer = getItem(position);
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

		if (offersOffer.getOfferType().equals("recommendation")) {
			OffersRecommendation offersrecommendation = (OffersRecommendation) offersOffer
					.getObject();

			offersrecommendation.thumbnailImageBitmap(viewHolder.imageView,
					new ImageListener() {

						public void onDone(View view, Bitmap bitmap) {
							((ImageView) view).setImageBitmap(bitmap);
						}

					});

			viewHolder.textView1.setText(offersOffer.getOfferType());
			viewHolder.textView2.setText(offersrecommendation.getName());

		} else {
			OffersCoupon offerscoupon = (OffersCoupon) offersOffer.getObject();

			offerscoupon.thumbnailImageBitmap(viewHolder.imageView,
					new ImageListener() {

						public void onDone(View view, Bitmap bitmap) {
							((ImageView) view).setImageBitmap(bitmap);
						}

					});

			viewHolder.textView1.setText(offersOffer.getOfferType());
			viewHolder.textView2.setText(offerscoupon.getTitle());
		}

		return convertView;
	}

	private static class ViewHolder {
		public TextView textView1, textView2;
		public ImageView imageView;
	}

}

package vn.co.transcosmos.simpleoffers;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import co.leonisand.libraries.Image.ImageListener;
import co.leonisand.offers.OffersCoupon;
import co.leonisand.offers.OffersKit;
import co.leonisand.offers.OffersKit.OffersListener;
import co.leonisand.offers.OffersOffer;
import co.leonisand.offers.OffersRecommendation;
import co.leonisand.offers.OffersStatus;

public class OffersFragment extends Fragment {

	public static String TITLE = "Offers";
	//private OffersAdapter mAdapter;
	//private ListView mListView;
	//private ProgressBar mProgressBar;
	
	private Context mContext;
	private ArrayAdapter<OffersOffer> mListAdapter;
	private ListView mListView;
	private ProgressBar mProgressBar;

	/*public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_offers, container,
				false);

		mProgressBar = (ProgressBar) rootView
				.findViewById(R.id.progressBarOffers);

		mListView = (ListView) rootView.findViewById(R.id.lvOffers);
		mAdapter = new OffersAdapter(getActivity());

		((HomeActivity) getActivity()).setActiobarTitle(TITLE);

		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub

				OffersOffer offersoffer = (OffersOffer) parent.getItemAtPosition(position);

				if (offersoffer.getOfferType().equals("recommendation")) {
					OffersRecommendation offersrecommendation = (OffersRecommendation) offersoffer
							.getObject();

					Bundle bundle = new Bundle();
					bundle.putInt("recommendation_id", offersoffer.getId());
					bundle.putString("type", offersrecommendation.getType());

					NewFragment newFragment = new NewFragment();
					newFragment.setArguments(bundle);
					getFragmentManager()
							.beginTransaction()
							.replace(R.id.container, newFragment,
									NewFragment.TITLE)
							.setTransition(
									FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
							.addToBackStack(null).commit();

				} else {
					OffersCoupon offerscoupon = (OffersCoupon) offersoffer
							.getObject();
					Bundle bundle = new Bundle();
					bundle.putInt("coupon_id", offersoffer.getId());
					bundle.putString("coupon_type",
							offerscoupon.getCouponType());

					OfferFragment offerFragment = new OfferFragment();
					offerFragment.setArguments(bundle);
					getFragmentManager()
							.beginTransaction()
							.replace(R.id.container, offerFragment,
									OfferFragment.TITLE)
							.setTransition(
									FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
							.addToBackStack(null).commit();
				}
			}
		});

		return rootView;
	}*/
	
	public View onCreateView(LayoutInflater layoutinflater, ViewGroup viewgroup, Bundle bundle) {
		
		((HomeActivity) getActivity()).setActiobarTitle(TITLE);
		
		mContext = layoutinflater.getContext();
		
		View view = layoutinflater.inflate(R.layout.fragment, viewgroup, false);
		
		FrameLayout framelayout = (FrameLayout) view.findViewById(R.id.fragment_content);
		
		mListView = new ListView(mContext);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterview, View view1, int i, long l) {
				OffersOffer offersoffer = (OffersOffer) adapterview.getItemAtPosition(i);
				if (offersoffer.getOfferType().equals("recommendation")) {
					OffersRecommendation offersrecommendation = (OffersRecommendation) offersoffer.getObject();

					Bundle bundle2 = new Bundle();
					bundle2.putInt("recommendation_id", offersoffer.getId());
					bundle2.putString("type", offersrecommendation.getType());
					
					NewFragment newFragment = new NewFragment();
					newFragment.setArguments(bundle2);
					
					getFragmentManager().beginTransaction()
						.replace(R.id.container, newFragment, offersrecommendation.getName())
						.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.addToBackStack(null)
						.commit();
					
					return;
				} else {
					OffersCoupon offerscoupon = (OffersCoupon) offersoffer.getObject();
					Bundle bundle1 = new Bundle();
					bundle1.putInt("coupon_id", offersoffer.getId());
					bundle1.putString("coupon_type", offerscoupon.getCouponType());
					OfferFragment offerFragment = new OfferFragment();
					offerFragment.setArguments(bundle1);
					getFragmentManager().beginTransaction().replace(R.id.container, offerFragment, offerscoupon.getTitle()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
					return;
				}
			}

		});

		mListAdapter = new ArrayAdapter<OffersOffer>(mContext, android.R.layout.simple_list_item_1) {
			private LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService("layout_inflater");

			@SuppressLint("InflateParams")
			public View getView(int i, View view1, ViewGroup viewgroup1) {
				OffersOffer offersoffer = (OffersOffer) getItem(i);
				if (view1 == null) {
					view1 = new LinearLayout(mContext);
					((LinearLayout) view1).setOrientation(LinearLayout.HORIZONTAL);
					
					ImageView imageview = new ImageView(mContext);
					imageview.setId(android.R.id.widget_frame);
					imageview.setLayoutParams(new LinearLayout.LayoutParams(72, 72));
					imageview.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
					((ViewGroup) view1).addView(imageview);
					
					TextView textview = (TextView) mLayoutInflater.inflate(android.R.layout.simple_list_item_1, null);
					textview.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0F));
					((ViewGroup) view1).addView(textview);
					
					TextView textview1 = new TextView(mContext);
					textview1.setId(android.R.id.text2);
					textview1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0F));
					((ViewGroup) view1).addView(textview1);
				}
				ImageView imageview1 = (ImageView) view1.findViewById(android.R.id.widget_frame);
				if (offersoffer.getOfferType().equals("recommendation")) {
					OffersRecommendation offersrecommendation = (OffersRecommendation) offersoffer.getObject();
					offersrecommendation.thumbnailImageBitmap(imageview1, new ImageListener() {

						public void onDone(View view, Bitmap bitmap) {
							((ImageView) view).setImageBitmap(bitmap);
						}

					});

					((TextView) view1.findViewById(android.R.id.text1)).setText(offersoffer.getOfferType());
					((TextView) view1.findViewById(android.R.id.text2)).setText(offersrecommendation.getName());
					
				} else {
					OffersCoupon offerscoupon = (OffersCoupon) offersoffer.getObject();
					offerscoupon.thumbnailImageBitmap(imageview1, new ImageListener() {

						public void onDone(View view, Bitmap bitmap) {
							((ImageView) view).setImageBitmap(bitmap);
						}

					});

					((TextView) view1.findViewById(android.R.id.text1)).setText(offersoffer.getOfferType());
					((TextView) view1.findViewById(android.R.id.text2)).setText(offerscoupon.getTitle());
				}
				return view1;
			}

		};

		mListView.setAdapter(mListAdapter);
		framelayout.addView(mListView);
		mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyle);
		mProgressBar.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) {
			{
				gravity = Gravity.CENTER;
			}
		});
		mProgressBar.setVisibility(View.GONE);
		framelayout.addView(mProgressBar);
		return view;
	}

	public void onStart() {

		super.onStart();
		mProgressBar.setVisibility(View.VISIBLE);
		OffersKit.getInstance().offers(true, new OffersListener() {

			public void onDone(Map<String, Object> map) {
				mProgressBar.setVisibility(View.GONE);

				mListAdapter.clear();
				if (map.get("offers") != null) {
					@SuppressWarnings("unchecked")
					List<OffersOffer> items = (List<OffersOffer>) map
							.get("offers");
					for (OffersOffer item : items) {
						// Get Coupons only
						if (item.getOfferType().equals("coupon")) {
							mListAdapter.add(item);
						}
						
					}

				} else {
					OffersStatus offersstatus = (OffersStatus) map
							.get("status");
					((HomeActivity) getActivity()).alert(
							"onDone",
							offersstatus.getCode() + ":"
									+ offersstatus.getMessage());
					return;
				}
			}

			public void onFail(Integer s) {
				mProgressBar.setVisibility(View.GONE);
				((HomeActivity) getActivity()).alert("onFail", s.toString());
			}
		});
	}

	public void onDestroyView() {
		mListAdapter = null;
		mListView.setAdapter(null);
		super.onDestroyView();
	}

}

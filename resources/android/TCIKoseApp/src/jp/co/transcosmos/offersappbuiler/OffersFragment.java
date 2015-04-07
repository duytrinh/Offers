package jp.co.transcosmos.offersappbuiler;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import co.leonisand.libraries.Image.ImageListener;
import co.leonisand.offers.OffersCoupon;
import co.leonisand.offers.OffersKit;
import co.leonisand.offers.OffersKit.OffersListener;
import co.leonisand.offers.OffersStatus;
import co.leonisand.offers.OffersTemplate;

public class OffersFragment extends Fragment {

	public static String TITLE = "COUPON";
	private Context mContext;

	private ArrayAdapter<OffersCoupon> mListAdapter;
	private ListView mListView;
	private ProgressBar mProgressBar;
	private int mTargetGroupId;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setHasOptionsMenu(true);

		// Get list coupon follow GroupId
		mTargetGroupId = 1;

		Bundle arguments = getArguments();
		if (arguments != null) {
			if (arguments.containsKey("group_id")) {
				mTargetGroupId = arguments.getInt("group_id");
			}
		}
	}

	public View onCreateView(LayoutInflater layoutinflater,
			ViewGroup viewgroup, Bundle bundle) {

		getFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);
		getActivity().getActionBar().setTitle(TITLE);
		
		mContext = layoutinflater.getContext();
		View view = layoutinflater.inflate(R.layout.fragment, viewgroup, false);
		
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		FrameLayout framelayout = (FrameLayout) view
				.findViewById(R.id.fragment_content);
		mListView = new ListView(mContext);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterview, View view1,
					int i, long l) {
				OffersCoupon offerscoupon = (OffersCoupon) adapterview
						.getItemAtPosition(i);
				Bundle bundle1 = new Bundle();
				bundle1.putInt("coupon_id", offerscoupon.getId());
				OfferFragment couponfragment = new OfferFragment();
				couponfragment.setArguments(bundle1);
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.fragment_content, couponfragment,
								offerscoupon.getTitle()).setTransition(4097)
						.addToBackStack(null).commit();
			}

		});

		mListAdapter = new ArrayAdapter<OffersCoupon>(mContext,
				android.R.layout.simple_list_item_1) {
			private LayoutInflater mLayoutInflater = (LayoutInflater) mContext
					.getSystemService("layout_inflater");

			@SuppressLint("InflateParams")
			public View getView(int i, View view1, ViewGroup viewgroup1) {
				if (view1 == null) {
					view1 = new LinearLayout(mContext);
					((LinearLayout) view1)
							.setOrientation(LinearLayout.VERTICAL);

					TextView tvTitle = (TextView) mLayoutInflater.inflate(
							android.R.layout.simple_list_item_1, null);
					tvTitle.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 1.0F));
					((ViewGroup) view1).addView(tvTitle);

					ImageView imageview = new ImageView(mContext);
					imageview.setId(android.R.id.widget_frame);
					RelativeLayout.LayoutParams layoutparams = new RelativeLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT);
					layoutparams.addRule(RelativeLayout.CENTER_HORIZONTAL);
					imageview.setLayoutParams(layoutparams);
					imageview
							.setScaleType(android.widget.ImageView.ScaleType.FIT_CENTER);
					((ViewGroup) view1).addView(imageview);

					TextView tvDesc = new TextView(mContext);
					tvDesc.setId(android.R.id.text2);
					tvDesc.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.WRAP_CONTENT,
							LayoutParams.WRAP_CONTENT, 1.0F));
					((ViewGroup) view1).addView(tvDesc);
				}
				
				OffersCoupon offerscoupon = (OffersCoupon) getItem(i);
				TextView tvTitle = (TextView) view1
						.findViewById(android.R.id.text1);
				TextView tvDesc = (TextView) view1
						.findViewById(android.R.id.text2);
				
				tvTitle.setText(offerscoupon.getTitle().toString());
				offerscoupon.thumbnailImageBitmap((ImageView) view1
						.findViewById(android.R.id.widget_frame),
						new ImageListener() {
							public void onDone(View view, Bitmap bitmap) {
								((ImageView) view).setImageBitmap(bitmap);
							}
						});
				tvDesc.setText(offerscoupon.getDescription().toString());

				final View _convertView = view1;

				// Set color for list_row Offerscoupon
				offerscoupon.template(new OffersListener() {
					public void onDone(Map<String, Object> map) {
						if (map.get("template") != null) {
							@SuppressWarnings("unchecked")
							Map<String, Object> map1 = (Map<String, Object>) ((OffersTemplate) map
									.get("template")).getValues().get(
									"background");
							_convertView.setBackgroundColor(Color
									.parseColor((String) map1.get("color")));
						}
					}

					public void onFail(Integer s) {
						((HomeActivity) getActivity()).alert("onFail",
								s.toString());
					}
				});

				return view1;
			}
		};

		mListView.setAdapter(mListAdapter);
		framelayout.addView(mListView);
		mProgressBar = new ProgressBar(mContext, null,
				android.R.attr.progressBarStyle);
		mProgressBar.setLayoutParams(new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) {
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
		OffersListener listener = new OffersListener() {
			public void onDone(Map<String, Object> map) {
				mProgressBar.setVisibility(View.GONE);

				mListAdapter.clear();

				if (map.get("coupons") != null) {
					@SuppressWarnings("unchecked")
					List<OffersCoupon> items = (List<OffersCoupon>) map
							.get("coupons");
					for (OffersCoupon item : items) {
						mListAdapter.add(item);
					}

				} else {
					OffersStatus offersstatus = (OffersStatus) map
							.get("status");
					((HomeActivity) getActivity()).alert(
							"coupons.onDone",
							offersstatus.getCode() + ":"
									+ offersstatus.getMessage());
					return;
				}
			}

			public void onFail(Integer s) {
				mProgressBar.setVisibility(View.GONE);
				((HomeActivity) getActivity()).alert("coupons.onFail",
						s.toString());
			}
		};

		Log.e("mTargetGroupId = ", String.valueOf(mTargetGroupId));
		if (mTargetGroupId == 1) {
			Bundle params = new Bundle();
			params.putString("sort_target", "delivery_from");
			params.putString("sort_direction", "descending");
			params.putString("offset", "0");
			params.putString("limit", "50");

			// Get OffersCoupon arrange DateTime
			OffersKit.getInstance().coupons(true, params, listener);
		} else {
			OffersKit.getInstance().coupons(new int[] { mTargetGroupId },
					listener);
		}
	}

	public void onDestroyView() {
		mListAdapter = null;
		mListView.setAdapter(null);
		super.onDestroyView();
	}

}

package vn.co.transcosmos.simpleoffers;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
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
import co.leonisand.offers.OffersStatus;
import co.leonisand.offers.OffersTemplate;

public class CouponsFragment extends Fragment {

	public static String TITLE = "Coupons";
	//private ArrayAdapter<OffersCoupon> mListAdapter;
	//private ListView mListView;
	//private ProgressBar mProgressBar;
	private int mTargetGroupId;
	
	private Context mContext;
	private ArrayAdapter<OffersCoupon> mListAdapter;
	private ListView mListView;
	private ProgressBar mProgressBar;
	
	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		// setHasOptionsMenu(true);

		// Get list coupon follow GroupId
		mTargetGroupId = 0;

		Bundle arguments = getArguments();
		if (arguments != null) {
			if (arguments.containsKey("group_id")) {
				mTargetGroupId = arguments.getInt("group_id");
			}
		}
	}

	/*public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_coupons, container,
				false);

		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

		mListView = (ListView) rootView.findViewById(R.id.lvCoupons);

		((HomeActivity) getActivity()).setActiobarTitle(TITLE);

		mListAdapter = new CouponsAdapter(getActivity());
		mListView.setAdapter(mListAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
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
				//To Do
			}

		});

		mListAdapter = new ArrayAdapter<OffersCoupon>(mContext, android.R.layout.simple_list_item_1) {
			private LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService("layout_inflater");
			
			@SuppressLint("InflateParams")
			public View getView(int i, View view1, ViewGroup viewgroup1) {
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
				}
				OffersCoupon offerscoupon = (OffersCoupon) getItem(i);
				TextView textview1 = (TextView) view1.findViewById(android.R.id.text1);
				
				String s;
				if (offerscoupon.getRead()){
					s = "既読";
				}else{
					s = "未読";
				}
				textview1.setText("[" + s + "]" + offerscoupon.getAvailableFrom().toString());
				offerscoupon.thumbnailImageBitmap((ImageView) view1.findViewById(android.R.id.widget_frame), new ImageListener() {
					public void onDone(View view, Bitmap bitmap) {
						((ImageView) view).setImageBitmap(bitmap);
					}
				});

				final View _convertView = view1;
				
				//Set color for list_row Offerscoupon
				offerscoupon.template(new OffersListener() {
					public void onDone(Map<String, Object> map) {
						if (map.get("template") != null) {
							@SuppressWarnings("unchecked")
							Map<String, Object> map1 = (Map<String, Object>) ((OffersTemplate) map.get("template")).getValues().get("background");
							_convertView.setBackgroundColor(Color.parseColor((String) map1.get("color")));
						}
					}

					public void onFail(Integer s) {
						((HomeActivity) getActivity()).alert("onFail", s.toString());
					}
				});

				return view1;
			}
		};

		mListView.setAdapter(mListAdapter);
		framelayout.addView(mListView);
		mProgressBar = new ProgressBar(mContext, null, android.R.attr.progressBarStyle);
		mProgressBar.setLayoutParams(new FrameLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) {{
			gravity = Gravity.CENTER;
		}});
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
				((HomeActivity) getActivity()).alert("coupons.onFail",
						s.toString());
			}
		};

		if (mTargetGroupId == 0) {
			Bundle params = new Bundle();
			params.putString("sort_target", "delivery_from");
			params.putString("sort_direction", "descending");
			params.putString("offset", "0");
			params.putString("limit", "20");

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

package vn.co.transcosmos.simpleoffers;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
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
import co.leonisand.offers.OffersGroup;
import co.leonisand.offers.OffersKit;
import co.leonisand.offers.OffersKit.OffersListener;
import co.leonisand.offers.OffersStatus;

public class GroupsFragment extends Fragment {

	public static String TITLE = "Groups";
	//private ArrayAdapter<OffersGroup> mAdapter;
	//private ListView mListView;
	//private ProgressBar mProgressBar;
	
	private Context mContext;
	private ArrayAdapter<OffersGroup> mListAdapter;
	private ListView mListView;
	private ProgressBar mProgressBar;
	private OffersGroup mSelectedGroup;

	/*public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_groups, container,
				false);

		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

		mListView = (ListView) rootView.findViewById(R.id.lvGroups);

		((HomeActivity) getActivity()).setActiobarTitle(TITLE);

		mAdapter = new GroupsAdapter(getActivity());
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				OffersGroup offersgroup = (OffersGroup) parent
						.getItemAtPosition(position);

				Bundle bundle = new Bundle();
				bundle.putInt("group_id", offersgroup.getId());

				CouponsFragment couponsfragment = new CouponsFragment();
				couponsfragment.setArguments(bundle);
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.container, couponsfragment,
								CouponsFragment.TITLE)
						.setTransition(
								FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.addToBackStack(null).commit();
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
				
				OffersGroup offersgroup = (OffersGroup) adapterview .getItemAtPosition(i);
				
				StringBuilder sb = new StringBuilder();
				
				sb.append("id: " + offersgroup.getId() + "\n");
				sb.append("name: " + offersgroup.getName() + "\n");
				sb.append("address: " + offersgroup.getAddress() + "\n");
				sb.append("latitude: " + offersgroup.getLatitude()+ "\n");
				sb.append("longitude: " + offersgroup.getLongitude() + "\n");
				sb.append("email: " + offersgroup.getEmail() + "\n");
				sb.append("twitter: " + offersgroup.getTwitter() + "\n");
				sb.append("facebook: " + offersgroup.getFacebook() + "\n");
				sb.append("website: " + offersgroup.getWebsite() + "\n");
				sb.append("blog: " + offersgroup.getBlog() + "\n");
				sb.append("group_image1: " + offersgroup.getGroupImage1() + "\n");
				sb.append("group_image2: " + offersgroup.getGroupImage2() + "\n");
				sb.append("phone_number: " + offersgroup.getPhoneNumber() + "\n");
				sb.append("opening_hours: " + offersgroup.getOpeningHours() + "\n");
				sb.append("updated_at: " + offersgroup.getUpdatedAt() + "\n");
				mSelectedGroup = offersgroup;
				
				((HomeActivity) getActivity()).confirm("店舗情報", sb.toString(), "OK", null, "店舗のクーポンを見る", new android.content.DialogInterface.OnClickListener() {
					public void onClick(android.content.DialogInterface dialog,int result) {
						// クーポン店舗検索させる
						Bundle bundle1 = new Bundle();
						bundle1.putInt("group_id", mSelectedGroup.getId());
						CouponsFragment couponsfragment = new CouponsFragment();
						couponsfragment.setArguments(bundle1);
						getFragmentManager().executePendingTransactions();
						getFragmentManager()
							.beginTransaction()
							.replace(R.id.container, couponsfragment, "店舗のクーポン一覧").setTransition(4097)
							.addToBackStack(null).commit();
					}
				});
				
			}

		});
		
		mListAdapter = new ArrayAdapter<OffersGroup>(mContext, android.R.layout.simple_list_item_1) {
			private LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService("layout_inflater");

			@SuppressLint("InflateParams")
			public View getView(int i, View view1, ViewGroup viewgroup1) {
				OffersGroup offersgroup = (OffersGroup) getItem(i);
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
					textview1.setId(0x1020015);
					textview1.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT, 1.0F));
					((ViewGroup) view1).addView(textview1);
				}
				
				offersgroup.groupImage1Bitmap((ImageView) view1.findViewById(android.R.id.widget_frame), new ImageListener() {
					public void onDone(View view, Bitmap bitmap) {
						((ImageView) view).setImageBitmap(bitmap);
					}
				});

				((TextView) view1.findViewById(android.R.id.text1)).setText(String.valueOf(offersgroup.getId()));
				((TextView) view1.findViewById(0x1020015)).setText(offersgroup.getName());
				
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
	
	OffersListener listener = new OffersListener() {
		
		@Override
		public void onFail(Integer s) {
			// TODO Auto-generated method stub
			((HomeActivity) getActivity()).alert("onFail", s.toString());
		}
		
		@Override
		public void onDone(Map<String, Object> map) {
			// TODO Auto-generated method stub
			mProgressBar.setVisibility(View.GONE);
			mListAdapter.clear();
			if (map.get("groups") != null) {
				Log.e("Error = ","Empty values");
				@SuppressWarnings("unchecked")
				List<OffersGroup> items = (List<OffersGroup>) map
						.get("groups");
				for (OffersGroup item : items) {
					mListAdapter.add(item);
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
	};
	
	public void onStart() {
		super.onStart();
		mProgressBar.setVisibility(View.VISIBLE);
		OffersKit.getInstance().resetAll();
		OffersKit.getInstance().groups(true, listener );
	}

	public void onDestroyView() {
		mListView.setAdapter(null);
		super.onDestroyView();
	}

}

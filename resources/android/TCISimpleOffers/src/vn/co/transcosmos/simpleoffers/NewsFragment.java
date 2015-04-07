package vn.co.transcosmos.simpleoffers;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import co.leonisand.libraries.Image.ImageListener;
import co.leonisand.offers.OffersKit;
import co.leonisand.offers.OffersKit.OffersListener;
import co.leonisand.offers.OffersRecommendation;
import co.leonisand.offers.OffersStatus;

public class NewsFragment extends Fragment {

	public static String TITLE = "Recommend";
	//private NewsAdapter mAdapter;
	//private ListView mListView;
	//private ProgressBar mProgressBar;
	
	private Context mContext;
	private ArrayAdapter<OffersRecommendation> mAdapter;
	private ListView mListView;
	private ProgressBar mProgressBar;

	/*public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_news, container,
				false);

		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);

		mListView = (ListView) rootView.findViewById(R.id.lvNews);

		((HomeActivity) getActivity()).setActiobarTitle(TITLE);

		mAdapter = new NewsAdapter(getActivity());
		mListView.setAdapter(mAdapter);

		mListView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				OffersRecommendation offersrecommendation = (OffersRecommendation) parent.getItemAtPosition(position);					

				Bundle bundle = new Bundle();
				bundle.putInt("recommendation_id", offersrecommendation.getId());
				bundle.putString("type", offersrecommendation.getType());

				NewFragment newFragment = new NewFragment();
				newFragment.setArguments(bundle);
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.container, newFragment, NewFragment.TITLE)
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
		((HomeActivity) getActivity()).setActiobarTitle(TITLE);
		
		mListView = new ListView(mContext);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterview, View view1, int i, long l) {
				
				OffersRecommendation offersrecommendation = (OffersRecommendation) adapterview.getItemAtPosition(i);
				Bundle bundle1 = new Bundle();
				bundle1.putInt("recommendation_id", offersrecommendation.getId());
				bundle1.putString("type", offersrecommendation.getType());
				
				NewFragment newFragment = new NewFragment();
				newFragment.setArguments(bundle1);
				getFragmentManager().beginTransaction().replace(R.id.container, newFragment, offersrecommendation.getName()).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).addToBackStack(null).commit();
			}
		});
		mAdapter = new ArrayAdapter<OffersRecommendation>(mContext, android.R.layout.simple_list_item_1) {

			@SuppressLint("InflateParams")
			public View getView(int i, View view1, ViewGroup viewgroup1) {
				OffersRecommendation offersrecommendation = (OffersRecommendation) getItem(i);
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
				offersrecommendation.thumbnailImageBitmap((ImageView) view1.findViewById(android.R.id.widget_frame), new ImageListener() {

					public void onDone(View view, Bitmap bitmap) {
						((ImageView) view).setImageBitmap(bitmap);
					}

				});
				((TextView) view1.findViewById(android.R.id.text1)).setText(String.valueOf(offersrecommendation.getId()));
				((TextView) view1.findViewById(android.R.id.text2)).setText(offersrecommendation.getName());
				return view1;
			}

			private LayoutInflater mLayoutInflater = (LayoutInflater) mContext.getSystemService("layout_inflater");
		};
		mListView.setAdapter(mAdapter);
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
		final Bundle params = new Bundle();
		params.putString("sort_target", "delivery_from");
		params.putString("sort_direction", "descending");
		params.putString("offset", "0");
		params.putString("limit", "20");
		OffersKit.getInstance().recommendations(true, params,
				new OffersListener() {

					public void onDone(Map<String, Object> map) {
						mProgressBar.setVisibility(View.GONE);
						mAdapter.clear();

						if (map.get("recommendations") != null) {

							@SuppressWarnings("unchecked")
							List<OffersRecommendation> items = (List<OffersRecommendation>) map
									.get("recommendations");
							for (OffersRecommendation item : items) {
								mAdapter.add(item);
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
						((HomeActivity) getActivity()).alert("onFail",
								s.toString());
					}

				});

	}

	public void onDestroyView() {
		mAdapter = null;
		mListView.setAdapter(null);
		super.onDestroyView();
	}

}

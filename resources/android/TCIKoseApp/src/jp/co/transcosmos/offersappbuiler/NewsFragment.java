package jp.co.transcosmos.offersappbuiler;

import java.util.List;
import java.util.Map;

import android.annotation.SuppressLint;
import android.app.ActionBar.LayoutParams;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
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
import co.leonisand.offers.OffersKit;
import co.leonisand.offers.OffersKit.OffersListener;
import co.leonisand.offers.OffersRecommendation;
import co.leonisand.offers.OffersStatus;

public class NewsFragment extends Fragment {

	public static String TITLE = "NEWS";
	private Context mContext;
	private ArrayAdapter<OffersRecommendation> mAdapter;
	private ListView mListView;
	private ProgressBar mProgressBar;

	public View onCreateView(LayoutInflater layoutinflater,
			ViewGroup viewgroup, Bundle bundle) {

		getActivity().getActionBar().setTitle(TITLE);
		getFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);

		mContext = layoutinflater.getContext();

		View view = layoutinflater.inflate(R.layout.fragment, viewgroup, false);
		view.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		FrameLayout frameLayout = (FrameLayout) view
				.findViewById(R.id.fragment_content);

		mListView = new ListView(mContext);
		mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

			public void onItemClick(AdapterView<?> adapterview, View view1,
					int i, long l) {

				OffersRecommendation offersrecommendation = (OffersRecommendation) adapterview
						.getItemAtPosition(i);
				if (offersrecommendation.getType().equals("RecommendedUrl")) {

					Bundle bundle1 = new Bundle();
					bundle1.putString("recommendation_url",
							offersrecommendation.getContent());

					NewUrlFragment newUrlFragment = new NewUrlFragment();
					newUrlFragment.setArguments(bundle1);

					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();

					ft.replace(R.id.fragment_content, newUrlFragment,
							offersrecommendation.getName());
					ft.addToBackStack(null);
					ft.commit();

				} else if (offersrecommendation.getType().equals(
						"RecommendedArticle")) {
					Bundle bundle1 = new Bundle();
					bundle1.putInt("recommendation_id",
							offersrecommendation.getId());
					bundle1.putString("title", offersrecommendation.getName());

					NewFragment newFragment = new NewFragment();
					newFragment.setArguments(bundle1);

					FragmentTransaction ft = getFragmentManager()
							.beginTransaction();

					ft.replace(R.id.fragment_content, newFragment,
							offersrecommendation.getName());
					ft.addToBackStack(null);
					ft.commit();
				}

			}
		});
		mAdapter = new ArrayAdapter<OffersRecommendation>(mContext,
				android.R.layout.simple_list_item_1) {

			@SuppressLint("InflateParams")
			public View getView(int i, View view1, ViewGroup viewgroup1) {
				OffersRecommendation offersrecommendation = (OffersRecommendation) getItem(i);
				if (view1 == null) {
					view1 = new LinearLayout(mContext);
					((LinearLayout) view1)
							.setOrientation(LinearLayout.VERTICAL);

					TextView textview = (TextView) mLayoutInflater.inflate(
							android.R.layout.simple_list_item_1, null);
					textview.setLayoutParams(new LinearLayout.LayoutParams(
							LayoutParams.MATCH_PARENT,
							LayoutParams.WRAP_CONTENT, 1.0F));
					((ViewGroup) view1).addView(textview);

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

				}
				offersrecommendation.thumbnailImageBitmap((ImageView) view1
						.findViewById(android.R.id.widget_frame),
						new ImageListener() {

							public void onDone(View view, Bitmap bitmap) {
								((ImageView) view).setImageBitmap(bitmap);
							}

						});

				((TextView) view1.findViewById(android.R.id.text1))
						.setText(String.valueOf(offersrecommendation.getName()));

				return view1;
			}

			private LayoutInflater mLayoutInflater = (LayoutInflater) mContext
					.getSystemService("layout_inflater");
		};

		mListView.setAdapter(mAdapter);
		frameLayout.addView(mListView);
		mProgressBar = new ProgressBar(mContext, null,
				android.R.attr.progressBarStyle);
		mProgressBar.setLayoutParams(new FrameLayout.LayoutParams(
				LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT) {
			{
				gravity = Gravity.CENTER;
			}
		});
		mProgressBar.setVisibility(View.GONE);
		frameLayout.addView(mProgressBar);
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

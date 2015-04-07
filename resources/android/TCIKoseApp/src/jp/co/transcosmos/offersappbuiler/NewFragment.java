package jp.co.transcosmos.offersappbuiler;

import java.util.Map;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import co.leonisand.libraries.Image.ImageListener;
import co.leonisand.offers.OffersKit;
import co.leonisand.offers.OffersKit.OffersListener;
import co.leonisand.offers.OffersRecommendation;
import co.leonisand.offers.OffersStatus;

public class NewFragment extends Fragment {

	private ImageView imageView;
	private TextView tvTitle;
	private WebView wvRecommend;
	private ProgressBar mProgressBar;

	private OffersRecommendation mRecommendation;
	private int mRecommendation_id;
	private String mTitle;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		// setHasOptionsMenu(true);
		mRecommendation_id = getArguments().getInt("recommendation_id");
		mTitle = getArguments().getString("title");

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_new, container,
				false);
		
		rootView.setOnTouchListener(new OnTouchListener() {
			
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});
		
		imageView = (ImageView) rootView.findViewById(R.id.imageRecommend);
		tvTitle = (TextView) rootView.findViewById(R.id.tvTitle);
		tvTitle.setText(mTitle);
		wvRecommend = (WebView) rootView.findViewById(R.id.wvRecommend);
		mProgressBar = (ProgressBar) rootView.findViewById(R.id.pbRecommend);

		wvRecommend.getSettings().setJavaScriptEnabled(true);
		wvRecommend.setWebChromeClient(new WebChromeClient() {
			public boolean onConsoleMessage(ConsoleMessage consoleMessage) {
				System.out.println("onConsoleMessage");
				((HomeActivity) getActivity()).alert(
						"onConsoleMessage",
						consoleMessage.message() + ":"
								+ consoleMessage.lineNumber() + ":"
								+ consoleMessage.sourceId());
				return true;
			}

			@Override
			public boolean onJsAlert(WebView view, String url, String message,
					android.webkit.JsResult result) {
				try {
					((HomeActivity) getActivity()).alert("onJsAlert",
							message.toString());
					return true;
				} finally {
					result.confirm();
				}
			}
		});

		return rootView;
	}

	public void onStart() {

		super.onStart();
		mProgressBar.setVisibility(View.VISIBLE);
		OffersKit.getInstance().recommendation(mRecommendation_id, true,
				new OffersListener() {

					public void onDone(Map<String, Object> map) {
						mProgressBar.setVisibility(View.GONE);
						if (map.get("recommendation") != null) {
							mRecommendation = (OffersRecommendation) map
									.get("recommendation");
							mRecommendation.actionType("open",
									new OffersListener() {

										public void onDone(
												Map<String, Object> map) {

										}

										public void onFail(Integer s) {
											((HomeActivity) getActivity())
													.alert("actionType.onFail",
															s.toString());
										}

									});

							OffersKit.getInstance().authenticationToken(
									new OffersListener() {
										@Override
										public void onDone(
												Map<String, Object> result) {
											if (result.get("token") != null) {

												wvRecommend
														.loadDataWithBaseURL(
																"http://?token="
																		+ result.get("token"),
																mRecommendation
																		.getContent(),
																"text/html",
																"utf-8", null);
											} else {
												OffersStatus offersstatus = (OffersStatus) result
														.get("status");
												((HomeActivity) getActivity())
														.alert("onDone",
																offersstatus
																		.getCode()
																		+ ":"
																		+ offersstatus
																				.getMessage());
											}
										}

										@Override
										public void onFail(Integer result) {
											((HomeActivity) getActivity())
													.alert("authenticationToken.onFail",
															result.toString());
										}

									});

							mRecommendation.imageBitmap(imageView,
									new ImageListener() {

										public void onDone(View view,
												Bitmap bitmap) {
											((ImageView) view)
													.setImageBitmap(bitmap);
										}

									});
							return;
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
						((HomeActivity) getActivity()).alert(
								"recommendation.onFail", s.toString());
					}

				});
	}
}

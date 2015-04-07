package vn.co.transcosmos.simpleoffers;

import java.util.Map;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ConsoleMessage;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import co.leonisand.libraries.Image.ImageListener;
import co.leonisand.offers.OffersKit;
import co.leonisand.offers.OffersKit.OffersListener;
import co.leonisand.offers.OffersRecommendation;
import co.leonisand.offers.OffersStatus;

public class NewFragment extends Fragment {

	public static String TITLE = "Recommend Detail";

	private ImageView imageView;
	private TextView tvDesc;
	private TextView tvDate;
	private TextView tvRead;
	private Button btView;
	private WebView wvRecommend;
	private ProgressBar mProgressBar;

	//private Context mContext;
	private OffersRecommendation mRecommendation;

	private int mRecommendation_id;
	private String mType;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);

		// setHasOptionsMenu(true);
		mRecommendation_id = getArguments().getInt("recommendation_id");
		mType = getArguments().getString("type");

	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//mContext = inflater.getContext();
		View rootView = inflater.inflate(R.layout.fragment_new, container,
				false);
		((HomeActivity) getActivity()).setActiobarTitle(TITLE);

		imageView = (ImageView) rootView.findViewById(R.id.imageRecommend);

		tvDesc = (TextView) rootView.findViewById(R.id.tvDesc);
		tvDate = (TextView) rootView.findViewById(R.id.tvDate);
		tvRead = (TextView) rootView.findViewById(R.id.tvRead);

		btView = (Button) rootView.findViewById(R.id.btView);
		wvRecommend = (WebView) rootView.findViewById(R.id.wvRecommend);
		mProgressBar = (ProgressBar) rootView.findViewById(R.id.pbRecommend);

		if (mType.equals("RecommendedUrl")) {

			btView.setVisibility(View.VISIBLE);
			
			btView.setOnClickListener(new android.view.View.OnClickListener() {

				public void onClick(View view1) {
					if (mRecommendation == null) {
						return;
					} else {
						mRecommendation.actionType("view",
								new OffersListener() {
									public void onDone(Map<String, Object> map) {
										
									}

									public void onFail(Integer s) {
										((HomeActivity) getActivity()).alert(
												"actionType.onFail",
												s.toString());
									}
								});

						Intent intent = new Intent(
								"android.intent.action.VIEW", Uri
										.parse(mRecommendation.getContent()));
						startActivity(intent);

						return;
					}
				}
			});

		} else if (mType.equals("RecommendedArticle")) {

			wvRecommend.setVisibility(View.VISIBLE);
			
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
				public boolean onJsAlert(WebView view, String url,
						String message, android.webkit.JsResult result) {
					try {
						((HomeActivity) getActivity()).alert("onJsAlert",
								message.toString());
						return true;
					} finally {
						result.confirm();
					}
				}
			});

		}

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

											if (mRecommendation.isUnread()) {
												tvRead.setText("未読");
											} else {
												tvRead.setText("既読");
											}

										}

										public void onFail(Integer s) {
											((HomeActivity) getActivity())
													.alert("actionType.onFail",
															s.toString());
										}

									});

							tvDesc.setText("説明:"
									+ mRecommendation.getDescription());
							tvDate.setText((new StringBuilder("利用期間:"))
									.append(mRecommendation.getDeliveryFromAt())
									.append(" 〜 ")
									.append(mRecommendation.getDeliveryToAt())
									.toString());

							if (mRecommendation.isUnread()) {
								tvRead.setText("未読");
							} else {
								tvRead.setText("既読");
							}

							//Log.e("Content = ", mRecommendation.getContent());

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

	public void onDestroyView() {
		if (mType.equals("RecommendedUrl")) {
			btView.setOnClickListener(null);
		}

		super.onDestroyView();
	}
}

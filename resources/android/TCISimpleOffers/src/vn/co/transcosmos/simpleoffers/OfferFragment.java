package vn.co.transcosmos.simpleoffers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.text.InputType;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.TextView.OnEditorActionListener;
import co.leonisand.libraries.Image.ImageListener;
import co.leonisand.offers.OffersCoupon;
import co.leonisand.offers.OffersKit;
import co.leonisand.offers.OffersKit.OffersListener;
import co.leonisand.offers.OffersStatus;
import co.leonisand.offers.OffersTemplate;
import co.leonisand.stamp.StampContent;
import co.leonisand.stamp.StampGestureView;
import co.leonisand.stamp.StampGestureView.StampGestureViewListener;
import co.leonisand.stamp.StampGroup;
import co.leonisand.stamp.StampKit;
import co.leonisand.stamp.StampStamp;
import co.leonisand.stamp.StampStamped;

public class OfferFragment extends Fragment implements StampGestureViewListener {

	public static String TITLE = "Offer Detail";
	private Dialog mAlertDialog;
	private Context mContext;
	private OffersCoupon mCoupon;
	private int mCoupon_id;

	private EditText mEditText;
	private ImageView mImageView;
	private LinearLayout mLinearLayout;
	private View rootView;
	private ProgressBar mProgressBar;
	private StampGestureView mStampView;
	private ImageView mSuccessimage;
	private ImageView mCheckMark;
	private Timer mTimerLive;

	private TextView mTitle;
	private TextView mDate;
	private TextView mDetail;
	private TextView mType;
	private TextView mUsed;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		// setHasOptionsMenu(true);
		mCoupon_id = getArguments().getInt("coupon_id");
	}

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		mContext = inflater.getContext();

		rootView = inflater.inflate(R.layout.fragment_offer, container, false);

		mLinearLayout = (LinearLayout) rootView
				.findViewById(R.id.stamp_container);

		((HomeActivity) getActivity()).setActiobarTitle(TITLE);

		mTitle = (TextView) rootView.findViewById(R.id.tvTitleOffer);
		mDate = (TextView) rootView.findViewById((R.id.tvDateOffer));

		mDetail = (TextView) rootView.findViewById(R.id.tvDetailOffer);
		mDetail.setBackgroundColor(-1);
		mDetail.setMinLines(5);

		mType = (TextView) rootView.findViewById(R.id.tvTypeOffer);
		mType.setOnClickListener(new android.view.View.OnClickListener() {
			public void onClick(View view1) {
				// If OffersCoupon is availability
				if (mCoupon.usable()) {
					if (mCoupon.getCouponType().equals("もぎとり")) {
						// 即刻消し込み
						apply(null);
					} else if (mCoupon.getCouponType().equals("暗証番号")) {
						// 暗証番号確認ダイアログ
						mAlertDialog.show();
					}
				}
			}
		});
		
		mUsed = (TextView) rootView.findViewById(R.id.tvUsedOffer);

		mImageView = (ImageView) rootView.findViewById(R.id.imOffer);
		mCheckMark = (ImageView) rootView.findViewById(R.id.check_mark);

		mSuccessimage = (ImageView) rootView.findViewById(R.id.imSuccessOffer);

		mStampView = new StampGestureView(mContext);
		mStampView.setVisibility(View.GONE);
		mStampView.setStampGestureViewListener(this);
		mLinearLayout.addView(mStampView);

		mEditText = new EditText(mContext);
		mEditText.setInputType(InputType.TYPE_CLASS_NUMBER);
		mEditText.setId(android.R.id.edit);
		mEditText.setOnEditorActionListener(new OnEditorActionListener() {
			public boolean onEditorAction(TextView textview, int i,
					KeyEvent keyevent) {
				mAlertDialog.dismiss();
				return false;
			}

		});

		AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
		builder.setTitle("暗証番号を入力してください。");
		builder.setView(mEditText);

		builder.setPositiveButton(android.R.string.ok,
				new DialogInterface.OnClickListener() {

					public void onClick(DialogInterface dialoginterface, int i) {

						// getSecretsList is error
						if (mCoupon.getSecretsList().contains(
								mEditText.getText().toString())) {
							apply(mEditText.getText().toString());
							return;
						} else {
							((HomeActivity) getActivity()).alert("エラー",
									"暗証番号が一致しません");
							return;
						}
					}
				});

		builder.setNegativeButton(android.R.string.cancel, null);

		mAlertDialog = builder.create();
		mAlertDialog.setOnShowListener(new OnShowListener() {

			public void onShow(DialogInterface dialoginterface) {
				((InputMethodManager) getActivity().getSystemService(
						"input_method")).showSoftInput(mEditText, 0);
			}
		});

		mProgressBar = (ProgressBar) rootView.findViewById(R.id.pbOffer);

		return rootView;
	}

	public void onStart() {
		super.onStart();
		if (mTimerLive != null) {
			mTimerLive.cancel();
			mTimerLive.purge();
		}
		mTimerLive = new Timer();

		mProgressBar.setVisibility(View.VISIBLE);
		OffersKit.getInstance().coupon(mCoupon_id, true, new OffersListener() {

			public void onDone(Map<String, Object> map) {
				mProgressBar.setVisibility(View.GONE);
				if (map.get("coupon") != null) {

					// Get information Title, Date, Image_Coupon, Detail
					mCoupon = (OffersCoupon) map.get("coupon");
					mCoupon.setAlreadyRead();

					mTitle.setText((new StringBuilder("("))
							.append(mCoupon.getCategory()).append(")")
							.append(mCoupon.getTitle()).toString());
					mDate.setText((new StringBuilder("利用期間:"))
							.append(mCoupon.getAvailableFrom()).append(" 〜 ")
							.append(mCoupon.getAvailableTo()).append("(配信中)")
							.append("残り :").append(mCoupon.getQuantity())
							.toString());
					mDetail.setText(mCoupon.getDescription());
					//Log.e("Detail = ", mCoupon.getDescription());

					mCoupon.imageBitmap(mImageView, new ImageListener() {

						public void onDone(View view, Bitmap bitmap) {
							((ImageView) view).setImageBitmap(bitmap);
						}
					});

					// Set background color for layout CouponFragment
					mCoupon.template(new OffersListener() {

						public void onDone(Map<String, Object> map) {
							if (map.get("template") != null) {
								OffersTemplate template = (OffersTemplate) map
										.get("template");

								@SuppressWarnings("unchecked")
								Map<String, Object> map1 = (Map<String, Object>) template
										.getValues().get("background");
								rootView.setBackgroundColor(Color
										.parseColor((String) map1.get("color")));
								return;
							} else {
								((HomeActivity) getActivity()).alert(
										"template.onDone", map.toString());
								return;
							}
						}

						public void onFail(Integer s) {
							((HomeActivity) getActivity()).alert(
									"template.onFail", s.toString());
						}

					});

					mType.setText(mCoupon.getCouponType());
					//Log.e("CouponType = ", mCoupon.getCouponType());
					// Type Coupon is Stamp
					if (mCoupon.getCouponType().equals("スタンプ")) {
						
						mCheckMark.setImageDrawable(getResources().getDrawable(R.drawable.check_mark_red));
						Bundle bundle = new Bundle();
						bundle.putInt("coupon_id", mCoupon.getId());

						// Get content of StampKit
						StampKit.getInstance().content(bundle,
								new StampKit.StampListener() {

									public void onDone(Map<String, Object> map,
											Error error) {
										if (error != null) {
											((HomeActivity) getActivity())
													.alert("StampKit.content error",
															error.getMessage());
											return;
										}
										if (map.get("content") != null) {
											final int content_id = ((StampContent) map
													.get("content"))
													.getContent_id();
											mProgressBar
													.setVisibility(View.VISIBLE);

											// Get groups of StampKit
											StampKit.getInstance()
													.groups(content_id,
															new StampKit.StampListener() {

																public void onDone(
																		Map<String, Object> map,
																		Error error) {
																	mProgressBar
																			.setVisibility(View.GONE);
																	if (error == null) {

																		if (map.get("groups") != null) {

																			@SuppressWarnings("unchecked")
																			final List<StampGroup> groups = (List<StampGroup>) map
																					.get("groups");

																			if (mCoupon
																					.isUsed()) {

																				// スタンプグループ１つ目のスタンプを押印した事にする。
																				StampGroup group = groups
																						.get(0);
																				List<StampStamp> stamps = group
																						.getStampsList();
																				StampStamp stamp = stamps
																						.get(0);

																				try {
																					JSONArray histories = new JSONArray();
																					JSONObject history = new JSONObject();

																					history.put(
																							"content_id",
																							stamp.getContent_id());

																					history.put(
																							"group_id",
																							stamp.getGroup_id());
																					history.put(
																							"stamp_id",
																							stamp.getStamp_id());
																					history.put(
																							"image_id",
																							stamp.getStampImage()
																									.getId());
																					history.put(
																							"image_url",
																							stamp.getStampImage()
																									.getUrl());
																					history.put(
																							"x",
																							160);
																					history.put(
																							"y",
																							240);
																					history.put(
																							"width",
																							stamp.getStampImage()
																									.getWidth() / 2);
																					history.put(
																							"height",
																							stamp.getStampImage()
																									.getHeight() / 2);
																					history.put(
																							"angle",
																							0.0F);

																					SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
																							"yyyyMMddHHmmss",
																							Locale.getDefault());
																					Date date = simpleDateFormat
																							.parse(mCoupon
																									.getUsedDate());

																					history.put(
																							"stamped_at",
																							date.getTime());
																					histories
																							.put(history);

																					//
																					StampKit.getInstance()
																							.setLocalHistories(
																									content_id,
																									histories,
																									new StampKit.StampListener() {
																										@Override
																										public void onDone(
																												Map<String, Object> arg0,
																												Error arg1) {
																											// ローカルDBより取得します
																											List<StampStamped> histories = StampKit
																													.getInstance()
																													.localHistories(
																															content_id);

																											mStampView
																													.setGroupsWithHistories(
																															groups,
																															histories);
																										}
																									});

																				} catch (JSONException e) {
																					e.printStackTrace();
																				} catch (ParseException e) {
																					e.printStackTrace();
																				}

																			} else {
																				mStampView
																						.setGroupsWithHistories(
																								groups,
																								null);
																			}
																		} else {
																			((HomeActivity) getActivity())
																					.alert("StampKit.groups",
																							map.toString());
																		}

																	} else {
																		((HomeActivity) getActivity())
																				.alert("StampKit.groups",
																						error.getMessage());
																		return;
																	}
																}

															});
										} else {
											((HomeActivity) getActivity())
													.alert("StampKit.content",
															"content null");
										}
									}
								});
						mStampView.setVisibility(View.VISIBLE);
					}

					// Type Coupon is other
					else {
						mStampView.setVisibility(View.GONE);
					}

					mSuccessimage.setImageBitmap(null);

					// Coupon us used
					if (mCoupon.isUsed()) {

						

						mUsed.setText((new StringBuilder("使用済:")).append(
								mCoupon.getUsedDate()).toString());
						mCoupon.applySuccessImageBitmap(mSuccessimage,
								new ImageListener() {

									public void onDone(View view, Bitmap bitmap) {
										((ImageView) view)
												.setImageBitmap(bitmap);
									}

								});
						if (mCoupon.getReusable() == true) {
							mUsed.append("※再利用可能");
							String reusable_time = mCoupon
									.getScheduledReusableTime();
							if (reusable_time != null) {
								mUsed.append(String.format("(次回: %s)",
										reusable_time));
							}
						}

						mUsed.append(String.format(
								"[%s]",
								mCoupon.usable() ? "つかえます" : (mCoupon
										.getReusable() == true ? "まだです"
										: "もう無理")));
					} else if (!mCoupon.isAvailable()) {

						
						mUsed.setText("利用期間外");
					} else {
						
						mUsed.setText("未使用");
					}

					final Handler mHandler = new Handler();

					if (mTimerLive == null) {
						return;
					}

					// Update time for Coupon
					mTimerLive.schedule(new TimerTask() {
						public void run() {
							mHandler.post(new Runnable() {

								public void run() {
									mCoupon.live(new OffersListener() {

										public void onDone(
												Map<String, Object> map) {
											if (((OffersStatus) map
													.get("status")).getCode() == 0) {
												mCoupon = (OffersCoupon) map
														.get("coupon");
												mDate.setText((new StringBuilder(
														"利用期間:"))
														.append(mCoupon
																.getAvailableFrom())
														.append(" 〜 ")
														.append(mCoupon
																.getAvailableTo())
														.append("(配信中)")
														.append("残り :")
														.append(mCoupon
																.getQuantity())
														.toString());
											}
										}

										public void onFail(Integer s) {
										}

									});
								}
							});
						}
					}, 3000L, 3000L);

				}

				// Coupon is null
				else {

					((HomeActivity) getActivity()).alert("coupon",
							map.toString());

				}
			}

			public void onFail(Integer s) {
				mProgressBar.setVisibility(View.GONE);
				((HomeActivity) getActivity()).alert("coupon.onFail",
						s.toString());
			}

		});
	}

	public void onStop() {
		super.onStop();
		mTimerLive.cancel();
		mTimerLive.purge();
		mTimerLive = null;
	}

	public void onDestroyView() {
		mEditText.setOnEditorActionListener(null);
		mType.setOnClickListener(null);
		super.onDestroyView();
	}

	@Override
	public void releaseStamp(List<Map<String, Object>> list) {
	}

	@Override
	public void afterStamp(Map<String, Object> map) {
		apply(null);
	}

	@Override
	public void beforeStamp(Map<String, Object> map) {
		Map<String, Object> hashmap = new HashMap<String, Object>();
		hashmap.put("x", Integer.valueOf(160));
		hashmap.put("y", Integer.valueOf(240));
		hashmap.put("angle", Integer.valueOf(0));
		hashmap.put("width", Float.valueOf(Float.parseFloat(map.get("width")
				.toString()) / 2.0F));
		hashmap.put("height", Float.valueOf(Float.parseFloat(map.get("height")
				.toString()) / 2.0F));
		mStampView.setStampedOption(hashmap);
	}

	@Override
	public void gestureSuccess(Map<String, Object> map) {
		((Vibrator) getActivity().getSystemService("vibrator")).vibrate(100L);
		apply(null);
	}

	@Override
	public void matchStamp(Map<String, Object> map) {
		((Vibrator) getActivity().getSystemService("vibrator")).vibrate(100L);
	}

	private void apply(String s) {
		mProgressBar.setVisibility(View.VISIBLE);
		mCoupon.apply(s, new OffersListener() {

			public void onDone(Map<String, Object> map) {
				mProgressBar.setVisibility(View.GONE);
				if (map.get("coupon") != null) {
					mCoupon = (OffersCoupon) map.get("coupon");
					onStart();
					return;
				} else {
					((HomeActivity) getActivity()).alert("apply.onDone",
							map.toString());
					return;
				}
			}

			public void onFail(Integer s1) {
				mProgressBar.setVisibility(View.GONE);
				((HomeActivity) getActivity()).alert("apply.onFail",
						s1.toString());
			}

		});
	}
}

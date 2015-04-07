package jp.co.transcosmos.offersappbuiler;

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
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout.LayoutParams;
import android.widget.PopupWindow;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
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

	public static String TITLE = "クーポン";
	private static int STAMP_TIME_OUT = 1000;
	private static int HEIGHT_POPUP = 400;

	private Context mContext;
	private OffersCoupon mCoupon;
	private int mCoupon_id;

	private View rootView;
	private CustomScrollView scrollView;
	private RelativeLayout mRelativeLayout;
	private RelativeLayout mRelativeLayout2;
	private FrameLayout mFrameLayout;

	private TextView mDate;
	private TextView mDetail;
	private ImageView mImageView;
	private ProgressBar mProgressBar;
	private StampGestureView mStampView;
	private ImageView mSuccessimage;

	private TextView mTitle;
	private TextView mUsed;
	private Switch mSwitch;
	private Button mClose;
	private Button mStamp;
	private TextView mPressStamp;

	private Timer mTimerLive;
	private PopupWindow popupWindow;
	private Button btnDismiss;
	boolean flag = false;

	public void onCreate(Bundle bundle) {
		super.onCreate(bundle);
		setHasOptionsMenu(true);

		mCoupon_id = getArguments().getInt("coupon_id");
	}

	public View onCreateView(LayoutInflater layoutinflater,
			ViewGroup viewgroup, Bundle bundle) {

		mContext = layoutinflater.getContext();

		rootView = layoutinflater.inflate(R.layout.fragment_offer, viewgroup,
				false);

		rootView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				return true;
			}
		});

		// Create Popup
		LayoutInflater inflater = LayoutInflater.from(mContext);
		View layout = inflater.inflate(R.layout.popup, null);
		popupWindow = new PopupWindow(layout, LayoutParams.MATCH_PARENT,
				getActivity().getWindowManager().getDefaultDisplay()
						.getHeight() / 2);
		btnDismiss = (Button) layout.findViewById(R.id.btn_dismiss);

		btnDismiss.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				showPopUp();
			}
		});

		mFrameLayout = (FrameLayout) rootView.findViewById(R.id.flOffer);
		scrollView = (CustomScrollView) rootView.findViewById(R.id.scrollView);
		mRelativeLayout = (RelativeLayout) rootView.findViewById(R.id.rlStamp);
		mRelativeLayout2 = (RelativeLayout) rootView.findViewById(R.id.rlOffer);

		mTitle = (TextView) rootView.findViewById(R.id.tvTitleOffer);
		mDate = (TextView) rootView.findViewById((R.id.tvDateOffer));

		mDetail = (TextView) rootView.findViewById(R.id.tvDescription);

		mUsed = (TextView) rootView.findViewById(R.id.tvUsedOffer);
		mSwitch = (Switch) rootView.findViewById(R.id.mySwitch);
		mImageView = (ImageView) rootView.findViewById(R.id.imOffer);

		mSuccessimage = (ImageView) rootView.findViewById(R.id.imSuccessOffer);

		mStampView = new StampGestureView(mContext);
		mStampView.setVisibility(View.GONE);
		mStampView.setStampGestureViewListener(this);
		mFrameLayout.addView(mStampView, 0);

		mClose = (Button) rootView.findViewById(R.id.btClose);
		mStamp = (Button) rootView.findViewById(R.id.btStamp);
		mPressStamp = (TextView) rootView.findViewById(R.id.tvStamp);

		mClose.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Log.e("Close = ", "Click");
				mRelativeLayout.setVisibility(View.GONE);
				mPressStamp.setVisibility(View.GONE);
				mSwitch.setEnabled(true);
				mSwitch.setChecked(false);
				scrollView.setScrollingEnabled(true);
			}
		});

		mStampView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				// TODO Auto-generated method stub
				Log.e("ThinhND = ", "Click");
				return false;
			}
		});

		mStamp.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				// get dialog.xml view
				LayoutInflater li = LayoutInflater.from(mContext);
				View dialog = li.inflate(R.layout.dialog, null);

				AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
						mContext);

				// set dialog.xml to alertdialog builder
				alertDialogBuilder.setView(dialog);

				final EditText userInput = (EditText) dialog
						.findViewById(R.id.etDialog);
				// set dialog message
				alertDialogBuilder
						.setCancelable(false)
						.setPositiveButton("OK",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										if (mCoupon.getSecretsList().contains(
												userInput.getText().toString())) {
											Log.e("Coupon = ", "apply");
											apply(userInput.getText()
													.toString());
											// TODO show popup
											return;
										} else {
											((HomeActivity) getActivity())
													.alert("エラー", "暗証番号が一致しません");
											return;
										}

									}
								})
						.setNegativeButton("Cancel",
								new DialogInterface.OnClickListener() {
									public void onClick(DialogInterface dialog,
											int id) {
										dialog.cancel();
										// mClose.setVisibility(View.VISIBLE);
									}
								});

				// create alert dialog
				AlertDialog alertDialog = alertDialogBuilder.create();

				// show dialog input number
				Log.e("Stamp = ", "Click");
				if (mCoupon.usable()) {
					Log.e("Apply Stamp = ", "Apply");
					if (mCoupon.getCouponType().equals("もぎとり")) {
						// 即刻消し込み
						Log.e("Input 1 = ", mCoupon.getCouponType().toString());
						apply(null);
					}
					// Confuse
					else if (!mCoupon.getCouponType().equals("スタンプ")) {
						// 暗証番号確認ダイアログ
						Log.e("Input 2 = ", mCoupon.getCouponType().toString());
						alertDialog.show();
						// mClose.setVisibility(View.GONE);
					}
				}
			}
		});

		mProgressBar = (ProgressBar) rootView.findViewById(R.id.pbOffer);

		mSwitch.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub

				if (isChecked) {

					Log.e("Status = ", "SwitchOn");
					// mSwitch.setEnabled(false);
					scrollView.setScrollingEnabled(false);
					mRelativeLayout.setVisibility(View.VISIBLE);
					// TODO
					if (mCoupon.getCouponType().equals("スタンプ")) {
						// mClose.setVisibility(View.GONE);
						mStamp.setVisibility(View.GONE);
						mPressStamp.setVisibility(View.VISIBLE);
					}

				} else {
					Log.e("Status = ", "SwitchOff");
				}
			}
		});
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
				Log.e("man", "1");

				mProgressBar.setVisibility(View.GONE);
				if (map.get("coupon") != null) {
					// Check coupon before show Switch
					mSwitch.setVisibility(View.VISIBLE);
					Log.e("man", "2");
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

					mCoupon.imageBitmap(mImageView, new ImageListener() {

						public void onDone(View view, Bitmap bitmap) {
							((ImageView) view).setImageBitmap(bitmap);
						}
					});

					// Set background color for layout CouponFragment
					mCoupon.template(new OffersListener() {

						public void onDone(Map<String, Object> map) {
							Log.e("man", "3");
							if (map.get("template") != null) {
								OffersTemplate template = (OffersTemplate) map
										.get("template");

								@SuppressWarnings("unchecked")
								Map<String, Object> map1 = (Map<String, Object>) template
										.getValues().get("background");
								scrollView.setBackgroundColor(Color
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

					// Type Coupon is Stamp
					if (mCoupon.getCouponType().equals("スタンプ")) {
						Log.e("man", "4");
						Bundle bundle = new Bundle();
						bundle.putInt("coupon_id", mCoupon.getId());

						// Get content of StampKit
						StampKit.getInstance().content(bundle,
								new StampKit.StampListener() {

									public void onDone(Map<String, Object> map,
											Error error) {
										Log.e("man", "5");
										if (error != null) {
											((HomeActivity) getActivity())
													.alert("StampKit.content error",
															error.getMessage());
											return;
										}
										if (map.get("content") != null) {
											Log.e("man", "6");
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
																	Log.e("man",
																			"7");
																	mProgressBar
																			.setVisibility(View.GONE);
																	if (error == null) {

																		if (map.get("groups") != null) {

																			@SuppressWarnings("unchecked")
																			final List<StampGroup> groups = (List<StampGroup>) map
																					.get("groups");

																			if (mCoupon
																					.isUsed()) {
																				Log.e("man",
																						"stamped image");
																				mSwitch.setVisibility(View.GONE);
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
																											Log.e("man",
																													"set Stamp, remove layout and show popup");
																											List<StampStamped> histories = StampKit
																													.getInstance()
																													.localHistories(
																															content_id);

																											mStampView
																													.setGroupsWithHistories(
																															groups,
																															histories);
																											// Remove
																											// layout
																											// and
																											// show
																											// popup
																											// TODO
																											new Handler()
																													.postDelayed(
																															new Runnable() {

																																@Override
																																public void run() {

																																	mFrameLayout
																																			.removeViewAt(1);
																																	mPressStamp
																																			.setVisibility(View.GONE);
																																	scrollView
																																			.setScrollingEnabled(true);
																																	// Show
																																	// popup
																																	if (mSwitch
																																			.isChecked()) {
																																		showPopUp();
																																	}

																																}
																															},
																															STAMP_TIME_OUT);

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
						Log.e("man", "8");
						mSwitch.setVisibility(View.GONE);
						if (mCoupon.getCouponType().equals("スタンプ")) {
							mFrameLayout.removeViewAt(0);
							mFrameLayout.addView(mStampView);
						}

						mUsed.setText((new StringBuilder("使用済:")).append(
								mCoupon.getUsedDate()).toString());
						mCoupon.applySuccessImageBitmap(mSuccessimage,
								new ImageListener() {
									public void onDone(View view, Bitmap bitmap) {
										Log.e("Image = ", "Successfull");
										((ImageView) view)
												.setImageBitmap(bitmap);

									}

								});
						if (mCoupon.getReusable() == true) {
							Log.e("man", "9");
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

						// Remove layout and show popup
						if (mSwitch.isChecked()
								&& !mCoupon.getCouponType().equals("スタンプ")) {
							new Handler().postDelayed(new Runnable() {

								@Override
								public void run() {
									scrollView.setScrollingEnabled(true);
									mFrameLayout.removeViewAt(2);
									showPopUp();

								}
							}, STAMP_TIME_OUT);
						}

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
		super.onDestroyView();
	}

	@Override
	public void releaseStamp(List<Map<String, Object>> list) {
		Log.e("man = ", "Release");
	}

	@Override
	public void afterStamp(Map<String, Object> map) {
		Log.e("man = ", "After");
		apply(null);
	}

	@Override
	public void beforeStamp(Map<String, Object> map) {
		Log.e("man = ", "Before");
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
		Log.e("man = ", "Gesture");
		((Vibrator) getActivity().getSystemService("vibrator")).vibrate(100L);
		if (mSwitch.isChecked()) {
			apply(null);
		}
	}

	@Override
	public void matchStamp(Map<String, Object> map) {
		Log.e("man = ", "Match");
		((Vibrator) getActivity().getSystemService("vibrator")).vibrate(100L);
	}

	public void showPopUp() {
		if (!flag) {
			popupWindow.setAnimationStyle(R.style.PopupAnimation);
			popupWindow.showAtLocation(rootView.findViewById(R.id.imOffer),
					Gravity.NO_GRAVITY, 0, getActivity().getWindowManager()
							.getDefaultDisplay().getHeight() / 2);
			popupWindow.setFocusable(true);
			popupWindow.update();
			flag = true;
		} else {
			popupWindow.dismiss();
			popupWindow.setFocusable(false);
			flag = false;
		}
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
				((HomeActivity) getActivity()).alert("apply.onFail",
						s1.toString());
			}

		});
	}

}
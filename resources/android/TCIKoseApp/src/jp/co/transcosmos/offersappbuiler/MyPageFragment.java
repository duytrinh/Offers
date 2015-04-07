package jp.co.transcosmos.offersappbuiler;

import android.net.http.SslError;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.SslErrorHandler;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

public class MyPageFragment extends Fragment {

	public static String TITLE = "MY PAGE";
	public static String URL = "http://www.eshop.jillstuart-beauty.com/shoplist/kantou.html";

	private WebView mWebView;
	private ProgressBar mProgressBar;
	private Button mBtnBack;
	private Button mBtnForward;
	private Button mBtnRefresh;
	private Button mBtnFull;
	public LinearLayout relativeLayout;
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		getFragmentManager().popBackStack(null,
				FragmentManager.POP_BACK_STACK_INCLUSIVE);

		getActivity().getActionBar().setTitle(TITLE);

		View rootView = inflater.inflate(R.layout.fragment_shops, container,
				false);

		mWebView = (WebView) rootView.findViewById(R.id.webView);
		relativeLayout = (LinearLayout) rootView.findViewById(R.id.toolbar);
		mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
		mBtnBack = (Button) rootView.findViewById(R.id.back);
		mBtnForward = (Button) rootView.findViewById(R.id.forward);
		mBtnRefresh = (Button) rootView.findViewById(R.id.refresh);
		mBtnFull = (Button) rootView.findViewById(R.id.fullScreen);
		startWebView();
		return rootView;
	}

	private void startWebView() {

		mWebView.requestFocusFromTouch();
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);

		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onReceivedSslError(WebView view,
					SslErrorHandler handler, SslError error) {
				// TODO Auto-generated method stub
				handler.proceed();
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {

				view.loadUrl(url);
				return true;
			}

		});

		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int progress) {
				Log.i("progress", String.valueOf(progress));
				if (progress < 100) {
					if (mProgressBar.getVisibility() == View.GONE) {
						mProgressBar.setVisibility(View.VISIBLE);
					}
					mProgressBar.setProgress(progress);
				} else {
					mProgressBar.setVisibility(View.GONE);
				}
			}
		});

		mWebView.loadUrl(URL);

		mBtnBack.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mWebView.goBack();

			}
		});

		mBtnForward.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mWebView.goForward();

			}
		});

		mBtnRefresh.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				mWebView.reload();

			}
		});

		mBtnFull.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				HomeActivity homeActivity = (HomeActivity) getActivity();
				homeActivity.mTabHost.setVisibility(View.GONE);
				getActivity().getActionBar().hide();
				relativeLayout.setVisibility(View.GONE);
			}
		});
	}
}

package jp.co.transcosmos.offersappbuiler;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class FullScreenActivity extends Activity {
	private WebView mWebView;
	private String mUrl;
	private int mX;
	private int mY;
	private ProgressBar mProgressBar;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getWindow().requestFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_fullscreen);

		Intent intent = getIntent();
		if (intent != null) {
			mUrl = getIntent().getStringExtra("url");
			mX = getIntent().getIntExtra("positionX", 0);
			mY = getIntent().getIntExtra("positionY", 0);
		}

		mWebView = (WebView) findViewById(R.id.webView);
		mProgressBar = (ProgressBar) findViewById(R.id.progressBar);
		startWebView();
	}

	private void startWebView() {

		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub

				view.loadUrl(url);
				return true;

			}

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);

				mProgressBar.setVisibility(View.GONE);
			}
		});

		// Javascript inabled on webview
		mWebView.getSettings().setJavaScriptEnabled(true);

		// Load url in webview
		mWebView.loadUrl(mUrl);
		mWebView.setScrollX(mX);
		mWebView.setScrollY(mY);
	}

	/*@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				Intent intent = new Intent();
				intent.putExtra("url", mWebView.getUrl());
				intent.putExtra("positionX", mWebView.getScrollX());
				intent.putExtra("positionY", mWebView.getScrollY());
				setResult(1, intent);								
				finish();
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}*/

}

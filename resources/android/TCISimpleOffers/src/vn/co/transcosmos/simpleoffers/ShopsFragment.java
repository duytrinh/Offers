package vn.co.transcosmos.simpleoffers;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

public class ShopsFragment extends Fragment {

	public static String TITLE = "Shop Information";
	public static String URL = "http://www.eshop.jillstuart-beauty.com/shoplist/kantou.html";

	private WebView webView;
	private ProgressBar progressBar;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_shops, container,
				false);

		((HomeActivity) getActivity()).setActiobarTitle(TITLE);

		webView = (WebView) rootView.findViewById(R.id.webView);
		progressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
		startWebView(URL);
		return rootView;
	}

	public void startWebView(String url) {

		webView.setWebViewClient(new WebViewClient() {
			
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

				progressBar.setVisibility(View.GONE);
			}
		});

		// Javascript inabled on webview
		webView.getSettings().setJavaScriptEnabled(true);

		// Load url in webview
		webView.loadUrl(url);
	}
}

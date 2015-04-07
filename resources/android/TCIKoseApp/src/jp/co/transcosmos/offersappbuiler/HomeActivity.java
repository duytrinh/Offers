package jp.co.transcosmos.offersappbuiler;

import java.util.HashMap;
import java.util.Map;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TabHost.OnTabChangeListener;
import android.widget.TabHost.TabSpec;
import android.widget.TextView;
import co.leonisand.offers.OffersKit;
import co.leonisand.platform.Leonis;

public class HomeActivity extends ActionBarActivity {

	public FragmentTabHost mTabHost;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private ActionBar mActionBar;
	private ArrayAdapter<Map<String, String>> mListAdapter;
	private OnBackStackChangedListener mOnBackStackChangedListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);

		setContentView(R.layout.activity_home);

		// init Leonis
		new Leonis(getApplication());
		Leonis.getInstance().onCreate();
		Leonis.getInstance().setRequestTimeoutInterval(10);

		// init OffersKit
		new OffersKit(getApplication());
		OffersKit.getInstance().onCreate();
		OffersKit.getInstance().setRequestTimeoutInterval(10);

		// Event Back Fragment
		mOnBackStackChangedListener = new android.support.v4.app.FragmentManager.OnBackStackChangedListener() {
			public void onBackStackChanged() {
				refreshActionBar();
			}
		};
		getSupportFragmentManager().addOnBackStackChangedListener(
				mOnBackStackChangedListener);
		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), R.id.realtabcontent);

		mTabHost.addTab(
				setIndicator(HomeActivity.this, mTabHost.newTabSpec("home"),
						R.drawable.tab_indicator_gen, "HOME", R.drawable.home),
				HomeFragment.class, null);

		mTabHost.addTab(
				setIndicator(HomeActivity.this, mTabHost.newTabSpec("shops"),
						R.drawable.tab_indicator_gen, "SHOPS", R.drawable.shops),
				ShopsFragment.class, null);

		mTabHost.addTab(
				setIndicator(HomeActivity.this, mTabHost.newTabSpec("news"),
						R.drawable.tab_indicator_gen, "NEWS", R.drawable.news),
				NewsFragment.class, null);

		mTabHost.addTab(
				setIndicator(HomeActivity.this, mTabHost.newTabSpec("coupon"),
						R.drawable.tab_indicator_gen, "COUPON",
						R.drawable.coupon), OffersFragment.class, null);

		mTabHost.addTab(
				setIndicator(HomeActivity.this, mTabHost.newTabSpec("mypage"),
						R.drawable.tab_indicator_gen, "MY PAGE",
						R.drawable.mypage), MyPageFragment.class, null);

		mTabHost.setOnTabChangedListener(new OnTabChangeListener() {
			@Override
			public void onTabChanged(final String tabId) {
				// TODO Auto-generated method stub
				Log.e("Tab = ", "Click");
				getSupportFragmentManager().popBackStack();
			}
		});

		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mActionBar = getSupportActionBar();
		// mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		// mActionBar.setCustomView(R.layout.actionbar);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mDrawerList.setOnItemClickListener(new DrawerItemClickListener());
		mListAdapter = new ArrayAdapter<Map<String, String>>(this,
				android.R.layout.simple_list_item_1) {
			public View getView(int i, View view, ViewGroup viewgroup) {
				TextView textview = (TextView) super
						.getView(i, view, viewgroup);
				textview.setText((String) ((Map<String, String>) getItem(i))
						.get("title"));
				return textview;
			}
		};
		mDrawerList.setAdapter(mListAdapter);
	}

	public boolean onOptionsItemSelected(MenuItem menuitem) {
		boolean res = true;
		switch (menuitem.getItemId()) {
		case android.R.id.home:
			if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
				getSupportFragmentManager().popBackStack();
			} else {
				mDrawerToggle.onOptionsItemSelected(menuitem);
			}
			break;
		default:
			res = super.onOptionsItemSelected(menuitem);
			break;
		}
		return res;
	}

	protected void onPostCreate(Bundle bundle) {
		super.onPostCreate(bundle);
		mDrawerToggle.syncState();
	}

	public void refreshActionBar() {
		Fragment fragment = (Fragment) getSupportFragmentManager()
				.getFragments().get(
						getSupportFragmentManager().getBackStackEntryCount());

		ActionBar actionbar = getSupportActionBar();
		// setActiobarTitle(fragment.getTag());
		// actionbar.setTitle(fragment.getTag());

		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			actionbar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_holo_dark);
		} else {
			actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);
		}
		supportInvalidateOptionsMenu();
	}

	public void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		mDrawerToggle.onConfigurationChanged(configuration);
	}

	public static Intent getOpenFacebookIntent(Context context) {

		try {
			context.getPackageManager()
					.getPackageInfo("com.facebook.katana", 0);
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("fb://profile/100000167545988"));
		} catch (Exception e) {
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/duymanbk"));
		}
	}

	public static Intent getOpenTwitterIntent(Context c) {

		try {
			c.getPackageManager().getPackageInfo("com.twitter.android", 0);
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("twitter://user?user_id=2993022606"));
		} catch (Exception e) {
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://twitter.com/beobaby123"));
		}
	}

	public static Intent getOpenIntent(Context c, String title) {

		if (title.equals("Facebook")) {
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://www.facebook.com/jillstuartbeauty"));
		} else if (title.equals("Instagram")) {
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("https://instagram.com/jillstuartbeauty"));
		} else {
			return new Intent(Intent.ACTION_VIEW,
					Uri.parse("http://www.jillstuart-beauty.com"));
		}

	}

	public void onStart() {

		super.onStart();
		mListAdapter.clear();
		Map<String, String> hashmap2 = new HashMap<String, String>();
		hashmap2.put("title", "JILL STUART Beauty");
		mListAdapter.add(hashmap2);
		Map<String, String> hashmap = new HashMap<String, String>();
		hashmap.put("title", "Facebook");
		mListAdapter.add(hashmap);
		Map<String, String> hashmap1 = new HashMap<String, String>();
		hashmap1.put("title", "Instagram");
		mListAdapter.add(hashmap1);

	}

	public boolean onCreateOptionsMenu(Menu menu) {
		menu.clear();
		return super.onCreateOptionsMenu(menu);
	}

	public void onDestroy() {

		getSupportFragmentManager().removeOnBackStackChangedListener(
				mOnBackStackChangedListener);
		OffersKit.getInstance().onTerminate();
		mDrawerList.setAdapter(null);

		super.onDestroy();
	}

	public void alert(String title, String message) {
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);

		builder.setPositiveButton(android.R.string.ok, null);

		builder.create().show();
	}

	public void confirm(String title, String message, String positive_title,
			android.content.DialogInterface.OnClickListener positive_listener,
			String negative_title,
			android.content.DialogInterface.OnClickListener negative_listener) {

		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setTitle(title);
		builder.setMessage(message);

		builder.setPositiveButton(positive_title, positive_listener);
		builder.setNegativeButton(negative_title, negative_listener);

		builder.create().show();
	}

	public void setActiobarTitle(String title) {
		View v = mActionBar.getCustomView();
		TextView titleActionBar = (TextView) v.findViewById(R.id.title);
		titleActionBar.setText(title);
	}

	private class DrawerItemClickListener implements
			ListView.OnItemClickListener {
		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {

			@SuppressWarnings("unchecked")
			Map<String, String> map = (Map<String, String>) parent
					.getItemAtPosition(position);
			startActivity(getOpenIntent(HomeActivity.this, map.get("title")));
		}
	}

	private TabSpec setIndicator(Context ctx, TabSpec spec, int resid,
			String string, int iconId) {
		View v = LayoutInflater.from(ctx).inflate(R.layout.tab_item, null);
		v.setBackgroundResource(resid);
		TextView tv = (TextView) v.findViewById(R.id.txt_tabtxt);
		ImageView imageView = (ImageView) v.findViewById(R.id.imageView1);

		imageView.setImageResource(iconId);
		tv.setText(string);
		return spec.setIndicator(v);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if (event.getAction() == KeyEvent.ACTION_DOWN) {
			switch (keyCode) {
			case KeyEvent.KEYCODE_BACK:
				if (!getActionBar().isShowing()) {

					mTabHost.setVisibility(View.VISIBLE);

					FragmentManager fm = this.getSupportFragmentManager();
					Fragment fragment = fm.findFragmentByTag(mTabHost
							.getCurrentTabTag());

					if (fragment instanceof HomeFragment) {
						((HomeFragment) fragment).relativeLayout
								.setVisibility(View.VISIBLE);
					} else if (fragment instanceof ShopsFragment) {
						((ShopsFragment) fragment).relativeLayout
								.setVisibility(View.VISIBLE);
					} else if (fragment instanceof NewUrlFragment) {
						((NewUrlFragment) fragment).relativeLayout
								.setVisibility(View.VISIBLE);
					} else if (fragment instanceof MyPageFragment) {
						((MyPageFragment) fragment).relativeLayout
								.setVisibility(View.VISIBLE);
					}

					getActionBar().show();
				} else {
					// TODO
					if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
						getSupportFragmentManager().popBackStack();
					} else {
						finish();
					}

				}
				return true;
			}

		}
		return super.onKeyDown(keyCode, event);
	}
}
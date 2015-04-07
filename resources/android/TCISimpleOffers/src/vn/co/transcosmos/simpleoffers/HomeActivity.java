package vn.co.transcosmos.simpleoffers;

import java.util.ArrayList;

import vn.co.transcosmos.simpleoffers.adapter.SettingAdapter;
import vn.co.transcosmos.simpleoffers.model.Setting;
import android.R.anim;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.TabHost.OnTabChangeListener;
import co.leonisand.offers.OffersKit;
import co.leonisand.platform.Leonis;

@SuppressLint("InflateParams")
public class HomeActivity extends ActionBarActivity  {
	private FragmentTabHost mTabHost;
	private ActionBar mActionBar;
	private Context mContext;
	private DrawerLayout mDrawerLayout;
	private ListView mDrawerList;
	private ActionBarDrawerToggle mDrawerToggle;
	private SettingAdapter mListAdapter;
	private ArrayList<Setting> mArrayList;
	private OnBackStackChangedListener mOnBackStackChangedListener;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);

		//moveDrawerToTop();
		mContext = this;

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
				//refreshActionBar();
			}
		};

		getSupportFragmentManager().addOnBackStackChangedListener(
				mOnBackStackChangedListener);

		mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
		mTabHost.setup(this, getSupportFragmentManager(), android.R.id.tabcontent);
		
		// 1
		mTabHost.addTab(
				mTabHost.newTabSpec("TOP").setIndicator("1"),
						HomeFragment.class, null);
				// 2
		mTabHost.addTab(
				mTabHost.newTabSpec("Category").setIndicator("2"),
								
								NewsFragment.class, null);
				// 3
		mTabHost.addTab(
				mTabHost.newTabSpec("Favorite").setIndicator("3"),
								
								OfferFragment.class, null);
				// 4
		mTabHost.addTab(
				mTabHost.newTabSpec("Cart").setIndicator("4"),
							
								GroupsFragment.class, null);
				// 5
		mTabHost.addTab(
				mTabHost.newTabSpec("Other").setIndicator("5"),
								
								ShopsFragment.class, null);
		
		mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
		mDrawerToggle = new ActionBarDrawerToggle(this, mDrawerLayout,
				R.drawable.ic_drawer, R.string.drawer_open,
				R.string.drawer_close);
		mDrawerToggle.setDrawerIndicatorEnabled(true);
		mDrawerLayout.setDrawerListener(mDrawerToggle);

		mActionBar = getSupportActionBar();
		mActionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		mActionBar.setCustomView(R.layout.actionbar_title);
		mActionBar.setDisplayHomeAsUpEnabled(true);
		mActionBar.setHomeButtonEnabled(true);

		mDrawerList = (ListView) findViewById(R.id.left_drawer);
		mArrayList = new ArrayList<Setting>();
		mListAdapter = new SettingAdapter(this, mArrayList);
		
		View header = getLayoutInflater().inflate(R.layout.header, null);
		mDrawerList.addHeaderView(header);
		
		mDrawerList.setAdapter(mListAdapter);

		/*try {
			View header = getLayoutInflater().inflate(R.layout.header, null);
			mDrawerList.addHeaderView(header);
		} catch (Exception e) {
			e.printStackTrace();
		}*/

		setActiobarTitle(HomeFragment.TITLE);
		/*getSupportFragmentManager()
				.beginTransaction()
				.replace(R.id.container, new HomeFragment(), HomeFragment.TITLE)
				.commit();*/

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		menu.clear();
		return super.onCreateOptionsMenu(menu);
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

	public void onConfigurationChanged(Configuration configuration) {
		super.onConfigurationChanged(configuration);
		mDrawerToggle.onConfigurationChanged(configuration);
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
		actionbar.setTitle(fragment.getTag());

		if (getSupportFragmentManager().getBackStackEntryCount() > 0) {
			actionbar.setHomeAsUpIndicator(R.drawable.abc_ic_ab_back_holo_dark);
		} else {
			actionbar.setHomeAsUpIndicator(R.drawable.ic_drawer);
		}
		supportInvalidateOptionsMenu();
	}

	public void onStart() {
		super.onStart();
		mArrayList.clear();

		Setting facebook = new Setting("Facebook", true);
		mArrayList.add(facebook);

		Setting twitter = new Setting("Twitter", false);
		mArrayList.add(twitter);

		Setting notification = new Setting("Notification", false);
		mArrayList.add(notification);

		mListAdapter.notifyDataSetChanged();
	}

	public void onResume() {
		super.onResume();

		//refreshActionBar();
	}

	public void onDestroy() {

		getSupportFragmentManager().removeOnBackStackChangedListener(
				mOnBackStackChangedListener);
		OffersKit.getInstance().onTerminate();
		mDrawerList.setAdapter(null);
		
		super.onDestroy();
	}

	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		if (e.getAction() == KeyEvent.ACTION_UP
				&& e.getKeyCode() == KeyEvent.KEYCODE_MENU) {
			boolean drawerOpen = mDrawerLayout.isDrawerOpen(mDrawerList);
			if (!drawerOpen)
				mDrawerLayout.openDrawer(Gravity.START);
			else
				mDrawerLayout.closeDrawer(Gravity.START);
			return true;
		}
		return super.dispatchKeyEvent(e);
	}

	public void alert(String title, String message) {
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				mContext);
		builder.setTitle(title);
		builder.setMessage(message);

		builder.setPositiveButton(android.R.string.ok, null);

		builder.create().show();
	}

	public void confirm(String title, String message, String positive_title,
			android.content.DialogInterface.OnClickListener positive_listener,
			String negative_title,
			android.content.DialogInterface.OnClickListener negative_listener) {
		android.app.AlertDialog.Builder builder = new AlertDialog.Builder(
				mContext);
		builder.setTitle(title);
		builder.setMessage(message);

		builder.setPositiveButton(positive_title, positive_listener);
		builder.setNegativeButton(negative_title, negative_listener);

		builder.create().show();
	}

	public void setActiobarTitle(String title) {
		View v = mActionBar.getCustomView();
		TextView titleTxtView = (TextView) v.findViewById(R.id.myTitle);
		titleTxtView.setText(title);
	}

	private void moveDrawerToTop() {

		LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		DrawerLayout drawer = (DrawerLayout) inflater.inflate(
				R.layout.activity_home, null); // "null" is important.

		ViewGroup decor = (ViewGroup) getWindow().getDecorView();
		View child = decor.getChildAt(0);
		decor.removeView(child);

		LinearLayout containerTemp = (LinearLayout) drawer
				.findViewById(R.id.containerTemp); // This is the container we
													// defined just now.
		containerTemp.addView(child, 0);
		drawer.findViewById(R.id.left_drawer).setPadding(0,
				getStatusBarHeight(), 0, 0);

		// Make the drawer replace the first child
		decor.addView(drawer);
	}

	public int getStatusBarHeight() {
		int result = 0;
		int resourceId = getResources().getIdentifier("status_bar_height",
				"dimen", "android");
		if (resourceId > 0) {
			result = getResources().getDimensionPixelSize(resourceId);
		}
		return result;
	}

	
}

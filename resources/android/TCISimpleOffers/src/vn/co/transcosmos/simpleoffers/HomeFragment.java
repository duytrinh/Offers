package vn.co.transcosmos.simpleoffers;

import android.R.animator;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class HomeFragment extends Fragment {

	public static String TITLE = "Home Screen";

	private Button news;
	private Button shops;
	private Button offers;
	private Button groups;

	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View rootView = inflater.inflate(R.layout.fragment_home, container,
				false);

		news = (Button) rootView.findViewById(R.id.news);
		news.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub

				getFragmentManager()
						.beginTransaction()
						.replace(android.R.id.tabcontent, new NewsFragment(),
								NewsFragment.TITLE)
						.setTransition(
								FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.addToBackStack(null).commit();
			}
		});

		shops = (Button) rootView.findViewById(R.id.shops);
		shops.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.container, new ShopsFragment(),
								ShopsFragment.TITLE)
						.setTransition(
								FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.addToBackStack(null).commit();
			}
		});

		offers = (Button) rootView.findViewById(R.id.offers);
		offers.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager()
						.beginTransaction()
						.replace(android.R.id.tabcontent, new OffersFragment(),
								OffersFragment.TITLE)
						.setTransition(
								FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.addToBackStack(null).commit();
			}
		});
		
		groups = (Button) rootView.findViewById(R.id.groups);
		groups.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				getFragmentManager()
						.beginTransaction()
						.replace(R.id.container, new GroupsFragment(),
								GroupsFragment.TITLE)
						.setTransition(
								FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
						.addToBackStack(null).commit();
			}
		});

		return rootView;
	}

}

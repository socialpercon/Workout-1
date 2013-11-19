package com.ece4564.group11.workout.userinterface;

/***************************************/
//To peoples:
//ignore all comments except ones that are in
//boxes like this unless you wanna learn
//how stuff works.
/***************************************/


import java.util.Locale;



import com.ece4564.group11.workout.fragments.HeartRateFragment;
import com.ece4564.group11.workout.fragments.PlannerFragment;
import com.ece4564.group11.workout.fragments.WorkoutFragment;
import com.ece4564.group11.workout.sensor.GPS;
import com.ece4564.group11.workout.testexample.DummySectionFragment;
import com.ece4564.group11.workout.testexample.SearchFragment;
import com.example.final_project.R;

import android.app.ActionBar;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;

public class ViewpagerNav extends FragmentActivity implements
		ActionBar.TabListener {

	SectionsPagerAdapter mSectionsPagerAdapter;
	ViewPager mViewPager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager_nav);
		// Set up the action bar.
		final ActionBar actionBar = getActionBar();
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		// Create the adapter that will return a fragment for each of the three
		// primary sections of the app.
		mSectionsPagerAdapter = new SectionsPagerAdapter(
				getSupportFragmentManager());
		// Set up the ViewPager with the sections adapter.
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setAdapter(mSectionsPagerAdapter);
		// When swiping between different sections, select the corresponding
		// tab. We can also use ActionBar.Tab#select() to do this if we have
		// a reference to the Tab.
		mViewPager
				.setOnPageChangeListener(new ViewPager.SimpleOnPageChangeListener() {
					@Override
					public void onPageSelected(int position) {
						actionBar.setSelectedNavigationItem(position);
					}
				});

		// For each of the sections in the app, add a tab to the action bar.
		for (int i = 0; i < mSectionsPagerAdapter.getCount(); i++) {
			// Create a tab with text corresponding to the page title defined by
			// the adapter. Also specify this Activity object, which implements
			// the TabListener interface, as the callback (listener) for when
			// this tab is selected.
			actionBar.addTab(actionBar.newTab()
					.setText(mSectionsPagerAdapter.getPageTitle(i))
					.setTabListener(this));
		}

		Intent intent = getIntent();
		setStartup(intent.getStringExtra(SplashScreen.EXTRA_MESSAGE), actionBar);
	}

	private void setStartup(String stringExtra, ActionBar ab) {
		/***************************************/
		// add cases here for first screen
		/***************************************/
		if (stringExtra.equals("default")) {
			ab.setSelectedNavigationItem(1);
		}

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.viewpager_nav, menu);
		return true;
	}

	@Override
	public void onTabSelected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
		mViewPager.setCurrentItem(tab.getPosition());
	}

	@Override
	public void onTabUnselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	@Override
	public void onTabReselected(ActionBar.Tab tab,
			FragmentTransaction fragmentTransaction) {
	}

	public class SectionsPagerAdapter extends FragmentPagerAdapter {

		public SectionsPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int position) {

			Fragment fragment = new DummySectionFragment();
			switch (position) {
			case 0:
				fragment = new HeartRateFragment();
				break;
			case 1:
				fragment = new SearchFragment();
				break;
			case 2:
				fragment = new PlannerFragment();
				break;
			case 3:
				fragment = new WorkoutFragment();
				break;
			case 4:
				fragment = new GPS();
				break;

			}

			/***************************************/
			// add new retrieval statements here
			/***************************************/

			Bundle args = new Bundle();
			args.putInt(DummySectionFragment.ARG_SECTION_NUMBER, position + 1);
			fragment.setArguments(args);
			return fragment;
		}

		@Override
		public int getCount() {
			/***************************************/
			// update this number when you add a new frag
			/***************************************/
			return 5;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			Locale l = Locale.getDefault();
			switch (position) {
			case 0:
				return getString(R.string.title_section1).toUpperCase(l);
			case 1:
				return getString(R.string.title_section2).toUpperCase(l);
			case 2:
				return getString(R.string.title_section3).toUpperCase(l);
			case 3:
				return getString(R.string.title_section4).toUpperCase(l);
			case 4:
				return getString(R.string.title_section5).toUpperCase(l);
				/***************************************/
				// add new names here and in strings.xml
				/***************************************/
			}
			return null;
		}
	}

}

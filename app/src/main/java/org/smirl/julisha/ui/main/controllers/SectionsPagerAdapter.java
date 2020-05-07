package org.smirl.julisha.ui.main.controllers;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.google.android.material.tabs.TabLayout;
import org.smirl.julisha.MainActivity;
import org.smirl.julisha.R;

/**
 * A [FragmentPagerAdapter] that returns a fragment corresponding to
 * one of the sections/tabs/pages.
 */
public class SectionsPagerAdapter extends FragmentPagerAdapter {

  @StringRes
  private static final int[] TAB_TITLES = new int[]{R.string.tab_text_1, R.string.tab_text_2, R.string.tab_text_3, R.string.tab_text_4};
  // private final Context mContext;
  private MainActivity mActivity;

  public SectionsPagerAdapter(MainActivity activity, FragmentManager fm) {
    super(fm);
    // mContext = context;
    mActivity = activity;
  }

  @Override
  public Fragment getItem(int position) {
    // getItem is called to instantiate the fragment for the given page.
    // Return a StatisticsFragment (defined as a static inner class below).
    switch (position) {
      case 1:
        return StatisticsFragment.newInstance(mActivity, position);
      case 2:
        return StatisticsVillesFragment.newInstance(mActivity, position);
      case 3:
        return CartographieFragment.newInstance(mActivity, position);

      default:
        return GraphicsFragment.newInstance(mActivity, position);
    }

  }


  @Nullable
  @Override
  public CharSequence getPageTitle(int position) {
    return mActivity.getResources().getString(TAB_TITLES[position]);
  }

  @Override
  public int getCount() {
    // Show 2 total pages.
    return TAB_TITLES.length;
  }
}
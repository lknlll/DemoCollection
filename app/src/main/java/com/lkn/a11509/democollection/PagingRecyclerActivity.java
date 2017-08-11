package com.lkn.a11509.democollection;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.TypedValue;

import com.astuetz.PagerSlidingTabStrip;
import com.lkn.a11509.democollection.Fragment.PagingRecyclerFragment;

import butterknife.BindView;

public class PagingRecyclerActivity extends BaseActivity {

    @BindView(R.id.tabs)
    PagerSlidingTabStrip tabs;
    @BindView(R.id.pager)
    ViewPager pager;

    LinDemoPagerAdapter linDemoPagerAdapter;
    @Override
    protected void setUpContentView() {
        setContentView(R.layout.activity_paging_recycler);
    }

    @Override
    protected void setUpView() {
        linDemoPagerAdapter = new LinDemoPagerAdapter(getSupportFragmentManager());
        pager.setAdapter(linDemoPagerAdapter);
        final int pageMargin = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 4, getResources()
                .getDisplayMetrics());
        pager.setPageMargin(pageMargin);

        tabs.setViewPager(pager);
    }

    @Override
    protected void setUpData(Bundle savedInstanceState) {

    }

    public class LinDemoPagerAdapter extends FragmentPagerAdapter{

        private final String[] TITLES = { "Categories", "Home", "Top Paid", "Top Free", "Top Grossing", "Top New Paid",
                "Top New Free", "Trending" };

        public LinDemoPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return TITLES[position];
        }

        @Override
        public Fragment getItem(int position) {
            return PagingRecyclerFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return TITLES.length;
        }
    }
}

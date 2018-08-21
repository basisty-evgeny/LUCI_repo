package com.luci.ui.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.luci.R;
import com.luci.ui.fragment.OptionDefaultFragment;
import com.luci.ui.fragment.OptionGeneralFragment;
import com.luci.ui.fragment.OptionSipFragment;

import java.util.ArrayList;
import java.util.List;

public class OptionsActivity extends BaseActionBarActivity implements
        View.OnClickListener {
    public static OptionsActivity instance;

    OptionDefaultFragment mDefaultFrag;
    OptionSipFragment mSipFrag;
    OptionGeneralFragment mGeneralFrag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        instance = this;
        setContentView(R.layout.activity_options);

        initActionBar();
        setTitle(R.string.Options);

        mDefaultFrag = OptionDefaultFragment.newInstance();
        mSipFrag = OptionSipFragment.newInstance();
        mGeneralFrag = OptionGeneralFragment.newInstance();

        PagerAdapter pagerAdapter = new PagerAdapter(getSupportFragmentManager());
        pagerAdapter.addFragment(mDefaultFrag, getString(R.string.Defaults));
        pagerAdapter.addFragment(mSipFrag, getString(R.string.SIP));
        pagerAdapter.addFragment(mGeneralFrag, getString(R.string.General));

        ViewPager viewPager = findViewById(R.id.viewPager);
        viewPager.setAdapter(pagerAdapter);

        TabLayout tabLayout = findViewById(R.id.tabLayout);
        tabLayout.setupWithViewPager(viewPager);

        findViewById(R.id.btn_ok).setOnClickListener(this);
        findViewById(R.id.btn_cancel).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_ok: {
                onBackPressed();
            }
            break;

            case R.id.btn_cancel: {
                onBackPressed();
            }
            break;
        }
    }

    static class PagerAdapter extends FragmentStatePagerAdapter {
        private final List<Fragment> fragmentList = new ArrayList<>();
        private final List<String> fragmentTitleList = new ArrayList<>();

        public PagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        public void addFragment(Fragment fragment, String title) {
            fragmentList.add(fragment);
            fragmentTitleList.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return fragmentList.get(position);
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return fragmentTitleList.get(position);
        }
    }
}

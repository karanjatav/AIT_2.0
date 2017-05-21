package com.example.darkknight.ait_20.Fragments.Main;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.darkknight.ait_20.Fragments.Info.Academics;
import com.example.darkknight.ait_20.Fragments.Info.Achievements;
import com.example.darkknight.ait_20.Fragments.Info.Infrastructure;
import com.example.darkknight.ait_20.R;

import java.util.ArrayList;
import java.util.List;


public class InfoTab extends Fragment {

    private TabLayout tabLayout;
    private ViewPager viewPager;

    private int[] tabIcons = {
            R.drawable.winfra1,
            R.drawable.wachiv1,
            R.drawable.wfacilit1,
            R.drawable.wacad1
    };


    public InfoTab() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v =  inflater.inflate(R.layout.fragment_info_tab, container, false);

        viewPager = (ViewPager) v.findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) v.findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();

        return v;
    }

    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        // tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(2).setIcon(tabIcons[3]);
    }


    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getFragmentManager());
        adapter.addFragment(new Infrastructure(), "Infrastructure");
        adapter.addFragment(new Achievements(), "Achievements");
        //adapter.addFragment(new ThreeFragment(), "Facilities");
        adapter.addFragment(new Academics(), "Academics");
        viewPager.setAdapter(adapter);
    }

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }


}

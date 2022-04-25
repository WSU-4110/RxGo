package com.example.demorxgo;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.annotation.StringRes;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class ViewPagerAdapter extends FragmentPagerAdapter {

    @StringRes
    private static final int[] TAB_TITLES = new int[]{R.string.tab_text_5, R.string.tab_text_6};

    private final Context mContext;

    public ViewPagerAdapter(FragmentManager fm, Context context) {
        super ( fm );
        mContext = context;
    }

    @Override
    public Fragment getItem(int position) {
        // set tab pages

        Fragment fragment = null;
        switch (position) {
            case 0:
                fragment = new ChatsFragment();
                break;
            case 1:
                fragment = new UserFragment();
                break;
        }
        return fragment;


    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        return mContext.getResources().getString(TAB_TITLES[position]);

    }

    @Override
    public int getCount() {
        return 2;
    }
}


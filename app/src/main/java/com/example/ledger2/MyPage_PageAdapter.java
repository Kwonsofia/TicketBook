package com.example.ledger2;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class MyPage_PageAdapter extends FragmentStatePagerAdapter {
    int counttab;

    public MyPage_PageAdapter(@NonNull FragmentManager fm, int counttab) {
        super(fm);
        this.counttab = counttab;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {
                return new MyPage_TabOne_Musical();

            }
            case 1: {
                return new MyPage_TabTwo_Play();
            }
            case 2: {
                return new MyPage_TabThree_Exhibition();
            }
            case 3: {
                return new MyPage_TabFour_Etc();
            }
            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return counttab;
    }
}

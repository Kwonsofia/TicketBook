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

    //class 생성한 뒤에 바로 implement.
    //Create constructor matching super

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0: {
                MyPage_TabOne_Musical myPageTaboneMusical = new MyPage_TabOne_Musical();
                return myPageTaboneMusical;
            }
            case 1: {
                MyPage_TabTwo_Play myPageTabTwoPlay = new MyPage_TabTwo_Play();
                return myPageTabTwoPlay;
            }
            case 2:{
                MyPage_TabThree_Exhibition myPageTabThreeExhibition = new MyPage_TabThree_Exhibition();
                return myPageTabThreeExhibition;
            }
            case 3:{
                MyPage_TabFour_Etc myPageTabFourEtc = new MyPage_TabFour_Etc();
                return  myPageTabFourEtc;
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

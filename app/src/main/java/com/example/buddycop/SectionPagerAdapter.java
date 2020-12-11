package com.example.buddycop;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

class SectionPagerAdapter extends FragmentPagerAdapter {

    public SectionPagerAdapter(FragmentManager fm){
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position){
            case 0:
                MyFirFragment myFirFragment=new MyFirFragment();
                return myFirFragment;
            case 1:
                NewFirFragment newFirFragment =new NewFirFragment();
                return newFirFragment;

             case 2:
                 SearchFirFragment searchFirFragment =new SearchFirFragment();
                 return searchFirFragment;
            default:
                return null;

        }
       // return null;
    }

    @Override
    public int getCount() {
        return 3;
    }

    public CharSequence getPageTitle(int position){

        switch (position){

            case 0:
                return "MY FIR";
            case 1:
                return "NEW FIR";
            case 2:
                return "SEARCH";
            default:
                return null;

        }
    }
}

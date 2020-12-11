package com.example.buddycop;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class EmergencyPagerAdapter extends FragmentPagerAdapter {
    public EmergencyPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position){
            case 0:
                EmergencyContactsFragment emergencyContactsFragment=new EmergencyContactsFragment();
                return emergencyContactsFragment;
            case 1:
                OtherContactsFragment otherContactsFragment =new OtherContactsFragment();
                return otherContactsFragment;

            default:
                return null;

        }
    }

    @Override
    public int getCount() {
        return 2;
    }

    public CharSequence getPageTitle(int position){

        switch (position){

            case 0:
                return "EMERGENCY CONTACTS";
            case 1:
                return "OTHER CONTACTS";
            default:
                return null;

        }
    }
}

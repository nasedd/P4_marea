package fr.nazodev.p4_mareu.user_interface;


import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class PagerAdapter extends FragmentPagerAdapter {
    public PagerAdapter(FragmentManager fm) { super(fm); }


    @NonNull
    @Override
    public Fragment getItem(int position) {
        return new MeetingFragment().newInstance();
    }

    @Override
    public int getCount() {
        return 1;
    }
}

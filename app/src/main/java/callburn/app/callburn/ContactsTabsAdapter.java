package callburn.app.callburn;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Bloom on 11/1/2016.
 */
public class ContactsTabsAdapter extends FragmentPagerAdapter {

    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 2;
    private final List<String> mFragmentTitleList = new ArrayList<>();


    public ContactsTabsAdapter(FragmentManager fm) {
        super(fm);
        mFragmentTitleList.add("DEVICE PHONEBOOK");
        mFragmentTitleList.add("CALLBURN PHONEBOOK");
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                TabsFragment1 fr1 = new TabsFragment1();
                return fr1;
            case 1:
                TabsFragment2 fr2 = new TabsFragment2();
                return fr2;
            default:
                TabsFragment1 fr = new TabsFragment1();
                return fr;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }


}

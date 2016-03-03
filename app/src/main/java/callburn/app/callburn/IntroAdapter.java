package callburn.app.callburn;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

public class IntroAdapter extends FragmentPagerAdapter {

    // Declare the number of ViewPager pages
    final int PAGE_COUNT = 6;

    public IntroAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                Intro1 intro1 = new Intro1();
                return intro1;
            case 1:
                Intro2 intro2 = new Intro2();
                return intro2;
            case 2:
                Intro3 intro3 = new Intro3();
                return intro3;
            case 3:
                Intro4 intro4 = new Intro4();
                return intro4;
            case 4:
                Intro5 intro5 = new Intro5();
                return intro5;
            case 5:
                Intro6 intro6 = new Intro6();
                return intro6;
            default:
                Intro1 intro = new Intro1();
                return intro;
        }
    }

    @Override
    public int getCount() {
        return PAGE_COUNT;
    }

}

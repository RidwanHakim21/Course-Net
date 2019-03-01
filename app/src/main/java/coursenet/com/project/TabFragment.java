package coursenet.com.project;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


/**
 * A simple {@link Fragment} subclass.
 */
public class TabFragment extends Fragment {


    public TabFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View a = inflater.inflate(R.layout.fragment_tab, container, false);

        TabLayout tab1 = (TabLayout) a.findViewById(R.id.tab1);
        ViewPager pager1 = (ViewPager) a.findViewById(R.id.pager1);

        pager1.setAdapter(new TabSetting(getChildFragmentManager()));
        tab1.setupWithViewPager(pager1);

        //tanpa tex tidak memakai pageTittle
        tab1.getTabAt(0).setIcon(R.drawable.ic_person_black_24dp);

        //menggunakan text memakai pageTittle
        tab1.getTabAt(1).setIcon(R.drawable.ic_vpn_key_black_24dp);

        return a;
    }


    class TabSetting extends FragmentStatePagerAdapter {

        public TabSetting(FragmentManager fm) {
            super(fm);

        }

        @Nullable
        @Override
        public CharSequence getPageTitle(int position) {
            if (position == 1)
            {
                return getString(R.string.about);
            }
            else if (position == 2)
            {
                return getString(R.string.change_password);
            }
            return super.getPageTitle(position);
        }

        @Override
        public Fragment getItem(int i) {
            if (i == 0) {
                return new HomeFragment();
            } else if (i == 1) {
                return new AboutFragment();
            } else if (i == 2) {
                return new PasswordFragment();
            }
            return null;

        }

        @Override
        public int getCount() {
            return 3;

        }
    }

}

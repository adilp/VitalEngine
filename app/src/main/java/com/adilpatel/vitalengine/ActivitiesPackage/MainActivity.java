package com.adilpatel.vitalengine.ActivitiesPackage;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.adilpatel.vitalengine.ActivitiesPackage.RecyclerConversation.RecyclerAddConvMyTeam;
import com.adilpatel.vitalengine.ActivitiesPackage.RecyclerMessage.RecyclerFromMessage;
import com.adilpatel.vitalengine.ActivitiesPackage.RecyclerReferral.newReferenceActivity;
import com.adilpatel.vitalengine.FragmentPackage.allFragment;
import com.adilpatel.vitalengine.FragmentPackage.conversationsFragment;
import com.adilpatel.vitalengine.FragmentPackage.messageFragment;
import com.adilpatel.vitalengine.FragmentPackage.referralFragment;
import com.adilpatel.vitalengine.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    //private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;


    private int[] tabIcons = {
        R.drawable.comments,
            R.drawable.message,
            R.drawable.all,
            R.drawable.rules

    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);
        //setupTabIcons();

//        for (int i = 0; i < tabLayout.getTabCount(); i++) {
//            TabLayout.Tab tab = tabLayout.getTabAt(i);
//            tab.setCustomView(pagerAdapter.getTabView(i));
//        }


        //FloatingActionButton button = (FloatingActionButton) findViewById(R.id.right_labels);
        //FloatingActionButton newReferralFab = (FloatingActionButton)findViewById(R.id.newReferralFab);

        FloatingActionButton newReferralFab = (FloatingActionButton) findViewById(R.id.newReferralFab);
        FloatingActionButton newMessageFab = (FloatingActionButton) findViewById(R.id.newMessageFab);
        FloatingActionButton newConversationFab = (FloatingActionButton) findViewById(R.id.newConversationFab);

        newReferralFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "New Referral Button clicked", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, newReferenceActivity.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        newMessageFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "New Message Button clicked", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, RecyclerFromMessage.class);
                MainActivity.this.startActivity(myIntent);
            }
        });

        newConversationFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Toast.makeText(MainActivity.this, "New Conversation Button clicked", Toast.LENGTH_SHORT).show();
                Intent myIntent = new Intent(MainActivity.this, RecyclerAddConvMyTeam.class);
                MainActivity.this.startActivity(myIntent);
            }
        });


    }

    private void setupTabIcons(){
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
        tabLayout.getTabAt(3).setIcon(tabIcons[3]);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        public PlaceholderFragment() {
        }

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(int sectionNumber) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_SECTION_NUMBER, sectionNumber);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);
            TextView textView = (TextView) rootView.findViewById(R.id.section_label);
            textView.setText(getString(R.string.section_format, getArguments().getInt(ARG_SECTION_NUMBER)));
            return rootView;
        }
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFrag(new referralFragment(), "Referral");
        adapter.addFrag(new conversationsFragment(), "Conversations");
        adapter.addFrag(new messageFragment(), "Messages");
        adapter.addFrag(new allFragment(), "All");
        viewPager.setAdapter(adapter);
        viewPager.setCurrentItem(1);

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

        public void addFrag(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mFragmentTitleList.get(position);
        }
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */


}

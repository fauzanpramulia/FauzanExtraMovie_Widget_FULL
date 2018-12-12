package com.fauzanpramulia.fauzanextramovies;

import android.content.ClipboardManager;
import android.content.Intent;

import android.provider.Settings;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SwitchCompat;
import android.support.v7.widget.Toolbar;

import android.view.Menu;
import android.view.MenuItem;

import com.fauzanpramulia.fauzanextramovies.fragment.Tab1NowPlaying;
import com.fauzanpramulia.fauzanextramovies.fragment.Tab2Upcoming;
import com.fauzanpramulia.fauzanextramovies.fragment.Tab3Cari;
import com.fauzanpramulia.fauzanextramovies.reminder.DailyAlarmReceiver;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tab_layout) TabLayout tabLayout;
    private SectionsPagerAdapter mSectionsPagerAdapter;
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Fauzan Extra Movies");
        toolbar.inflateMenu(R.menu.main_menu);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);

        mViewPager.setAdapter(mSectionsPagerAdapter);
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
//        if (id==R.id.menu_refresh){
//            //getNowPlayingMoview();
//            //loadDummyData();
//        }
        if (id==R.id.menu_language){
            Intent intent = new Intent(Settings.ACTION_LOCALE_SETTINGS);
            startActivity(intent);
        }
        if (id==R.id.favorit){
            Intent intent = new Intent(this, FavoritActivity.class);
            startActivity(intent);
        }
        if (id==R.id.home){
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
        if (id==R.id.settings){
            Intent intent = new Intent(this, SettingActivity.class);
            startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }

    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            switch (position){
                case 0:
                    Tab1NowPlaying tab1 = new Tab1NowPlaying();
                    return tab1;
                case 1:
                    Tab2Upcoming tab2 = new Tab2Upcoming();
                    return tab2;
                case 2:
                    Tab3Cari tab3 = new Tab3Cari();
                    return tab3;
                default:
                    return null;
            }
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

    }

}

package com.github.anniepank.schedule.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.getbase.floatingactionbutton.FloatingActionsMenu;
import com.github.anniepank.schedule.AppData;
import com.github.anniepank.schedule.ClassData;
import com.github.anniepank.schedule.R;
import com.github.anniepank.schedule.TaskData;
import com.github.anniepank.schedule.fragments.TodayFragment;
import com.github.anniepank.schedule.fragments.TodoFragment;
import com.github.anniepank.schedule.fragments.WeekFragment;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter sectionsPagerAdapter;
    public TodoFragment todoFragment = new TodoFragment();
    public WeekFragment weekFragment = new WeekFragment();

    @BindView(R.id.toolbar)
    Toolbar toolbar;
    @BindView(R.id.container)
    ViewPager viewPager;
    @BindView(R.id.tabs)
    TabLayout tabLayout;
    @BindView(R.id.fab_menu)
    FloatingActionsMenu fabMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);
        setSupportActionBar(toolbar);

        sectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        sectionsPagerAdapter.todoFragment = todoFragment;
        sectionsPagerAdapter.weekFragment = weekFragment;

        viewPager.setAdapter(sectionsPagerAdapter);


        tabLayout.setupWithViewPager(viewPager);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        todoFragment.onActivityResult();
        weekFragment.onActivityResult();
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

    @OnClick(R.id.new_task) void onNewTask() {
        viewPager.setCurrentItem(2, false);
        TaskData newTask = new TaskData("New Task", "");
        AppData.get(this).tasks.add(newTask);
        fabMenu.collapse();
        startActivityForResult(EditTaskActivity.createIntent(this, newTask), 1);
    }

    @OnClick(R.id.new_class)
    void onNewClass() {
        viewPager.setCurrentItem(1, false);
        ClassData newClass = new ClassData();
        AppData.get(this).classes.add(newClass);
        fabMenu.collapse();
        startActivityForResult(EditClassActivity.createIntent(this, newClass), 1);
    }
    /**
     * A {@link FragmentPagerAdapter} that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    public class SectionsPagerAdapter extends FragmentPagerAdapter {

        public TodoFragment todoFragment;
        public WeekFragment weekFragment;


        public SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            switch (position) {
                case 0:
                    return new TodayFragment();
                case 1:
                    return weekFragment;
                case 2:
                    return todoFragment;
            }
            return null;
        }

        @Override
        public int getCount() {
            // Show 3 total pages.
            return 3;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            switch (position) {
                case 0:
                    return "TODAY";
                case 1:
                    return "WEEK";
                case 2:
                    return "TODO";
            }
            return null;
        }
    }
}

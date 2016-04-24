package holmusk.com.holmuskchallenge.Activities.DailyActivity;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import holmusk.com.holmuskchallenge.Activities.DailyActivity.Fragments.FoodsFragment;
import holmusk.com.holmuskchallenge.Activities.DailyActivity.Fragments.SummaryFragment;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Dialog.SettingsDialogFragment;
import holmusk.com.holmuskchallenge.Storage.Databases.DatabaseWrapper;

public class DailyActivity extends AppCompatActivity implements SettingsDialogFragment.OnSessionDialogListener {

    private DatabaseWrapper wrapper;

    // layout
    private FragmentPagerItemAdapter adapter;
    private ViewPager viewPager;

    /*
    * Activity methods
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily);

        initializeStorage();
        setUpWidgets();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            onBackPressed();
            return true;

        } else if (id == R.id.action_setting) {
            showSettingsDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        wrapper.destroy();
    }

    @Override
    public void populateWidgets() {

    }


    /*
    * Storage
    * */

    private void initializeStorage() {
        initializeDatabaseWrapper();
    }

    private void initializeDatabaseWrapper() {
        wrapper = DatabaseWrapper.getInstance(this);
    }


    /*
    * Dialog
    * */

    private void showSettingsDialog() {
        SettingsDialogFragment dialogFragment = new SettingsDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), SettingsDialogFragment.DIALOG_NAME);
    }


    /*
    * Widget methods
    * */

    private void setUpWidgets() {
        initializeTabLayout();
    }

    private void initializeTabLayout() {
        initializeViewPagerAdapter();
        initializeViewPager();
        initializeTabs();
    }

    private void initializeViewPagerAdapter() {
        FragmentPagerItems items = FragmentPagerItems.with(this)
                .add(R.string.summary_tab_title, SummaryFragment.class)
                .add(R.string.food_tab_title, FoodsFragment.class)
                .create();

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), items);
    }

    private void initializeViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        viewPager.setAdapter(adapter);
    }

    private void initializeTabs() {
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpager_tab);
        viewPagerTab.setViewPager(viewPager);
    }
}

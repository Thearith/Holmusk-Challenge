package holmusk.com.holmuskchallenge.Activities.DailyActivity;

import android.app.SearchManager;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.ogaclejapan.smarttablayout.SmartTabLayout;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItemAdapter;
import com.ogaclejapan.smarttablayout.utils.v4.FragmentPagerItems;

import cn.pedant.SweetAlert.SweetAlertDialog;
import holmusk.com.holmuskchallenge.Activities.DailyActivity.Fragments.FoodsFragment;
import holmusk.com.holmuskchallenge.Activities.DailyActivity.Fragments.SummaryTabFragment;
import holmusk.com.holmuskchallenge.Models.Food;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Dialog.SettingsDialogFragment;
import holmusk.com.holmuskchallenge.Resources.Fragments.FoodFragment;
import holmusk.com.holmuskchallenge.Storage.Databases.DatabaseWrapper;

public class DailyActivity extends AppCompatActivity implements SettingsDialogFragment.OnSessionDialogListener,
        SearchView.OnQueryTextListener, FoodFragment.FoodFragmentCallback {

    private static final String TAG         = DailyActivity.class.getSimpleName();

    private DatabaseWrapper wrapper;

    private SweetAlertDialog noInternetDialog;

    // layout
    private FragmentPagerItemAdapter adapter;
    private SearchView searchView;
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

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(this);


        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == android.R.id.home) {
            finish();
            return true;

        } else if (id == R.id.action_setting) {
            showSettingsDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    * Implement methods
    * */

    @Override
    public void populateWidgets() {
        clearViewPagerAdapter();
        initializeTabLayout();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        searchView.clearFocus();
        ConnectivityManager connManager = (ConnectivityManager)
                getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connManager.getActiveNetworkInfo();

        if (activeNetworkInfo == null || !activeNetworkInfo.isConnected()) {
            showNoInternetDialog();
            return true;
        }

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        return false;
    }

    @Override
    public void addFood(Food food) {
        //DO NOTHING
    }

    @Override
    public void removeFood(Food food) {
        wrapper.removeFoodToday(food);
        showRemoveFoodToast(food.getName());
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

    private void initializeNoInternetDialog() {
        String title = getResources().getString(R.string.no_internet_dialog_title);
        String description = getResources().getString(R.string.no_internet_dialog_description);

        noInternetDialog = new SweetAlertDialog(this, SweetAlertDialog.WARNING_TYPE);
        noInternetDialog
                .setTitleText(title)
                .setContentText(description)
                .setConfirmText("OK")
                .setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
                    @Override
                    public void onClick(SweetAlertDialog sweetAlertDialog) {
                        sweetAlertDialog.dismiss();
                    }
                });
    }

    private void showNoInternetDialog() {
        if(noInternetDialog == null) {
            initializeNoInternetDialog();
        }

        noInternetDialog.show();
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
                .add(R.string.summary_tab_title, SummaryTabFragment.class)
                .add(R.string.food_tab_title, FoodsFragment.class)
                .create();

        adapter = new FragmentPagerItemAdapter(getSupportFragmentManager(), items);
    }

    private void initializeViewPager() {
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        if(viewPager != null) {
            viewPager.setAdapter(adapter);
        }
    }

    private void initializeTabs() {
        SmartTabLayout viewPagerTab = (SmartTabLayout) findViewById(R.id.viewpager_tab);
        if(viewPagerTab != null) {
            viewPagerTab.setViewPager(viewPager);
        }
    }

    private void clearViewPagerAdapter() {
        viewPager.setAdapter(null);
    }

    private void showRemoveFoodToast(String foodName) {
        Toast.makeText(this, foodName + " removed!", Toast.LENGTH_SHORT).show();
    }

}

package holmusk.com.holmuskchallenge.Activities.MainActivity;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import cn.pedant.SweetAlert.SweetAlertDialog;
import holmusk.com.holmuskchallenge.Activities.MainActivity.Fragments.FirstTimeFragment;
import holmusk.com.holmuskchallenge.Activities.MainActivity.Fragments.MainFragment;
import holmusk.com.holmuskchallenge.Activities.SearchActivity.SearchActivity;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Dialog.SettingsDialogFragment;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

public class MainActivity extends AppCompatActivity implements SettingsDialogFragment.OnSessionDialogListener,
        SearchView.OnQueryTextListener{

    private SettingsPreferences settingsPref;
    private boolean isUsedFirstTime;
    private SweetAlertDialog noInternetDialog;

    private SearchView searchView;
    private MainFragment mainFragment;

    /*
    * Activity methods
    * */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initializeStorage();
        initializeStateVariables();
        setUpActionBar();
        setUpFragments();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.action_search).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(false);
        searchView.setOnQueryTextListener(this);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            showSettingsDialog();
            return true;
        } else if(id == R.id.action_search) {

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == SearchActivity.SEARCH_RESULT) {
            if(resultCode == RESULT_OK) {
                populateWidgets();
            }
        }
    }


    /*
    * Overriden methods
    * */

    @Override
    public void populateWidgets() {
        mainFragment.populateWidgetsAfterSettings();
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



    /*
    * Storage
    * */

    private void initializeStorage() {
        initializeSharedPreferences();
    }

    private void initializeSharedPreferences() {
        settingsPref = SettingsPreferences.getInstance(this);
    }


    /*
    * state variable methods
    * */

    private void initializeStateVariables() {
        isUsedFirstTime = settingsPref.getUsedFirstTime();
    }


    /*
    * Action bar methods
    * */

    private void setUpActionBar() {
        if(isUsedFirstTime) {
            getSupportActionBar().hide();
        } else {
            getSupportActionBar().show();
        }
    }


    /*
    * Dialog
    * */

    private void showSettingsDialog() {
        SettingsDialogFragment dialogFragment = new SettingsDialogFragment();
        dialogFragment.show(getSupportFragmentManager(), SettingsDialogFragment.DIALOG_NAME);
    }


    /*
    * UI methods
    * */

    private void setUpFragments() {
        FirstTimeFragment firstTimeFragment = new FirstTimeFragment();
        mainFragment = new MainFragment();

        if(isUsedFirstTime) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.mainContainer, firstTimeFragment, FirstTimeFragment.TITLE).commit();
        } else {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.mainContainer, mainFragment, FirstTimeFragment.TITLE).commit();
        }
    }


    /*
    * dialog methods
    * */

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

}

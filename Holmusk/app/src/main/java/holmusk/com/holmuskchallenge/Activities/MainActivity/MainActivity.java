package holmusk.com.holmuskchallenge.Activities.MainActivity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import holmusk.com.holmuskchallenge.Activities.MainActivity.Fragments.FirstTimeFragment;
import holmusk.com.holmuskchallenge.Activities.MainActivity.Fragments.MainFragment;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Dialog.SettingsDialogFragment;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

public class MainActivity extends AppCompatActivity implements SettingsDialogFragment.OnSessionDialogListener{


    private SettingsPreferences settingsPref;
    private boolean isUsedFirstTime;

    private FirstTimeFragment firstTimeFragment;
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
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_setting) {
            showSettingsDialog();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    /*
    * Overriden methods
    * */

    @Override
    public void populateWidgets() {
        mainFragment.populateWidgetsAfterSettings();
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
        firstTimeFragment = new FirstTimeFragment();
        mainFragment = new MainFragment();

        if(isUsedFirstTime) {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.mainContainer, firstTimeFragment, FirstTimeFragment.TITLE).commit();
        } else {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.mainContainer, mainFragment, FirstTimeFragment.TITLE).commit();
        }
    }
}

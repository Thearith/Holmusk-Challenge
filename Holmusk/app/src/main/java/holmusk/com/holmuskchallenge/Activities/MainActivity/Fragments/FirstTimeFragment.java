package holmusk.com.holmuskchallenge.Activities.MainActivity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.rey.material.widget.Button;

import holmusk.com.holmuskchallenge.Activities.MainActivity.MainActivity;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Fragments.SettingsFragment;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

/**
 * Created by thearith on 22/4/16.
 */
public class FirstTimeFragment extends Fragment implements View.OnClickListener{

    public static final String TITLE    = "first_time_fragment";

    private SettingsPreferences settingsPref;
    private Button continueButton;

    /*
    * Overriden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_first_time, container, false);

        initializeStorage();
        setUpWidgets(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.continueButton:
                setNotFirstTime();
                goToMainActivity();
                getActivity().finish();
                break;
        }
    }

    private void goToMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }


    /*
    * Storage methods
    * */

    private void initializeStorage() {
        initializeSharedPreferences();
    }

    private void initializeSharedPreferences() {
        settingsPref = SettingsPreferences.getInstance(getActivity());
    }

    private void setNotFirstTime() {
        settingsPref.setUsedFirstTime(!SettingsPreferences.IS_USED_FIRST_TIME);
    }


    /*
    * Widget methods
    * */

    private void setUpWidgets(View view) {
        setUpSettingsFragment();
        setUpContinueButton(view);
    }

    private void setUpSettingsFragment() {
        FragmentManager fragmentManager = getChildFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

        Fragment settingsFragment = new SettingsFragment();
        fragmentTransaction.add(R.id.settingsContainer, settingsFragment);
        fragmentTransaction.commit();
    }

    private void setUpContinueButton(View view) {
        continueButton = (Button) view.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(this);
    }
}

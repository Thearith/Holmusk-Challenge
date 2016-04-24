package holmusk.com.holmuskchallenge.Activities.DailyActivity.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Hashtable;

import holmusk.com.holmuskchallenge.Helpers.DateTime;
import holmusk.com.holmuskchallenge.Models.Nutrient;
import holmusk.com.holmuskchallenge.Models.Settings;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Fragments.DateIntakeProgress;
import holmusk.com.holmuskchallenge.Resources.Fragments.LineChartFragment;
import holmusk.com.holmuskchallenge.Storage.Databases.DatabaseWrapper;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

/**
 * Created by thearith on 25/4/16.
 */

public class SummaryFragment extends Fragment {

    private DatabaseWrapper wrapper;
    private SettingsPreferences settingsPref;

    private String date;
    private Settings settings;
    private Nutrient nutrient;

    private String[] weeklyDates;
    private float[] weeklyCalories;


    /*
    * overridden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_summary, container, false);

        initializeStorage();
        initializeVariables();
        setUpFragments();

        return view;
    }


    /*
    * Storage methods
    * */

    private void initializeStorage() {
        initializeDatabasewrapper();
        initializeSharedPreferences();
    }

    private void initializeDatabasewrapper() {
        wrapper = DatabaseWrapper.getInstance(getActivity());
    }

    private void initializeSharedPreferences() {
        settingsPref = SettingsPreferences.getInstance(getActivity());
    }


    /*
    * variables methods
    * */

    private void initializeVariables() {
        initializeDate();
        initializeSettings();
        initializeNutrients();
        initializeWeeklyCalories();
    }

    private void initializeDate() {
        date = DateTime.getTodayDate();
    }

    private void initializeSettings() {
        settings = settingsPref.getSettings();
    }

    private void initializeNutrients() {
        //TODO: calculate today's nutrients from database
    }

    private void initializeWeeklyCalories() {
        //TODO: calculate weekly calories from database
    }


    /*
    * fragment methods
    * */

    private void setUpFragments() {
        setUpDateIntakeProgress();
        setUpWeeklyCalories();
    }

    private void setUpDateIntakeProgress() {
        Fragment fragment = DateIntakeProgress.newInstance(date, settings, nutrient);
        addFragmentToFragmentManager(fragment);
    }

    private void setUpWeeklyCalories() {
        String chartTitle = getActivity().getResources().getString(R.string.weekly_calories);
        Fragment fragment = LineChartFragment.newInstance(chartTitle, weeklyDates, weeklyCalories);
        addFragmentToFragmentManager(fragment);
    }

    private void addFragmentToFragmentManager(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .add(R.id.summaryContainer, fragment).commit();
    }

}

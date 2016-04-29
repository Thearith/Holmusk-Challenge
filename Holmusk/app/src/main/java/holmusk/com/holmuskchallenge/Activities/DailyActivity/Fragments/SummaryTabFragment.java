package holmusk.com.holmuskchallenge.Activities.DailyActivity.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;

import holmusk.com.holmuskchallenge.Helpers.CalorieCalculator;
import holmusk.com.holmuskchallenge.Helpers.DateTime;
import holmusk.com.holmuskchallenge.Models.Food;
import holmusk.com.holmuskchallenge.Models.FoodIntake;
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

public class SummaryTabFragment extends Fragment {

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
        ArrayList<Food> foods = wrapper.getFoods();
        nutrient = CalorieCalculator.getNutrient(foods);
    }

    private void initializeWeeklyCalories() {
        weeklyDates = DateTime.getWeeklyDatesLabels();

        long[] weekDates = DateTime.getWeeklyDates();
        ArrayList<ArrayList<Food>> foodsList = wrapper.getWeeklyFoods(weekDates);
        weeklyCalories = CalorieCalculator.getWeeklyCalories(foodsList);
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
        addFragmentToFragmentManager(R.id.intakeProgressContainer, fragment);
    }

    private void setUpWeeklyCalories() {
        String chartTitle = getActivity().getResources().getString(R.string.weekly_calories);
        Fragment fragment = LineChartFragment.newInstance(chartTitle, weeklyDates, weeklyCalories);
        addFragmentToFragmentManager(R.id.weeklyCaloriesContainer, fragment);
    }

    private void addFragmentToFragmentManager(int containerId, Fragment fragment) {
        getChildFragmentManager().beginTransaction().add(containerId, fragment).commit();
    }

}

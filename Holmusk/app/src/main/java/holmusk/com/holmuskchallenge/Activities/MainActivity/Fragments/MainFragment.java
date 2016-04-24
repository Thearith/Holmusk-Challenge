package holmusk.com.holmuskchallenge.Activities.MainActivity.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.gigamole.library.ArcProgressStackView;
import com.rey.material.widget.Button;
import com.rey.material.widget.TextView;

import java.util.ArrayList;

import holmusk.com.holmuskchallenge.Activities.DailyActivity.DailyActivity;
import holmusk.com.holmuskchallenge.Helpers.CalorieCalculator;
import holmusk.com.holmuskchallenge.Helpers.DateTime;
import holmusk.com.holmuskchallenge.Models.Settings;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

/**
 * Created by thearith on 22/4/16.
 */

public class MainFragment extends Fragment implements View.OnClickListener{

    public static final String TITLE    = "main_fragment";

    private SettingsPreferences settingsPref;

    private Settings settings;
    private double totalCalorie;

    private TextView dateTextView;
    private TextView totalCalorieTextView;
    private ArcProgressStackView calorieProgressBar;
    private TextView proteinIntakeTextView;
    private TextView fatIntakeTextView;
    private TextView carbIntakeTextView;


    /*
    * Overridden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_main, container, false);

        initializeStorage();
        initializeVariables();
        setUpWidgets(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.checkMoreButton:
                goToDailyActivity();
                break;
        }
    }

    public void populateWidgetsAfterSettings() {
        initializeVariables();
        populateWidgets();
    }

    private void goToDailyActivity() {
        Intent intent = new Intent(getActivity(), DailyActivity.class);
        startActivity(intent);
    }


    /*
    * Storage
    * */

    private void initializeStorage() {
        initializeSharedPreferences();
    }

    private void initializeSharedPreferences() {
        settingsPref = SettingsPreferences.getInstance(getActivity());
    }

    private void initializeVariables() {
        settings = settingsPref.getSettings();
        totalCalorie = CalorieCalculator.calculateCalorieInput(settings);
    }


    /*
    * Widgets
    * */

    private void setUpWidgets(View view) {
        initializeWidgets(view);
        populateWidgets();
    }

    private void initializeWidgets(View view) {
        initializeDateTextView(view);
        initializeTotalCalorieTextView(view);
        initializeProgressBar(view);
        initializeCheckMoreButton(view);
    }

    private void initializeDateTextView(View view) {
        dateTextView = (TextView) view.findViewById(R.id.dateTextView);
    }

    private void initializeTotalCalorieTextView(View view) {
        totalCalorieTextView = (TextView) view.findViewById(R.id.totalCalorieTextView);
    }

    private void initializeProgressBar(View view) {
        calorieProgressBar = (ArcProgressStackView) view.findViewById(R.id.calorieProgressBar);
    }

    private void initializeCheckMoreButton(View view) {
        Button checkMoreButton = (Button) view.findViewById(R.id.checkMoreButton);
        checkMoreButton.setOnClickListener(this);
    }

    private void populateWidgets() {
        populateDateTextView();
        populateTotalCalorieTextView();
        populateCalorieProgressBar();
    }

    private void populateDateTextView() {
        String todayDate = DateTime.getTodayDate();
        dateTextView.setText(todayDate);
    }

    private void populateTotalCalorieTextView() {
        totalCalorieTextView.setText(String.valueOf(totalCalorie));
    }

    private void populateCalorieProgressBar() {
        int percentage = (int) (0.0f / totalCalorie);

        final int bgColor = getActivity().getResources().getColor(R.color.progressBarBgColor);
        final int startColor = getActivity().getResources().getColor(R.color.progressBarStartColor);

        final ArrayList<ArcProgressStackView.Model> models = new ArrayList<>();
        models.add(new ArcProgressStackView.Model("Calorie", 25, bgColor, startColor));

        calorieProgressBar.setModels(models);
    }
}

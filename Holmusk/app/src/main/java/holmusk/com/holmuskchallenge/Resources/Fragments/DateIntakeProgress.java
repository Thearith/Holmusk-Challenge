package holmusk.com.holmuskchallenge.Resources.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.akexorcist.roundcornerprogressbar.TextRoundCornerProgressBar;

import holmusk.com.holmuskchallenge.Helpers.CalorieCalculator;
import holmusk.com.holmuskchallenge.Models.Nutrient;
import holmusk.com.holmuskchallenge.Models.Settings;
import holmusk.com.holmuskchallenge.R;

/**
 * Created by thearith on 23/4/16.
 */

public class DateIntakeProgress extends Fragment {

    // constants
    private static final String DATE        = "date";
    private static final String GENDER      = "gender";
    private static final String AGE         = "age";
    private static final String WEIGHT      = "weight";
    private static final String HEIGHT      = "height";
    private static final String CALORIES    = "calories";
    private static final String PROTEIN     = "protein";
    private static final String FAT         = "fat";
    private static final String CARB        = "carb";
    private static final String CHOLESTEROL = "cholesterol";
    private static final String SUGAR       = "sugar";
    private static final String FIBRE       = "fibre";
    private static final String SODIUM      = "sodium";
    private static final String POTASSIUM   = "potassium";


    private TextView dateTextView;
    private TextRoundCornerProgressBar calorieProgressBar;
    private TextView proteinIntakeTextView;
    private TextView fatIntakeTextView;
    private TextView carbIntakeTextView;

    private String date;
    private Settings settings;
    private Nutrient nutrient;



    /*
    * public methods
    * */

    public static DateIntakeProgress newInstance(String date, Settings settings, Nutrient nutrient) {
        Bundle bundle = getBundle(date, settings, nutrient);
        DateIntakeProgress fragment = new DateIntakeProgress();
        fragment.setArguments(bundle);

        return fragment;
    }



    /*
    * Overridden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_date_intake_progress, container, false);

        Bundle bundle = getArguments();

        initializeVariables(bundle);
        setUpWidgets(view);

        return view;
    }


    /*
    * Bundle arguments
    * */

    private static Bundle getBundle(String date, Settings settings, Nutrient nutrient) {
        Bundle bundle = new Bundle();

        bundle.putString(DATE, date);

        bundle.putBoolean(GENDER, settings.isGender());
        bundle.putInt(AGE, settings.getAge());
        bundle.putFloat(WEIGHT, settings.getWeight());
        bundle.putFloat(HEIGHT, settings.getHeight());

        bundle.putDouble(CALORIES, nutrient.getCalories());
        bundle.putDouble(PROTEIN, nutrient.getProtein());
        bundle.putDouble(FAT, nutrient.getTotalFats());
        bundle.putDouble(CARB, nutrient.getTotalCarbs());
        bundle.putDouble(CHOLESTEROL, nutrient.getCholesterol());
        bundle.putDouble(SUGAR, nutrient.getSugar());
        bundle.putDouble(FIBRE, nutrient.getDietaryFibre());
        bundle.putDouble(SODIUM, nutrient.getSodium());
        bundle.putDouble(POTASSIUM, nutrient.getPotassium());

        return bundle;
    }

    private String extractDate(Bundle bundle) {
        return bundle.getString(DATE);
    }

    private Settings extractSettings(Bundle bundle) {
        boolean gender = bundle.getBoolean(GENDER);
        int age = bundle.getInt(AGE);
        float weight = bundle.getFloat(WEIGHT);
        float height = bundle.getFloat(HEIGHT);

        return new Settings(gender, age, weight, height);
    }

    private Nutrient extractNutrient(Bundle bundle) {
        double calories = bundle.getDouble(CALORIES);
        double protein = bundle.getDouble(PROTEIN);
        double fat = bundle.getDouble(FAT);
        double carbs = bundle.getDouble(CARB);
        double cholesterol = bundle.getDouble(CHOLESTEROL);
        double sugar = bundle.getDouble(SUGAR);
        double fibre = bundle.getDouble(FIBRE);
        double sodium = bundle.getDouble(SODIUM);
        double potassium = bundle.getDouble(POTASSIUM);

        return new Nutrient(calories, protein, fat, carbs, cholesterol, sugar, fibre, sodium, potassium);
    }


    /*
    * Variables method
    * */

    private void initializeVariables(Bundle bundle) {
        initializeDate(bundle);
        initializeSettings(bundle);
        initializeNutrient(bundle);
    }

    private void initializeDate(Bundle bundle) {
        date = extractDate(bundle);
    }

    private void initializeSettings(Bundle bundle) {
        settings = extractSettings(bundle);
    }

    private void initializeNutrient(Bundle bundle) {
        nutrient = extractNutrient(bundle);
    }


    /*
    * Widget methods
    * */

    private void setUpWidgets(View view) {
        initializeWidgets(view);
        populateWidgets();
    }

    private void initializeWidgets(View view) {
        initializeDateTextView(view);
        initializeCalorieProgressBar(view);
        initializeNutrientIntakeWidgets(view);
        setUpNutrientFragment();
    }

    private void initializeDateTextView(View view) {
        dateTextView = (TextView) view.findViewById(R.id.dateTextView);
    }

    private void initializeCalorieProgressBar(View view) {
        calorieProgressBar = (TextRoundCornerProgressBar) view.findViewById(R.id.calorieProgressBar);

        int pbPrimary = getActivity().getResources().getColor(R.color.calorie_pb_primary);
        calorieProgressBar.setProgressColor(pbPrimary);

        int pbSecondary = getActivity().getResources().getColor(R.color.calorie_pb_secondary);
        calorieProgressBar.setSecondaryProgressColor(pbSecondary);

        int pbBg = getActivity().getResources().getColor(R.color.calorie_pb_bg);
        calorieProgressBar.setProgressBackgroundColor(pbBg);

        int pbText = getActivity().getResources().getColor(R.color.calorie_pb_text);
        calorieProgressBar.setTextProgressColor(pbText);

        calorieProgressBar.setTextProgressSize(35);

        calorieProgressBar.setRadius(20);
        calorieProgressBar.setPadding(10);
    }

    private void initializeNutrientIntakeWidgets(View view) {
        proteinIntakeTextView = (TextView) view.findViewById(R.id.proteinIntakeTextView);
        fatIntakeTextView = (TextView) view.findViewById(R.id.fatIntakeTextView);
        carbIntakeTextView = (TextView) view.findViewById(R.id.carbIntakeTextView);
    }

    private void setUpNutrientFragment() {
        NutrientFragment nutrientFragment = NutrientFragment.newInstance(nutrient);

        getChildFragmentManager().beginTransaction()
                .add(R.id.nutrientContainer, nutrientFragment).commit();
    }

    private void populateWidgets() {
        populateDateTextView();
        populateCalorieProgressBar();
        populateNutrientIntakeWidgets();
    }

    private void populateDateTextView() {
        dateTextView.setText(date);
    }

    private void populateCalorieProgressBar() {
        int recommendedCalorie = (int) CalorieCalculator.calculateCalorieInput(settings);
        int totalCalorie = (int) nutrient.getCalories();
        int progress = totalCalorie < recommendedCalorie ?
                (totalCalorie * 100 / recommendedCalorie) : 100;

        calorieProgressBar.setMax(100);
        calorieProgressBar.setSecondaryProgress(100);
        calorieProgressBar.setProgress(progress);

        String calorieText = totalCalorie + " kCal out of " + recommendedCalorie + " kCal";

        calorieProgressBar.setProgressText(calorieText);
    }

    private void populateNutrientIntakeWidgets() {
        proteinIntakeTextView.setText(String.valueOf(nutrient.getProtein()));
        fatIntakeTextView.setText(String.valueOf(nutrient.getTotalFats()));
        carbIntakeTextView.setText(String.valueOf(nutrient.getTotalCarbs()));
    }

}

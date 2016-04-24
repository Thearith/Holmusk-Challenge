package holmusk.com.holmuskchallenge.Resources.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import holmusk.com.holmuskchallenge.Models.FoodNutrient;
import holmusk.com.holmuskchallenge.Models.Nutrient;
import holmusk.com.holmuskchallenge.R;

/**
 * Created by thearith on 25/4/16.
 */

public class FoodFragment extends Fragment {

    private static final String FOOD_NAME   = "food_name";
    private static final String CALORIES    = "calories";
    private static final String PROTEIN     = "protein";
    private static final String FAT         = "fat";
    private static final String CARB        = "carb";
    private static final String CHOLESTEROL = "cholesterol";
    private static final String SUGAR       = "sugar";
    private static final String FIBRE       = "fibre";
    private static final String SODIUM      = "sodium";
    private static final String POTASSIUM   = "potassium";


    private String foodName;
    private Nutrient nutrients;

    private TextView foodNameTextView;
    private TextView totalCalorieTextView;
    private TextView proteinIntakeTextView;
    private TextView fatIntakeTextView;
    private TextView carbIntakeTextView;


    /*
    * public methods
    * */

    public static FoodFragment newInstance(FoodNutrient food) {
        Bundle bundle = getBundle(food);
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    /*
    * Overriden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        Bundle bundle = getArguments();
        initializeVariables(bundle);

        setUpWidgets(view);

        return view;
    }


    /*
    * Bundle arguments
    * */

    private static Bundle getBundle(FoodNutrient food) {
        Bundle bundle = new Bundle();

        String foodName = food.getFoodName();
        Nutrient nutrient = food.getNutrient();

        bundle.putString(FOOD_NAME, foodName);

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

    private String extractFoodName(Bundle bundle) {
        return bundle.getString(FOOD_NAME);
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
    * Variable methods
    * */

    private void initializeVariables(Bundle bundle) {
        initializeFoodName(bundle);
        initializeNutrients(bundle);
    }

    private void initializeFoodName(Bundle bundle) {
        foodName = extractFoodName(bundle);
    }

    private void initializeNutrients(Bundle bundle) {
        nutrients = extractNutrient(bundle);
    }


    /*
    * Widgets
    * */

    private void setUpWidgets(View view) {
        initializeWidgets(view);
        populateWidgets();
    }

    private void initializeWidgets(View view) {
        initializeFoodNameTextView(view);
        initializeCaloriesTextView(view);
        initializeNutrientIntakeWidgets(view);
        setUpNutrientFragment();
    }

    private void initializeFoodNameTextView(View view) {
        foodNameTextView = (TextView) view.findViewById(R.id.foodNameTextView);
    }

    private void initializeCaloriesTextView(View view) {
        totalCalorieTextView = (TextView) view.findViewById(R.id.totalCalorieTextView);
    }

    private void initializeNutrientIntakeWidgets(View view) {
        proteinIntakeTextView = (TextView) view.findViewById(R.id.proteinIntakeTextView);
        fatIntakeTextView = (TextView) view.findViewById(R.id.fatIntakeTextView);
        carbIntakeTextView = (TextView) view.findViewById(R.id.carbIntakeTextView);
    }

    private void setUpNutrientFragment() {
        NutrientFragment nutrientFragment = NutrientFragment.newInstance(nutrients);

        getChildFragmentManager().beginTransaction()
                .add(R.id.nutrientContainer, nutrientFragment).commit();
    }

    private void populateWidgets() {
        populateFoodNameTextView();
        populateTotalCalorieTextView();
        populateNutrientIntakeWidgets();
    }

    private void populateFoodNameTextView() {
        foodNameTextView.setText(foodName);
    }

    private void populateTotalCalorieTextView() {
        totalCalorieTextView.setText(String.valueOf(nutrients.getCalories()));
    }

    private void populateNutrientIntakeWidgets() {
        proteinIntakeTextView.setText(String.valueOf(nutrients.getProtein()));
        fatIntakeTextView.setText(String.valueOf(nutrients.getTotalFats()));
        carbIntakeTextView.setText(String.valueOf(nutrients.getTotalCarbs()));
    }


}

package holmusk.com.holmuskchallenge.Resources.Fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import holmusk.com.holmuskchallenge.Models.Food;
import holmusk.com.holmuskchallenge.Models.Nutrient;
import holmusk.com.holmuskchallenge.R;

/**
 * Created by thearith on 25/4/16.
 */

public class FoodFragment extends Fragment implements View.OnClickListener {

    private static final String TAG             = FoodFragment.class.getSimpleName();

    private static final String ID              = "_id";
    private static final String NAME            = "name";
    private static final String BRAND_NAME      = "brand_name";
    private static final String SOURCE_NAME     = "source";
    private static final String DIETARY_FIBRE   = "dietary_fibre";
    private static final String TOTAL_CARBS     = "total_carbs";
    private static final String SODIUM          = "sodium";
    private static final String POTASSIUM       = "potassium";
    private static final String CALORIES        = "calories";
    private static final String SUGAR           = "sugar";
    private static final String TOTAL_FATS      = "total_fats";
    private static final String CHOLESTEROL     = "cholesterol";
    private static final String PROTEIN         = "protein";
    private static final String HAS_ADD_BTN     = "has_add_button";

    public static final boolean HAD_ADD_BTN_VAL = true;


    private FoodFragmentCallback mCallback;

    private Food food;
    private boolean hasAddButton;

    private TextView foodNameTextView;
    private TextView totalCalorieTextView;
    private TextView proteinIntakeTextView;
    private TextView fatIntakeTextView;
    private TextView carbIntakeTextView;


    public interface FoodFragmentCallback {
        void addFood(Food food);
        void removeFood(Food food);
    }


    /*
    * public methods
    * */

    public static FoodFragment newInstance(Food food, boolean hasAddButton) {
        Bundle bundle = getBundle(food, hasAddButton);
        FoodFragment fragment = new FoodFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    /*
    * Overriden methods
    * */

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            mCallback = (FoodFragmentCallback) context;
        } catch (ClassCastException e) {
            Log.d(TAG, context.toString() + " must implement FoodFragmentCallback");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_food, container, false);

        Bundle bundle = getArguments();
        initializeVariables(bundle);

        setUpWidgets(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        if(v.getId() == R.id.addFoodButton) {
            if(hasAddButton) {
                mCallback.addFood(food);
            } else {
                mCallback.removeFood(food);
            }
        }
    }


    /*
    * Bundle arguments
    * */

    private static Bundle getBundle(Food food, boolean hasAddButton) {
        Bundle bundle = new Bundle();

        bundle.putString(ID, food.getId());
        bundle.putString(NAME, food.getName());
        bundle.putString(BRAND_NAME, food.getBrandName());
        bundle.putString(SOURCE_NAME, food.getSource());

        Nutrient nutrient = food.getNutrient();

        bundle.putDouble(CALORIES, nutrient.getCalories());
        bundle.putDouble(PROTEIN, nutrient.getProtein());
        bundle.putDouble(TOTAL_FATS, nutrient.getTotalFats());
        bundle.putDouble(TOTAL_CARBS, nutrient.getTotalCarbs());
        bundle.putDouble(CHOLESTEROL, nutrient.getCholesterol());
        bundle.putDouble(SUGAR, nutrient.getSugar());
        bundle.putDouble(DIETARY_FIBRE, nutrient.getDietaryFibre());
        bundle.putDouble(SODIUM, nutrient.getSodium());
        bundle.putDouble(POTASSIUM, nutrient.getPotassium());

        bundle.putBoolean(HAS_ADD_BTN, hasAddButton);

        return bundle;
    }

    /*
    * Variable methods
    * */

    private void initializeVariables(Bundle bundle) {
        String id = bundle.getString(ID);
        String foodName = bundle.getString(NAME);
        String brandName = bundle.getString(BRAND_NAME);
        String source = bundle.getString(SOURCE_NAME);

        food = new Food(id, foodName, brandName, source);

        food.setNutrient(CALORIES, bundle.getDouble(CALORIES));
        food.setNutrient(PROTEIN, bundle.getDouble(PROTEIN));
        food.setNutrient(TOTAL_FATS, bundle.getDouble(TOTAL_FATS));
        food.setNutrient(TOTAL_CARBS, bundle.getDouble(TOTAL_CARBS));
        food.setNutrient(CHOLESTEROL, bundle.getDouble(CHOLESTEROL));
        food.setNutrient(SUGAR, bundle.getDouble(SUGAR));
        food.setNutrient(DIETARY_FIBRE, bundle.getDouble(DIETARY_FIBRE));
        food.setNutrient(SODIUM, bundle.getDouble(SODIUM));
        food.setNutrient(POTASSIUM, bundle.getDouble(POTASSIUM));

        hasAddButton = bundle.getBoolean(HAS_ADD_BTN);
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
        initializeAddFoodButton(view);
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

    private void initializeAddFoodButton(View view) {
        ImageButton addFoodButton = (ImageButton) view.findViewById(R.id.addFoodButton);
        addFoodButton.setOnClickListener(this);

        int drawableRes = hasAddButton ? R.drawable.ic_plus : R.drawable.ic_minus;
        addFoodButton.setImageResource(drawableRes);
        if(!hasAddButton) {
            addFoodButton.setVisibility(View.GONE);
        }
    }

    private void setUpNutrientFragment() {
        Nutrient nutrients = food.getNutrient();
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
        String foodName = food.getName();
        foodNameTextView.setText(foodName);
    }

    private void populateTotalCalorieTextView() {
        Nutrient nutrients = food.getNutrient();
        totalCalorieTextView.setText(String.valueOf(nutrients.getCalories()));
    }

    private void populateNutrientIntakeWidgets() {
        Nutrient nutrients = food.getNutrient();

        proteinIntakeTextView.setText(String.valueOf(nutrients.getProtein()));
        fatIntakeTextView.setText(String.valueOf(nutrients.getTotalFats()));
        carbIntakeTextView.setText(String.valueOf(nutrients.getTotalCarbs()));
    }


}

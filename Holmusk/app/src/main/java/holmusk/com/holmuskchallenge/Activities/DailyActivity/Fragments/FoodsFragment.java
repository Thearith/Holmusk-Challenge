package holmusk.com.holmuskchallenge.Activities.DailyActivity.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import holmusk.com.holmuskchallenge.Models.FoodNutrient;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Fragments.FoodFragment;
import holmusk.com.holmuskchallenge.Storage.Databases.DatabaseWrapper;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

/**
 * Created by thearith on 24/4/16.
 */

public class FoodsFragment extends Fragment {

    private DatabaseWrapper wrapper;

    private ArrayList<FoodNutrient> foods;

    private TextView numFoodsTextView;


    /*
    * overridden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_foods, container, false);

        initializeStorage();
        initializeVariables();
        setUpFragments(view);

        return view;
    }


    /*
    * Storage methods
    * */

    private void initializeStorage() {
        initializeDatabasewrapper();
    }

    private void initializeDatabasewrapper() {
        wrapper = DatabaseWrapper.getInstance(getActivity());
    }


    /*
    * variables methods
    * */

    private void initializeVariables() {
        initializeFoods();
    }

    private void initializeFoods() {
        //TODO: get a list of foods from database
    }


    /*
    * widget methods
    * */

    private void setUpFragments(View view) {
        initializeWidgets(view);
        populateWidgets();
    }

    private void initializeWidgets(View view) {
        initializeNumFoodsTextView(view);
        setUpFoodFragments();
    }

    private void initializeNumFoodsTextView(View view) {
        numFoodsTextView = (TextView) view.findViewById(R.id.numFoodsTextView);
    }

    private void setUpFoodFragments() {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        for(FoodNutrient food : foods) {
            FoodFragment foodFragment = FoodFragment.newInstance(food);
            transaction.add(R.id.foodsContainer, foodFragment);
        }

        transaction.commit();
    }

    private void populateWidgets() {
        populateNumFoodsTextView();
    }

    private void populateNumFoodsTextView() {
        String numFoodFormat = getActivity().getResources().getString(R.string.num_foods);
        String numFoods = String.format(numFoodFormat, foods.size());
        numFoodsTextView.setText(numFoods);
    }


}

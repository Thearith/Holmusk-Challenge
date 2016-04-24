package holmusk.com.holmuskchallenge.Resources.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import holmusk.com.holmuskchallenge.Models.Nutrient;
import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.SlideAnimation;

/**
 * Created by thearith on 24/4/16.
 */

public class NutrientFragment extends Fragment implements View.OnClickListener {

    // constants
    public static final String CHOLESTEROL  = "cholesterol";
    public static final String SUGAR        = "sugar";
    public static final String FIBRE        = "fibre";
    public static final String SODIUM       = "sodium";
    public static final String POTASSIUM    = "potassium";


    private Nutrient nutrient;

    private LinearLayout clickToExpand;
    private LinearLayout expandContainer;

    private TextView cholesterolTextView;
    private TextView sugarTextView;
    private TextView fibreTextView;
    private TextView sodiumTextView;
    private TextView potassiumTextView;


    /*
    * public methods
    * */

    public static NutrientFragment newInstance(Nutrient nutrient) {
        Bundle bundle = getNutrientBundle(nutrient);
        NutrientFragment fragment = new NutrientFragment();
        fragment.setArguments(bundle);

        return fragment;
    }


    /*
    * Overridden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_nutrient, container, false);

        Bundle bundle = getArguments();
        initializeVariables(bundle);
        setUpWidgets(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.clickToExpand:
                expandWidgets();
                break;

            case R.id.clickToShrink:
                shrinkWidgets();
                break;
        }
    }


    /*
    * variable methods
    * */

    private void initializeVariables(Bundle bundle) {
        nutrient = extractNutrient(bundle);
    }


    /*
    * Bundle methods
    * */

    private static Bundle getNutrientBundle(Nutrient nutrient) {
        Bundle bundle = new Bundle();
        bundle.putDouble(CHOLESTEROL, nutrient.getCholesterol());
        bundle.putDouble(SUGAR, nutrient.getSugar());
        bundle.putDouble(FIBRE, nutrient.getDietaryFibre());
        bundle.putDouble(SODIUM, nutrient.getSodium());
        bundle.putDouble(POTASSIUM, nutrient.getPotassium());

        return bundle;
    }

    private Nutrient extractNutrient(Bundle bundle) {
        double cholesterol = bundle.getDouble(CHOLESTEROL);
        double sugar = bundle.getDouble(SUGAR);
        double fibre = bundle.getDouble(FIBRE);
        double sodium = bundle.getDouble(SODIUM);
        double potassium = bundle.getDouble(POTASSIUM);

        return new Nutrient(cholesterol, sugar, fibre, sodium, potassium);
    }


    /*
    * Widget methods
    * */

    private void expandWidgets() {
        clickToExpand.setVisibility(View.GONE);

        expandContainer.setVisibility(View.VISIBLE);
        SlideAnimation.slideDown(getActivity(), expandContainer);
    }

    private void shrinkWidgets() {
        expandContainer.setVisibility(View.GONE);
        SlideAnimation.slideUp(getActivity(), expandContainer);

        clickToExpand.setVisibility(View.VISIBLE);
    }

    private void setUpWidgets(View view) {
        initializeWidgets(view);
        populateWidgets();
    }

    private void initializeWidgets(View view) {
        initializeClickToExpand(view);
        initializeExpandContainer(view);
        initializeExpandDetails(view);
        initializeClickToShrink(view);
    }

    private void initializeClickToExpand(View view) {
        clickToExpand = (LinearLayout) view.findViewById(R.id.clickToExpand);
        clickToExpand.setOnClickListener(this);
    }

    private void initializeExpandContainer(View view) {
        expandContainer = (LinearLayout) view.findViewById(R.id.expandContainer);
    }

    private void initializeExpandDetails(View view) {
        cholesterolTextView = (TextView) view.findViewById(R.id.cholesterolTextView);
        sugarTextView = (TextView) view.findViewById(R.id.sugarTextView);
        fibreTextView = (TextView) view.findViewById(R.id.dietaryFibreTextView);
        sodiumTextView = (TextView) view.findViewById(R.id.sodiumTextView);
        potassiumTextView = (TextView) view.findViewById(R.id.potassiumTextView);
    }

    private void initializeClickToShrink(View view) {
        LinearLayout clickToShrink = (LinearLayout) view.findViewById(R.id.clickToShrink);
        clickToShrink.setOnClickListener(this);
    }

    private void populateWidgets() {
        populateExpandDetails();
    }

    private void populateExpandDetails() {
        cholesterolTextView.setText(String.valueOf(nutrient.getCholesterol()));
        sugarTextView.setText(String.valueOf(nutrient.getSugar()));
        fibreTextView.setText(String.valueOf(nutrient.getDietaryFibre()));
        sodiumTextView.setText(String.valueOf(nutrient.getSodium()));
        potassiumTextView.setText(String.valueOf(nutrient.getPotassium()));
    }
}

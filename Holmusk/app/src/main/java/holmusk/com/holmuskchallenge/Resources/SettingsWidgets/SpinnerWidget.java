package holmusk.com.holmuskchallenge.Resources.SettingsWidgets;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.rey.material.widget.Spinner;

import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

/**
 * Created by thearith on 22/4/16.
 */
public class SpinnerWidget extends Fragment implements Spinner.OnItemClickListener{

    private static final String SETTINGS_TYPE   = "settings_type";
    private static final int MALE_POS           = 0;
    private static final int FEMALE_POS         = 1;

    // storage
    private SettingsPreferences settingsPrefs;

    // bundle variables
    private int settingsType;

    // widgets
    private TextView inputLabel;
    private TextView inputSecondaryText;
    private Spinner inputSpinner;


    /*
    * public static methods
    * */

    public static SpinnerWidget newInstance(int settingType) {
        Bundle bundle = new Bundle();
        bundle.putInt(SETTINGS_TYPE, settingType);

        SpinnerWidget fragment = new SpinnerWidget();
        fragment.setArguments(bundle);

        return fragment;
    }

    /*
    * Overriden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_spinner, container, false);

        Bundle bundle = getArguments();
        initializeBundleArguments(bundle);

        initalizeStorage();
        setUpWidgets(view);

        return view;
    }

    @Override
    public boolean onItemClick(Spinner parent, View view, int position, long id) {
        onValueChangeRecord(position);
        return true;
    }

    /*
    * bundle arguments
    * */

    private void initializeBundleArguments(Bundle bundle) {
        settingsType = bundle.getInt(SETTINGS_TYPE);
    }


    /*
    * Storage methods
    * */

    private void initalizeStorage() {
        initializeSharedPreferences();
    }

    private void initializeSharedPreferences() {
        settingsPrefs = SettingsPreferences.getInstance(getActivity());
    }

    private void onValueChangeRecord(int position) {
        switch(settingsType) {
            case SettingsPreferences.GENDER_TYPE:
                onValueChangeGender(position);
                break;
        }
    }

    private void onValueChangeGender(int position) {
        boolean gender = position == MALE_POS;
        settingsPrefs.setGender(gender);
    }

    private int getSpinnerValue() {
        switch(settingsType) {
            case SettingsPreferences.GENDER_TYPE:
                return getGenderSpinnerValue();
        }

        return 0;
    }

    private int getGenderSpinnerValue() {
        boolean gender = settingsPrefs.getGender();
        return gender ? MALE_POS : FEMALE_POS;
    }


    /*
    * widget methods
    * */

    private void setUpWidgets(View view) {
        initiailizeWidgets(view);
        populateWidgets();
    }

    private void initiailizeWidgets(View view) {
        initializeInputLabel(view);
        initializeInputSecondaryText(view);
        initializeInputSpinner(view);
    }

    private void initializeInputLabel(View view) {
        inputLabel = (TextView) view.findViewById(R.id.inputLabel);
    }

    private void initializeInputSecondaryText(View view) {
        inputSecondaryText = (TextView) view.findViewById(R.id.inputSecondaryText);
    }

    private void initializeInputSpinner(View view) {
        inputSpinner = (Spinner) view.findViewById(R.id.inputSpinner);
        inputSpinner.setOnItemClickListener(this);
    }

    private void populateWidgets() {
        populateLabel();
        populateSecondaryText();
        populateInputSpinner();
    }

    private void populateLabel() {
        String label = getLabel();
        inputLabel.setText(label);
    }

    private void populateSecondaryText() {
        String secondaryText = getSecondaryText();
        inputSecondaryText.setText(secondaryText);
    }

    private void populateInputSpinner() {
        String[] spinnerTexts = getSpinnerTexts();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.settings_spinner_row,
                spinnerTexts);
        adapter.setDropDownViewResource(R.layout.settings_spinner_dropdown);
        inputSpinner.setAdapter(adapter);

        int position = getSpinnerValue();
        inputSpinner.setSelection(position);
    }



    /*
    * Constant helper method
    * */

    private String getLabel() {
        String[] labels = getActivity().getResources().getStringArray(R.array.spinner_label);
        return labels[settingsType];
    }

    private String getSecondaryText() {
        String[] secondaryTexts = getActivity().getResources().
                getStringArray(R.array.spinner_secondary_text);
        return secondaryTexts[settingsType];
    }

    private String[] getSpinnerTexts() {
        return SettingsPreferences.SPINNER_TEXTS[settingsType];
    }
}

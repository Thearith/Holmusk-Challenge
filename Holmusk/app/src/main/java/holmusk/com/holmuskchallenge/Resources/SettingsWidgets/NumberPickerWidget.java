package holmusk.com.holmuskchallenge.Resources.SettingsWidgets;

import android.app.AlertDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;

import com.rey.material.widget.Button;

import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

/**
 * Created by thearith on 22/4/16.
 */

public class NumberPickerWidget extends Fragment implements View.OnClickListener{

    private static final String SETTINGS_TYPE   = "settings_type";
    private static final String LAST_FRAGMENT   = "last_fragment";
    private static final int MINIMUM            = 0;
    private static final int MAXIMUM            = 1;

    // storage
    private SettingsPreferences settingsPrefs;

    // bundle variables
    private int settingsType;
    private boolean isLastFragment;

    // widgets
    private TextView inputLabel;
    private TextView inputSecondaryText;
    private TextView inputValue;

    // dialog
    private AlertDialog dialog;
    private NumberPicker inputNumberPicker;


    /*
    * public static methods
    * */

    public static NumberPickerWidget newInstance(int settingType, boolean lastFragment) {
        Bundle bundle = new Bundle();
        bundle.putInt(SETTINGS_TYPE, settingType);
        bundle.putBoolean(LAST_FRAGMENT, lastFragment);

        NumberPickerWidget fragment = new NumberPickerWidget();
        fragment.setArguments(bundle);

        return fragment;
    }


    /*
    * Overriden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_number_picker, container, false);

        Bundle bundle = getArguments();
        initializeBundleArguments(bundle);

        initalizeStorage();
        setUpWidgets(view);

        return view;
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()) {
            case R.id.settingsWidget:
                showNumberPickerDialog();
                break;
        }
    }


    /*
    * Bundle arguments
    * */

    private void initializeBundleArguments(Bundle bundle) {
        settingsType = bundle.getInt(SETTINGS_TYPE);
        isLastFragment = bundle.getBoolean(LAST_FRAGMENT);
    }


    /*
    * Storage
    * */

    private void initalizeStorage() {
        initializeSharedPreferences();
    }

    private void initializeSharedPreferences() {
        settingsPrefs = SettingsPreferences.getInstance(getActivity());
    }

    private void onValueChangeRecord(int value) {
        switch(settingsType) {
            case SettingsPreferences.AGE_TYPE:
                settingsPrefs.setAge(value);
                break;

            case SettingsPreferences.WEIGHT_TYPE:
                settingsPrefs.setWeight(value);
                break;

            case SettingsPreferences.HEIGHT_TYPE:
                settingsPrefs.setHeight(value);
                break;

            default:
                break;
        }
    }

    private int getNumberPickerValue() {
        switch(settingsType) {
            case SettingsPreferences.AGE_TYPE:
                return settingsPrefs.getAge();
            case SettingsPreferences.WEIGHT_TYPE:
                return (int) settingsPrefs.getWeight();
            case SettingsPreferences.HEIGHT_TYPE:
                return (int) settingsPrefs.getHeight();
        }

        return 0;
    }


    /*
    * Dialog
    * */

    private void initializeNumberPickerDialog() {
        dialog = createAlertDialog();
    }

    private AlertDialog createAlertDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        View alertDialogView = View.inflate(getActivity(), R.layout.settings_number_picker_dialog, null);

        // textviews
        TextView dialogTitle = (TextView) alertDialogView.findViewById(R.id.dialogTitle);
        String dialogTitleString = getLabel();
        dialogTitle.setText(dialogTitleString);

        TextView secondaryText = (TextView) alertDialogView.findViewById(R.id.seekbarLabel);
        String secondaryTextString = getSecondaryText();
        secondaryText.setText(secondaryTextString);

        // number picker
        initializeNumberPicker(alertDialogView);
        populateNumberPicker();

        // buttons
        Button dialogOKButton = (Button) alertDialogView.findViewById(R.id.dialogOKButton);
        dialogOKButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int value = inputNumberPicker.getValue();
                onValueChangeRecord(value);
                populateInputValue();

                dialog.dismiss();
            }
        });


        builder.setCancelable(false);
        builder.setView(alertDialogView);

        return builder.create();
    }

    private void showNumberPickerDialog() {
        initializeNumberPickerDialog();
        dialog.show();
    }


    /*
    * Widget methods
    * */

    private void setUpWidgets(View view) {
        initializeWidgets(view);
        populateWidgets();
    }

    private void initializeWidgets(View view) {
        initializeSettingsWidget(view);

        initializeInputLabel(view);
        initializeInputSecondaryText(view);
        initializeInputValue(view);
    }

    private void initializeSettingsWidget(View view) {
        LinearLayout settingsWidget = (LinearLayout) view.findViewById(R.id.settingsWidget);
        settingsWidget.setOnClickListener(this);

        if(isLastFragment) {
            settingsWidget.setBackground(null);
        }
    }

    private void initializeInputLabel(View view) {
        inputLabel = (TextView) view.findViewById(R.id.inputLabel);
    }

    private void initializeInputSecondaryText(View view) {
        inputSecondaryText = (TextView) view.findViewById(R.id.inputSecondaryText);
    }

    private void initializeInputValue(View view) {
        inputValue = (TextView) view.findViewById(R.id.inputValue);
    }

    private void populateWidgets() {
        populateLabel();
        populateSecondaryText();
        populateInputValue();
    }

    private void populateLabel() {
        String label = getLabel();
        inputLabel.setText(label);
    }

    private void populateSecondaryText() {
        String secondaryText = getSecondaryText();
        inputSecondaryText.setText(secondaryText);
    }

    private void populateInputValue() {
        int value = getNumberPickerValue();
        inputValue.setText(String.valueOf(value));
    }

    /*
    * Number picker methods
    * */

    private void initializeNumberPicker(View view) {
        inputNumberPicker = (NumberPicker) view.findViewById(R.id.inputNumberPicker);
    }

    private void populateNumberPicker() {
        setNumberPickerMinValue();
        setNumberPickerMaxValue();
        setNumberPickerValue();
    }

    private void setNumberPickerValue() {
        int value = getNumberPickerValue();
        inputNumberPicker.setValue(value);
    }

    private void setNumberPickerMinValue() {
        int min = getNumberPickerMinimumValue();
        inputNumberPicker.setMinValue(min);
    }

    private void setNumberPickerMaxValue() {
        int max = getNumberPickerMaximumValue();
        inputNumberPicker.setMaxValue(max);
    }


    /*
    * Constant helper methods
    * */

    private String getLabel() {
        String[] labels = getActivity().getResources().getStringArray(R.array.number_picker_label);
        return labels[settingsType];
    }

    private String getSecondaryText() {
        String[] secondaryTexts = getActivity().getResources()
                .getStringArray(R.array.number_picker_secondary_text);
        return secondaryTexts[settingsType];
    }

    private int getNumberPickerMinimumValue() {
        return SettingsPreferences.SETTINGS_RANGE[settingsType][MINIMUM];
    }

    private int getNumberPickerMaximumValue() {
        return SettingsPreferences.SETTINGS_RANGE[settingsType][MAXIMUM];
    }


}

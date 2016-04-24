package holmusk.com.holmuskchallenge.Resources.Fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.SettingsWidgets.NumberPickerWidget;
import holmusk.com.holmuskchallenge.Resources.SettingsWidgets.SpinnerWidget;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

/**
 * Created by thearith on 22/4/16.
 */
public class SettingsFragment extends Fragment{

    /*
    * Fragment overridden methods
    * */

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_settings, container, false);

        setUpFragments();

        return view;
    }


    /*
    * fragment methods
    * */

    private void setUpFragments() {
        setUpSpinnerFragments();
        setUpNumberPickerFragments();
    }

    private void setUpSpinnerFragments() {
        for(int type : SettingsPreferences.SPINNER_TYPE) {
            Fragment spinner = createSpinner(type);
            addFragmentToFrameLayout(spinner);
        }
    }

    private void setUpNumberPickerFragments() {
        for(int type : SettingsPreferences.NUMBER_PICKER_TYPE) {
            boolean isLastFragment = type == SettingsPreferences.NUMBER_PICKER_TYPE.length - 1;
            Fragment numberPicker = createNumberPicker(type, isLastFragment);
            addFragmentToFrameLayout(numberPicker);
        }
    }

    private Fragment createSpinner(int settingType) {
        return SpinnerWidget.newInstance(settingType);
    }

    private Fragment createNumberPicker(int settingsType, boolean isLastFragment) {
        return NumberPickerWidget.newInstance(settingsType, isLastFragment);
    }

    private void addFragmentToFrameLayout(Fragment fragment) {
        getChildFragmentManager().beginTransaction()
                .add(R.id.settingsGroupContainer, fragment).commit();
    }
}

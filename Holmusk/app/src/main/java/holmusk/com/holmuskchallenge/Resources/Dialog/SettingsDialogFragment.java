package holmusk.com.holmuskchallenge.Resources.Dialog;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.LinearLayout;

import com.rey.material.widget.Button;

import holmusk.com.holmuskchallenge.R;
import holmusk.com.holmuskchallenge.Resources.Fragments.SettingsFragment;

/**
 * Created by thearith on 23/4/16.
 */

public class SettingsDialogFragment extends DialogFragment implements View.OnClickListener {

    public static final String DIALOG_NAME  = "settings_dialog_fragment";

    // callbacks
    private OnSessionDialogListener mCallback;

    public interface OnSessionDialogListener {
        void populateWidgets();
    }

    /*
    * Fragment overriden methods
    * */

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);

        try {
            mCallback = (OnSessionDialogListener) activity;
        } catch (ClassCastException e) {
            throw new ClassCastException(activity.toString()
                    + " must implement OnSessionDialogListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.settings_dialog, container, false);

        getDialog().getWindow().requestFeature(Window.FEATURE_NO_TITLE);
        setUpWidgets(view);

        return view;
    }

    @Override
    public void onResume() {
        setUpDialogDisplay();
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.continueButton:
                mCallback.populateWidgets();
                getDialog().dismiss();
                break;
        }
    }

    private void setUpDialogDisplay() {
        ViewGroup.LayoutParams params = getDialog().getWindow().getAttributes();
        params.width = LinearLayout.LayoutParams.MATCH_PARENT;
        params.height = LinearLayout.LayoutParams.WRAP_CONTENT;
        getDialog().getWindow().setAttributes((android.view.WindowManager.LayoutParams) params);
    }


    /*
    * widget methods
    * */

    private void setUpWidgets(View view) {
        setUpSettingsFragment();
        initializeContinueButton(view);
    }

    private void setUpSettingsFragment() {
        Fragment settingsFragment = new SettingsFragment();

        getChildFragmentManager().beginTransaction()
                .add(R.id.settingsContainer, settingsFragment).commit();
    }

    private void initializeContinueButton(View view) {
        Button continueButton = (Button) view.findViewById(R.id.continueButton);
        continueButton.setOnClickListener(this);
    }
}

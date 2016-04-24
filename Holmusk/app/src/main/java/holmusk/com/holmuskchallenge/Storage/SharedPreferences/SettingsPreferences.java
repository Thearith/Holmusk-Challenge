package holmusk.com.holmuskchallenge.Storage.SharedPreferences;

import android.content.Context;
import android.content.SharedPreferences;

import holmusk.com.holmuskchallenge.Models.Settings;

/**
 * Created by thearith on 22/4/16.
 */
public class SettingsPreferences {

    // constants
    public static final String SETTINGS_PREFERENCES = "settings_preferences";

    public static final String USED_FIRST_TIME      = "used_first_time";
    public static final String GENDER               = "gender";
    public static final String AGE                  = "age";
    public static final String WEIGHT               = "weight";
    public static final String HEIGHT               = "height";

    public static final boolean IS_USED_FIRST_TIME  = true;
    public static final boolean IS_MALE             = true;
    public static final boolean IS_FEMALE           = !IS_MALE;

    private static final boolean GENDER_VALUE       = IS_MALE;
    private static final int AGE_VALUE              = 20;
    private static final int WEIGHT_VALUE           = 75;
    private static final int HEIGHT_VALUE           = 175;


    public static final int GENDER_TYPE             = 0;
    public static final int[] SPINNER_TYPE = {
            GENDER_TYPE
    };
    public static final String[][] SPINNER_TEXTS    = {
            { "Male", "Female"}
    };

    public static final int AGE_TYPE                = 0;
    public static final int WEIGHT_TYPE             = 1;
    public static final int HEIGHT_TYPE             = 2;
    public static final int[] NUMBER_PICKER_TYPE    = {
            AGE_TYPE,
            WEIGHT_TYPE,
            HEIGHT_TYPE
    };
    public static final int[][] SETTINGS_RANGE = {
            {10, 100},
            {20, 200},
            {100, 250}
    };



    // variables
    private static SettingsPreferences settingsPreferences;
    private SharedPreferences prefs;


    /*
    * Constructors
    * */

    public static SettingsPreferences getInstance(Context context) {
        if(settingsPreferences == null) {
            settingsPreferences = new SettingsPreferences(context);
        }

        return settingsPreferences;
    }

    private SettingsPreferences(Context context) {
        prefs = context.getSharedPreferences(SETTINGS_PREFERENCES, Context.MODE_PRIVATE);
    }



    /*
    * public methods
    * */

    public void setSettings(Settings settings) {
        setUsedFirstTime(!IS_USED_FIRST_TIME);
        setGender(settings.isGender());
        setAge(settings.getAge());
        setHeight(settings.getHeight());
        setWeight(settings.getWeight());
    }

    public Settings getSettings() {
        boolean gender = getGender();
        int age = getAge();
        float height = getHeight();
        float weight = getWeight();

        return new Settings(gender, age, weight, height);
    }

    public boolean getUsedFirstTime() {
        return prefs.getBoolean(USED_FIRST_TIME, IS_USED_FIRST_TIME);
    }


    /*
    * Getters and Setters
    * */

    public void setUsedFirstTime(boolean isUsedFirstTime) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(USED_FIRST_TIME, isUsedFirstTime);
        editor.commit();
    }

    public void setGender(boolean gender) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putBoolean(GENDER, gender);
        editor.commit();
    }

    public void setAge(int age) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(AGE, age);
        editor.commit();
    }

    public void setHeight(float height) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(HEIGHT, height);
        editor.commit();
    }

    public void setWeight(float weight) {
        SharedPreferences.Editor editor = prefs.edit();
        editor.putFloat(WEIGHT, weight);
        editor.commit();
    }


    public boolean getGender() {
        return prefs.getBoolean(GENDER, GENDER_VALUE);
    }

    public int getAge() {
        return prefs.getInt(AGE, AGE_VALUE);
    }

    public float getWeight() {
        return prefs.getFloat(WEIGHT, WEIGHT_VALUE);
    }

    public float getHeight() {
        return prefs.getFloat(HEIGHT, HEIGHT_VALUE);
    }
}

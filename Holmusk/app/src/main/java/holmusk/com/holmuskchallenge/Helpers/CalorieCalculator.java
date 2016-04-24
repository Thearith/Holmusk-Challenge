package holmusk.com.holmuskchallenge.Helpers;

import holmusk.com.holmuskchallenge.Models.Settings;
import holmusk.com.holmuskchallenge.Storage.SharedPreferences.SettingsPreferences;

/**
 * Created by thearith on 22/4/16.
 */

public class CalorieCalculator {

    public static double calculateCalorieInput(Settings settings) {
        boolean gender = settings.isGender();
        double weight = settings.getWeight();
        double height = settings.getHeight();
        int age = settings.getAge();

        if(gender == SettingsPreferences.IS_MALE) {
            return 10 * weight + 6.25 * height - 5 * age + 5;
        } else {
            return 10 * weight + 6.25 * height - 5 * age - 161;
        }

    }
}

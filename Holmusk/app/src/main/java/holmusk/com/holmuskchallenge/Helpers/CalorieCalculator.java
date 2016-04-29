package holmusk.com.holmuskchallenge.Helpers;

import java.util.ArrayList;

import holmusk.com.holmuskchallenge.Models.Food;
import holmusk.com.holmuskchallenge.Models.Nutrient;
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

    public static Nutrient getNutrient(ArrayList<Food> foods) {
        double calories = extractCalories(foods);
        double protein = extractProtein(foods);
        double fat = extractFat(foods);
        double carbs = extractCarbs(foods);
        double cholesterol = extractCholesterol(foods);
        double sugar = extractSugar(foods);
        double fibre = extractFibre(foods);
        double sodium = extractSodium(foods);
        double potassium = extractPotassium(foods);

        return new Nutrient(calories, protein, fat, carbs, cholesterol, sugar, fibre, sodium, potassium);
    }

    public static Nutrient getNutrient(Food food) {
        double calories = food.getCalories();
        double protein = food.getProtein();
        double fat = food.getTotalFats();
        double carbs = food.getTotalCarbs();
        double cholesterol = food.getCholesterol();
        double sugar = food.getSugar();
        double fibre = food.getDietaryFibre();
        double sodium = food.getSodium();
        double potassium = food.getPotassium();

        return new Nutrient(calories, protein, fat, carbs, cholesterol, sugar, fibre, sodium, potassium);
    }

    public static float[] getWeeklyCalories(ArrayList<ArrayList<Food>> foodsList) {
        float[] calories = new float[7];
        for(int i=0; i<foodsList.size(); i++) {
            ArrayList<Food> foods = foodsList.get(i);
            calories[i] = (float) extractCalories(foods);
        }

        return calories;
    }


    /*
    * Extracting nutrients, private methods
    * */

    private static double extractCalories(ArrayList<Food> foods) {
        double calories = 0.0f;
        for(Food food : foods) {
            calories += food.getCalories();
        }

        return calories;
    }

    private static double extractProtein(ArrayList<Food> foods) {
        double protein = 0.0f;
        for(Food food : foods) {
            protein += food.getProtein();
        }

        return protein;
    }

    private static double extractFat(ArrayList<Food> foods) {
        double fat = 0.0f;
        for(Food food : foods) {
            fat += food.getTotalFats();
        }

        return fat;
    }

    private static double extractCarbs(ArrayList<Food> foods) {
        double carbs = 0.0f;
        for(Food food : foods) {
            carbs += food.getTotalCarbs();
        }

        return carbs;
    }

    private static double extractCholesterol(ArrayList<Food> foods) {
        double cholesterol = 0.0f;
        for(Food food : foods) {
            cholesterol += food.getCholesterol();
        }

        return cholesterol;
    }

    private static double extractSugar(ArrayList<Food> foods) {
        double sugar = 0.0f;
        for(Food food : foods) {
            sugar += food.getSugar();
        }

        return sugar;
    }

    private static double extractFibre(ArrayList<Food> foods) {
        double fibre = 0.0f;
        for(Food food : foods) {
            fibre += food.getDietaryFibre();
        }

        return fibre;
    }

    private static double extractSodium(ArrayList<Food> foods) {
        double sodium = 0.0f;
        for(Food food : foods) {
            sodium += food.getSodium();
        }

        return sodium;
    }

    private static double extractPotassium(ArrayList<Food> foods) {
        double potassium = 0.0f;
        for(Food food : foods) {
            potassium += food.getPotassium();
        }

        return potassium;
    }

}

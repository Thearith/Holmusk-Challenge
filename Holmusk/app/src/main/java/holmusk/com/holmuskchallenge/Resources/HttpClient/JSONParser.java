package holmusk.com.holmuskchallenge.Resources.HttpClient;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import holmusk.com.holmuskchallenge.Models.Food;

/**
 * Created by thearith on 28/4/16.
 */

public class JSONParser {

    // constants
    private static final String TAG                 = JSONParser.class.getSimpleName();

    private static final String ID                  = "_id";
    private static final String PORTIONS            = "portions";
    private static final String NAME                = "name";
    private static final String BRAND_NAME          = "brand_name";
    private static final String SOURCE_NAME         = "source";

    private static final String IMPORTANT           = "important";
    private static final String NUTRIENT            = "nutrients";
    private static final String DIETARY_FIBRE       = "dietary_fibre";
    private static final String TOTAL_CARBS         = "total_carbs";
    private static final String SODIUM              = "sodium";
    private static final String POTASSIUM           = "potassium";
    private static final String CALORIES            = "calories";
    private static final String SUGAR               = "sugar";
    private static final String TOTAL_FATS          = "total_fats";
    private static final String CHOLESTEROL         = "cholesterol";
    private static final String PROTEIN             = "protein";

    private static final String VALUE               = "value";


    private static final String ONE_SERVING         = "1 serving";
    private static final String PER_SERVING         = "Per Serving";
    private static final String ONE_SLICE           = "1 slice";


    public static ArrayList<Food> convertToFood (String responseBody){
        ArrayList<Food> foods = new ArrayList<>();

        try {
            JSONArray jsonArray = new JSONArray(responseBody);
            for(int i=0; i<jsonArray.length(); i++) {
                JSONObject object = jsonArray.getJSONObject(i);
                String id = object.has(ID)? object.getString(ID) : null;
                String name = object.has(NAME)? object.getString(NAME) : null;
                String brandName = object.has(BRAND_NAME)? object.getString(BRAND_NAME) : null;
                String sourceName = object.has(SOURCE_NAME)? object.getString(SOURCE_NAME) : null;

                Food food = new Food(id, name, brandName, sourceName);

                JSONArray portions = object.getJSONArray(PORTIONS);
                for(int j=0; j<portions.length(); j++) {
                    JSONObject portion = portions.getJSONObject(j);
                    String portionName = portion.getString(NAME);

                    if(portionName.contains(ONE_SERVING) || portionName.contains(PER_SERVING) ||
                            portionName.contains(ONE_SLICE)) {

                        JSONObject nutrient = portion.getJSONObject(NUTRIENT);
                        JSONObject important = nutrient.getJSONObject(IMPORTANT);


                        double fibreValue = !important.isNull(DIETARY_FIBRE) ?
                                important.getJSONObject(DIETARY_FIBRE).getDouble(VALUE) : 0;
                        food.setNutrient(DIETARY_FIBRE, fibreValue);


                        JSONObject carbs = !important.isNull(TOTAL_CARBS) ?
                                important.getJSONObject(TOTAL_CARBS) : null;
                        double carbsValue = carbs != null ? carbs.getDouble(VALUE) : 0;
                        food.setNutrient(TOTAL_CARBS, carbsValue);


                        JSONObject sodium = !important.isNull(SODIUM) ?
                                important.getJSONObject(SODIUM) : null;
                        double sodiumValue = sodium != null ? sodium.getDouble(VALUE) : 0;
                        food.setNutrient(SODIUM, sodiumValue);


                        JSONObject potassium = !important.isNull(POTASSIUM) ?
                                important.getJSONObject(POTASSIUM) : null;
                        double potassiumValue = potassium != null ? potassium.getDouble(VALUE): 0;
                        food.setNutrient(POTASSIUM, potassiumValue);


                        JSONObject calories = !important.isNull(CALORIES) ?
                                important.getJSONObject(CALORIES) : null;
                        double caloriesValue = calories != null ? calories.getDouble(VALUE) : 0;
                        food.setNutrient(CALORIES, caloriesValue);


                        JSONObject sugar = !important.isNull(SUGAR) ?
                                important.getJSONObject(SUGAR) : null;
                        double sugarValue = sugar != null ? sugar.getDouble(VALUE) : 0;
                        food.setNutrient(SUGAR, sugarValue);


                        JSONObject fats = !important.isNull(TOTAL_FATS) ?
                                important.getJSONObject(TOTAL_FATS) : null;
                        double fatsValue = fats != null ? fats.getDouble(VALUE) : 0;
                        food.setNutrient(TOTAL_FATS, fatsValue);


                        JSONObject cholesterol = !important.isNull(CHOLESTEROL) ?
                                important.getJSONObject(CHOLESTEROL) : null;
                        double cholesterolValue = cholesterol != null ?
                                cholesterol.getDouble(VALUE) : 0;
                        food.setNutrient(CHOLESTEROL, cholesterolValue);


                        JSONObject protein = !important.isNull(PROTEIN) ?
                                important.getJSONObject(PROTEIN) : null;
                        double proteinValue = protein != null ? protein.getDouble(VALUE) : 0;
                        food.setNutrient(PROTEIN, proteinValue);

                        break;
                    }
                }

                foods.add(food);
            }

        } catch(JSONException e) {
            Log.e(TAG, e.getMessage());
        }
        return foods;
    }
}

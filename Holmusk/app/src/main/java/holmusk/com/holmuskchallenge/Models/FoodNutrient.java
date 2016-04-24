package holmusk.com.holmuskchallenge.Models;

/**
 * Created by thearith on 25/4/16.
 */
public class FoodNutrient {

    private String foodName;
    private Nutrient nutrient;

    public FoodNutrient(String foodName, Nutrient nutrient) {
        setFoodName(foodName);
        setNutrient(nutrient);
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public Nutrient getNutrient() {
        return nutrient;
    }

    public void setNutrient(Nutrient nutrient) {
        this.nutrient = nutrient;
    }
}

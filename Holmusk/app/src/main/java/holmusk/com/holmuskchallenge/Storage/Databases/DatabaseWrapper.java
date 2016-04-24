package holmusk.com.holmuskchallenge.Storage.Databases;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;

import holmusk.com.holmuskchallenge.Helpers.DateTime;
import holmusk.com.holmuskchallenge.Models.Food;
import holmusk.com.holmuskchallenge.Models.FoodIntake;
import holmusk.com.holmuskchallenge.Models.Portion;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

/**
 * Created by thearith on 23/4/16.
 */

public class DatabaseWrapper {

    public static final String TAG = DatabaseWrapper.class.getSimpleName();

    // variables
    private static DatabaseWrapper wrapper;
    private Realm realm;


    /*
    * Constructor
    * */

    public static DatabaseWrapper getInstance(Context context) {
        if(wrapper == null) {
            wrapper = new DatabaseWrapper(context);
        }

        return wrapper;
    }

    private DatabaseWrapper(Context context) {
        RealmConfiguration config = new RealmConfiguration.Builder(context).build();
        realm = Realm.getInstance(config);
    }

    public void destroy() {
        realm.close();
    }


    /*
    * public methods: FoodIntake
    * */

    public FoodIntake getFoodIntake() {
        long today = DateTime.getTodayDateInteger();
        return getFoodIntake(today);
    }

    public FoodIntake getFoodIntake(long date) {
        RealmResults<FoodIntake> foodIntakes = realm.where(FoodIntake.class).
                beginsWith("date", String.valueOf(date)).findAll();

        if(foodIntakes.size() == 1) {
            return foodIntakes.first();
        } else {
            Log.e(TAG, "Food intake query cannot be more than 1");
            return null;
        }
    }

    public void addFoodToday(Food food) {
        long today = DateTime.getTodayDateInteger();
        addFood(today, food);
    }

    public void addFood(long date, Food food) {
        FoodIntake foodIntake = getFoodIntake(date);

        if(foodIntake == null) {
            realm.beginTransaction();

            FoodIntake intake = realm.createObject(FoodIntake.class);
            intake.setDate(date);
            intake.addFood(food.getId());

            realm.commitTransaction();

        } else {
            realm.beginTransaction();

            foodIntake.addFood(food.getId());

            realm.commitTransaction();
        }
    }


    /*
    * Public methods - Food
    * */

    public Food getFood(String id) {
        RealmResults<Food> food = realm.where(Food.class).beginsWith("id", id).findAll();
        return food.first();
    }

    public ArrayList<Food> getFood() {
        long today = DateTime.getTodayDateInteger();
        return getFoods(today);
    }

    public ArrayList<Food> getFoods(long date) {
        ArrayList<Food> foods = new ArrayList<>();
        FoodIntake foodIntake = getFoodIntake(date);
        String[] foodIds = foodIntake.getAllFoods();
        for(String foodId : foodIds) {
            Food food = getFood(foodId);
            foods.add(food);
        }

        return foods;
    }

    public void setFood(Food food) {
        boolean isExist = isFoodExistInDatabase(food.getId());
        if(!isExist) {
            realm.beginTransaction();
            realm.copyToRealmOrUpdate(food);
            realm.commitTransaction();
        }
    }

    private boolean isFoodExistInDatabase(String id) {
        RealmResults<Food> food = realm.where(Food.class).beginsWith("id", id).findAll();
        return !food.isEmpty();
    }


    /*
    * Public methods - Portion
    * */

    public void setPortion(Portion portion) {
        realm.beginTransaction();
        realm.copyToRealmOrUpdate(portion);
        realm.commitTransaction();
    }
}

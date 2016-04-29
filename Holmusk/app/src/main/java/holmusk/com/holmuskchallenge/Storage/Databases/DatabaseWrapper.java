package holmusk.com.holmuskchallenge.Storage.Databases;

import android.content.Context;
import android.util.Log;
import android.util.TimingLogger;

import java.util.ArrayList;

import holmusk.com.holmuskchallenge.Helpers.DateTime;
import holmusk.com.holmuskchallenge.Models.Food;
import holmusk.com.holmuskchallenge.Models.FoodIntake;
import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;
import io.realm.exceptions.RealmMigrationNeededException;

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
        try {
            realm = Realm.getInstance(config);
        } catch (RealmMigrationNeededException e){
            try {
                Realm.deleteRealm(config);
                //Realm file has been deleted.
                realm = Realm.getInstance(config);
            } catch (Exception ex){
                throw ex;
            }
        }
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
        FoodIntake foodIntake = realm.where(FoodIntake.class).
                equalTo("date", date).findFirst();

        if(foodIntake == null) {
            Log.e(TAG, "Food intake query is null");
        }

        return foodIntake;
    }

    public void addFoodToday(Food food) {
        long today = DateTime.getTodayDateInteger();
        addFood(today, food);
    }

    public void addFood(long date, Food food) {
        TimingLogger timings = new TimingLogger(TAG, "adding food method");

        setFood(food);
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

        timings.dumpToLog();
    }

    public void removeFoodToday(Food food) {
        long today = DateTime.getTodayDateInteger();
        removeFood(today, food);
    }

    private void removeFood(long date, Food food) {
        FoodIntake foodIntake = getFoodIntake(date);

        if(foodIntake == null) {
            realm.beginTransaction();

            FoodIntake intake = realm.createObject(FoodIntake.class);
            intake.setDate(date);
            intake.removeFood(food.getId());

            realm.commitTransaction();
        } else {
            realm.beginTransaction();

            foodIntake.removeFood(food.getId());

            realm.commitTransaction();
        }
    }


    /*
    * Public methods - Food
    * */

    public ArrayList<ArrayList<Food>> getWeeklyFoods(long[] dates) {
        ArrayList<ArrayList<Food>> foodsList = new ArrayList<>();
        for(long date : dates) {
            ArrayList<Food> foods = getFoods(date);
            foodsList.add(foods);
        }

        return foodsList;
    }

    public Food getFood(String id) {
        TimingLogger timings = new TimingLogger(TAG, "retrieving food method");

        RealmResults<Food> food = realm.where(Food.class).beginsWith("id", id).findAll();

        timings.dumpToLog();

        return food.first();
    }

    public ArrayList<Food> getFoods() {
        long today = DateTime.getTodayDateInteger();
        return getFoods(today);
    }

    public ArrayList<Food> getFoods(long date) {
        ArrayList<Food> foods = new ArrayList<>();
        FoodIntake foodIntake = getFoodIntake(date);
        if (foodIntake != null) {
            String[] foodIds = foodIntake.getAllFoods();
            for (String foodId : foodIds) {
                if(foodId != null && !foodId.equalsIgnoreCase("")) {
                    Food food = getFood(foodId);
                    foods.add(food);
                }
            }
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
}

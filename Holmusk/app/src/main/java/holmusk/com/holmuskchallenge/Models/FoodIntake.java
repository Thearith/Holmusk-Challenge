package holmusk.com.holmuskchallenge.Models;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;

/**
 * Created by thearith on 23/4/16.
 */

public class FoodIntake extends RealmObject {

    private static final String SEPERATOR    = ",";

    private long date;
    private String foods;

    public void setDate(long date) {
        this.date = date;
    }

    public long getDate() {
        return this.date;
    }

    public void addFood(String foodId) {
        if(foods == null || foods.equalsIgnoreCase("") )
            foods = foodId;
        else
            foods += SEPERATOR + foodId;
    }

    public void removeFood(String foodId) {
        if(foods != null && !foods.equalsIgnoreCase("") ) {
            foods.replace(foodId, "");
        }
    }

    public String[] getAllFoods() {
        return foods.split(SEPERATOR);
    }
}

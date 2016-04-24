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

    public void addFood(String food) {
        if(foods == null || foods == "")
            foods = food;
        else
            foods = SEPERATOR + food;
    }

    public String[] getAllFoods() {
        return foods.split(SEPERATOR);
    }
}

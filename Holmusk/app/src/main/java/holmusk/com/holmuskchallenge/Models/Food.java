package holmusk.com.holmuskchallenge.Models;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by thearith on 23/4/16.
 */

public class Food extends RealmObject {

    private static final String DIETARY_FIBRE       = "dietary_fibre";
    private static final String TOTAL_CARBS         = "total_carbs";
    private static final String SODIUM              = "sodium";
    private static final String POTASSIUM           = "potassium";
    private static final String CALORIES            = "calories";
    private static final String SUGAR               = "sugar";
    private static final String TOTAL_FATS          = "total_fats";
    private static final String CHOLESTEROL         = "cholesterol";
    private static final String PROTEIN             = "protein";

    @PrimaryKey
    private String id;

    private String name;
    private String brandName;
    private String source;

    // nutrients
    private String portionName;

    private double dietaryFibre;
    private double totalCarbs;
    private double sodium;
    private double potassium;
    private double calories;
    private double sugar;
    private double totalFats;
    private double cholesterol;
    private double protein;

    private String dietaryFibreUnit;
    private String totalCarbsUnit;
    private String sodiumUnit;
    private String potassiumUnit;
    private String caloriesUnit;
    private String sugarUnit;
    private String totalFatsUnit;
    private String cholesterolUnit;
    private String proteinUnit;


    /*
    * Constructor
    * */

    public Food() {

    }

    public Food(String id, String name, String brandName, String source) {
        setId(id);
        setName(name);
        setBrandName(brandName);
        setSource(source);
    }


    /*
    * Methods relating to name and ids
    * */


    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }


    /*
    * Methods relating to nutrients
    * */

    public void setPortionName(String portionName) {
        this.portionName = portionName;
    }

    public String getPortionName() {
        return this.portionName;
    }

    public Nutrient getNutrient() {
        return new Nutrient(calories, protein, totalFats, totalCarbs, cholesterol, sugar,
                dietaryFibre, sodium, potassium);
    }

    public void setNutrient(String name, String unit, double value) {
        switch(name) {
            case DIETARY_FIBRE:
                setDietaryFibreUnit(unit);
                setDietaryFibre(value);
                break;

            case TOTAL_CARBS:
                setTotalCarbsUnit(unit);
                setTotalCarbs(value);
                break;

            case SODIUM:
                setSodiumUnit(unit);
                setSodium(value);
                break;

            case POTASSIUM:
                setPotassiumUnit(unit);
                setPotassium(value);
                break;

            case CALORIES:
                setCaloriesUnit(unit);
                setCalories(value);
                break;

            case SUGAR:
                setSugarUnit(unit);
                setSugar(value);
                break;

            case TOTAL_FATS:
                setTotalFatsUnit(unit);
                setTotalFats(value);
                break;

            case CHOLESTEROL:
                setCholesterolUnit(unit);
                setCholesterol(value);
                break;

            case PROTEIN:
                setProteinUnit(unit);
                setProtein(value);
                break;
        }
    }

    public void setNutrient(String name, double value) {
        switch(name) {
            case DIETARY_FIBRE:
                setDietaryFibre(value);
                break;

            case TOTAL_CARBS:
                setTotalCarbs(value);
                break;

            case SODIUM:
                setSodium(value);
                break;

            case POTASSIUM:
                setPotassium(value);
                break;

            case CALORIES:
                setCalories(value);
                break;

            case SUGAR:
                setSugar(value);
                break;

            case TOTAL_FATS:
                setTotalFats(value);
                break;

            case CHOLESTEROL:
                setCholesterol(value);
                break;

            case PROTEIN:
                setProtein(value);
                break;
        }
    }




    // getters and setters


    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public String getCaloriesUnit() {
        return caloriesUnit;
    }

    public void setCaloriesUnit(String caloriesUnit) {
        this.caloriesUnit = caloriesUnit;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public String getCholesterolUnit() {
        return cholesterolUnit;
    }

    public void setCholesterolUnit(String cholesterolUnit) {
        this.cholesterolUnit = cholesterolUnit;
    }

    public double getDietaryFibre() {
        return dietaryFibre;
    }

    public void setDietaryFibre(double dietaryFibre) {
        this.dietaryFibre = dietaryFibre;
    }

    public String getDietaryFibreUnit() {
        return dietaryFibreUnit;
    }

    public void setDietaryFibreUnit(String dietaryFibreUnit) {
        this.dietaryFibreUnit = dietaryFibreUnit;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public String getPotassiumUnit() {
        return potassiumUnit;
    }

    public void setPotassiumUnit(String potassiumUnit) {
        this.potassiumUnit = potassiumUnit;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public String getProteinUnit() {
        return proteinUnit;
    }

    public void setProteinUnit(String proteinUnit) {
        this.proteinUnit = proteinUnit;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public String getSodiumUnit() {
        return sodiumUnit;
    }

    public void setSodiumUnit(String sodiumUnit) {
        this.sodiumUnit = sodiumUnit;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public String getSugarUnit() {
        return sugarUnit;
    }

    public void setSugarUnit(String sugarUnit) {
        this.sugarUnit = sugarUnit;
    }

    public double getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public String getTotalCarbsUnit() {
        return totalCarbsUnit;
    }

    public void setTotalCarbsUnit(String totalCarbsUnit) {
        this.totalCarbsUnit = totalCarbsUnit;
    }

    public double getTotalFats() {
        return totalFats;
    }

    public void setTotalFats(double totalFats) {
        this.totalFats = totalFats;
    }

    public String getTotalFatsUnit() {
        return totalFatsUnit;
    }

    public void setTotalFatsUnit(String totalFatsUnit) {
        this.totalFatsUnit = totalFatsUnit;
    }
}

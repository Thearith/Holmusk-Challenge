package holmusk.com.holmuskchallenge.Models;

import io.realm.RealmObject;

/**
 * Created by thearith on 23/4/16.
 */
public class Portion extends RealmObject {

    // constants
    public static final String DIETARY_FIBRE    = "dietary_fibre";
    public static final String TRANS            = "trans";
    public static final String SATURATED        = "saturated";
    public static final String TOTAL_CARBS      = "total_carbs";
    public static final String SODIUM           = "sodium";
    public static final String POTASSIUM        = "potassium";
    public static final String POLY_UNSATURATED = "polyunsaturated";
    public static final String CALORIES         = "calories";
    public static final String SUGAR            = "sugar";
    public static final String TOTAL_FATS       = "total_fats";
    public static final String MONO_UNSATURATED = "monounsaturated";
    public static final String CHOLESTEROL      = "cholesterol";
    public static final String PROTEIN          = "protein";

    public static final String[] UNITS    = {"g", "mg", "kCal"};


    private String name;

    // nutrients
    private double dietaryFibre;
    private double trans;
    private double saturated;
    private double totalCarbs;
    private double sodium;
    private double potassium;
    private double polyUnsaturated;
    private double calories;
    private double sugar;
    private double totalFats;
    private double monoUnsaturated;
    private double cholesterol;
    private double protein;

    private String dietaryFibreUnit;
    private String transUnit;
    private String saturatedUnit;
    private String totalCarbsUnit;
    private String sodiumUnit;
    private String potassiumUnit;
    private String polyUnsaturatedUnit;
    private String caloriesUnit;
    private String sugarUnit;
    private String totalFatsUnit;
    private String monoUnsaturatedUnit;
    private String cholesterolUnit;
    private String proteinUnit;


    // public methods

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setNutrient(String name, String unit, double value) {
        switch(name) {
            case DIETARY_FIBRE:
                setDietaryFibreUnit(unit);
                setDietaryFibre(value);
                break;

            case TRANS:
                setTransUnit(unit);
                setTrans(value);
                break;

            case SATURATED:
                setSaturatedUnit(unit);
                setSaturated(value);
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

            case POLY_UNSATURATED:
                setPolyUnsaturatedUnit(unit);
                setPolyUnsaturated(value);
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

            case MONO_UNSATURATED:
                setMonoUnsaturatedUnit(unit);
                setMonoUnsaturated(value);
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

    public double getMonoUnsaturated() {
        return monoUnsaturated;
    }

    public void setMonoUnsaturated(double monoUnsaturated) {
        this.monoUnsaturated = monoUnsaturated;
    }

    public String getMonoUnsaturatedUnit() {
        return monoUnsaturatedUnit;
    }

    public void setMonoUnsaturatedUnit(String monoUnsaturatedUnit) {
        this.monoUnsaturatedUnit = monoUnsaturatedUnit;
    }

    public double getPolyUnsaturated() {
        return polyUnsaturated;
    }

    public void setPolyUnsaturated(double polyUnsaturated) {
        this.polyUnsaturated = polyUnsaturated;
    }

    public String getPolyUnsaturatedUnit() {
        return polyUnsaturatedUnit;
    }

    public void setPolyUnsaturatedUnit(String polyUnsaturatedUnit) {
        this.polyUnsaturatedUnit = polyUnsaturatedUnit;
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

    public double getSaturated() {
        return saturated;
    }

    public void setSaturated(double saturated) {
        this.saturated = saturated;
    }

    public String getSaturatedUnit() {
        return saturatedUnit;
    }

    public void setSaturatedUnit(String saturatedUnit) {
        this.saturatedUnit = saturatedUnit;
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

    public double getTrans() {
        return trans;
    }

    public void setTrans(double trans) {
        this.trans = trans;
    }

    public String getTransUnit() {
        return transUnit;
    }

    public void setTransUnit(String transUnit) {
        this.transUnit = transUnit;
    }
}

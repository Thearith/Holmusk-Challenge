package holmusk.com.holmuskchallenge.Models;

/**
 * Created by thearith on 24/4/16.
 */
public class Nutrient {

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

    public Nutrient(double dietaryFibre, double trans, double saturated, double totalCarbs,
        double sodium, double potassium, double polyUnsaturated, double calories, double sugar,
        double totalFats, double monoUnsaturated, double cholesterol, double protein) {

        setDietaryFibre(dietaryFibre);
        setTrans(trans);
        setSaturated(saturated);
        setTotalCarbs(totalCarbs);
        setSodium(sodium);
        setPotassium(potassium);
        setPolyUnsaturated(polyUnsaturated);
        setCalories(calories);
        setSugar(sugar);
        setTotalFats(totalFats);
        setMonoUnsaturated(monoUnsaturated);
        setCholesterol(cholesterol);
        setProtein(protein);
    }

    public Nutrient(double calories, double protein, double totalFats, double totalCarbs,
        double cholesterol, double sugar, double dietaryFibre, double sodium, double potassium) {

        setDietaryFibre(dietaryFibre);
        setTotalCarbs(totalCarbs);
        setSodium(sodium);
        setPotassium(potassium);
        setCalories(calories);
        setSugar(sugar);
        setTotalFats(totalFats);
        setCholesterol(cholesterol);
        setProtein(protein);
    }

    public Nutrient(double cholesterol, double sugar, double dietaryFibre, double sodium,
        double potassium) {

        setDietaryFibre(dietaryFibre);
        setSodium(sodium);
        setPotassium(potassium);
        setSugar(sugar);
        setCholesterol(cholesterol);

    }

    public double getCalories() {
        return calories;
    }

    public void setCalories(double calories) {
        this.calories = calories;
    }

    public double getCholesterol() {
        return cholesterol;
    }

    public void setCholesterol(double cholesterol) {
        this.cholesterol = cholesterol;
    }

    public double getDietaryFibre() {
        return dietaryFibre;
    }

    public void setDietaryFibre(double dietaryFibre) {
        this.dietaryFibre = dietaryFibre;
    }

    public double getMonoUnsaturated() {
        return monoUnsaturated;
    }

    public void setMonoUnsaturated(double monoUnsaturated) {
        this.monoUnsaturated = monoUnsaturated;
    }

    public double getPolyUnsaturated() {
        return polyUnsaturated;
    }

    public void setPolyUnsaturated(double polyUnsaturated) {
        this.polyUnsaturated = polyUnsaturated;
    }

    public double getPotassium() {
        return potassium;
    }

    public void setPotassium(double potassium) {
        this.potassium = potassium;
    }

    public double getProtein() {
        return protein;
    }

    public void setProtein(double protein) {
        this.protein = protein;
    }

    public double getSaturated() {
        return saturated;
    }

    public void setSaturated(double saturated) {
        this.saturated = saturated;
    }

    public double getSodium() {
        return sodium;
    }

    public void setSodium(double sodium) {
        this.sodium = sodium;
    }

    public double getSugar() {
        return sugar;
    }

    public void setSugar(double sugar) {
        this.sugar = sugar;
    }

    public double getTotalCarbs() {
        return totalCarbs;
    }

    public void setTotalCarbs(double totalCarbs) {
        this.totalCarbs = totalCarbs;
    }

    public double getTotalFats() {
        return totalFats;
    }

    public void setTotalFats(double totalFats) {
        this.totalFats = totalFats;
    }

    public double getTrans() {
        return trans;
    }

    public void setTrans(double trans) {
        this.trans = trans;
    }
}

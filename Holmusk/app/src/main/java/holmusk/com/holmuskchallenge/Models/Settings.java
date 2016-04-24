package holmusk.com.holmuskchallenge.Models;

/**
 * Created by thearith on 22/4/16.
 */
public class Settings {

    private boolean gender;
    private int age;
    private float weight;
    private float height;

    public Settings(boolean gender, int age, float weight, float height) {
        setAge(age);
        setGender(gender);
        setHeight(height);
        setWeight(weight);
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public float getWeight() {
        return weight;
    }

    public void setWeight(float weight) {
        this.weight = weight;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}

package holmusk.com.holmuskchallenge.Models;

import java.util.ArrayList;

import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

/**
 * Created by thearith on 23/4/16.
 */

public class Food extends RealmObject {

    @PrimaryKey
    private String id;

    private String name;
    private String brandName;
    private Portion portion;
    private String source;
    private String tags;


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

    public Portion getPortions() {
        return portion;
    }

    public void setPortions(Portion portion) {
        this.portion = portion;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}

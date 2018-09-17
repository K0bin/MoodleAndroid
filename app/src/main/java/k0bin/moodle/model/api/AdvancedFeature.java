package k0bin.moodle.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AdvancedFeature {

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("value")
    @Expose
    private int value;

    public AdvancedFeature(String name, int value) {
        super();
        this.name = name;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public int getValue() {
        return value;
    }
}

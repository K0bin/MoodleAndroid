package k0bin.moodle.model.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseContents {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("visible")
    @Expose
    private int visible;

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("summaryformat")
    @Expose
    private int summaryFormat;

    @SerializedName("section")
    @Expose
    private int section;

    @SerializedName("hiddenbynumsections")
    @Expose
    private int hiddenByNumSections;

    @SerializedName("uservisible")
    @Expose
    private boolean userVisible;

    @SerializedName("modules")
    @Expose
    private List<CourseModule> modules = null;

    public CourseContents(int id, String name, int visible, String summary, int summaryFormat, int section, int hiddenByNumSections, boolean userVisible, List<CourseModule> modules) {
        this.id = id;
        this.name = name;
        this.visible = visible;
        this.summary = summary;
        this.summaryFormat = summaryFormat;
        this.section = section;
        this.hiddenByNumSections = hiddenByNumSections;
        this.userVisible = userVisible;
        this.modules = modules;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getVisible() {
        return visible;
    }

    public String getSummary() {
        return summary;
    }

    public int getSummaryFormat() {
        return summaryFormat;
    }

    public int getSection() {
        return section;
    }

    public int getHiddenByNumSections() {
        return hiddenByNumSections;
    }

    public boolean isUserVisible() {
        return userVisible;
    }

    public List<CourseModule> getModules() {
        return modules;
    }
}

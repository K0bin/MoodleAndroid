package k0bin.moodle.model.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseModule {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("url")
    @Expose
    private String url;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("instance")
    @Expose
    private int instance;

    @SerializedName("visible")
    @Expose
    private int visible;

    @SerializedName("uservisible")
    @Expose
    private boolean isUserVisible;

    @SerializedName("visibleoncoursepage")
    @Expose
    private int visibleOnCoursePage;

    @SerializedName("modicon")
    @Expose
    private String modIcon;

    @SerializedName("modname")
    @Expose
    private String modName;

    @SerializedName("modplural")
    @Expose
    private String modPlural;

    @SerializedName("indent")
    @Expose
    private int indent;

    @SerializedName("contents")
    @Expose
    private List<CourseContent> contents = null;

    public CourseModule(int id, String url, String name, int instance, int visible, boolean isUserVisible, int visibleOnCoursePage, String modIcon, String modName, String modPlural, int indent, List<CourseContent> contents) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.instance = instance;
        this.visible = visible;
        this.isUserVisible = isUserVisible;
        this.visibleOnCoursePage = visibleOnCoursePage;
        this.modIcon = modIcon;
        this.modName = modName;
        this.modPlural = modPlural;
        this.indent = indent;
        this.contents = contents;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public String getName() {
        return name;
    }

    public int getInstance() {
        return instance;
    }

    public int getVisible() {
        return visible;
    }

    public boolean isUserVisible() {
        return isUserVisible;
    }

    public int getVisibleOnCoursePage() {
        return visibleOnCoursePage;
    }

    public String getModIcon() {
        return modIcon;
    }

    public String getModName() {
        return modName;
    }

    public String getModPlural() {
        return modPlural;
    }

    public int getIndent() {
        return indent;
    }

    public List<CourseContent> getContents() {
        return contents;
    }
}
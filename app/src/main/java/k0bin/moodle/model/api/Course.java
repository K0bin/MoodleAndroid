package k0bin.moodle.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

public class Course {

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("shortname")
    @Expose
    private String shortName;

    @SerializedName("fullname")
    @Expose
    private String fullName;

    @SerializedName("enrolledusercount")
    @Expose
    private int enrolledUserCount;

    @SerializedName("idnumber")
    @Expose
    private String idNumber;

    @SerializedName("visible")
    @Expose
    private int visible;

    @SerializedName("summary")
    @Expose
    private String summary;

    @SerializedName("summaryformat")
    @Expose
    private int summaryFormat;

    @SerializedName("format")
    @Expose
    private String format;

    @SerializedName("showgrades")
    @Expose
    private boolean showGrades;

    @SerializedName("lang")
    @Expose
    private String lang;

    @SerializedName("enablecompletion")
    @Expose
    private boolean enableCompletion;

    @SerializedName("category")
    @Expose
    private int category;

    @SerializedName("progress")
    @Expose
    private Object progress;

    @SerializedName("startdate")
    @Expose
    private int startDate;

    @SerializedName("enddate")
    @Expose
    private int endDate;

    public Course(int id, String shortName, String fullName, int enrolledUserCount, String idNumber, int visible, String summary, int summaryFormat, String format, boolean showGrades, String lang, boolean enableCompletion, int category, Object progress, int startDate, int endDate) {
        super();
        this.id = id;
        this.shortName = shortName;
        this.fullName = fullName;
        this.enrolledUserCount = enrolledUserCount;
        this.idNumber = idNumber;
        this.visible = visible;
        this.summary = summary;
        this.summaryFormat = summaryFormat;
        this.format = format;
        this.showGrades = showGrades;
        this.lang = lang;
        this.enableCompletion = enableCompletion;
        this.category = category;
        this.progress = progress;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public int getId() {
        return id;
    }

    public String getShortName() {
        return shortName;
    }

    public String getFullName() {
        return fullName;
    }

    public int getEnrolledUserCount() {
        return enrolledUserCount;
    }

    public String getIdNumber() {
        return idNumber;
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

    public String getFormat() {
        return format;
    }

    public boolean getShowGrades() {
        return showGrades;
    }

    public String getLang() {
        return lang;
    }

    public boolean getEnableCompletion() {
        return enableCompletion;
    }

    public int getCategory() {
        return category;
    }

    public Object getProgress() {
        return progress;
    }

    public int getStartDate() {
        return startDate;
    }

    public int getEndDate() {
        return endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Course course = (Course) o;
        return id == course.id &&
                enrolledUserCount == course.enrolledUserCount &&
                visible == course.visible &&
                summaryFormat == course.summaryFormat &&
                showGrades == course.showGrades &&
                enableCompletion == course.enableCompletion &&
                category == course.category &&
                startDate == course.startDate &&
                endDate == course.endDate &&
                Objects.equals(shortName, course.shortName) &&
                Objects.equals(fullName, course.fullName) &&
                Objects.equals(idNumber, course.idNumber) &&
                Objects.equals(summary, course.summary) &&
                Objects.equals(format, course.format) &&
                Objects.equals(lang, course.lang) &&
                Objects.equals(progress, course.progress);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, shortName, fullName, enrolledUserCount, idNumber, visible, summary, summaryFormat, format, showGrades, lang, enableCompletion, category, progress, startDate, endDate);
    }
}

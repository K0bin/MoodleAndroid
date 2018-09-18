package k0bin.moodle.model.api;

import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class CourseContent {

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("filename")
    @Expose
    private String fileName;

    @SerializedName("filepath")
    @Expose
    private String filePath;

    @SerializedName("filesize")
    @Expose
    private int fileSize;

    @SerializedName("fileurl")
    @Expose
    private String fileUrl;

    @SerializedName("timecreated")
    @Expose
    private Long timeCreated;

    @SerializedName("timemodified")
    @Expose
    private Long timeModified;

    @SerializedName("sortorder")
    @Expose
    private String sortOrder;

    @SerializedName("userid")
    @Expose
    private Long userId;
    @SerializedName("author")

    @Expose
    private String author;

    @SerializedName("license")
    @Expose
    private String license;

    public CourseContent(String type, String fileName, String filePath, int fileSize, String fileUrl, Long timeCreated, Long timeModified, String sortOrder, Long userId, String author, String license) {
        this.type = type;
        this.fileName = fileName;
        this.filePath = filePath;
        this.fileSize = fileSize;
        this.fileUrl = fileUrl;
        this.timeCreated = timeCreated;
        this.timeModified = timeModified;
        this.sortOrder = sortOrder;
        this.userId = userId;
        this.author = author;
        this.license = license;
    }

    public String getType() {
        return type;
    }

    public String getFileName() {
        return fileName;
    }

    public String getFilePath() {
        return filePath;
    }

    public int getFileSize() {
        return fileSize;
    }

    public String getFileUrl() {
        return fileUrl;
    }

    public Long getTimeCreated() {
        return timeCreated;
    }

    public Long getTimeModified() {
        return timeModified;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public Long getUserId() {
        return userId;
    }

    public String getAuthor() {
        return author;
    }

    public String getLicense() {
        return license;
    }
}

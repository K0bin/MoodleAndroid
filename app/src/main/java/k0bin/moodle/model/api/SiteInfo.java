
package k0bin.moodle.model.api;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SiteInfo {

    @SerializedName("sitename")
    @Expose
    private final String siteName;

    @SerializedName("username")
    @Expose
    private final String userName;

    @SerializedName("firstname")
    @Expose
    private final String firstName;

    @SerializedName("lastname")
    @Expose
    private final String lastName;

    @SerializedName("fullname")
    @Expose
    private final String fullName;

    @SerializedName("lang")
    @Expose
    private final String lang;

    @SerializedName("userid")
    @Expose
    private final long userId;

    @SerializedName("siteurl")
    @Expose
    private final String siteUrl;

    @SerializedName("userpictureurl")
    @Expose
    private final String userpictureurl;

    @SerializedName("functions")
    @Expose
    private final List<ApiFunction> functions;

    @SerializedName("downloadfiles")
    @Expose
    private final int downloadFiles;

    @SerializedName("uploadfiles")
    @Expose
    private final int uploadFiles;

    @SerializedName("release")
    @Expose
    private final String release;

    @SerializedName("version")
    @Expose
    private final String version;

    @SerializedName("mobilecssurl")
    @Expose
    private final String mobileCssUrl;

    @SerializedName("advancedfeatures")
    @Expose
    private final List<AdvancedFeature> advancedFeatures;

    @SerializedName("usercanmanageownfiles")
    @Expose
    private final boolean userCanManageOwnFiles;

    @SerializedName("userquota")
    @Expose
    private final long userQuota;

    @SerializedName("usermaxuploadfilesize")
    @Expose
    private final long userMaxUploadFileSize;

    @SerializedName("userhomepage")
    @Expose
    private final int userHomepage;

    @SerializedName("siteid")
    @Expose
    private final int siteId;

    @SerializedName("sitecalendartype")
    @Expose
    private final String siteCalendarType;

    @SerializedName("usercalendartype")
    @Expose
    private final String userCalendarType;

    public SiteInfo(String siteName, String userName, String firstName, String lastName, String fullName, String lang, long userId, String siteUrl, String userpictureurl, List<ApiFunction> functions, int downloadFiles, int uploadFiles, String release, String version, String mobileCssUrl, List<AdvancedFeature> advancedFeatures, boolean userCanManageOwnFiles, long userQuota, long userMaxUploadFileSize, int userHomepage, int siteId, String siteCalendarType, String userCalendarType) {
        super();
        this.siteName = siteName;
        this.userName = userName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.fullName = fullName;
        this.lang = lang;
        this.userId = userId;
        this.siteUrl = siteUrl;
        this.userpictureurl = userpictureurl;
        this.functions = functions;
        this.downloadFiles = downloadFiles;
        this.uploadFiles = uploadFiles;
        this.release = release;
        this.version = version;
        this.mobileCssUrl = mobileCssUrl;
        this.advancedFeatures = advancedFeatures;
        this.userCanManageOwnFiles = userCanManageOwnFiles;
        this.userQuota = userQuota;
        this.userMaxUploadFileSize = userMaxUploadFileSize;
        this.userHomepage = userHomepage;
        this.siteId = siteId;
        this.siteCalendarType = siteCalendarType;
        this.userCalendarType = userCalendarType;
    }

    public String getSiteName() {
        return siteName;
    }

    public String getUserName() {
        return userName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public String getLang() {
        return lang;
    }

    public long getUserId() {
        return userId;
    }

    public String getSiteUrl() {
        return siteUrl;
    }

    public String getUserpictureurl() {
        return userpictureurl;
    }

    public List<ApiFunction> getFunctions() {
        return functions;
    }

    public int getDownloadfiles() {
        return downloadFiles;
    }

    public int getUploadfiles() {
        return uploadFiles;
    }

    public String getRelease() {
        return release;
    }

    public String getVersion() {
        return version;
    }

    public String getMobileCssUrl() {
        return mobileCssUrl;
    }

    public List<AdvancedFeature> getAdvancedFeatures() {
        return advancedFeatures;
    }

    public boolean userCanManageOwnFiles() {
        return userCanManageOwnFiles;
    }

    public long getUserQuota() {
        return userQuota;
    }

    public long getUserMaxUploadFileSize() {
        return userMaxUploadFileSize;
    }

    public int getUserHomepage() {
        return userHomepage;
    }

    public int getSiteId() {
        return siteId;
    }

    public String getSiteCalendarType() {
        return siteCalendarType;
    }

    public String getUserCalendarType() {
        return userCalendarType;
    }
}

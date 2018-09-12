package k0bin.moodle.model.api;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PublicConfig {
    @SerializedName("wwwroot")
    @Expose
    private final String wwwRoot;

    @SerializedName("httpswwwroot")
    @Expose
    private final String httpsWwwRoot;

    @SerializedName("sitename")
    @Expose
    private final String siteName;

    @SerializedName("guestlogin")
    @Expose
    private final int guestLogin;

    @SerializedName("rememberusername")
    @Expose
    private final int rememberUsername;

    @SerializedName("authloginviaemail")
    @Expose
    private final int authLoginViaEmail;

    @SerializedName("registerauth")
    @Expose
    private final String registerAuth;

    @SerializedName("forgottenpasswordurl")
    @Expose
    private final String forgottenPasswordUrl;

    @SerializedName("authinstructions")
    @Expose
    private final String authInstructions;

    @SerializedName("authnoneenabled")
    @Expose
    private final int authNoneEnabled;

    @SerializedName("enablewebservices")
    @Expose
    private final int enableWebServices;

    @SerializedName("enablemobilewebservice")
    @Expose
    private final int enableMobileWebService;

    @SerializedName("maintenanceenabled")
    @Expose
    private final int maintenanceEnabled;

    @SerializedName("maintenancemessage")
    @Expose
    private final String maintenanceMessage;

    @SerializedName("logourl")
    @Expose
    private final String logoUrl;

    @SerializedName("compactlogourl")
    @Expose
    private final String compactlogourl;

    @SerializedName("typeoflogin")
    @Expose
    private final int typeOfLogin;

    @SerializedName("launchurl")
    @Expose
    private final String launchUrl;

    @SerializedName("mobilecssurl")
    @Expose
    private final String mobileCssUrl;

    @SerializedName("tool_mobile_disabledfeatures")
    @Expose
    private final String toolMobileDisabledFeatures;

    @SerializedName("warnings")
    @Expose
    private final List<String> warnings;

    public PublicConfig(String wwwRoot, String httpsWwwRoot, String siteName, int guestLogin, int rememberUsername, int authLoginViaEmail, String registerAuth, String forgottenPasswordUrl, String authInstructions, int authNoneEnabled, int enableWebServices, int enableMobileWebService, int maintenanceEnabled, String maintenanceMessage, String logoUrl, String compactlogourl, int typeOfLogin, String launchUrl, String mobileCssUrl, String toolMobileDisabledFeatures, List<String> warnings) {
        super();
        this.wwwRoot = wwwRoot;
        this.httpsWwwRoot = httpsWwwRoot;
        this.siteName = siteName;
        this.guestLogin = guestLogin;
        this.rememberUsername = rememberUsername;
        this.authLoginViaEmail = authLoginViaEmail;
        this.registerAuth = registerAuth;
        this.forgottenPasswordUrl = forgottenPasswordUrl;
        this.authInstructions = authInstructions;
        this.authNoneEnabled = authNoneEnabled;
        this.enableWebServices = enableWebServices;
        this.enableMobileWebService = enableMobileWebService;
        this.maintenanceEnabled = maintenanceEnabled;
        this.maintenanceMessage = maintenanceMessage;
        this.logoUrl = logoUrl;
        this.compactlogourl = compactlogourl;
        this.typeOfLogin = typeOfLogin;
        this.launchUrl = launchUrl;
        this.mobileCssUrl = mobileCssUrl;
        this.toolMobileDisabledFeatures = toolMobileDisabledFeatures;
        this.warnings = warnings;
    }

    public String getWwwRoot() {
        return wwwRoot;
    }

    public String getHttpsWwwRoot() {
        return httpsWwwRoot;
    }

    public String getSiteName() {
        return siteName;
    }

    public int getGuestLogin() {
        return guestLogin;
    }

    public int getRememberUsername() {
        return rememberUsername;
    }

    public int getAuthLoginViaEmail() {
        return authLoginViaEmail;
    }

    public String getRegisterAuth() {
        return registerAuth;
    }

    public String getForgottenPasswordUrl() {
        return forgottenPasswordUrl;
    }

    public String getAuthInstructions() {
        return authInstructions;
    }

    public int getAuthNoneEnabled() {
        return authNoneEnabled;
    }

    public int getEnableWebServices() {
        return enableWebServices;
    }

    public int getEnableMobileWebService() {
        return enableMobileWebService;
    }

    public int getMaintenanceEnabled() {
        return maintenanceEnabled;
    }

    public String getMaintenanceMessage() {
        return maintenanceMessage;
    }

    public String getLogoUrl() {
        return logoUrl;
    }

    public String getCompactlogourl() {
        return compactlogourl;
    }

    public int getTypeOfLogin() {
        return typeOfLogin;
    }

    public String getLaunchUrl() {
        return launchUrl;
    }

    public String getMobileCssUrl() {
        return mobileCssUrl;
    }

    public String getToolMobileDisabledFeatures() {
        return toolMobileDisabledFeatures;
    }

    public List<String> getWarnings() {
        return warnings;
    }
}
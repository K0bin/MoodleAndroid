package k0bin.moodle.model.api.ajax;

import java.lang.reflect.Type;
import java.util.List;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

public class PublicConfig {
    @SerializedName("wwwroot")
    @Expose
    private final String wwwroot;
    @SerializedName("httpswwwroot")
    @Expose
    private final String httpswwwroot;
    @SerializedName("sitename")
    @Expose
    private final String sitename;
    @SerializedName("guestlogin")
    @Expose
    private final int guestlogin;
    @SerializedName("rememberusername")
    @Expose
    private final int rememberusername;
    @SerializedName("authloginviaemail")
    @Expose
    private final int authloginviaemail;
    @SerializedName("registerauth")
    @Expose
    private final String registerauth;
    @SerializedName("forgottenpasswordurl")
    @Expose
    private final String forgottenpasswordurl;
    @SerializedName("authinstructions")
    @Expose
    private final String authinstructions;
    @SerializedName("authnoneenabled")
    @Expose
    private final int authnoneenabled;
    @SerializedName("enablewebservices")
    @Expose
    private final int enablewebservices;
    @SerializedName("enablemobilewebservice")
    @Expose
    private final int enablemobilewebservice;
    @SerializedName("maintenanceenabled")
    @Expose
    private final int maintenanceenabled;
    @SerializedName("maintenancemessage")
    @Expose
    private final String maintenancemessage;
    @SerializedName("logourl")
    @Expose
    private final String logourl;
    @SerializedName("compactlogourl")
    @Expose
    private final String compactlogourl;
    @SerializedName("typeoflogin")
    @Expose
    private final int typeoflogin;
    @SerializedName("launchurl")
    @Expose
    private final String launchurl;
    @SerializedName("mobilecssurl")
    @Expose
    private final String mobilecssurl;
    @SerializedName("tool_mobile_disabledfeatures")
    @Expose
    private final String toolMobileDisabledfeatures;
    @SerializedName("warnings")
    @Expose
    private final List<String> warnings;

    public PublicConfig(String wwwroot, String httpswwwroot, String sitename, int guestlogin, int rememberusername, int authloginviaemail, String registerauth, String forgottenpasswordurl, String authinstructions, int authnoneenabled, int enablewebservices, int enablemobilewebservice, int maintenanceenabled, String maintenancemessage, String logourl, String compactlogourl, int typeoflogin, String launchurl, String mobilecssurl, String toolMobileDisabledfeatures, List<String> warnings) {
        super();
        this.wwwroot = wwwroot;
        this.httpswwwroot = httpswwwroot;
        this.sitename = sitename;
        this.guestlogin = guestlogin;
        this.rememberusername = rememberusername;
        this.authloginviaemail = authloginviaemail;
        this.registerauth = registerauth;
        this.forgottenpasswordurl = forgottenpasswordurl;
        this.authinstructions = authinstructions;
        this.authnoneenabled = authnoneenabled;
        this.enablewebservices = enablewebservices;
        this.enablemobilewebservice = enablemobilewebservice;
        this.maintenanceenabled = maintenanceenabled;
        this.maintenancemessage = maintenancemessage;
        this.logourl = logourl;
        this.compactlogourl = compactlogourl;
        this.typeoflogin = typeoflogin;
        this.launchurl = launchurl;
        this.mobilecssurl = mobilecssurl;
        this.toolMobileDisabledfeatures = toolMobileDisabledfeatures;
        this.warnings = warnings;
    }

    public String getWwwroot() {
        return wwwroot;
    }

    public String getHttpswwwroot() {
        return httpswwwroot;
    }

    public String getSitename() {
        return sitename;
    }

    public int getGuestlogin() {
        return guestlogin;
    }

    public int getRememberusername() {
        return rememberusername;
    }

    public int getAuthloginviaemail() {
        return authloginviaemail;
    }

    public String getRegisterauth() {
        return registerauth;
    }

    public String getForgottenpasswordurl() {
        return forgottenpasswordurl;
    }

    public String getAuthinstructions() {
        return authinstructions;
    }

    public int getAuthnoneenabled() {
        return authnoneenabled;
    }

    public int getEnablewebservices() {
        return enablewebservices;
    }

    public int getEnablemobilewebservice() {
        return enablemobilewebservice;
    }

    public int getMaintenanceenabled() {
        return maintenanceenabled;
    }

    public String getMaintenancemessage() {
        return maintenancemessage;
    }

    public String getLogourl() {
        return logourl;
    }

    public String getCompactlogourl() {
        return compactlogourl;
    }

    public int getTypeoflogin() {
        return typeoflogin;
    }

    public String getLaunchurl() {
        return launchurl;
    }

    public String getMobilecssurl() {
        return mobilecssurl;
    }

    public String getToolMobileDisabledfeatures() {
        return toolMobileDisabledfeatures;
    }

    public List<String> getWarnings() {
        return warnings;
    }
}
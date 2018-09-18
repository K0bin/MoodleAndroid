package k0bin.moodle.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;

public class MoodleConfiguration {
    @NonNull
    private static final String SHARED_PREF_NAME = "moodle_conf";
    @NonNull
    private static final String MOODLE_SITE_PREF = "moodle_site";
    @NonNull
    private static final String MOODLE_TOKEN_PREF = "moodle_token";
    @NonNull
    private static final String MOODLE_USER_ID_PREF = "moodle_user_id";

    @NonNull
    private String siteUrl = "";
    @NonNull
    private String token = "";
    private long userId = 0;

    @NonNull
    public String getSiteUrl() {
        return siteUrl;
    }

    @NonNull
    public String getToken() {
        return token;
    }

    public long getUserId() {
        return userId;
    }

    public void setSiteUrl(@NonNull String siteUrl) {
        this.siteUrl = siteUrl;
    }

    public void setToken(@NonNull String token) {
        this.token = token;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    @NonNull
    public static MoodleConfiguration load(@NonNull Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        final MoodleConfiguration configuration = new MoodleConfiguration();
        configuration.setSiteUrl(prefs.getString(MOODLE_SITE_PREF, ""));
        configuration.setToken(prefs.getString(MOODLE_TOKEN_PREF, ""));
        configuration.setUserId(prefs.getLong(MOODLE_USER_ID_PREF, 0));
        return configuration;
    }

    public void save(@NonNull Context context) {
        SharedPreferences prefs = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        prefs
                .edit()
                .putString(MOODLE_SITE_PREF, siteUrl)
                .putString(MOODLE_TOKEN_PREF, token)
                .putLong(MOODLE_USER_ID_PREF, userId)
                .apply();
    }

    @NonNull
    public MoodleSetupStatus getStatus() {
        if (siteUrl.length() == 0) {
            return MoodleSetupStatus.NEEDS_SETUP;
        } else if (token.length() == 0 || userId == 0) {
            return MoodleSetupStatus.NEEDS_LOGIN;
        } else {
            return MoodleSetupStatus.DONE;
        }
    }
}

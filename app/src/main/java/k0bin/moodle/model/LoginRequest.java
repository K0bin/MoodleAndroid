package k0bin.moodle.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public abstract class LoginRequest {
    @NonNull
    private final String moodleSiteUrl;

    public LoginRequest(@NonNull String moodleSiteUrl) {
        this.moodleSiteUrl = moodleSiteUrl;
    }

    @NonNull
    public String getMoodleSiteUrl() {
        return moodleSiteUrl;
    }

    public static class UsernamePasswordLoginRequest extends LoginRequest {
        public UsernamePasswordLoginRequest(@NonNull String moodleSiteUrl) {
            super(moodleSiteUrl);
        }
    }

    public static class SsoLoginRequest extends LoginRequest {
        @NonNull
        private final String loginUrl;

        public SsoLoginRequest(@NonNull String moodleSiteUrl, @NonNull String loginUrl) {
            super(moodleSiteUrl);
            this.loginUrl = loginUrl;
        }

        @NonNull
        public String getLoginUrl() {
            return loginUrl;
        }
    }
}

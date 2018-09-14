package k0bin.moodle.model;

import android.support.annotation.Nullable;

public abstract class Authentication {
    public static class MoodleUser extends Authentication {
        private final String token;
        public MoodleUser(String token) {
            this.token = token;
        }

        public String getToken() {
            return token;
        }
    }

    public static class SsoLoginRequest extends Authentication {
        @Nullable
        private final String url;

        public SsoLoginRequest(@Nullable String authUrl) {
            this.url = authUrl;
        }

        @Nullable
        public String getUrl() {
            return url;
        }
    }

    public static class SetupRequest extends Authentication {
    }
}

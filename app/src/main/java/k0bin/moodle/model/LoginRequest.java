package k0bin.moodle.model;

import android.support.annotation.Nullable;

public final class LoginRequest {
    @Nullable
    private final String url;

    public LoginRequest(@Nullable String url) {
        this.url = url;
    }

    @Nullable
    public String getUrl() {
        return url;
    }
}

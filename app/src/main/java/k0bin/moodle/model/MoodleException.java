package k0bin.moodle.model;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import k0bin.moodle.model.api.RestError;

public class MoodleException extends Exception {
    @NonNull
    private static final String ERROR_CODE_INVALID_TOKEN = "invalidtoken";

    @NonNull
    private final String serverMessage;

    public MoodleException(@NonNull String message) {
        super(message);
        this.serverMessage = "";
    }

    public MoodleException(@NonNull String message, @NonNull String serverMessage) {
        super(message);
        this.serverMessage = serverMessage;
    }

    @NonNull
    public String getServerMessage() {
        return serverMessage;
    }

    public static class InvalidTokenException extends MoodleException {
        public InvalidTokenException() {
            super("Invalid token");
        }
    }

    @Nullable
    public static MoodleException parse(@NonNull RestError error) {
        switch (error.getErrorCode()) {
            case ERROR_CODE_INVALID_TOKEN:
                return new InvalidTokenException();
        }
        return new MoodleException(error.getMessage());
    }
}

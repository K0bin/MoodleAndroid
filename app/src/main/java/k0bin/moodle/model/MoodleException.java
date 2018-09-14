package k0bin.moodle.model;

import android.support.annotation.NonNull;

public class MoodleException extends Exception {
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

}

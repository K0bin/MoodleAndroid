package k0bin.moodle.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RestError {
    @SerializedName("exception")
    @Expose
    private final String exception;

    @SerializedName("errorcode")
    @Expose
    private final String errorCode;

    @SerializedName("message")
    @Expose
    private final String message;

    public RestError(String exception, String errorCode, String message) {
        this.exception = exception;
        this.errorCode = errorCode;
        this.message = message;
    }

    public boolean isSuccessful() {
        return (exception == null || exception.length() == 0) && (errorCode == null || errorCode.length() == 0);
    }

    public String getException() {
        return exception;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public String getMessage() {
        return message;
    }
}

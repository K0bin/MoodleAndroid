package k0bin.moodle.model.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public final class AjaxResponse<T> {
    @SerializedName("error")
    @Expose
    private final boolean error;

    @SerializedName("data")
    @Expose
    private final T data;

    public AjaxResponse(boolean error, T data) {
        this.error = error;
        this.data = data;
    }

    public boolean isSuccessful() {
        return !error;
    }

    public T getData() {
        return data;
    }
}

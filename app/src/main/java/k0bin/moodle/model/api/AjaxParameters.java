package k0bin.moodle.model.api;

import com.google.gson.annotations.SerializedName;

public final class AjaxParameters<T> {
    @SerializedName("index")
    public final int index;
    @SerializedName("methodname")
    public final String methodName;
    @SerializedName("args")
    public final T args;

    public AjaxParameters(int index, String methodName, T args) {
        this.index = index;
        this.methodName = methodName;
        this.args = args;
    }
}

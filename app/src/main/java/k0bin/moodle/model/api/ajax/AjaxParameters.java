package k0bin.moodle.model.api.ajax;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import retrofit2.http.Field;

public class AjaxParameters {
    @SerializedName("index")
    public final int index;
    @SerializedName("methodname")
    public final String methodName;
    @SerializedName("args")
    public final Object args;

    public AjaxParameters(int index, String methodName, Object args) {
        this.index = index;
        this.methodName = methodName;
        this.args = args;
    }

    public AjaxParameters(String methodName) {
        this(0, methodName, new Object());
    }
}

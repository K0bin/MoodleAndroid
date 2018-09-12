package k0bin.moodle.model.api;

public class AjaxResponse<T> {
    private final boolean error;
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

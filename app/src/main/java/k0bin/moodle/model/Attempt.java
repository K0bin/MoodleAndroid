package k0bin.moodle.model;

import android.support.annotation.NonNull;

public final class Attempt<T> {
    private final Throwable error;
    private final T value;

    public Attempt(@NonNull T value) {
        this.value = value;
        this.error = null;
    }

    public Attempt(@NonNull Throwable error) {
        this.value = null;
        this.error = error;
    }

    public boolean isSuccessful() {
        return error == null;
    }

    public T getValue() {
        return value;
    }

    public Throwable getError() {
        return error;
    }
}

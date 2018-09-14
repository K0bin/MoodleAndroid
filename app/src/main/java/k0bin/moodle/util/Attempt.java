package k0bin.moodle.util;

import android.support.annotation.Nullable;

public class Attempt<T> {
    @Nullable
    private final T value;
    @Nullable
    private final Throwable error;

    public Attempt(T value) {
        this.value = value;
        this.error = null;
    }

    public Attempt(Throwable error) {
        this.value = null;
        this.error = error;
    }

    public boolean wasSuccessful() {
        return error == null;
    }

    @Nullable
    public T getValue() {
        return value;
    }

    @Nullable
    public Throwable getError() {
        return error;
    }
}

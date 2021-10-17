package pl.vertty.wings.wings;

public class SkinChangeException extends Exception
{
    public SkinChangeException() {
    }

    public SkinChangeException(final String message) {
        super(message);
    }

    public SkinChangeException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public SkinChangeException(final Throwable cause) {
        super(cause);
    }

    protected SkinChangeException(final String message, final Throwable cause, final boolean enableSuppression, final boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}

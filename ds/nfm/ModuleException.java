package ds.nfm;

public class ModuleException extends RuntimeException {
    /**
     *
     */
    private static final long serialVersionUID = 2007674564336027625L;

    public ModuleException() {
    }

    public ModuleException(final String message) {
        super(message);
    }

    public ModuleException(final String message, final Throwable cause) {
        super(message, cause);
    }

    public ModuleException(final Throwable cause) {
        super(cause);
    }
}

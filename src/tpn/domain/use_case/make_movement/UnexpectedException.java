package tpn.domain.use_case.make_movement;

public class UnexpectedException extends Exception {

    public UnexpectedException() {
    }

    /**
     * Constructs an instance of <code>UnexpectedException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public UnexpectedException(String msg) {
        super(msg);
    }
}

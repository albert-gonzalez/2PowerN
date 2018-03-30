package tpn.domain.use_case.make_movement;

public class WinException extends Exception {

    public WinException() {
    }

    /**
     * Constructs an instance of <code>SuccessException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public WinException(String msg) {
        super(msg);
    }
}

package tpn.domain.use_case.make_movement;

public class UnknownInstructionException extends Exception {

    public UnknownInstructionException() {
    }

    /**
     * Constructs an instance of <code>UnknownInstructionException</code> with
     * the specified detail message.
     *
     * @param msg the detail message.
     */
    public UnknownInstructionException(String msg) {
        super(msg);
    }
}

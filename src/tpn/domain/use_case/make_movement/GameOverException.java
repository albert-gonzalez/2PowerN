package tpn.domain.use_case.make_movement;

public class GameOverException extends Exception {

    public GameOverException() {
    }

    /**
     * Constructs an instance of <code>GameOverException</code> with the
     * specified detail message.
     *
     * @param msg the detail message.
     */
    public GameOverException(String msg) {
        super(msg);
    }
}

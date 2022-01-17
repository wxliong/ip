public class DukeException extends Exception{

    protected String message;

    public DukeException(String message) {
        this.message = message;
    }

    @Override
    public String toString() {
        return message + "\n";
    }
}
package batman.tasks;

import java.time.format.DateTimeParseException;

public class Todo extends Task {

    /**
     * @param isDone Whether the task is done.
     * @param description Description of the task.
     * @throws DateTimeParseException  If by is wrongly formatted.
     */
    public Todo(boolean isDone, String description) {
        super(description);
        this.isDone = isDone;
    }

    /**
     * @param description Description of the task.
     * @throws DateTimeParseException  If by is wrongly formatted.
     */
    public Todo(String description) throws DateTimeParseException {
        this(false, description);
    }

    @Override
    public String appendToFile() {
        return "T|" + (super.isDone ? "1" : "0") + "|" + super.description + "\n";
    }

    @Override
    public String toString() {
        return "[T]" + super.toString();
    }
}

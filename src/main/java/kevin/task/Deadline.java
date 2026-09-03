package kevin.task;

import java.time.LocalDateTime;

/**
 * Deadline Class is a Task with a by date.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    public Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    public Deadline(String description, LocalDateTime by) {
        super(description, false);
        this.by = by;
    }

    @Override
    public String formatSaveString() {
        return "D | " + super.formatSaveString() + " | " + formatDateTime(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatDateTime(by) + ")";
    }
}

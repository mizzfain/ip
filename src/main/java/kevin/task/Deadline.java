package kevin.task;

import kevin.KevinException;

import java.time.LocalDateTime;

/**
 * Deadline Class is a Task with a by date.
 */
public class Deadline extends Task {
    private LocalDateTime by;

    /**
     * Creates new Deadline.
     * Used for creating new Deadline as well as loading saved Deadlines.
     * @param description
     * @param isDone
     * @param by
     */
    public Deadline(String description, boolean isDone, LocalDateTime by) {
        super(description, isDone);
        this.by = by;
    }

    /**
     * Creates new Deadline with default isDone as false.
     * Checks that by datetime is later than now.
     * @param description
     * @param by
     * @throws KevinException If by datetime is earlier than now.
     */
    public Deadline(String description, LocalDateTime by) throws KevinException {
        checkDateTimeLaterThanNow(by);
        this(description, false, by);
    }

    /**
     * Snoozes by date to a later datetime.
     * @param newByDate
     * @throws KevinException If new by date is earlier than now.
     */
    public void snooze(LocalDateTime newByDate) throws KevinException {
        checkDateTimeLaterThanNow(by);
        this.by = newByDate;
    }

    /**
     * Formats Deadline into String for saving into tasks.txt.
     */
    @Override
    public String formatSaveString() {
        return "D | " + super.formatSaveString() + " | " + formatDateTime(by);
    }

    @Override
    public String toString() {
        return "[D]" + super.toString() + " (by: " + formatDateTime(by) + ")";
    }
}

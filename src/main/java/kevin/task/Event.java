package kevin.task;

import kevin.KevinException;

import java.time.LocalDateTime;

/**
 * Event Class is a Task with a start and end date.
 */
public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    /**
     * Creates new Event.
     * Checks that end datetime is later than start.
     * @param description
     * @param isDone
     * @param start
     * @param end
     * @throws KevinException If end is earlier than start.
     */
    public Event(String description, boolean isDone, LocalDateTime start, LocalDateTime end) throws KevinException {
        checkEndLaterThanStart(start, end);

        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    /**
     * Creates new Event with default isDone as false.
     * Checks that datetimes are later than now.
     * @param description
     * @param start
     * @param end
     * @throws KevinException If datetimes are earlier than now, or end is earlier than start.
     */
    public Event(String description, LocalDateTime start, LocalDateTime end) throws KevinException {
        checkDateTimeLaterThanNow(start);
        checkDateTimeLaterThanNow(end);

        this(description, false, start, end);
    }

    public void snooze(LocalDateTime newStart, LocalDateTime newEnd) throws KevinException {
        checkDateTimeLaterThanNow(newStart);
        checkDateTimeLaterThanNow(newEnd);
        checkEndLaterThanStart(newStart, newEnd);

        this.start = newStart;
        this.end = newEnd;
    }

    /**
     * Checks if end DateTime is earlier than start DateTime.
     * @param start
     * @param end
     * @throws KevinException If end is earlier than start.
     */
    public static void checkEndLaterThanStart(LocalDateTime start, LocalDateTime end) throws KevinException {
        if (end.isBefore(start)) {
            throw new KevinException("To datetime cannot be earlier than from datetime.");
        }
    }

    @Override
    public String formatSaveString() {
        return "E | " + super.formatSaveString() + " | " + formatDateTime(start)
                + " | " + formatDateTime(end);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatDateTime(start)
                + " to: " + formatDateTime(end) + ")";
    }
}

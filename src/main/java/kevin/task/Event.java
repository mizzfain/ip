package kevin.task;

import kevin.KevinException;

import java.time.LocalDateTime;

/**
 * Event Class is a Task with a start and end date.
 */
public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String description, boolean isDone, LocalDateTime start, LocalDateTime end) throws KevinException {
        checkDateTimeLaterThanNow(start);
        checkDateTimeLaterThanNow(end);
        checkEndEarlierThanStart(start, end);

        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    public Event(String description, LocalDateTime start, LocalDateTime end) throws KevinException {
        checkDateTimeLaterThanNow(start);
        checkDateTimeLaterThanNow(end);
        checkEndEarlierThanStart(start, end);

        super(description, false);
        this.start = start;
        this.end = end;
    }

    public void snooze(LocalDateTime newStart, LocalDateTime newEnd) {
        this.start = newStart;
        this.end = newEnd;
    }

    /**
     * Checks if end DateTime is earlier than start DateTime.
     * @param start
     * @param end
     * @throws KevinException If end is earlier than start.
     */
    public static void checkEndEarlierThanStart(LocalDateTime start, LocalDateTime end) throws KevinException {
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

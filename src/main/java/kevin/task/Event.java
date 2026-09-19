package kevin.task;

import kevin.KevinException;

import java.time.LocalDateTime;

/**
 * Event Class is a Task with a from and to date.
 */
public class Event extends Task {
    private LocalDateTime from;
    private LocalDateTime to;

    /**
     * Creates new Event.
     * Checks that to datetime is later than from.
     * @param description
     * @param isDone
     * @param from
     * @param to
     * @throws KevinException If to is earlier than from.
     */
    public Event(String description, boolean isDone, LocalDateTime from, LocalDateTime to) throws KevinException {
        checkFromLaterThanTo(from, to);

        super(description, isDone);
        this.from = from;
        this.to = to;
    }

    /**
     * Creates new Event with default isDone as false.
     * Checks that datetimes are later than now.
     * @param description
     * @param from
     * @param end
     * @throws KevinException If datetimes are earlier than now, or to is earlier than from.
     */
    public Event(String description, LocalDateTime from, LocalDateTime end) throws KevinException {
        checkDateTimeLaterThanNow(from);
        checkDateTimeLaterThanNow(end);

        this(description, false, from, end);
    }

    /**
     * Snoozes from and/or to dates to a later datetime.
     * @param newFrom
     * @param newTo
     * @throws KevinException If new from/to date is later than now, or to is earlier than from.
     */
    public void snooze(LocalDateTime newFrom, LocalDateTime newTo) throws KevinException {
        checkDateTimeLaterThanNow(newFrom);
        checkDateTimeLaterThanNow(newTo);
        checkFromLaterThanTo(newFrom, newTo);

        this.from = newFrom;
        this.to = newTo;
    }

    /**
     * Checks if to DateTime is earlier than from DateTime.
     * @param from
     * @param to
     * @throws KevinException If to is earlier than from.
     */
    public static void checkFromLaterThanTo(LocalDateTime from, LocalDateTime to) throws KevinException {
        if (to.isBefore(from)) {
            throw new KevinException("To datetime cannot be earlier than from datetime.");
        }
    }

    /**
     * Format Event into String for saving into tasks.txt.
     */
    @Override
    public String formatSaveString() {
        return "E | " + super.formatSaveString() + " | " + formatDateTime(from)
                + " | " + formatDateTime(to);
    }

    @Override
    public String toString() {
        return "[E]" + super.toString() + " (from: " + formatDateTime(from)
                + " to: " + formatDateTime(to) + ")";
    }
}

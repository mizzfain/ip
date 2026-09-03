import java.time.LocalDateTime;

public class Event extends Task {
    private LocalDateTime start;
    private LocalDateTime end;

    public Event(String description, boolean isDone, LocalDateTime start, LocalDateTime end) {
        super(description, isDone);
        this.start = start;
        this.end = end;
    }

    public Event(String description, LocalDateTime start, LocalDateTime end) {
        super(description, false);
        this.start = start;
        this.end = end;
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

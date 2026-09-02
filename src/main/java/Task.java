import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task {
    protected String description;
    protected boolean isDone;

    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }

    public Task mark() {
        this.isDone = true;
        return this;
    }

    public Task unmark() {
        this.isDone = false;
        return this;
    }

    protected String formatDateTime(LocalDateTime dateTime) {
        String pattern = "d MMM yyyy ";

        if (dateTime.getMinute() == 0) {
            pattern += "ha";
        } else {
            pattern += "hmma";
        }

        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    public String formatSaveString() {
        if (isDone) {
            return "1 | " + description;
        } else {
            return "0 | " + description;
        }
    }

    @Override
    public String toString() {
        if (isDone) {
            return "[X] " + description;
        } else {
            return "[ ] " + description;
        }
    }
}

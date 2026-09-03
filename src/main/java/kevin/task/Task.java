package kevin.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Pattern;

public class Task {
    protected String description;
    protected boolean isDone;

    private static final Pattern PIPE_SPLITTER = Pattern.compile("\\s*\\|\\s*");

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    public Task(String description) {
        this(description,false);
    }

    public Task mark() {
        this.isDone = true;
        return this;
    }

    public Task unmark() {
        this.isDone = false;
        return this;
    }

    public boolean contains(String keyword) {
        return this.description.contains(keyword);
    }

    protected String formatDateTime(LocalDateTime dateTime) {
        String pattern = "d MMM yyyy ";

        if (dateTime.getMinute() == 0) {
            pattern += "ha";
        } else {
            pattern += "hmma";
        }

        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
    }

    public static LocalDateTime parseSavedDateTimeString(String dateTimeString) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().
                appendPattern("d MMM yyyy ").
                optionalStart().appendPattern("hmm").optionalEnd().
                optionalStart().appendPattern("h").optionalEnd().
                appendPattern("a").
                toFormatter(Locale.ENGLISH);
        return LocalDateTime.parse(dateTimeString, formatter);
    }


    public String formatSaveString() {
        if (isDone) {
            return "1 | " + description;
        } else {
            return "0 | " + description;
        }
    }

    public static Task fromFormatString(String formatString) {
        String[] parts = PIPE_SPLITTER.split(formatString);
        String type = parts[0];
        boolean isDone = parts[1].equals("1");
        String description = parts[2];

        Task task = switch (type) {
            case "T" -> new ToDo(description, isDone);
            case "D" -> new Deadline(description, isDone, parseSavedDateTimeString(parts[3]));
            case "E" -> new Event(description, isDone, parseSavedDateTimeString(parts[3]),
                    parseSavedDateTimeString(parts[4]));
            default -> new Task("Invalid task, can ignore");
        };

        return task;
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

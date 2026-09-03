package kevin.task;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Locale;
import java.util.regex.Pattern;

/**
 * Task is the parent class of all tasks.
 */
public class Task {
    private static final Pattern PIPE_SPLITTER = Pattern.compile("\\s*\\|\\s*");

    protected String description;
    protected boolean isDone;

    public Task(String description, boolean isDone) {
        this.description = description;
        this.isDone = isDone;
    }

    /**
     * Creates Task with default value of false for isDone.
     */
    public Task(String description) {
        this(description,false);
    }

    /**
     * Marks Task as done and returns it.
     */
    public Task mark() {
        isDone = true;
        return this;
    }
    /**
     * Unmarks Task and returns it.
     */
    public Task unmark() {
        isDone = false;
        return this;
    }

    public boolean contains(String keyword) {
        return this.description.contains(keyword);
    }

    /**
     * Formats LocalDateTime object into String
     * Outputs as d MMM yyyy hmma e.g 12 Apr 2026 1130am.
     * If minutes is 0, exclude the minutes eg 12 Apr 2026 12pm.
     */
    protected String formatDateTime(LocalDateTime dateTime) {
        String pattern = "d MMM yyyy ";

        if (dateTime.getMinute() == 0) {
            pattern += "ha";
        } else {
            pattern += "hmma";
        }

        return dateTime.format(DateTimeFormatter.ofPattern(pattern, Locale.ENGLISH));
    }

    /**
     * Helper Function in parseLine().
     * Parses DateTimeString from tasks.txt into LocalDateTime.
     * Accepts d MMM yyyy hmma e.g 12 Apr 2026 1230pm and without the minutes e.g 12 Apr 2026 1130am.
     */
    public static LocalDateTime parseSavedDateTimeString(String dateTimeString) {
        DateTimeFormatter formatter = new DateTimeFormatterBuilder().
                appendPattern("d MMM yyyy ").
                optionalStart().appendPattern("hmm").optionalEnd().
                optionalStart().appendPattern("h").optionalEnd().
                appendPattern("a").
                toFormatter(Locale.ENGLISH);
        return LocalDateTime.parse(dateTimeString, formatter);
    }

    /**
     * Formats Task into String for saving into tasks.txt.
     * Output is 1/0 | description where 1 is done and 0 is not done.
     */
    public String formatSaveString() {
        if (isDone) {
            return "1 | " + description;
        } else {
            return "0 | " + description;
        }
    }

    /**
     * Parses Task from line when loading tasks.txt.
     * @param line
     * @return Task
     */
    public static Task parseLine(String line) {
        String[] parts = PIPE_SPLITTER.split(line);
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

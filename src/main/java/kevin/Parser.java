package kevin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Parser Class that handles reading the user command.
 * Handles 1 String input at a time.
 */
public class Parser {
    private String input;

    public Parser(String input) {
        this.input = input;
    }

    /**
     * Checks if input is bye.
     */
    public boolean isNotBye() {
        return !input.equals("bye");
    }

    /**
     * Checks if input is list.
     */
    public boolean isList() {
        return input.equals("list");
    }

    /**
     * Checks if input starts with String command.
     * @param command
     */
    public boolean startsWith(String command) {
        return input.startsWith(command);
    }

    /**
     * Parses index from input of the form command index.
     * @param command
     * @return index
     * @throws KevinException If no index in input.
     */
    public int parseIndex(String command) throws KevinException {
        Pattern pattern = Pattern.compile(command + "\\s+(\\d+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return Integer.parseInt(matcher.group(1)) - 1;
        } else {
            throw new KevinException("Must include a task number.");
        }
    }

    public String parseKeyword() throws KevinException {
        Pattern pattern = Pattern.compile("^find\\s+(.+)");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return matcher.group(1);
        } else {
            throw new KevinException("No keyword to search for.");
        }
    }


    /**
     * Parses ToDo from input.
     * @return description
     * @throws KevinException If input does not have a description.
     */
    public String parseToDo() throws KevinException {
        Pattern pattern = Pattern.compile("^todo\\s+(?<description>.+?)$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return matcher.group("description");
        } else {
            throw new KevinException("ToDo does not have a description.");
        }
    }

    /**
     * Parses Deadline from input.
     * @return Matcher matcher contains description and by date.
     * @throws KevinException If input does not have a description or /by date.
     */
    public Matcher parseDeadline() throws KevinException {
        String regex = "^deadline\\s+(?<description>.+?)\\s+/by\\s+(?<by>.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return matcher;
        } else {
            throw new KevinException("Deadline does not have a description or a by date.");
        }
    }

    /**
     * Parses Event from input.
     * @return Matcher matcher contains description, from date and to date.
     * @throws KevinException If input does not have a description, /from date or /to date.
     */
    public Matcher parseEvent() throws KevinException {
        String regex = "^event\\s+(?<description>.+?)\\s+"
                + "/from\\s+(?<from>.+?)\\s+"
                + "/to\\s+(?<to>.+)$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return matcher;
        } else {
            throw new KevinException("Event does not have a description, from or to date.");
        }
    }
}

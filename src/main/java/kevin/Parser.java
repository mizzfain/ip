package kevin;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Parser {
    private String input;

    public Parser(String input) {
        this.input = input;
    }

    public boolean isNotBye() {
        return !input.equals("bye");
    }

    public boolean isList() {
        return input.equals("list");
    }

    public boolean startsWith(String start) {
        return input.startsWith(start);
    }

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

    public String parseToDo() throws KevinException {
        Pattern pattern = Pattern.compile("^todo\\s+(?<description>.+?)$");
        Matcher matcher = pattern.matcher(input);

        if (matcher.matches()) {
            return matcher.group("description");
        } else {
            throw new KevinException("ToDo does not have a description.");
        }
    }

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

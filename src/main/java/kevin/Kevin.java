package kevin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Function;
import java.util.regex.Matcher;

import kevin.task.Deadline;
import kevin.task.Event;
import kevin.task.Task;
import kevin.task.TaskList;
import kevin.task.ToDo;

/**
 * Chatbot named Kevin.
 * Handles all logic and runs the chatbot.
 */
public class Kevin {
    private Ui ui;
    private TaskList tasks;
    private Storage storage;

    /**
     * Creates Kevin.
     * Loads tasks from tasks.txt, or creates empty TaskList if does not exist.
     * @param filePath of tasks.txt.
     */
    public Kevin(String filePath) {
        this.ui = new Ui();
        this.storage = new Storage(filePath);
        try {
            this.tasks = storage.load();
        } catch (KevinException e) {
            this.tasks = new TaskList();
        }
    }

    /**
     * Runs the chatbot.
     */
    public void run() {
        Parser parser = new Parser(ui.start());
        boolean isNotBye = true;

        while (isNotBye) {
            String response = respond(parser);
            ui.print(response);
            if (parser.isBye()) {
                break;
            }
            parser = new Parser(ui.readNextLine());
        }
    }

    /**
     * Responds to input as a String.
     * @param parser
     */
    public String respond(Parser parser) {
        try {
            if (parser.isList()) {
                return tasks.list();

            } else if (parser.startsWith("mark")) {
                return handleMark(parser);

            } else if (parser.startsWith("unmark")) {
                return handleUnmark(parser);

            } else if (parser.startsWith("todo")) {
                return handleToDo(parser);

            } else if (parser.startsWith("deadline")) {
                return handleDeadline(parser);

            } else if (parser.startsWith("event")) {
                return handleEvent(parser);

            } else if (parser.startsWith("delete")) {
                return handleDelete(parser);

            } else if (parser.startsWith("find")) {
                String keyword = parser.parseKeyword();
                return tasks.find(keyword);

            } else if (parser.isBye()) {
                return "Bye. Hope I was of assistance to you!";
            } else {
                return "??? Sorry but I don't speak gibberish.\n";
            }
        } catch (KevinException e) {
            return e.getMessage() + '\n';
        }
    }

    /**
     * Handles mark commands.
     * Parses taskIndex, marks Task and saves updated TaskList.
     * @param parser
     * @return String response
     * @throws KevinException If input does not have a task number.
     */
    public String handleMark(Parser parser) throws KevinException {
        int taskIndex = parser.parseIndex("mark");
        Task markedTask = tasks.mark(taskIndex);
        storage.save(tasks);

        return "Nice! I've marked this task as done:\n  "
                + markedTask + "\n";
    }

    /**
     * Handles unmark commands.
     * Parses taskIndex, unmarks Task and saves updated TaskList.
     * @param parser
     * @return String response
     * @throws KevinException If input does not have a task number.
     */
    public String handleUnmark(Parser parser) throws KevinException {
        int taskIndex = parser.parseIndex("unmark");
        Task unmarkedTask = tasks.unmark(taskIndex);
        storage.save(tasks);

        return "OK, I've marked this task as not done yet:\n  "
                + unmarkedTask + "\n";
    }

    /**
     * Handles delete commands.
     * Parses taskIndex, deletes Task and saves updated TaskList.
     * @param parser
     * @return String response
     * @throws KevinException If input does not have a task number.
     */
    public String handleDelete(Parser parser) throws KevinException {
        int taskIndex = parser.parseIndex("delete");
        Task deletedTask = tasks.delete(taskIndex);
        storage.save(tasks);

        return "Noted. I've removed this task:\n  "
                + deletedTask + "\nNow you have " + tasks.size()
                + " tasks in the list.\n";
    }

    /**
     * Handles ToDo commands.
     * Parses description, adds ToDo to TaskList and saves updated TaskList.
     * @param parser
     * @return String response
     * @throws KevinException If input does not have a description or a /by date.
     */
    public String handleToDo(Parser parser) throws KevinException {
        String description = parser.parseToDo();
        ToDo todo = new ToDo(description);
        tasks.add(todo);
        storage.save(tasks);

        return "Got it. I've added this task:\n  " + todo
                + "\nNow you have " + tasks.size() + " tasks in the list.\n";
    }

    /**
     * Handles Deadline commands.
     * Parses description, by date, adds Deadline to TaskList and saves updated TaskList.
     * @param parser
     * @return String response
     * @throws KevinException If input does not have a description or a /by date.
     */
    public String handleDeadline(Parser parser) throws KevinException {
        Matcher matcher = parser.parseDeadline();

        String description = matcher.group("description");
        LocalDateTime byDateTime = parseDateTimeString(matcher.group("by"));
        Deadline deadline = new Deadline(description, byDateTime);

        tasks.add(deadline);
        storage.save(tasks);

        return "Got it. I've added this task:\n  " + deadline
                + "\nNow you have " + tasks.size() + " tasks in the list.\n";
    }

    /**
     * Handles Event commands.
     * Parses description, from and to date, adds Event to TaskList and saves updated TaskList.
     * @param parser
     * @return String response
     * @throws KevinException If input does not have a description, /from date or /to date.
     */
    public String handleEvent(Parser parser) throws KevinException {
        Matcher matcher = parser.parseEvent();

        String description = matcher.group("description");
        LocalDateTime from = parseDateTimeString(matcher.group("from"));
        LocalDateTime to = parseDateTimeString(matcher.group("to"));
        Event event = new Event(description, from, to);

        tasks.add(event);
        storage.save(tasks);

        return "Got it. I've added this task:\n  " + event
                + "\nNow you have " + tasks.size() + " tasks in the list.\n";
    }

    /**
     * Main entry point for chatbot.
     */
    public static void main(String[] args) throws KevinException {
        new Kevin("data/tasks.txt").run();
    }

    /**
     * Helper Function in run().
     * Parses DateTimeString from the user input into LocalDateTime.
     * @param dateTimeString
     * @return LocalDateTime
     * @throws KevinException If user input wrong format for date time.
     */
    public static LocalDateTime parseDateTimeString(String dateTimeString) throws KevinException {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[d/M/yy hmma][d/M/yy ha]");
            return LocalDateTime.parse(dateTimeString, formatter);
        } catch (Exception e) {
            throw new KevinException("Please input date time using D/M/YY Ham/pm or HMMam/pm");
        }
    }
}

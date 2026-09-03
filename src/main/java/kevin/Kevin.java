package kevin;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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

        while (parser.isNotBye()) {
            try {
                if (parser.isList()) {
                    tasks.list();
                    parser = new Parser(ui.readNextLine());

                } else if (parser.startsWith("mark")) {
                    int taskIndex = parser.parseIndex("mark");
                    Task markedTask = tasks.mark(taskIndex);

                    ui.print("Nice! I've marked this task as done:\n  "
                            + markedTask + "\n");

                    storage.save(tasks);
                    parser = new Parser(ui.readNextLine());

                } else if (parser.startsWith("unmark")) {
                    int taskIndex = parser.parseIndex("unmark");
                    Task unmarkedTask = tasks.unmark(taskIndex);

                    ui.print("OK, I've marked this task as not done yet:\n  "
                            + unmarkedTask + "\n");

                    storage.save(tasks);
                    parser = new Parser(ui.readNextLine());

                } else if (parser.startsWith("todo")) {
                    String description = parser.parseToDo();
                    ToDo todo = new ToDo(description);
                    tasks.add(todo);

                    ui.print("Got it. I've added this task:\n  " + todo
                            + "\nNow you have " + tasks.size() + " tasks in the list.\n");

                    storage.save(tasks);
                    parser = new Parser(ui.readNextLine());

                } else if (parser.startsWith("deadline")) {
                    Matcher matcher = parser.parseDeadline();

                    String description = matcher.group("description");
                    LocalDateTime byDateTime = parseDateTimeString(matcher.group("by"));

                    Deadline deadline = new Deadline(description, byDateTime);
                    tasks.add(deadline);

                    ui.print("Got it. I've added this task:\n  " + deadline
                            + "\nNow you have " + tasks.size() + " tasks in the list.\n");

                    storage.save(tasks);
                    parser = new Parser(ui.readNextLine());

                } else if (parser.startsWith("event")) {
                    Matcher matcher = parser.parseEvent();

                    String description = matcher.group("description");
                    LocalDateTime from = parseDateTimeString(matcher.group("from"));
                    LocalDateTime to = parseDateTimeString(matcher.group("to"));

                    Event event = new Event(description, from, to);
                    tasks.add(event);

                    ui.print("Got it. I've added this task:\n  " + event
                            + "\nNow you have " + tasks.size() + " tasks in the list.\n");

                    storage.save(tasks);
                    parser = new Parser(ui.readNextLine());

                } else if (parser.startsWith("delete")) {
                    int taskIndex = parser.parseIndex("delete");
                    Task deletedTask = tasks.delete(taskIndex);

                    ui.print("Noted. I've removed this task:\n  "
                            + deletedTask + "\nNow you have " + tasks.size()
                            + " tasks in the list.\n");

                    storage.save(tasks);
                    parser = new Parser(ui.readNextLine());

                } else if (parser.startsWith("find")) {
                    String keyword = parser.parseKeyword();
                    tasks.find(keyword);

                    parser = new Parser(ui.readNextLine());
                } else {
                    throw new KevinException("??? Sorry but I don't speak gibberish.");
                }
            } catch (KevinException e) {
                System.out.println(e.getMessage() + "\n");
                parser = new Parser(ui.readNextLine());
            }
        }

        ui.end();
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

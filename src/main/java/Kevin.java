import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Kevin {
    private Ui ui;
    private TaskList tasks;

    public static void main(String[] args) throws KevinException {
        Ui ui = new Ui();
        String filePath = "data/tasks.txt";
        Storage storage = new Storage(filePath);

        TaskList tasks = new TaskList();

        String input = ui.start();

        while (!input.equals("bye")) {
            try {
                if (input.equals("list")) {
                    tasks.list();

                    input = ui.readNextLine();
                } else if (input.startsWith("mark")) {
                    Pattern pattern = Pattern.compile("mark\\s+(\\d+)");
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
                        Task markedTask = tasks.mark(taskIndex);

                        System.out.println("Nice! I've marked this task as done:\n  "
                                + markedTask + "\n");

                        storage.save(tasks);
                        input = ui.readNextLine();
                    } else {
                        throw new KevinException("FAIL! Must include a task number.");
                    }
                } else if (input.startsWith("unmark")) {
                    Pattern pattern = Pattern.compile("unmark\\s+(\\d+)");
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
                        Task unmarkedTask = tasks.unmark(taskIndex);

                        System.out.println("Nice! I've marked this task as done:\n  "
                                + unmarkedTask + "\n");

                        storage.save(tasks);
                        input = ui.readNextLine();
                    } else {
                        throw new KevinException("FAIL! Must include a task number.");
                    }
                } else if (input.startsWith("todo")) {
                    String regex = "todo\\s+(?<description>.+?)$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        String description = matcher.group("description");

                        ToDo todo = new ToDo(description);
                        tasks.add(todo);
                        System.out.println("Got it. I've added this task:\n  " + todo
                                + "\nNow you have " + tasks.size() + " tasks in the list.\n");

                        storage.save(tasks);
                        input = ui.readNextLine();
                    } else {
                        throw new KevinException("FAIL! ToDo does not have a description.");
                    }
                } else if (input.startsWith("deadline")) {
                    String regex = "deadline\\s+(?<description>.+?)\\s+/by\\s+(?<by>.+)$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        String description = matcher.group("description");
                        String byString = matcher.group("by");
                        LocalDateTime byDateTime = parseDateTimeString(byString);

                        Deadline deadline = new Deadline(description, byDateTime);
                        tasks.add(deadline);
                        System.out.println("Got it. I've added this task:\n  " + deadline
                                + "\nNow you have " + tasks.size() + " tasks in the list.\n");

                        storage.save(tasks);
                        input = ui.readNextLine();
                    } else {
                        throw new KevinException("FAIL! Deadline does not have a description"
                                + " or a by date.");
                    }
                } else if (input.startsWith("event")) {
                    String regex = "event\\s+(?<description>.+?)\\s+"
                            + "/from\\s+(?<from>.+?)\\s+"
                            + "/to\\s+(?<to>.+)$";
                    Pattern pattern = Pattern.compile(regex);
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        String description = matcher.group("description");
                        LocalDateTime from = parseDateTimeString(matcher.group("from"));
                        LocalDateTime to = parseDateTimeString(matcher.group("to"));

                        Event event = new Event(description, from, to);
                        tasks.add(event);
                        System.out.println("Got it. I've added this task:\n  " + event
                                + "\nNow you have " + tasks.size() + " tasks in the list.\n");

                        storage.save(tasks);
                        input = ui.readNextLine();
                    } else {
                        throw new KevinException("FAIL! Event does not have a description," +
                                " from or to date.");
                    }
                } else if (input.startsWith("delete")) {
                    Pattern pattern = Pattern.compile("delete\\s+(\\d+)");
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
                        Task deletedTask = tasks.delete(taskIndex);

                        System.out.println("Noted. I've removed this task:\n  "
                                + deletedTask + "\nNow you have " + tasks.size()
                                + " tasks in the list.\n");

                        storage.save(tasks);
                        input = ui.readNextLine();
                    } else {
                        throw new KevinException("FAIL! Must include a task number.");
                    }
                } else {
                    throw new KevinException("??? Sorry but I don't speak gibberish.");
                }
            } catch (KevinException e) {
                System.out.println(e.getMessage() + "\n");
                input = ui.readNextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Must provide a valid task number.\n");
                input = ui.readNextLine();
            }
        }

        String ending = "Bye. Hope I was of assistance to you!";
        System.out.println(ending);
    }

    public static LocalDateTime parseDateTimeString(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[d/M/yy hmma][d/M/yy ha]");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}

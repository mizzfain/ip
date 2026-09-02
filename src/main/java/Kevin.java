import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.BufferedWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Kevin {
    public static void main(String[] args) throws KevinException {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> tasks = new ArrayList<Task>();

        String banner = "Hello! I'm Kevin.\n"
                + "What would you like me to help you with?\n";

        System.out.println(banner);

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            try {
                if (input.equals("list")) {
                    int counter = 1;
                    for (Task task : tasks) {
                        System.out.println(counter + "." + task);
                        counter++;
                    }
                    System.out.println();
                    input = scanner.nextLine();
                } else if (input.startsWith("mark")) {
                    Pattern pattern = Pattern.compile("mark\\s+(\\d+)");
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
                        Task task = tasks.get(taskIndex);

                        Task markedTask = task.mark();
                        tasks.set(taskIndex, markedTask);

                        System.out.println("Nice! I've marked this task as done:\n  "
                                + markedTask + "\n");

                        Kevin.saveFile(tasks);
                        input = scanner.nextLine();
                    } else {
                        throw new KevinException("FAIL! Must include a task number.");
                    }
                } else if (input.startsWith("unmark")) {
                    Pattern pattern = Pattern.compile("unmark\\s+(\\d+)");
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
                        Task task = tasks.get(taskIndex);

                        Task unmarkedTask = task.unmark();
                        tasks.set(taskIndex, unmarkedTask);

                        System.out.println("Nice! I've marked this task as done:\n  "
                                + unmarkedTask + "\n");

                        Kevin.saveFile(tasks);
                        input = scanner.nextLine();
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

                        Kevin.saveFile(tasks);
                        input = scanner.nextLine();
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

                        Kevin.saveFile(tasks);
                        input = scanner.nextLine();
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

                        Kevin.saveFile(tasks);
                        input = scanner.nextLine();
                    } else {
                        throw new KevinException("FAIL! Event does not have a description," +
                                " from or to date.");
                    }
                } else if (input.startsWith("delete")) {
                    Pattern pattern = Pattern.compile("delete\\s+(\\d+)");
                    Matcher matcher = pattern.matcher(input);

                    if (matcher.matches()) {
                        int taskIndex = Integer.parseInt(matcher.group(1)) - 1;
                        Task task = tasks.get(taskIndex);
                        tasks.remove(taskIndex);

                        System.out.println("Noted. I've removed this task:\n  "
                                + task + "\nNow you have " + tasks.size()
                                + " tasks in the list.\n");

                        Kevin.saveFile(tasks);
                        input = scanner.nextLine();
                    } else {
                        throw new KevinException("FAIL! Must include a task number.");
                    }
                } else {
                    throw new KevinException("??? Sorry but I don't speak gibberish.");
                }
            } catch (KevinException e) {
                System.out.println(e.getMessage() + "\n");
                input = scanner.nextLine();
            } catch (IndexOutOfBoundsException e) {
                System.out.println("Must provide a valid task number.\n");
                input = scanner.nextLine();
            }
        }

        String ending = "Bye. Hope I was of assistance to you!";
        System.out.println(ending);
    }

    public static void saveFile(ArrayList<Task> tasks) {
        Path dataPath = Path.of("data");
        Path filePath = dataPath.resolve("kevin.txt");

        try {
            if (Files.notExists(dataPath)) {
                Files.createDirectories(dataPath);
            }

            try (BufferedWriter writer = Files.newBufferedWriter(filePath)) {
                for (Task task : tasks) {
                    String taskString = task.toString();
                    writer.write(taskString);
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static LocalDateTime parseDateTimeString(String dateTimeString) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("[d/M/yy hmma][d/M/yy ha]");
        return LocalDateTime.parse(dateTimeString, formatter);
    }
}

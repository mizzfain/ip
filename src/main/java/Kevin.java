import java.util.Scanner;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Kevin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        ArrayList<Task> tasks = new ArrayList<Task>();

        String banner = "Hello! I'm Kevin.\n"
                + "What would you like me to help you with?\n";

        System.out.println(banner);

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            if (input.equals("list")) {
                int counter = 1;
                for (Task task : tasks) {
                    System.out.println(counter + "." + task);
                    counter++;
                }
                System.out.println();
                input = scanner.nextLine();
            } else if (input.startsWith("mark")) {
                int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                Task task = tasks.get(taskIndex);
                Task markedTask = task.mark();

                tasks.set(taskIndex, markedTask);
                System.out.println("Nice! I've marked this task as done:\n  "
                        + markedTask + "\n");

                input = scanner.nextLine();
            } else if (input.startsWith("unmark")) {
                int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                Task task = tasks.get(taskIndex);
                Task unmarkedTask = task.unmark();

                tasks.set(taskIndex, unmarkedTask);
                System.out.println("OK, I've marked this task as not done yet:\n  "
                        + unmarkedTask + "\n");

                input = scanner.nextLine();
            } else if (input.startsWith("todo")) {
                ToDo todo = new ToDo(input.substring(5));
                tasks.add(todo);
                System.out.println("Got it. I've added this task:\n  " + todo
                        + "\nNow you have " + tasks.size() + " tasks in the list.\n");
                input = scanner.nextLine();
            } else if (input.startsWith("deadline")) {
                String regex = "^deadline\\s+(?<description>.+?)\\s+/by\\s+(?<by>.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(input);

                if (matcher.matches()) {
                    String description = matcher.group("description");
                    String by = matcher.group("by");

                    Deadline deadline = new Deadline(description, by);
                    tasks.add(deadline);
                    System.out.println("Got it. I've added this task:\n  " + deadline
                            + "\nNow you have " + tasks.size() + " tasks in the list.\n");

                    input = scanner.nextLine();
                }
            } else if (input.startsWith("event")) {
                String regex = "^event\\s+(?<description>.+?)\\s+"
                        + "/from\\s+(?<from>.+?)\\s+"
                        + "/to\\s+(?<to>.+)$";
                Pattern pattern = Pattern.compile(regex);
                Matcher matcher = pattern.matcher(input);

                if (matcher.matches()) {
                    String description = matcher.group("description");
                    String from = matcher.group("from");
                    String to = matcher.group("to");

                    Event event = new Event(description, from, to);
                    tasks.add(event);
                    System.out.println("Got it. I've added this task:\n  " + event
                            + "\nNow you have " + tasks.size() + " tasks in the list.\n");

                    input = scanner.nextLine();
                }
            } else {
                tasks.add(new Task(input));
                System.out.println("added: " + input + "\n");
                input = scanner.nextLine();
            }
        }
        String ending = "Bye. Hope I was of assistance to you!";
        System.out.println(ending);
    }
}

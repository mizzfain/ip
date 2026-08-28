import java.util.Scanner;
import java.util.ArrayList;

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

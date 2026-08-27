import java.util.Scanner;

public class Kevin {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        String banner = "Hello! I'm Kevin.\n"
                + "What would you like me to help you with?\n";

        System.out.println(banner);

        String input = scanner.nextLine();

        while (!input.equals("bye")) {
            System.out.println(input + "\n");
            input = scanner.nextLine();
        }
        String ending = "Bye. Hope I was of assistance to you!";
        System.out.println(ending);
    }
}

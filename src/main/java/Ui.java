import java.util.Scanner;

public class Ui {
    private Scanner scanner;

    private static final String START_BANNER =
            "Hello! I'm Kevin.\nWhat would you like me to help you with?\n";
    private static final String END_BANNER = "Bye. Hope I was of assistance to you!";

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readNextLine() {
        return scanner.nextLine();
    }

    public void printOutput(String output) {
        System.out.println(output);
    }

    public String start() {
        System.out.println(START_BANNER);
        return readNextLine();
    }

    public void end() {
        System.out.println(END_BANNER);
    }
}

package kevin;

import java.util.Scanner;

public class Ui {
    private static final String START_BANNER =
            "Hello! I'm Kevin.\nWhat would you like me to help you with?\n";
    private static final String END_BANNER = "Bye. Hope I was of assistance to you!";

    private Scanner scanner;

    public Ui() {
        this.scanner = new Scanner(System.in);
    }

    public String readNextLine() {
        return scanner.nextLine();
    }

    public void print(String output) {
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
